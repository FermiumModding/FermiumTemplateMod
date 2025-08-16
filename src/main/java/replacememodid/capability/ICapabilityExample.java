package replacememodid.capability;

import net.minecraft.nbt.NBTTagCompound;

/**
 * This is the actual capability. Expose various ways to interact with the data stored in your implementation
 */
public interface ICapabilityExample {
    void tickExampleData(); // just some example method

    NBTTagCompound writeToNBT();
    void readFromNBT(NBTTagCompound nbt);
}
