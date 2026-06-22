package examplemod.init;

import examplemod.ExampleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ExampleMod.MODID);

    public static final RegistryObject<SoundEvent> EXAMPLE_SOUND = SOUND_EVENTS.register("example_sound",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ExampleMod.MODID, "example_sound")));
}