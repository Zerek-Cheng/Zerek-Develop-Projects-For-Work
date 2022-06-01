package noppes.npcs.controllers;

import net.minecraft.nbt.*;

public class QuestData
{
    public Quest quest;
    public boolean isCompleted;
    public NBTTagCompound extraData;
    
    public QuestData(final Quest quest) {
        this.extraData = new NBTTagCompound();
        this.quest = quest;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setBoolean("QuestCompleted", this.isCompleted);
        nbttagcompound.setTag("ExtraData", (NBTBase)this.extraData);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        this.isCompleted = nbttagcompound.getBoolean("QuestCompleted");
        this.extraData = nbttagcompound.getCompoundTag("ExtraData");
    }
}
