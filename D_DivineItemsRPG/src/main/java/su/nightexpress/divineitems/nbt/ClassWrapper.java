/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package su.nightexpress.divineitems.nbt;

import org.bukkit.Bukkit;

public enum ClassWrapper {
    CRAFT_ITEMSTACK("org.bukkit.craftbukkit.", ".inventory.CraftItemStack"),
    CRAFT_ENTITY("org.bukkit.craftbukkit.", ".entity.CraftEntity"),
    CRAFT_WORLD("org.bukkit.craftbukkit.", ".CraftWorld"),
    NMS_NBTBASE("net.minecraft.server.", ".NBTBase"),
    NMS_NBTTAGSTRING("net.minecraft.server.", ".NBTTagString"),
    NMS_ITEMSTACK("net.minecraft.server.", ".ItemStack"),
    NMS_NBTTAGCOMPOUND("net.minecraft.server.", ".NBTTagCompound"),
    NMS_NBTTAGLIST("net.minecraft.server.", ".NBTTagList"),
    NMS_NBTCOMPRESSEDSTREAMTOOLS("net.minecraft.server.", ".NBTCompressedStreamTools"),
    NMS_MOJANGSONPARSER("net.minecraft.server.", ".MojangsonParser"),
    NMS_TILEENTITY("net.minecraft.server.", ".TileEntity"),
    NMS_BLOCKPOSITION("net.minecraft.server.", ".BlockPosition");
    
    private Class<?> clazz;

    private ClassWrapper(String string2, int n2, String string3, String string4) {
        try {
            string3 = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            this.clazz = Class.forName(String.valueOf(string2) + string3 + (String)n2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Class<?> getClazz() {
        return this.clazz;
    }
}

