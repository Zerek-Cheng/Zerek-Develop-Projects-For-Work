/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.ArrayUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryPickupItemEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package su.nightexpress.divineitems.modules.soulbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.SoulboundCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.gui.ContentType;
import su.nightexpress.divineitems.gui.GUI;
import su.nightexpress.divineitems.gui.GUIItem;
import su.nightexpress.divineitems.gui.GUIManager;
import su.nightexpress.divineitems.gui.GUIType;
import su.nightexpress.divineitems.nbt.NBTItem;

public class SoulboundManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private MyConfig settingsCfg;
    private String untrade;
    private String soul;
    private String soulset;
    private List<String> cmds;
    private boolean b_drop;
    private boolean b_pick;
    private boolean b_click;
    private boolean b_use;
    private boolean i_pick;
    private boolean i_use;
    private boolean i_drop;
    private boolean i_death;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_SOUL = "Owner";

    public SoulboundManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        if (!fileConfiguration.contains("Interact.AllowDrop")) {
            fileConfiguration.set("Interact.AllowDrop", (Object)true);
        }
        this.settingsCfg.save();
        this.untrade = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Untradable"));
        this.soul = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Soulbound"));
        this.soulset = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("SoulboundSet"));
        this.cmds = new ArrayList<String>(fileConfiguration.getStringList("CmdBlock"));
        this.b_drop = fileConfiguration.getBoolean("BindToPlayer.OnItemDrop");
        this.b_pick = fileConfiguration.getBoolean("BindToPlayer.OnItemPickup");
        this.b_click = fileConfiguration.getBoolean("BindToPlayer.OnItemClick");
        this.b_use = fileConfiguration.getBoolean("BindToPlayer.OnItemUse");
        this.i_pick = fileConfiguration.getBoolean("Interact.AllowPickup");
        this.i_use = fileConfiguration.getBoolean("Interact.AllowUse");
        this.i_drop = fileConfiguration.getBoolean("Interact.AllowDrop");
        this.i_death = fileConfiguration.getBoolean("Interact.DropOnDeath");
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isResolvable() {
        return false;
    }

    @Override
    public String name() {
        return "Soulbound";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new SoulboundCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    public ItemStack removeSoulbound(ItemStack itemStack) {
        String[] arrstring = this.getSoulSetString().split("%p");
        String string = "";
        string = arrstring[0] != null ? arrstring[0] : arrstring[1];
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> arrayList = new ArrayList<String>(itemMeta.getLore());
        int n = 0;
        for (String object2 : arrayList) {
            if (object2.contains(string)) {
                n = arrayList.indexOf(object2);
                arrayList.remove(n);
                arrayList.add(n, this.getSoulString());
                break;
            }
            if (!object2.contains(this.getUntradeString())) continue;
            n = arrayList.indexOf(object2);
            arrayList.remove(n);
            break;
        }
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.removeKey("Owner");
        return nBTItem.getItem();
    }

    public boolean hasSoulbound(ItemStack itemStack) {
        return this.isSoulBinded(itemStack);
    }

    public boolean hasOwner(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        return nBTItem.hasKey("Owner");
    }

    public boolean isOwner(ItemStack itemStack, Player player) {
        if (!player.getUniqueId().toString().equals(this.getOwner(itemStack)) && !player.getName().equalsIgnoreCase(this.getOwner(itemStack))) {
            return false;
        }
        return true;
    }

    public String getOwner(ItemStack itemStack) {
        return new NBTItem(itemStack).getString("Owner");
    }

    public ItemStack setOwner(ItemStack itemStack, Player player) {
        NBTItem nBTItem = new NBTItem(itemStack);
        nBTItem.setString("Owner", player.getUniqueId().toString());
        return nBTItem.getItem();
    }

    public boolean isUntradeable(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        for (String string : list) {
            if (!string.contains(this.getUntradeString())) continue;
            return true;
        }
        return false;
    }

    public boolean isSoulboundRequired(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        for (String string : list) {
            if (!string.contains(this.getSoulString())) continue;
            return true;
        }
        return false;
    }

    public boolean isSoulBinded(ItemStack itemStack) {
        UUID uUID;
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return false;
        }
        if (!this.hasOwner(itemStack)) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List list = itemMeta.getLore();
        String string = this.getOwner(itemStack);
        try {
            uUID = UUID.fromString(string);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
        Player player = Bukkit.getPlayer((UUID)uUID);
        String string2 = player == null ? Bukkit.getOfflinePlayer((UUID)UUID.fromString(string)).getName() : player.getName();
        if (this.getSoulSetString() == null) {
            return false;
        }
        String string3 = this.getSoulSetString().replace("%p", string2);
        if (list.contains(string3)) {
            return true;
        }
        return false;
    }

    public String getUntradeString() {
        return this.untrade;
    }

    public String getSoulString() {
        return this.soul;
    }

    public String getSoulSetString() {
        return this.soulset;
    }

    public List<String> getCmds() {
        return this.cmds;
    }

    public boolean bindOnDrop() {
        return this.b_drop;
    }

    public boolean bindOnPickup() {
        return this.b_pick;
    }

    public boolean bindOnClick() {
        return this.b_click;
    }

    public boolean bindOnUse() {
        return this.b_use;
    }

    public boolean allowPickup() {
        return this.i_pick;
    }

    public boolean allowUse() {
        return this.i_use;
    }

    public boolean allowDropDeath() {
        return this.i_death;
    }

    public void openGUI(Player player, ItemStack itemStack) {
        GUI gUI = new GUI(this.plugin.getGUIManager().getGUIByType(GUIType.SOULBOUND));
        gUI.getItems().get((Object)ContentType.TARGET).setItem(itemStack);
        this.plugin.getGUIManager().setGUI(player, gUI);
        player.openInventory(gUI.build());
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent playerPickupItemEvent) {
        if (playerPickupItemEvent.getItem().hasMetadata("dont_pick_me")) {
            playerPickupItemEvent.setCancelled(true);
            return;
        }
        ItemStack itemStack = playerPickupItemEvent.getItem().getItemStack();
        Player player = playerPickupItemEvent.getPlayer();
        if (ItemAPI.isSoulBinded(itemStack) && !ItemAPI.isOwner(itemStack, player)) {
            playerPickupItemEvent.setCancelled(true);
            return;
        }
        if (ItemAPI.hasOwner(itemStack) && !ItemAPI.isOwner(itemStack, player)) {
            playerPickupItemEvent.setCancelled(true);
            return;
        }
        if (ItemAPI.isUntradeable(itemStack) && this.bindOnPickup()) {
            playerPickupItemEvent.getItem().setItemStack(ItemAPI.setOwner(itemStack, player));
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (inventoryClickEvent.getInventory().getType() == InventoryType.CRAFTING && inventoryClickEvent.getInventory().getHolder().equals((Object)player)) {
            if (itemStack == null || !itemStack.hasItemMeta()) {
                return;
            }
            if (this.isSoulboundRequired(itemStack) && inventoryClickEvent.isRightClick() && !inventoryClickEvent.isShiftClick() && inventoryClickEvent.getSlotType() != InventoryType.SlotType.CRAFTING) {
                if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.ARMOR || inventoryClickEvent.getSlot() == 40) {
                    return;
                }
                player.closeInventory();
                this.openGUI(player, itemStack);
                inventoryClickEvent.setCancelled(true);
                return;
            }
        } else if ((this.isSoulBinded(itemStack) || this.hasOwner(itemStack)) && !this.isOwner(itemStack, player) && !player.hasPermission("divineitems.bypass.owner")) {
            inventoryClickEvent.setCancelled(true);
            return;
        }
        if (this.isUntradeable(itemStack) && !this.hasOwner(itemStack) && this.bindOnClick()) {
            itemStack = this.setOwner(itemStack, player);
            inventoryClickEvent.setCurrentItem(itemStack);
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onInter(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = playerInteractEvent.getItem();
        if (itemStack == null || itemStack.getItemMeta() == null || itemStack.getItemMeta().getLore() == null) {
            return;
        }
        if (this.isUntradeable(itemStack) && !this.hasOwner(itemStack) && this.bindOnUse()) {
            if (playerInteractEvent.getHand() == EquipmentSlot.OFF_HAND) {
                player.getInventory().setItemInOffHand(this.setOwner(itemStack, player));
            } else {
                player.getInventory().setItemInMainHand(this.setOwner(itemStack, player));
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onItemDrop(PlayerDropItemEvent playerDropItemEvent) {
        ItemStack itemStack = playerDropItemEvent.getItemDrop().getItemStack();
        if (!this.i_drop && (this.isSoulBinded(itemStack) || this.isUntradeable(itemStack))) {
            playerDropItemEvent.setCancelled(true);
            return;
        }
        if (itemStack == null) {
            return;
        }
        if (!this.isUntradeable(itemStack)) {
            return;
        }
        if (this.hasOwner(itemStack)) {
            return;
        }
        if (!this.bindOnDrop()) {
            return;
        }
        Player player = playerDropItemEvent.getPlayer();
        if (this.isUntradeable(itemStack)) {
            playerDropItemEvent.getItemDrop().setItemStack(this.setOwner(itemStack, player));
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent playerCommandPreprocessEvent) {
        Player player = playerCommandPreprocessEvent.getPlayer();
        String string = playerCommandPreprocessEvent.getMessage();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemStack itemStack2 = player.getInventory().getItemInOffHand();
        if (itemStack == null && itemStack2 == null) {
            return;
        }
        for (String string2 : this.getCmds()) {
            if (!string.startsWith(string2) || !this.isUntradeable(itemStack) && !this.isSoulBinded(itemStack) && !this.isUntradeable(itemStack2) && !this.isSoulBinded(itemStack2)) continue;
            playerCommandPreprocessEvent.setCancelled(true);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_NoCommands.toMsg());
            return;
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onHopper(InventoryPickupItemEvent inventoryPickupItemEvent) {
        ItemStack itemStack = inventoryPickupItemEvent.getItem().getItemStack();
        if ((this.isUntradeable(itemStack) || this.isSoulBinded(itemStack)) && inventoryPickupItemEvent.getInventory().getType() == InventoryType.HOPPER) {
            inventoryPickupItemEvent.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent playerDeathEvent) {
        Player player2;
        if (this.allowDropDeath()) {
            return;
        }
        ArrayList arrayList = new ArrayList(playerDeathEvent.getDrops());
        final ArrayList<Player> arrayList2 = new ArrayList<Player>();
        for (final Player player2 : arrayList) {
            if (!this.hasOwner((ItemStack)player2)) continue;
            playerDeathEvent.getDrops().remove((Object)player2);
            arrayList2.add(player2);
        }
        player2 = playerDeathEvent.getEntity();
        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                for (ItemStack itemStack : arrayList2) {
                    player2.getInventory().addItem(new ItemStack[]{itemStack});
                }
            }
        });
    }

    @EventHandler
    public void onClickGUI(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.plugin.getGUIManager().valid(player, GUIType.SOULBOUND)) {
            return;
        }
        GUI gUI = this.plugin.getGUIManager().getPlayerGUI(player, GUIType.SOULBOUND);
        if (gUI == null || !inventoryClickEvent.getInventory().getTitle().equals(gUI.getTitle())) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        GUIItem gUIItem = gUI.getItems().get((Object)ContentType.ACCEPT);
        int n = inventoryClickEvent.getRawSlot();
        if (itemStack.isSimilar(gUIItem.getItem()) || ArrayUtils.contains((int[])gUIItem.getSlots(), (int)n)) {
            ItemStack itemStack2 = new ItemStack(gUI.getItems().get((Object)ContentType.TARGET).getItem());
            player.getInventory().removeItem(new ItemStack[]{itemStack2});
            int n2 = 0;
            ItemMeta itemMeta = itemStack2.getItemMeta();
            List list = itemMeta.getLore();
            n2 = list.indexOf(this.getSoulString());
            list.remove(n2);
            list.add(n2, this.getSoulSetString().replace("%p", player.getName()));
            itemMeta.setLore(list);
            itemStack2.setItemMeta(itemMeta);
            itemStack2 = this.setOwner(itemStack2, player);
            player.getInventory().addItem(new ItemStack[]{itemStack2});
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_SoulAccept.toMsg());
            player.closeInventory();
            this.plugin.getGUIManager().reset(player);
            return;
        }
        GUIItem gUIItem2 = gUI.getItems().get((Object)ContentType.DECLINE);
        if (itemStack.isSimilar(gUIItem2.getItem()) || ArrayUtils.contains((int[])gUIItem2.getSlots(), (int)n)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Restrictions_SoulDecline.toMsg());
            player.closeInventory();
            this.plugin.getGUIManager().reset(player);
        }
    }

}

