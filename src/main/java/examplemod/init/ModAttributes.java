package examplemod.init;

import examplemod.ExampleMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ExampleMod.MODID);

    public static final RegistryObject<Attribute> EXAMPLE_ATTRIBUTE = ATTRIBUTES.register("example_attribute",
            () -> new RangedAttribute("example_attribute", 0, -1024, 1024).setSyncable(true));
}