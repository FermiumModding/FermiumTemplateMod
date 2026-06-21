package examplemod.config;

import fermiumbooter.api.MixinToggle;
import fermiumbooter.api.FailureAction;
import fermiumbooter.api.MixinConfig;
import fermiumbooter.api.CompatHandling;

import java.util.Arrays;
import java.util.List;

/**
 * Example mixin toggle configuration.
 * This class demonstrates how to use the conditional mixin loading API.
 */
@MixinConfig
public class MixinToggles {

    /**
     * Simple boolean toggle for vanilla player mixins.
     * When true, loads mixins from mixins.examplemod.vanilla.json
     */
    @MixinToggle(
            mixinJson = "mixins.examplemod.vanilla.json",
            onFailure = FailureAction.WARN,
            failureMessage = "Vanilla player tweaks are disabled in config",
            comment = "Simple boolean toggle for vanilla mixins. Set to false to disable."
    )
    public static boolean enableVanillaPlayerTweaks = true;

    /**
     * Additional mod dependency check.
     * Only loads if enabled and JEI is present with version >= 11.0.
     */
    @MixinToggle(
            mixinJson = "mixins.examplemod.jei.json",
            dependencies = {@CompatHandling(modid = "jei", versionRange = "[11.0,)")},
            onFailure = FailureAction.WARN,
            failureMessage = "Requires JEI version 11.0 or higher",
            comment = "Example mod dependency toggle. Requires JEI 11.0+. Set to false to disable"
    )
    public static boolean enableJeiFeatures = true;

    /**
     * Toggles can be any primitive type, like int
     * Set to -1 to disable the mixin.
     * Any other value enables it.
     */
    @MixinToggle(
            mixinJson = "mixins.examplemod.somefeature.json",
            disableWhen = "-1",
            onFailure = FailureAction.WARN,
            comment = "Integer toggle with special disable value. Set to -1 to disable, any other value enables."
    )
    public static int someInt = 64;

    /**
     * They can even be Lists.
     * Set to empty list [] to disable the mixin.
     */
    @MixinToggle(
            mixinJson = "mixins.examplemod.someotherfeature.json",
            disableWhen = "[]", //empty list, disablestate can be different
            onFailure = FailureAction.WARN,
            comment = "String list toggle. Set to empty list [] to disable the mixin."
    )
    public static List<String> someList = Arrays.asList("minecraft:diamond", "minecraft:emerald");
}