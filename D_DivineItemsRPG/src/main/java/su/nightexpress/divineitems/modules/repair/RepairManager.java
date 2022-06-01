/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.apache.commons.lang.ArrayUtils
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.repair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.DivinePerms;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.RepairCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.gui.ContentType;
import su.nightexpress.divineitems.gui.GUI;
import su.nightexpress.divineitems.gui.GUIItem;
import su.nightexpress.divineitems.gui.GUIManager;
import su.nightexpress.divineitems.gui.GUIType;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.external.VaultHook;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.utils.ParticleUtils;
import su.nightexpress.divineitems.utils.Utils;

public class RepairManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private MyConfig settingsCfg;
    private RepairSettings rs;
    private RepairGem gem;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_GEM = "DIVINE_RGEM";

    public RepairManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.setupSettings();
        this.setupGem();
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    @Override
    public boolean isResolvable() {
        return false;
    }

    @Override
    public String name() {
        return "Repair";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new RepairCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.rs = null;
            this.gem = null;
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private void setupSettings() {
        Object object3;
        Object object22;
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        String string = "Anvil.";
        boolean bl = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        String string2 = fileConfiguration.getString(String.valueOf(string) + "Action.Click").toUpperCase();
        boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string) + "Action.isShiftOnly");
        HashMap<RepairType, Boolean> hashMap = new HashMap<RepairType, Boolean>();
        for (Object object3 : fileConfiguration.getConfigurationSection(String.valueOf(string) + "Currency").getKeys(false)) {
            Object object4 = RepairType.valueOf(object3.toString().toUpperCase());
            boolean bl3 = fileConfiguration.getBoolean(String.valueOf(string) + "Currency." + (String)object3);
            hashMap.put((RepairType)((Object)object4), bl3);
        }
        object3 = new HashMap();
        for (Object object22 : fileConfiguration.getConfigurationSection(String.valueOf(string) + "RepairCost").getKeys(false)) {
            RepairType repairType = RepairType.valueOf(object22.toString().toUpperCase());
            double d = fileConfiguration.getDouble(String.valueOf(string) + "RepairCost." + (String)object22);
            object3.put(repairType, d);
        }
        object22 = new HashMap();
        for (Object object4 : fileConfiguration.getConfigurationSection(String.valueOf(string) + "MaterialsTable").getKeys(false)) {
            String string3 = String.valueOf(string) + "MaterialsTable." + (String)object4;
            Material material = Material.getMaterial((String)object4.toString().toUpperCase());
            if (material == null) continue;
            List list = fileConfiguration.getStringList(string3);
            object22.put(material, list);
        }
        this.rs = new RepairSettings(bl, string2, bl2, hashMap, (HashMap<RepairType, Double>)object3, (HashMap<Material, List<String>>)object22);
    }

    private void setupGem() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        String string = "Item.";
        boolean bl = fileConfiguration.getBoolean(String.valueOf(string) + "Enabled");
        String string2 = fileConfiguration.getString(String.valueOf(string) + "Material");
        String string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string) + "Display"));
        List list = fileConfiguration.getStringList(String.valueOf(string) + "Lore");
        List list2 = fileConfiguration.getStringList(String.valueOf(string) + "ItemFlags");
        boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string) + "SetUnbreakable");
        int n = fileConfiguration.getInt(String.valueOf(string) + "RepairMode");
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        for (String string4 : fileConfiguration.getConfigurationSection(String.valueOf(string) + "Levels").getKeys(false)) {
            int n2 = Integer.parseInt(string4.toString());
            int n3 = fileConfiguration.getInt(String.valueOf(string) + "Levels." + string4);
            hashMap.put(n2, n3);
        }
        this.gem = new RepairGem(bl, string2, string3, list, list2, bl2, n, hashMap);
    }

    public RepairSettings getSettings() {
        return this.rs;
    }

    public RepairGem getGem() {
        return this.gem;
    }

    public boolean isRepairGem(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_RGEM");
    }

    public void openGemGUI(Player player, ItemStack itemStack, ItemStack itemStack2, int n) {
        GUI gUI = new GUI(this.plugin.getGUIManager().getGUIByType(GUIType.REPAIR_GEM));
        gUI.getItems().get((Object)ContentType.TARGET).setItem(itemStack);
        gUI.getItems().get((Object)ContentType.SOURCE).setItem(itemStack2);
        gUI.getItems().get((Object)ContentType.RESULT).setItem(this.getResult(new ItemStack(itemStack), player, n));
        this.plugin.getGUIManager().setGUI(player, gUI);
        player.openInventory(gUI.build());
    }

    public void openAnvilGUI(Player player, ItemStack itemStack) {
        if (!player.hasPermission(DivinePerms.REPAIR_ANVL.node())) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_NoPerm.toMsg());
            return;
        }
        GUI gUI = new GUI(this.plugin.getGUIManager().getGUIByType(GUIType.REPAIR_ANVIL));
        gUI.getItems().get((Object)ContentType.TARGET).setItem(itemStack);
        RepairType[] arrrepairType = RepairType.values();
        int n = arrrepairType.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack itemStack2;
            GUIItem gUIItem;
            RepairType repairType = arrrepairType[n2];
            if (this.getSettings().getCurrency().get((Object)repairType).booleanValue()) {
                gUIItem = new GUIItem(gUI.getItems().get((Object)ContentType.valueOf(repairType.name())));
                itemStack2 = new ItemStack(gUIItem.getItem());
                ItemMeta itemMeta = itemStack2.getItemMeta();
                List list = itemMeta.getLore();
                ArrayList<String> arrayList = new ArrayList<String>();
                for (String string : list) {
                    String string2 = this.plugin.getCM().getDefaultItemName(new ItemStack(this.getCraftMaterial(itemStack.getType().name())));
                    arrayList.add(string.replace("%material%", string2).replace("%d%", "" + this.getPlayerBalance(player, repairType, itemStack.getType())).replace("%s%", "" + this.calcCost(itemStack, repairType)));
                }
                itemMeta.setLore(arrayList);
                itemStack2.setItemMeta(itemMeta);
                gUIItem.setItem(itemStack2);
                gUI.getItems().put(ContentType.valueOf(repairType.name()), gUIItem);
            } else {
                gUIItem = new GUIItem(gUI.getItems().get((Object)ContentType.valueOf(repairType.name())));
                itemStack2 = new ItemStack(Material.AIR);
                gUIItem.setItem(itemStack2);
                gUI.getItems().put(ContentType.valueOf(repairType.name()), gUIItem);
            }
            ++n2;
        }
        this.plugin.getGUIManager().setGUI(player, gUI);
        player.openInventory(gUI.build());
    }

    private ItemStack getResult(ItemStack itemStack, Player player, int n) {
        ItemStack itemStack2 = ItemAPI.setDurability(itemStack, ItemAPI.getDurability(itemStack, 0) + n, ItemAPI.getDurability(itemStack, 1));
        ItemMeta itemMeta = itemStack2.getItemMeta();
        itemMeta.setDisplayName(Lang.Repair_Result.toMsg());
        itemStack2.setItemMeta(itemMeta);
        return new ItemStack(itemStack2);
    }

    private int calcCost(ItemStack itemStack, RepairType repairType) {
        int n = 0;
        if (itemStack == null) {
            return n;
        }
        int n2 = ItemAPI.getDurability(itemStack, 0);
        int n3 = ItemAPI.getDurability(itemStack, 1);
        double d = this.getSettings().getCosts().get((Object)repairType);
        double d2 = d * (double)(n3 - n2);
        n = (int)d2;
        if (n <= 0) {
            n = 1;
        }
        return n;
    }

    private boolean payForRepair(Player player, RepairType repairType, ItemStack itemStack) {
        int n = this.calcCost(itemStack, repairType);
        if (this.getPlayerBalance(player, repairType, itemStack.getType()) < n) {
            return false;
        }
        if (repairType == RepairType.EXP) {
            player.setLevel(player.getLevel() - n);
        } else if (repairType == RepairType.MATERIAL) {
            ItemStack[] arritemStack = player.getInventory().getContents();
            int n2 = arritemStack.length;
            int n3 = 0;
            while (n3 < n2) {
                ItemStack itemStack2 = arritemStack[n3];
                if (itemStack2 != null && itemStack2.getType() == this.getCraftMaterial(itemStack.getType().name())) {
                    if (itemStack2.getAmount() > n) {
                        player.getInventory().removeItem(new ItemStack[]{itemStack2});
                        itemStack2.setAmount(itemStack2.getAmount() - n);
                        player.getInventory().addItem(new ItemStack[]{itemStack2});
                        return true;
                    }
                    player.getInventory().removeItem(new ItemStack[]{itemStack2});
                    if ((n -= itemStack2.getAmount()) <= 0) {
                        return true;
                    }
                }
                ++n3;
            }
        } else if (repairType == RepairType.VAULT) {
            return this.plugin.getHM().getVault().take(player, n);
        }
        return true;
    }

    private int getPlayerBalance(Player player, RepairType repairType, Material material) {
        int n = 0;
        if (repairType == RepairType.EXP) {
            return player.getLevel();
        }
        if (repairType == RepairType.MATERIAL) {
            ItemStack[] arritemStack = player.getInventory().getContents();
            int n2 = arritemStack.length;
            int n3 = 0;
            while (n3 < n2) {
                ItemStack itemStack = arritemStack[n3];
                if (itemStack != null && itemStack.getType() == this.getCraftMaterial(material.name())) {
                    n += itemStack.getAmount();
                }
                ++n3;
            }
        } else if (repairType == RepairType.VAULT) {
            return (int)this.plugin.getHM().getVault().getBalans(player);
        }
        return n;
    }

    private Material getCraftMaterial(String string) {
        for (Material material : this.rs.getMaterials().keySet()) {
            List<String> list = this.rs.getMaterials().get((Object)material);
            for (String string2 : list) {
                String string3;
                if (!(string2.endsWith("*") ? string.startsWith(string3 = string2.replace("*", "")) : (string2.startsWith("*") ? string.endsWith(string3 = string2.replace("*", "")) : string.equalsIgnoreCase(string2)))) continue;
                return material;
            }
        }
        return Material.AIR;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        if (!this.getGem().isEnabled()) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCursor();
        ItemStack itemStack2 = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || itemStack2 == null) {
            return;
        }
        if (ItemAPI.getDurability(itemStack2, 0) < 0) {
            return;
        }
        if (inventoryClickEvent.getInventory().getType() != InventoryType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.ARMOR || inventoryClickEvent.getSlot() == 40) {
            return;
        }
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_RGEM").booleanValue()) {
            return;
        }
        int n = nBTItem.getInteger("DIVINE_RGEM");
        int n2 = this.getGem().getLevels().get(n);
        int n3 = ItemAPI.getDurability(itemStack2, this.getGem().getMode());
        double d = (double)n3 * ((double)n2 / 100.0);
        int n4 = (int)d;
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_FullInventory.toMsg());
            return;
        }
        player.getInventory().addItem(new ItemStack[]{itemStack});
        inventoryClickEvent.setCursor(null);
        inventoryClickEvent.setCancelled(true);
        this.openGemGUI(player, itemStack2, itemStack, n4);
    }

    @EventHandler
    public void onAnvil(PlayerInteractEvent playerInteractEvent) {
        if (!this.getSettings().isAnvilEnabled()) {
            return;
        }
        Player player = playerInteractEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (playerInteractEvent.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (playerInteractEvent.getAction().name().contains(this.getSettings().getAction())) {
            if (this.getSettings().isShift() && !player.isSneaking()) {
                return;
            }
            if (playerInteractEvent.getClickedBlock() != null && playerInteractEvent.getClickedBlock().getType() == Material.ANVIL) {
                if (itemStack == null) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_NoItem.toMsg());
                    return;
                }
                if (ItemAPI.getDurability(itemStack, 0) < 0) {
                    player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_NoDurability.toMsg());
                    return;
                }
                if (ItemAPI.getDurability(itemStack, 0) == ItemAPI.getDurability(itemStack, 1)) {
                    return;
                }
                this.openAnvilGUI(player, itemStack);
                playerInteractEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClickGUI(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.plugin.getGUIManager().valid(player, GUIType.REPAIR_GEM)) {
            return;
        }
        GUI gUI = this.plugin.getGUIManager().getPlayerGUI(player, GUIType.REPAIR_GEM);
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
            ItemStack itemStack2;
            ItemStack itemStack3 = gUI.getItems().get((Object)ContentType.TARGET).getItem();
            ItemStack itemStack4 = gUI.getItems().get((Object)ContentType.SOURCE).getItem();
            NBTItem nBTItem = new NBTItem(itemStack4);
            int n2 = nBTItem.getInteger("DIVINE_RGEM");
            int n3 = this.getGem().getLevels().get(n2);
            int n4 = ItemAPI.getDurability(itemStack3, this.getGem().getMode());
            double d = (double)n4 * ((double)n3 / 100.0);
            int n5 = (int)d;
            int n6 = 0;
            ItemStack[] arritemStack = player.getInventory().getContents();
            int n7 = arritemStack.length;
            int n8 = 0;
            while (n8 < n7) {
                itemStack2 = arritemStack[n8];
                if (itemStack2 != null && itemStack2.equals((Object)itemStack3)) break;
                ++n6;
                ++n8;
            }
            itemStack2 = new ItemStack(itemStack4);
            itemStack2.setAmount(1);
            player.getInventory().removeItem(new ItemStack[]{itemStack2});
            player.getInventory().removeItem(new ItemStack[]{itemStack3});
            ItemStack itemStack5 = ItemAPI.setDurability(itemStack3, ItemAPI.getDurability(itemStack3, 0) + n5, ItemAPI.getDurability(itemStack3, 1));
            player.getInventory().setItem(n6, itemStack5);
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_Done.toMsg());
            player.closeInventory();
            this.plugin.getGUIManager().reset(player);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 0.5f);
            Utils.playEffect("VILLAGER_HAPPY", 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
            ParticleUtils.repairEffect(player.getLocation());
            return;
        }
        GUIItem gUIItem2 = gUI.getItems().get((Object)ContentType.DECLINE);
        if (itemStack.isSimilar(gUIItem2.getItem()) || ArrayUtils.contains((int[])gUIItem2.getSlots(), (int)n)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_Cancel.toMsg());
            player.closeInventory();
            this.plugin.getGUIManager().reset(player);
        }
    }

    @EventHandler
    public void onClick2(InventoryClickEvent inventoryClickEvent) {
        ItemStack itemStack;
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        if (!this.plugin.getGUIManager().valid(player, GUIType.REPAIR_ANVIL)) {
            return;
        }
        GUI gUI = this.plugin.getGUIManager().getPlayerGUI(player, GUIType.REPAIR_ANVIL);
        if (gUI == null || !inventoryClickEvent.getInventory().getTitle().equals(gUI.getTitle())) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        ItemStack itemStack2 = inventoryClickEvent.getCurrentItem();
        if (itemStack2 == null || !itemStack2.hasItemMeta() || !itemStack2.getItemMeta().hasDisplayName()) {
            return;
        }
        String string = itemStack2.getItemMeta().getDisplayName();
        ItemStack itemStack3 = gUI.getItems().get((Object)ContentType.TARGET).getItem();
        RepairType repairType = null;
        if (gUI.getItems().get((Object)ContentType.EXP).getItem().getItemMeta() != null && string.equals(gUI.getItems().get((Object)ContentType.EXP).getItem().getItemMeta().getDisplayName())) {
            repairType = RepairType.EXP;
        } else if (gUI.getItems().get((Object)ContentType.MATERIAL).getItem().getItemMeta() != null && string.equals(gUI.getItems().get((Object)ContentType.MATERIAL).getItem().getItemMeta().getDisplayName())) {
            repairType = RepairType.MATERIAL;
        } else if (gUI.getItems().get((Object)ContentType.VAULT).getItem().getItemMeta() != null && string.equals(gUI.getItems().get((Object)ContentType.VAULT).getItem().getItemMeta().getDisplayName())) {
            repairType = RepairType.VAULT;
        } else {
            return;
        }
        if (!this.payForRepair(player, repairType, itemStack3)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_CantAfford.toMsg());
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 0.5f, 0.5f);
            return;
        }
        int n = 0;
        ItemStack[] arritemStack = player.getInventory().getContents();
        int n2 = arritemStack.length;
        int n3 = 0;
        while (n3 < n2) {
            itemStack = arritemStack[n3];
            if (itemStack != null && itemStack.equals((Object)itemStack3)) break;
            ++n;
            ++n3;
        }
        player.getInventory().removeItem(new ItemStack[]{itemStack3});
        itemStack = ItemAPI.setDurability(itemStack3, 1000000, ItemAPI.getDurability(itemStack3, 1));
        player.getInventory().setItem(n, itemStack);
        player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Repair_Done.toMsg());
        player.closeInventory();
        this.plugin.getGUIManager().reset(player);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 0.5f, 0.5f);
        Utils.playEffect("VILLAGER_HAPPY", 0.30000001192092896, 0.0, 0.30000001192092896, 0.30000001192092896, 15, player.getLocation().add(0.0, 0.5, 0.0));
        Block block = player.getTargetBlock(null, 100);
        if (block == null || block.getType() != Material.ANVIL) {
            return;
        }
        ParticleUtils.repairEffect(block.getLocation());
    }

    public class RepairGem {
        private boolean enabled;
        private String material;
        private String display;
        private List<String> lore;
        private List<String> flags;
        private boolean unb;
        private int mode;
        private HashMap<Integer, Integer> levels;

        public RepairGem(boolean bl, String string, String string2, List<String> list, List<String> list2, boolean bl2, int n, HashMap<Integer, Integer> hashMap) {
            this.setEnabled(bl);
            this.setMaterial(string);
            this.setDisplay(string2);
            this.setLore(list);
            this.setFlags(list2);
            this.setUnbreak(bl2);
            this.setMode(n);
            this.setLevels(hashMap);
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean bl) {
            this.enabled = bl;
        }

        public String getMaterial() {
            return this.material;
        }

        public void setMaterial(String string) {
            this.material = string;
        }

        public String getDisplay() {
            return this.display;
        }

        public void setDisplay(String string) {
            this.display = string;
        }

        public List<String> getLore() {
            return this.lore;
        }

        public void setLore(List<String> list) {
            this.lore = list;
        }

        public List<String> getFlags() {
            return new ArrayList<String>(this.flags);
        }

        public void setFlags(List<String> list) {
            this.flags = list;
        }

        public boolean setUnbreak() {
            return this.unb;
        }

        public void setUnbreak(boolean bl) {
            this.unb = bl;
        }

        public int getMode() {
            return this.mode;
        }

        public void setMode(int n) {
            this.mode = n;
        }

        public HashMap<Integer, Integer> getLevels() {
            return this.levels;
        }

        public void setLevels(HashMap<Integer, Integer> hashMap) {
            this.levels = hashMap;
        }

        public ItemStack getItemGem(int n) {
            if (!this.isEnabled()) {
                return null;
            }
            if (!this.getLevels().containsKey(n)) {
                n = 1;
            }
            String[] arrstring = this.getMaterial().split(":");
            ItemStack itemStack = Utils.buildItem(arrstring, "repair-gem-" + n);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(this.getDisplay().replace("%s", Utils.IntegerToRomanNumeral(n)));
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String object2 : this.getLore()) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2.replace("%level%", Utils.IntegerToRomanNumeral(n)).replace("%s%", "" + this.getLevels().get(n))));
            }
            itemMeta.setLore(arrayList);
            for (String string : this.getFlags()) {
                itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.valueOf((String)string)});
            }
            itemMeta.spigot().setUnbreakable(this.setUnbreak());
            itemStack.setItemMeta(itemMeta);
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setInteger("DIVINE_RGEM", n);
            return nBTItem.getItem();
        }
    }

    public class RepairSettings {
        private boolean anvil_use;
        private String action;
        private boolean shift;
        private HashMap<RepairType, Boolean> anvil_currency;
        private HashMap<RepairType, Double> anvil_costs;
        private HashMap<Material, List<String>> mats;

        public RepairSettings(boolean bl, String string, boolean bl2, HashMap<RepairType, Boolean> hashMap, HashMap<RepairType, Double> hashMap2, HashMap<Material, List<String>> hashMap3) {
            this.setAnvilEnabled(bl);
            this.setAction(string);
            this.setShift(bl2);
            this.setCurrency(hashMap);
            this.setCosts(hashMap2);
            this.setMaterials(hashMap3);
        }

        public boolean isAnvilEnabled() {
            return this.anvil_use;
        }

        public void setAnvilEnabled(boolean bl) {
            this.anvil_use = bl;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String string) {
            this.action = string;
        }

        public boolean isShift() {
            return this.shift;
        }

        public void setShift(boolean bl) {
            this.shift = bl;
        }

        public HashMap<RepairType, Boolean> getCurrency() {
            return this.anvil_currency;
        }

        public void setCurrency(HashMap<RepairType, Boolean> hashMap) {
            this.anvil_currency = hashMap;
        }

        public HashMap<RepairType, Double> getCosts() {
            return this.anvil_costs;
        }

        public void setCosts(HashMap<RepairType, Double> hashMap) {
            this.anvil_costs = hashMap;
        }

        public HashMap<Material, List<String>> getMaterials() {
            return this.mats;
        }

        public void setMaterials(HashMap<Material, List<String>> hashMap) {
            this.mats = hashMap;
        }
    }

    public static enum RepairType {
        EXP,
        MATERIAL,
        VAULT;
        

        private RepairType(String string2, int n2) {
        }
    }

}

