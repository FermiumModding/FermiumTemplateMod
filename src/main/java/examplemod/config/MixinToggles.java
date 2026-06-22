package examplemod.config;

import fermiumbooter.api.CompatHandling;
import fermiumbooter.api.FailureAction;
import fermiumbooter.api.MixinConfig;
import fermiumbooter.api.MixinToggle;

import java.util.Arrays;
import java.util.List;

@MixinConfig
@SuppressWarnings("unused")
public class MixinToggles {

    @MixinToggle(
            mixinJson = "mixins.examplemod.examples.json",
            comment = "Mixin examples showing most injector types. Can be disabled here, but has no effect anyway."
    )
    public static boolean enableMixinExamples = true;

    @MixinToggle(
            mixinJson = "mixins.examplemod.somefeature.json",
            disableWhen = "-1",
            comment = "int toggle. Set to -1 to disable the mixin."
    )
    public static int someInt = 64;

    @MixinToggle(
            mixinJson = "mixins.examplemod.someotherfeature.json",
            disableWhen = "[]", //empty list, disable state can be anything
            comment = "String list toggle. Set to empty list [] to disable the mixin."
    )
    public static List<String> someList = Arrays.asList("minecraft:diamond", "minecraft:emerald");

    @MixinToggle(
            mixinJson = "mixins.examplemod.jei.json",
            dependencies = {@CompatHandling(modid = "jei", versionRange = "[11.0,)")},
            onFailure = FailureAction.WARN,
            failureMessage = "Requires JEI version 11.0 or higher",
            comment = "Example mod dependency toggle. Requires JEI 11.0+. Set to false to disable the mixin"
    )
    public static boolean enableJeiFeatures = true;
}