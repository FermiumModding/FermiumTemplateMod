package examplemod.mixin.jei;

import examplemod.ExampleMod;
import mezz.jei.forge.JustEnoughItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JustEnoughItems.class)
public abstract class JustEnoughItemsMixin {

    @Inject(
            method = "<init>",
            at = @At("TAIL"),
            remap = false
    )
    public void examplemod_jeiMain_init(CallbackInfo ci) {
        ExampleMod.LOGGER.info("JEI Init");
    }
}