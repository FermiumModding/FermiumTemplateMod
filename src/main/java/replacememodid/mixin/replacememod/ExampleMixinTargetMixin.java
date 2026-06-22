package replacememodid.mixin.replacememod;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import replacememodid.ReplaceMeModName;
import replacememodid.mixintarget.ExampleMixinTarget;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin examples demonstrating most injectors.
 * Targets this mods own ExampleMixinTarget class to avoid breaking anything.
 *
 * If you're using Mixins, get the <a href="https://mcdev.io/">mcdev plugin for intellij</a> i beg you
 * Check the results of your mixins in /run/.mixin.out/class
 *
 * Not covered here:
 * - @Local for grabbing or modifying any local variable
 * - @Share to add your own local variables
 * - how to use interfaces to interact with added fields/methods from outside the mixin
 * - casting with (TargetClass)(Object) mixinClassObject
 * - @Invoker and @Accessor
 * - @WrapMethod - mostly not needed, can usually be done with @Inject at HEAD
 * - @ModifyReceiver - rare injector to modify obj in obj.func()
 * - targeting multiple things (check which annotation parameters allow arrays, you'll be surprised)
 * - require = ... to fail hard if injections fail
 * - @Slice for more precise targeting
 * - @Expression for almost arbitrary injection points
 * - OuterClass this0 for targeting the object of the enclosing class
 * - @Cancelable injects a CallbackInfo to return anywhere
 */

@Mixin(value = ExampleMixinTarget.class, //private classes can be targeted using targets = "path.to.Class"
// DON'T DO THIS:
remap = false  // <-- this would apply to all other annotations inside this class if you set it here.
)
// Remap tutorial:
// Mixins tries to automatically remap vanilla field+method names.
// You will have to disable that for forge+modded targets, by putting remap=false.
// BUT
// If you put one too many remap=false at the wrong spot, your mixin won't apply SILENTLY and you will wonder why it doesn't work.
//
// ----> Only add remap=false if the compiler complains! <----
//   red error: put @SomeInjector(method=..., remap=false)
//   yellow warn: put ..., at = @At(value=..., remap=false)
public abstract class ExampleMixinTargetMixin {

    /**
     * @Shadow - Provides access to private fields in the target class.
     * Reads or modify private fields that aren't normally accessible.
     * Must match the exact field name and type from target class.
     *
     * This only gives you access inside this mixin class.
     * For access from other classes, use @Accessor and @Invoker
     */
    @Shadow private static String staticFieldToShadow;

    //You can add mutable to make final fields mutable again
    @Final @Mutable
    @Shadow private int fieldToShadow; // works on non-static fields too

    // and for methods
    @Shadow protected abstract void methodToShadow();

    // shadowing static methods requires a method body, which won't be injected
    @Shadow private static void staticMethodToShadow() {
        throw new AssertionError("Failed to @Shadow ExampleMixinTarget.getSecretValue()");
    }

    /**
     * @Inject - Execute your own code wherever you want.
     * Parameters are the original mods parameters
     * and a CallBackInfo or CallBackInfoReturnable<ReturnType> to early return in the target method
     */
    @Inject(
            method = "<init>",
            at = @At("TAIL") // Mixins is worried about you injecting anywhere else than the end of the constructor
    )
    private void replacememodid_inject(int num, CallbackInfo ci){
        //you can't do this in constructors but it would basically write: return;
        //ci.cancel();
    }

    /**
     * @ModifyConstant - Modifies constants in the target code.
     * Works on any primitive data type or classes (typically for instanceof someClass)
     */
    @ModifyConstant(
            method = "<clinit>", //class init, the static{..} block and static field inits
            constant = @Constant(stringValue = "actuallySecret")
    )
    private static String replacememodid_modifyConstant(String original) {
        return original; //modify here
    }

    /**
     * @ModifyArg - Modifies one argument of a method call within the target method.
     * You can target arguments by their type or by their index.
     * <p>
     * Use @ModifyArgs with Args.set(idx, Args.get(idx))
     * to modify multiple arguments at the same time
     */
    @ModifyArg(
            method = "targetStaticMethod",
            at = @At(
                    value = "INVOKE",
                    target = "Lreplacememodid/mixintarget/ExampleMixinTarget;callToModifyArg(III)V"
            ),
            index = 1 // middle parameter
    )
    private static int replacememodid_modifyArg(int j) {
        return j;
    }

    /**
     * @ModifyExpressionValue - Modifies the result of an expression.
     * Expressions are method calls, field reads
     * using @Expression's they can be almost anything
     */
    @ModifyExpressionValue(
            method = "targetStaticMethod",
            at = @At(value = "FIELD", target = "Lreplacememodid/mixintarget/ExampleMixinTarget;staticFieldToShadow:Ljava/lang/String;")
    )
    private static String replacememodid_modifyExpressionValue(String original) {
        return original; //modify here
    }

    /**
     * @ModifyVariable - Captures and modifies any local variables anywhere,
     * no matter if they are the targeted methods parameters or fully local.
     *
     * It just injects a line targetVar = modifyTargetVar(targetVar)
     *
     * Special injection points: STORE and LOAD.
     * To inject where the targeted variable itself is modified (after-STORE) or accessed (before-LOAD)
     */
    @ModifyVariable(
            method = "targetStaticMethod",
            at = @At(value = "STORE", ordinal = 0), // First instance of localVariable being written to
            name = "localVariable" //you can name variables using their name, ordinal (of that type) or index (in local variable table)
    )
    private static int replacememodid_modifyVariable(int localVar) {
        return localVar; //modify here
    }

    /**
     * @ModifyReturnValue - Modifies the value returned by a method.
     * Captures the value right before it's returned.
     * If method has multiple returns, this captures all of them.
     */
    @ModifyReturnValue(
            method = "targetStaticMethod",
            at = @At(value = "RETURN", ordinal = 0) // if no ordinal, this will target ALL return statements
    )
    private static float replacememodid_modifyReturnValue(float original) {
        return original; // Modify here
    }

    /**
     * @WrapWithCondition - Only call if you want to
     * Only works on methods returning void. If you return false, the method won't be called
     * Simpler than @WrapOperation when you only need conditional execution.
     */
    @WrapWithCondition(
            method = "targetExampleMethod",
            at = @At(value = "INVOKE", target = "Lreplacememodid/mixintarget/ExampleMixinTarget;callToWrapWithCondition(II)V")
    )
    private boolean replacememodid_wrapWithCondition(ExampleMixinTarget instance, int x, int y) {
        return true; //false to not run method
    }

    /**
     * @WrapOperation - Wraps a method call or field access.
     * This allows you to run code before, after or instead of the original call.
     * Or modify its arguments or result (=expression value)
     * Run original.call(params) to let the wrapped code run
     */
    @WrapOperation(
            method = "targetExampleMethod",
            at = @At(value = "INVOKE", target = "Lreplacememodid/mixintarget/ExampleMixinTarget;callToWrapOperation(Ljava/lang/String;Z)Ljava/lang/String;")
    )
    private String replacememodid_wrapOperation(ExampleMixinTarget instance, String a, boolean b, Operation<String> original) {
        //This would normally be an @Inject
        ReplaceMeModName.LOGGER.info("@WrapOperation: Before helperMethod()");

        // Call the original method
        String originalResult = original.call(instance, a, b); //you can @ModifyArgs here directly

        // This would be an @Inject with shift=AFTER
        ReplaceMeModName.LOGGER.info("@WrapOperation: After helperMethod()");

        // You can @ModifyExpressionValue here
        return originalResult;
    }

    /**
     * @Overwrite - Completely replaces the target method with this implementation.
     * WARNING: DANGEROUS! Creates hard conflicts with other mods.
     * - Any other mod trying to modify this method will crash or break
     * - Only do this if you WANT to break with other mods wanting
     *   to modify anything in this method
     * - Document WHY you need this
     * - For normal modifications use the other injectors
     *
     * @author ExampleAuthor
     * @reason This is just an example, but i'm shivering anyway
     */
    @Overwrite
    public String methodToOverwrite() {
        return "Completely replaced implementation";
    }

    /**
     * The other thing you shouldn't do...
     *
     * @Redirect - Replaces a specific method call or field access with your own code
     * <p>
     * WARNING: Invasive. Will crash if multiple mods redirect same call.
     * Only do this if you WANT to conflict with other mods targeting these methods or fields.
     * <p>
     * For intermod compatibility you can always replace @Redirect with either @WrapOperation or @ModifyExpressionValue
     */
    @Redirect(
            method = "targetExampleMethod",
            at = @At(value = "INVOKE", target = "Ljava/lang/String;length()I")
    )
    private int replacememodid_redirect(String instance) {
        return instance.length(); //same call as originally, but now we conflict :)
    }
}