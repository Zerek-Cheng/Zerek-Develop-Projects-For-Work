package noppes.npcs.scripted;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class ScriptItemStack
{
    protected ItemStack item;
    
    public ScriptItemStack(final ItemStack item) {
        this.item = item;
    }
    
    public String getName() {
        return Item.itemRegistry.getNameForObject((Object)this.item.getItem());
    }
    
    public int getStackSize() {
        return this.item.stackSize;
    }
    
    public boolean hasCustomName() {
        return this.item.hasDisplayName();
    }
    
    public void setCustomName(final String name) {
        this.item.setStackDisplayName(name);
    }
    
    public String getDisplayName() {
        return this.item.getDisplayName();
    }
    
    public String getItemName() {
        return this.item.getItem().getItemStackDisplayName(this.item);
    }
    
    public void setStackSize(int size) {
        if (size < 0) {
            size = 1;
        }
        else if (size > 64) {
            size = 64;
        }
        this.item.stackSize = size;
    }
    
    public int getItemDamage() {
        return this.item.getMetadata();
    }
    
    public void setItemDamage(final int value) {
        this.item.setMetadata(value);
    }
    
    public void setTag(final String key, final Object value) {
        if (value instanceof Number) {
            this.getTag().setDouble(key, ((Number)value).doubleValue());
        }
        else if (value instanceof String) {
            this.getTag().setString(key, (String)value);
        }
    }
    
    public boolean hasTag(final String key) {
        return this.getTag().hasKey(key);
    }
    
    public Object getTag(final String key) {
        final NBTBase tag = this.getTag().getTag(key);
        if (tag == null) {
            return null;
        }
        if (tag instanceof NBTBase.NBTPrimitive) {
            return ((NBTBase.NBTPrimitive)tag).getDouble();
        }
        if (tag instanceof NBTTagString) {
            return ((NBTTagString)tag).getString();
        }
        return tag;
    }
    
    public boolean isEnchanted() {
        return this.item.isItemEnchanted();
    }
    
    public boolean hasEnchant(final int id) {
        if (!this.isEnchanted()) {
            return false;
        }
        final NBTTagList list = this.item.getEnchantmentTagList();
        for (int i = 0; i < list.tagCount(); ++i) {
            final NBTTagCompound compound = list.getCompoundTagAt(i);
            if (compound.getShort("id") == id) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isWrittenBook() {
        return this.item.getItem() == Items.written_book || this.item.getItem() == Items.writable_book;
    }
    
    public String getBookTitle() {
        return this.item.getTagCompound().getString("title");
    }
    
    public String getBookAuthor() {
        return this.item.getTagCompound().getString("author");
    }
    
    public String[] getBookText() {
        if (!this.isWrittenBook()) {
            return null;
        }
        final List<String> list = new ArrayList<String>();
        final NBTTagList pages = this.item.getTagCompound().getTagList("pages", 8);
        for (int i = 0; i < pages.tagCount(); ++i) {
            list.add(pages.getStringTagAt(i));
        }
        return list.toArray(new String[list.size()]);
    }
    
    private NBTTagCompound getTag() {
        if (this.item.stackTagCompound == null) {
            this.item.stackTagCompound = new NBTTagCompound();
        }
        return this.item.stackTagCompound;
    }
    
    public boolean isBlock() {
        final Block block = Block.getBlockFromItem(this.item.getItem());
        return block != null && block != Blocks.air;
    }
    
    public ItemStack getMCItemStack() {
        return this.item;
    }
}
