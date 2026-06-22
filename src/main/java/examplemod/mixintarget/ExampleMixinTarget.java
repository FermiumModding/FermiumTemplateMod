package examplemod.mixintarget;

import examplemod.ExampleMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

// Target class for mixin examples. This is just some garbage to have smth to hook into
public class ExampleMixinTarget {

    // ------ This part is called <clinit>, ran on first class load ------

    private static String staticFieldToShadow = "secret";

    static {
        staticFieldToShadow = "actuallySecret";
    }

    // ------ This part is called <init>, ran on object construction ------

    private final int fieldToShadow;

    public ExampleMixinTarget(int num) {
        this.fieldToShadow = num;
    }

    // ----------- normal class from here -----------

    private void methodToShadow(){}
    private static void staticMethodToShadow(){}

    // Method to be completely overwritten by the mixin. Avoid to Overwrite at all cost
    public String methodToOverwrite() {
        return "Original implementation - will be overwritten";
    }

    public static float targetStaticMethod() {
        callToModifyArg(1, 2, 3);

        int localVariable = ExampleMixinTarget.staticFieldToShadow.length();

        return localVariable;
    }

    public int targetExampleMethod(int input, String text) {
        callToWrapWithCondition(1, 2);

        String localString = callToWrapOperation("a", true);

        return localString.length();
    }

    private static void callToModifyArg(int i, int j, int k) {
    }

    private void callToWrapWithCondition(int x, int y) {
    }

    public String callToWrapOperation(String a, boolean b) {
        return staticFieldToShadow;
    }

    @Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EventHandler {
        @SubscribeEvent
        public static void onLoadComplete(FMLLoadCompleteEvent event) {
            //This is just to force load the class so the mixin can be applied
        }
    }
}