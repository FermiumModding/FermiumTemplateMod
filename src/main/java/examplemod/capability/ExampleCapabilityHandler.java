package examplemod.capability;

import examplemod.ExampleMod;
import examplemod.init.ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

//Handles capability registration and attachment. Includes a basic gameplay interaction
public class ExampleCapabilityHandler {

    //You can also register conditionally using ExampleMod.MOD_EVENT_BUS.register(ExampleCapabilityHandler.Registration.class) instead of the annotation
    @Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.MOD)
    public static class Registration {
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(ExampleCapability.IExampleCapability.class);
        }
    }

    @Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Bus.FORGE)
    public static class Attachment {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            Entity entity = event.getObject();

            //EXAMPLE: only on example_entity
            if (entity.getType() == ModEntities.EXAMPLE_ENTITY.get()) {
                ExampleCapability.Provider provider = new ExampleCapability.Provider(true);

                event.addCapability(new ResourceLocation(ExampleMod.MODID, ExampleCapability.EXAMPLE_CAP_NAME), provider);

                // Invalidation callback on object deletion, seems to be important
                event.addListener(provider::invalidate);
            }
        }

        // EXAMPLE interaction
        @SubscribeEvent
        public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
            Entity entity = event.getTarget();

            if (entity.getType() != ModEntities.EXAMPLE_ENTITY.get()) return;

            // Get the capability from the capability holder and do smth with it
            entity.getCapability(ExampleCapability.EXAMPLE_CAP).ifPresent(cap -> {
                cap.incrementCounter();

                if (event.getLevel().isClientSide)
                    event.getEntity().displayClientMessage(Component.literal("Example Entity clicked " + cap.getCounter() + " times!"), true);} //true = status msg instead of chat
            );
        }
    }
}