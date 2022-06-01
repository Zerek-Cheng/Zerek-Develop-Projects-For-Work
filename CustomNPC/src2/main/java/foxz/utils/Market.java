package foxz.utils;

import noppes.npcs.roles.*;
import net.minecraft.nbt.*;
import noppes.npcs.util.*;
import java.io.*;
import noppes.npcs.*;
import noppes.npcs.entity.*;

public class Market
{
    public static void save(final RoleTrader r, final String name) {
        if (name.isEmpty()) {
            return;
        }
        final File file = getFile(name + "_new");
        final File file2 = getFile(name);
        try {
            NBTJsonUtil.SaveFile(file, r.writeNBT(new NBTTagCompound()));
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
        }
        catch (Exception ex) {}
    }
    
    public static void load(final RoleTrader role, final String name) {
        if (role.npc.worldObj.isRemote) {
            return;
        }
        final File file = getFile(name);
        if (!file.exists()) {
            return;
        }
        try {
            role.readNBT(NBTJsonUtil.LoadFile(file));
        }
        catch (Exception ex) {}
    }
    
    private static File getFile(final String name) {
        final File dir = new File(CustomNpcs.getWorldSaveDirectory(), "markets");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return new File(dir, name.toLowerCase() + ".json");
    }
    
    public static void setMarket(final EntityNPCInterface npc, final String marketName) {
        if (marketName.isEmpty()) {
            return;
        }
        if (!getFile(marketName).exists()) {
            save((RoleTrader)npc.roleInterface, marketName);
        }
        load((RoleTrader)npc.roleInterface, marketName);
    }
}
