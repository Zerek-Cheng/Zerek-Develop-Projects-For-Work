/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.inventory.ItemStack
 */
package su.nightexpress.divineitems.nbt;

import org.bukkit.inventory.ItemStack;
import su.nightexpress.divineitems.nbt.NBTCompound;
import su.nightexpress.divineitems.nbt.NBTContainer;
import su.nightexpress.divineitems.nbt.NBTReflectionUtil;
import su.nightexpress.divineitems.nbt.ReflectionMethod;

public class NBTItem
extends NBTCompound {
    private ItemStack bukkitItem;

    public NBTItem(ItemStack itemStack) {
        super(null, null);
        if (itemStack == null) {
            throw new NullPointerException("ItemStack can't be null!");
        }
        this.bukkitItem = itemStack.clone();
    }

    @Override
    protected Object getCompound() {
        return NBTReflectionUtil.getItemRootNBTTagCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, new Object[]{this.bukkitItem}));
    }

    @Override
    protected void setCompound(Object object) {
        Object object2 = ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, new Object[]{this.bukkitItem});
        ReflectionMethod.ITEMSTACK_SET_TAG.run(object2, object);
        this.bukkitItem = (ItemStack)ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null, object2);
    }

    public ItemStack getItem() {
        return this.bukkitItem;
    }

    protected void setItem(ItemStack itemStack) {
        this.bukkitItem = itemStack;
    }

    public boolean hasNBTData() {
        if (this.getCompound() != null) {
            return true;
        }
        return false;
    }

    public static NBTContainer convertItemtoNBT(ItemStack itemStack) {
        return NBTReflectionUtil.convertNMSItemtoNBTCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, new Object[]{itemStack}));
    }

    public static ItemStack convertNBTtoItem(NBTCompound nBTCompound) {
        return (ItemStack)ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null, NBTReflectionUtil.convertNBTCompoundtoNMSItem(nBTCompound));
    }
}

