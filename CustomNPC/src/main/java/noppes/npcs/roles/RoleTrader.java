package noppes.npcs.roles;

import noppes.npcs.entity.*;
import foxz.utils.*;
import net.minecraft.nbt.*;
import java.util.logging.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import net.minecraft.item.*;
import noppes.npcs.*;
import java.util.*;

public class RoleTrader extends RoleInterface
{
    public String marketName;
    public NpcMiscInventory inventoryCurrency;
    public NpcMiscInventory inventorySold;
    public boolean ignoreDamage;
    public boolean ignoreNBT;
    public boolean toSave;
    
    public RoleTrader(final EntityNPCInterface npc) {
        super(npc);
        this.marketName = "";
        this.ignoreDamage = false;
        this.ignoreNBT = false;
        this.toSave = false;
        this.inventoryCurrency = new NpcMiscInventory(36);
        this.inventorySold = new NpcMiscInventory(18);
    }
    
    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setString("TraderMarket", this.marketName);
        this.writeNBT(nbttagcompound);
        if (this.toSave && !this.npc.isRemote()) {
            Market.save(this, this.marketName);
        }
        this.toSave = false;
        return nbttagcompound;
    }
    
    public NBTTagCompound writeNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setTag("TraderCurrency", (NBTBase)this.inventoryCurrency.getToNBT());
        nbttagcompound.setTag("TraderSold", (NBTBase)this.inventorySold.getToNBT());
        nbttagcompound.setBoolean("TraderIgnoreDamage", this.ignoreDamage);
        nbttagcompound.setBoolean("TraderIgnoreNBT", this.ignoreNBT);
        return nbttagcompound;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        this.marketName = nbttagcompound.getString("TraderMarket");
        this.readNBT(nbttagcompound);
        try {
            Market.load(this, this.marketName);
        }
        catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readNBT(final NBTTagCompound nbttagcompound) {
        this.inventoryCurrency.setFromNBT(nbttagcompound.getCompoundTag("TraderCurrency"));
        this.inventorySold.setFromNBT(nbttagcompound.getCompoundTag("TraderSold"));
        this.ignoreDamage = nbttagcompound.getBoolean("TraderIgnoreDamage");
        this.ignoreNBT = nbttagcompound.getBoolean("TraderIgnoreNBT");
    }
    
    @Override
    public void interact(final EntityPlayer player) {
        this.npc.say(player, this.npc.advanced.getInteractLine());
        try {
            Market.load(this, this.marketName);
        }
        catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTrader, this.npc);
    }
    
    public boolean hasCurrency(final ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        for (final ItemStack item : this.inventoryCurrency.items.values()) {
            if (item != null && NoppesUtilPlayer.compareItems(item, itemstack, this.ignoreDamage, this.ignoreNBT)) {
                return true;
            }
        }
        return false;
    }
}
