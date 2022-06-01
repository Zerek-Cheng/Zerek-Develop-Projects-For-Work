package noppes.npcs.controllers;

import net.minecraftforge.common.*;
import noppes.npcs.scripted.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class ScriptEntityData implements IExtendedEntityProperties
{
    public ScriptEntity base;
    
    public ScriptEntityData(final ScriptEntity base) {
        this.base = base;
    }
    
    public void saveNBTData(final NBTTagCompound compound) {
    }
    
    public void loadNBTData(final NBTTagCompound compound) {
    }
    
    public void init(final Entity entity, final World world) {
    }
}
