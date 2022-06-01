package noppes.npcs.controllers;

import net.minecraft.util.*;
import java.util.*;
import noppes.npcs.*;
import net.minecraft.nbt.*;

public class SpawnData extends WeightedRandom.Item
{
    public List<String> biomes;
    public int id;
    public String name;
    public NBTTagCompound compound1;
    public boolean liquid;
    
    public SpawnData() {
        super(10);
        this.biomes = new ArrayList<String>();
        this.id = -1;
        this.name = "";
        this.compound1 = new NBTTagCompound();
        this.liquid = false;
    }
    
    public void readNBT(final NBTTagCompound compound) {
        this.id = compound.getInteger("SpawnId");
        this.name = compound.getString("SpawnName");
        this.itemWeight = compound.getInteger("SpawnWeight");
        if (this.itemWeight == 0) {
            this.itemWeight = 1;
        }
        this.biomes = NBTTags.getStringList(compound.getTagList("SpawnBiomes", 10));
        this.compound1 = compound.getCompoundTag("SpawnCompound1");
    }
    
    public NBTTagCompound writeNBT(final NBTTagCompound compound) {
        compound.setInteger("SpawnId", this.id);
        compound.setString("SpawnName", this.name);
        compound.setInteger("SpawnWeight", this.itemWeight);
        compound.setTag("SpawnBiomes", (NBTBase)NBTTags.nbtStringList(this.biomes));
        compound.setTag("SpawnCompound1", (NBTBase)this.compound1);
        return compound;
    }
}
