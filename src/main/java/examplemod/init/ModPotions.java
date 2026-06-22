package examplemod.init;

import examplemod.ExampleMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, ExampleMod.MODID);

    public static final RegistryObject<Potion> EXAMPLE_POTION = POTIONS.register("example",
            () -> new Potion(new MobEffectInstance(ModEffects.EXAMPLE_EFFECT.get(), 3600, 0)));

//    //Uncomment for additional potion variants:
//    public static final RegistryObject<Potion> LONG_EXAMPLE_POTION = POTIONS.register("long_example",
//            () -> new Potion(new MobEffectInstance(ModEffects.EXAMPLE_EFFECT.get(), 9600, 0)));
//
//    public static final RegistryObject<Potion> STRONG_EXAMPLE_POTION = POTIONS.register("strong_example",
//            () -> new Potion(new MobEffectInstance(ModEffects.EXAMPLE_EFFECT.get(), 1800, 1)));
}