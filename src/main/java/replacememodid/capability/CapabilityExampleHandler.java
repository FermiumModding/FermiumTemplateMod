package replacememodid.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import replacememodid.ReplaceMeModName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This class handles interaction of the capability with everything else
 */
public class CapabilityExampleHandler {
    public static final ResourceLocation CAP_EXAMPLE_KEY = new ResourceLocation(ReplaceMeModName.MODID, "example");

    @CapabilityInject(ICapabilityExample.class)
    public static Capability<ICapabilityExample> CAP_EXAMPLE;

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(ICapabilityExample.class, new Storage(), CapabilityExample::new); //Note: the factory here in the last parameter is only used in Capability.getDefaultImplementation which has no default usage. Otherwise the Provider is used
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
    }

    /**
     * Attaching the capability and various ways of interaction
     */
    public static class EventHandler {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if(!ReplaceMeModName.completedLoading) return; //only needed when other mods instantiate objects during startup that would get the capability (example: JEI creating ItemStacks)

            if(event.getObject().hasCapability(CAP_EXAMPLE, null)) return;

            //Filter down as far as possible here, to only give those objects your capability that actually should have it

            event.addCapability(CAP_EXAMPLE_KEY, new Provider(event.getObject() /*, various constructor parameters*/));
        }

        @SubscribeEvent
        public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
            ICapabilityExample cap = event.getEntity().getCapability(CapabilityExampleHandler.CAP_EXAMPLE, null);
            if(cap == null) return;

            cap.tickExampleData();
        }
    }

    /**
     * creating instances of the capability
     */
    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
        private final ICapabilityExample instance;

        public Provider(Entity entity /*, add various constructor parameters here*/) {
            this.instance = new CapabilityExample(entity /*, constructor parameters*/);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CAP_EXAMPLE;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CAP_EXAMPLE ? CAP_EXAMPLE.cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return (NBTTagCompound) CAP_EXAMPLE.writeNBT(instance, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            CAP_EXAMPLE.readNBT(instance, null, nbt);
        }
    }

    /**
     * NBT handling for the capability (read/write)
     */
    public static class Storage implements Capability.IStorage<ICapabilityExample> {
        //Doesn't need to redirect to capability-internal handling if all the relevant data is accessible from outside

        @Override
        public NBTBase writeNBT(Capability<ICapabilityExample> capability, ICapabilityExample instance, EnumFacing side) {
            return instance.writeToNBT();
            //Note: ItemStacks don't sync capability NBT between server + client, need to do custom handling for that (either via the get/readNBTShareTag or custom packets)
        }

        @Override
        public void readNBT(Capability<ICapabilityExample> capability, ICapabilityExample instance, EnumFacing side, NBTBase nbt) {
            instance.readFromNBT((NBTTagCompound) nbt);
        }
    }
}
