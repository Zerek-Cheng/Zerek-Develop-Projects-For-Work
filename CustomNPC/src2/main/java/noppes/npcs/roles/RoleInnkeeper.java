package noppes.npcs.roles;

import noppes.npcs.controllers.*;
import noppes.npcs.entity.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class RoleInnkeeper extends RoleInterface
{
    private String innName;
    private HashMap<String, InnDoorData> doors;
    
    public RoleInnkeeper(final EntityNPCInterface npc) {
        super(npc);
        this.innName = "Inn";
        this.doors = new HashMap<String, InnDoorData>();
    }
    
    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setString("InnName", this.innName);
        nbttagcompound.setTag("InnDoors", this.nbtInnDoors(this.doors));
        return nbttagcompound;
    }
    
    private NBTBase nbtInnDoors(final HashMap<String, InnDoorData> doors1) {
        final NBTTagList nbttaglist = new NBTTagList();
        if (doors1 == null) {
            return (NBTBase)nbttaglist;
        }
        for (final String name : doors1.keySet()) {
            final InnDoorData door = doors1.get(name);
            if (door == null) {
                continue;
            }
            final NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Name", name);
            nbttagcompound.setInteger("posX", door.x);
            nbttagcompound.setInteger("posY", door.y);
            nbttagcompound.setInteger("posZ", door.z);
            nbttaglist.appendTag((NBTBase)nbttagcompound);
        }
        return (NBTBase)nbttaglist;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        this.innName = nbttagcompound.getString("InnName");
        this.doors = this.getInnDoors(nbttagcompound.getTagList("InnDoors", 10));
    }
    
    private HashMap<String, InnDoorData> getInnDoors(final NBTTagList tagList) {
        final HashMap<String, InnDoorData> list = new HashMap<String, InnDoorData>();
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
            final String name = nbttagcompound.getString("Name");
            final InnDoorData door = new InnDoorData();
            door.x = nbttagcompound.getInteger("posX");
            door.y = nbttagcompound.getInteger("posY");
            door.z = nbttagcompound.getInteger("posZ");
            list.put(name, door);
        }
        return list;
    }
    
    @Override
    public void interact(final EntityPlayer player) {
        this.npc.say(player, this.npc.advanced.getInteractLine());
        if (this.doors.isEmpty()) {
            player.addChatMessage((IChatComponent)new ChatComponentTranslation("No Rooms available", new Object[0]));
        }
    }
}
