package examplemod.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//Capabilities allow you to attach data and functionality to entities, items, chunks, etc
public class ExampleCapability {

    // Use to get+attach the cap
    public static final Capability<IExampleCapability> EXAMPLE_CAP = CapabilityManager.get(new CapabilityToken<>(){});
    public static final String EXAMPLE_CAP_NAME = "example_capability";

    // Functionality
    public interface IExampleCapability {
        int getCounter();
        void setCounter(int value);
        void incrementCounter();
    }

    // Default implementation
    public static class Implementation implements IExampleCapability {
        private int counter = 0;

        public Implementation(boolean someFlag) {}

        @Override
        public int getCounter() {
            return counter;
        }

        @Override
        public void setCounter(int value) {
            this.counter = value;
        }

        @Override
        public void incrementCounter() {
            this.counter++;
        }
    }

    // Provider + NBT save&load
    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        private final IExampleCapability capability;
        private final LazyOptional<IExampleCapability> lazyOptional;

        //You can modify this constructor to pass data (of the holding object) to the cap
        public Provider(boolean someFlag) {
            capability = new Implementation(someFlag);
            lazyOptional = LazyOptional.of(() -> capability);
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == EXAMPLE_CAP) return lazyOptional.cast();
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() { // Save
            CompoundTag tag = new CompoundTag();
            tag.putInt("counter", capability.getCounter());
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) { // Load
            capability.setCounter(nbt.getInt("counter"));
        }

        //Gets auto called when capability holding object is removed
        public void invalidate() {
            lazyOptional.invalidate();
        }
    }
}