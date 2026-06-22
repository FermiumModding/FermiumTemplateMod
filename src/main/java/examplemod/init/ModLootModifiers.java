package examplemod.init;

import com.mojang.serialization.Codec;
import examplemod.ExampleMod;
import examplemod.loot.ExampleLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ExampleMod.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> EXAMPLE_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("example_loot_modifier", ExampleLootModifier.CODEC);
}
