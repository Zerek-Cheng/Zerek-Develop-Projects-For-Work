package noppes.npcs.controllers;

import net.minecraft.nbt.*;
import java.util.*;
import noppes.npcs.entity.*;
import net.minecraft.entity.player.*;
import noppes.npcs.constants.*;
import noppes.npcs.*;
import net.minecraft.item.*;
import noppes.npcs.containers.*;
import net.minecraft.inventory.*;

public class BankData
{
    public HashMap<Integer, NpcMiscInventory> itemSlots;
    public HashMap<Integer, Boolean> upgradedSlots;
    public int unlockedSlots;
    public int bankId;
    
    public BankData() {
        this.unlockedSlots = 0;
        this.bankId = -1;
        this.itemSlots = new HashMap<Integer, NpcMiscInventory>();
        this.upgradedSlots = new HashMap<Integer, Boolean>();
        for (int i = 0; i < 6; ++i) {
            this.itemSlots.put(i, new NpcMiscInventory(54));
            this.upgradedSlots.put(i, false);
        }
    }
    
    public void readNBT(final NBTTagCompound nbttagcompound) {
        this.bankId = nbttagcompound.getInteger("DataBankId");
        this.unlockedSlots = nbttagcompound.getInteger("UnlockedSlots");
        this.itemSlots = this.getItemSlots(nbttagcompound.getTagList("BankInv", 10));
        this.upgradedSlots = NBTTags.getBooleanList(nbttagcompound.getTagList("UpdatedSlots", 10));
    }
    
    private HashMap<Integer, NpcMiscInventory> getItemSlots(final NBTTagList tagList) {
        final HashMap<Integer, NpcMiscInventory> list = new HashMap<Integer, NpcMiscInventory>();
        for (int i = 0; i < tagList.tagCount(); ++i) {
            final NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
            final int slot = nbttagcompound.getInteger("Slot");
            final NpcMiscInventory inv = new NpcMiscInventory(54);
            inv.setFromNBT(nbttagcompound.getCompoundTag("BankItems"));
            list.put(slot, inv);
        }
        return list;
    }
    
    public void writeNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("DataBankId", this.bankId);
        nbttagcompound.setInteger("UnlockedSlots", this.unlockedSlots);
        nbttagcompound.setTag("UpdatedSlots", (NBTBase)NBTTags.nbtBooleanList(this.upgradedSlots));
        nbttagcompound.setTag("BankInv", (NBTBase)this.nbtItemSlots(this.itemSlots));
    }
    
    private NBTTagList nbtItemSlots(final HashMap<Integer, NpcMiscInventory> items) {
        final NBTTagList list = new NBTTagList();
        for (final int slot : items.keySet()) {
            final NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Slot", slot);
            nbttagcompound.setTag("BankItems", (NBTBase)items.get(slot).getToNBT());
            list.appendTag((NBTBase)nbttagcompound);
        }
        return list;
    }
    
    public boolean isUpgraded(final Bank bank, final int slot) {
        return bank.isUpgraded(slot) || (bank.canBeUpgraded(slot) && this.upgradedSlots.get(slot));
    }
    
    public void openBankGui(final EntityPlayer player, final EntityNPCInterface npc, final int bankId, final int slot) {
        Bank bank = BankController.getInstance().banks.get(bankId);
        if (bank == null) {
            bank = BankController.getInstance().banks.values().iterator().next();
        }
        if (bank.getMaxSlots() <= slot) {
            return;
        }
        if (bank.startSlots > this.unlockedSlots) {
            this.unlockedSlots = bank.startSlots;
        }
        ItemStack currency = null;
        if (this.unlockedSlots <= slot) {
            currency = bank.currencyInventory.getStackInSlot(slot);
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUnlock, npc, slot, bank.id, 0);
        }
        else if (this.isUpgraded(bank, slot)) {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankLarge, npc, slot, bank.id, 0);
        }
        else if (bank.canBeUpgraded(slot)) {
            currency = bank.upgradeInventory.getStackInSlot(slot);
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankUprade, npc, slot, bank.id, 0);
        }
        else {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerBankSmall, npc, slot, bank.id, 0);
        }
        final NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("MaxSlots", bank.getMaxSlots());
        compound.setInteger("UnlockedSlots", this.unlockedSlots);
        if (currency != null) {
            compound.setTag("Currency", (NBTBase)NoppesUtilServer.writeItem(currency, new NBTTagCompound()));
            final ContainerNPCBankInterface container = this.getContainer(player);
            container.currency.item = currency;
        }
        Server.sendData((EntityPlayerMP)player, EnumPacketClient.GUI_DATA, compound);
    }
    
    private ContainerNPCBankInterface getContainer(final EntityPlayer player) {
        final Container con = player.openContainer;
        if (con == null || !(con instanceof ContainerNPCBankInterface)) {
            return null;
        }
        return (ContainerNPCBankInterface)con;
    }
}
