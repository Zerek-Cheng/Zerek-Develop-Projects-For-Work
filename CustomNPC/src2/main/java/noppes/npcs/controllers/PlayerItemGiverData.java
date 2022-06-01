package noppes.npcs.controllers;

import java.util.*;
import noppes.npcs.*;
import net.minecraft.nbt.*;
import noppes.npcs.roles.*;

public class PlayerItemGiverData
{
    private HashMap<Integer, Long> itemgivers;
    private HashMap<Integer, Integer> chained;
    
    public PlayerItemGiverData() {
        this.itemgivers = new HashMap<Integer, Long>();
        this.chained = new HashMap<Integer, Integer>();
    }
    
    public void loadNBTData(final NBTTagCompound compound) {
        this.chained = NBTTags.getIntegerIntegerMap(compound.getTagList("ItemGiverChained", 10));
        this.itemgivers = NBTTags.getIntegerLongMap(compound.getTagList("ItemGiversList", 10));
    }
    
    public void saveNBTData(final NBTTagCompound compound) {
        compound.setTag("ItemGiverChained", (NBTBase)NBTTags.nbtIntegerIntegerMap(this.chained));
        compound.setTag("ItemGiversList", (NBTBase)NBTTags.nbtIntegerLongMap(this.itemgivers));
    }
    
    public boolean hasInteractedBefore(final JobItemGiver jobItemGiver) {
        return this.itemgivers.containsKey(jobItemGiver.itemGiverId);
    }
    
    public long getTime(final JobItemGiver jobItemGiver) {
        return this.itemgivers.get(jobItemGiver.itemGiverId);
    }
    
    public void setTime(final JobItemGiver jobItemGiver, final long day) {
        this.itemgivers.put(jobItemGiver.itemGiverId, day);
    }
    
    public int getItemIndex(final JobItemGiver jobItemGiver) {
        if (this.chained.containsKey(jobItemGiver.itemGiverId)) {
            return this.chained.get(jobItemGiver.itemGiverId);
        }
        return 0;
    }
    
    public void setItemIndex(final JobItemGiver jobItemGiver, final int i) {
        this.chained.put(jobItemGiver.itemGiverId, i);
    }
}
