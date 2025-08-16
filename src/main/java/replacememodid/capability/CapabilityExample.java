package replacememodid.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This is your implementation of the ICapability. All handling should be encapsulated and only exposed via the methods of ICapability
 */
public class CapabilityExample implements ICapabilityExample {
    private final Entity entity; //Can be TileEntity, Entity, Village, World, Chunk, ItemStack

    private int exampleData = 0;

    //Only for Capability.getDefaultImplementation, which should only be used if the capability is owner-agnostic (so if there's no need to store entity here)
    public CapabilityExample(){
        this(null);
    }

    public CapabilityExample(Entity entity /*, constructor parameters*/) {
        this.entity = entity;
    }

    @Override
    public void tickExampleData() {
        this.exampleData++;
    }

    @Override
    public NBTTagCompound writeToNBT() {
        //For large data, implement caching here, using a markDirty system. Only write the data to NBT if it has changed, otherwise use the cached NBTTagCompound
        NBTTagCompound tags = new NBTTagCompound();
        tags.setInteger("example", this.exampleData);
        return tags;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.exampleData = nbt.getInteger("example");
    }
}
