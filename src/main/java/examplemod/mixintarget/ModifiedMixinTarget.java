package examplemod.mixintarget;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import examplemod.ExampleMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This is the ExampleMixinTarget class as it will behave like after the mixin is applied
 * Simplified and commentated for educational purposes. To get the actual output check /run/.mixin.out/class
 */
public class ModifiedMixinTarget {
    // --- <clinit> ---

    private static String staticFieldToShadow = "secret";

    static {
        staticFieldToShadow = constant$examplemod_modifyConstant("actuallySecret");
    }

    // --- <init> ---

//    @Final
    private int fieldToShadow;

    public ModifiedMixinTarget(int num) {
        this.fieldToShadow = num;
        CallbackInfo ci = new CallbackInfo("<init>", true);
        this.handler$examplemod_inject(num, ci);
        if (!ci.isCancelled()) {
            ;
        }
    }

    // --- Rest ---

    private void methodToShadow() {
    }

    private static void staticMethodToShadow() {
    }

    // --- Overwritten ---
    public String methodToOverwrite() {
        return "Completely replaced implementation";
    }

    public static float targetStaticMethod() {
        //ModifyArg
        callToModifyArg(1, modify$examplemod_modifyArg(2), 3);

        //@MdoifyExpressionValue of a static field
        int localVariable = modifyExpressionValue$examplemod_modifyExpressionValue(staticFieldToShadow).length();

        //@ModifyVariable injects directly after localVariable=... (STORE)
        localVariable = localvar$examplemod_modifyVariable(localVariable);

        //@ModifyReturnValue
        return modifyReturnValue$examplemod_modifyReturnValue((float) localVariable);
    }

    public int targetExampleMethod(int input, String text) {
        //@WrapWithCondition decides if the call happens
        if (this.wrapWithCondition$examplemod_wrapWithCondition((ExampleMixinTarget)(Object)this, 1, 2))
            this.callToWrapWithCondition(1, 2);

        //@WrapOperation can do whatever it wants
        String localString = this.wrapOperation$examplemod_wrapOperation((ExampleMixinTarget)(Object)this, "a", true,
                //It looks weird but this is the original call, given to WrapOp as a function handle
                args -> ((ExampleMixinTarget) args[0] ).callToWrapOperation((String) args[1], (Boolean)args[2])
        );

        //@Redirect removed the original .length() and replaced it with its own call
        return this.redirect$examplemod_redirect(localString);
    }

    private static void callToModifyArg(int i, int j, int k) {}
    private void callToWrapWithCondition(int x, int y) {}
    public String callToWrapOperation(String a, boolean b) {return staticFieldToShadow;}

    // ------ INJECTED METHODS ------

    private void handler$examplemod_inject(int num, CallbackInfo ci) {
        ci.cancel();
    }

    private static String constant$examplemod_modifyConstant(String original) {
        return original;
    }

    private static int modify$examplemod_modifyArg(int j) {
        return j;
    }

    private static String modifyExpressionValue$examplemod_modifyExpressionValue(String original) {
        return original;
    }

    private static int localvar$examplemod_modifyVariable(int localVar) {
        return localVar;
    }

    private static float modifyReturnValue$examplemod_modifyReturnValue(float original) {
        return original;
    }

    private boolean wrapWithCondition$examplemod_wrapWithCondition(ExampleMixinTarget instance, int x, int y) {
        return true;
    }

    public String wrapOperation$examplemod_wrapOperation(ExampleMixinTarget instance, String a, boolean b, Operation<String> original) {
        ExampleMod.LOGGER.info("@WrapOperation: Before helperMethod()");
        String originalResult = original.call(instance, a, b);
        ExampleMod.LOGGER.info("@WrapOperation: After helperMethod()");
        return originalResult;
    }

    private int redirect$examplemod_redirect(String instance) {
        return instance.length();
    }
}
