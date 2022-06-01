/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.identify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.IdentifyCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.tiers.TierManager;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.utils.Utils;

public class IdentifyManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private MyConfig settingsCfg;
    private MyConfig tomesCfg;
    private MyConfig itemsCfg;
    private HashMap<String, IdentifyTome> tomes;
    private HashMap<String, UnidentifiedItem> uis;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_IT = "DIVINE_TOME";
    private final String NBT_KEY_UI = "DIVINE_UNIT";

    public IdentifyManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.tomes = new HashMap();
        this.uis = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.tomesCfg = new MyConfig(this.plugin, "/modules/" + this.n, "tomes.yml");
        this.itemsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "items.yml");
        this.setup();
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
        return "Identify";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new IdentifyCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.tomes.clear();
            this.uis.clear();
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private void setup() {
        this.settingsCfg.save();
        this.setupTomes();
        this.setupItems();
    }

    private void setupTomes() {
        FileConfiguration fileConfiguration = this.tomesCfg.getConfig();
        if (fileConfiguration.contains("IdentifyTomes")) {
            for (String string : fileConfiguration.getConfigurationSection("IdentifyTomes").getKeys(false)) {
                String string2 = "IdentifyTomes." + string.toString() + ".";
                String string3 = string.toString().toLowerCase();
                String string4 = fileConfiguration.getString(String.valueOf(string2) + "Material");
                String string5 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Display"));
                List list = fileConfiguration.getStringList(String.valueOf(string2) + "Lore");
                IdentifyTome identifyTome = new IdentifyTome(string3, string4, string5, list);
                this.tomes.put(string3, identifyTome);
            }
        }
    }

    private void setupItems() {
        FileConfiguration fileConfiguration = this.itemsCfg.getConfig();
        if (fileConfiguration.contains("UnidentifiedItems")) {
            for (String string : fileConfiguration.getConfigurationSection("UnidentifiedItems").getKeys(false)) {
                String string2 = "UnidentifiedItems." + string.toString() + ".";
                String string3 = string.toString().toLowerCase();
                String string4 = fileConfiguration.getString(String.valueOf(string2) + "Tier").toLowerCase();
                String string5 = fileConfiguration.getString(String.valueOf(string2) + "Tome").toLowerCase();
                String string6 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Display"));
                List list = fileConfiguration.getStringList(String.valueOf(string2) + "Lore");
                UnidentifiedItem unidentifiedItem = new UnidentifiedItem(string3, string4, string5, string6, list);
                this.uis.put(string3, unidentifiedItem);
            }
        }
    }

    public boolean isTome(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_TOME");
    }

    public boolean isUnidentified(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_UNIT");
    }

    public boolean isValidTome(ItemStack itemStack, ItemStack itemStack2) {
        String[] arrstring;
        NBTItem nBTItem = new NBTItem(itemStack);
        NBTItem nBTItem2 = new NBTItem(itemStack2);
        String string = nBTItem2.getString("DIVINE_TOME");
        if (string.equalsIgnoreCase((arrstring = nBTItem.getString("DIVINE_UNIT").split(":"))[2])) {
            return true;
        }
        return false;
    }

    public Collection<IdentifyTome> getTomes() {
        return this.tomes.values();
    }

    public List<String> getTomeNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (IdentifyTome identifyTome : this.getTomes()) {
            arrayList.add(identifyTome.getId());
        }
        return arrayList;
    }

    public Collection<UnidentifiedItem> getItems() {
        return this.uis.values();
    }

    public List<String> getUINames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (UnidentifiedItem unidentifiedItem : this.getItems()) {
            arrayList.add(unidentifiedItem.getId());
        }
        return arrayList;
    }

    public IdentifyTome getTomeById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<IdentifyTome>(this.getTomes()).get(this.r.nextInt(this.getTomes().size()));
        }
        return this.tomes.get(string.toLowerCase());
    }

    public UnidentifiedItem getItemById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<UnidentifiedItem>(this.getItems()).get(this.r.nextInt(this.getItems().size()));
        }
        return this.uis.get(string.toLowerCase());
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (!(inventoryClickEvent.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player)inventoryClickEvent.getWhoClicked();
        ItemStack itemStack = inventoryClickEvent.getCursor();
        ItemStack itemStack2 = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || itemStack.getType() == Material.AIR || itemStack2 == null) {
            return;
        }
        if (inventoryClickEvent.getInventory().getType() != InventoryType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.CRAFTING) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.ARMOR || inventoryClickEvent.getSlot() == 40) {
            return;
        }
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            return;
        }
        if (!itemStack2.hasItemMeta() || !itemStack2.getItemMeta().hasLore()) {
            return;
        }
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_TOME").booleanValue()) {
            return;
        }
        NBTItem nBTItem2 = new NBTItem(itemStack2);
        if (!nBTItem2.hasKey("DIVINE_UNIT").booleanValue()) {
            return;
        }
        String[] arrstring = nBTItem2.getString("DIVINE_UNIT").split(":");
        String string = arrstring[1];
        int n = Integer.parseInt(arrstring[3]);
        if (!this.isValidTome(itemStack2, itemStack)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Identify_WrongTome.toMsg());
            return;
        }
        TierManager.Tier tier = this.plugin.getMM().getTierManager().getTierById(string);
        if (tier == null) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_Internal.toMsg());
            return;
        }
        int n2 = 0;
        int n3 = 0;
        try {
            n2 = Integer.parseInt(tier.getLevels().split("-")[0]);
            n3 = Integer.parseInt(tier.getLevels().split("-")[1]);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (n < n2) {
            n = n2;
        } else if (n > n3) {
            n = n3;
        }
        inventoryClickEvent.setCancelled(true);
        ItemStack itemStack3 = tier.create(n, itemStack2.getType());
        itemStack.setAmount(itemStack.getAmount() - 1);
        if (itemStack.getAmount() <= 0) {
            inventoryClickEvent.setCursor(null);
        } else {
            inventoryClickEvent.setCursor(itemStack);
        }
        inventoryClickEvent.setCurrentItem(itemStack3);
    }

    public ItemStack identify(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_UNIT").booleanValue()) {
            return itemStack;
        }
        String[] arrstring = nBTItem.getString("DIVINE_UNIT").split(":");
        String string = arrstring[1];
        int n = Integer.parseInt(arrstring[3]);
        TierManager.Tier tier = this.plugin.getMM().getTierManager().getTierById(string);
        if (tier == null) {
            return itemStack;
        }
        int n2 = Integer.parseInt(tier.getLevels().split("-")[0]);
        int n3 = Integer.parseInt(tier.getLevels().split("-")[1]);
        if (n < n2) {
            n = n2;
        } else if (n > n3) {
            n = n3;
        }
        ItemStack itemStack2 = tier.create(n, itemStack.getType());
        return itemStack2;
    }

    public class IdentifyTome {
        private String id;
        private String material;
        private String display;
        private List<String> lore;

        public IdentifyTome(String string, String string2, String string3, List<String> list) {
            this.setId(string);
            this.setMaterial(string2);
            this.setDisplay(string3);
            this.setLore(list);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
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

        public ItemStack create() {
            String[] arrstring = this.getMaterial().split(":");
            ItemStack itemStack = Utils.buildItem(arrstring, this.id);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> arrayList = new ArrayList<String>();
            itemMeta.setDisplayName(this.getDisplay());
            for (String object2 : this.getLore()) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2));
            }
            itemMeta.setLore(arrayList);
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_TOME", this.getId());
            itemStack = nBTItem.getItem();
            return new ItemStack(itemStack);
        }
    }

    public class UnidentifiedItem {
        private String id;
        private String tier;
        private String tome;
        private String display;
        private List<String> lore;

        public UnidentifiedItem(String string, String string2, String string3, String string4, List<String> list) {
            this.setId(string);
            this.setTier(string2);
            this.setTomeName(string3);
            this.setDisplay(string4);
            this.setLore(list);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public String getTier() {
            return this.tier;
        }

        public void setTier(String string) {
            this.tier = string;
        }

        public String getTomeName() {
            return this.tome;
        }

        public void setTomeName(String string) {
            this.tome = string;
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

        public ItemStack create(int n) {
            TierManager.Tier tier = IdentifyManager.this.plugin.getMM().getTierManager().getTierById(this.getTier());
            ItemStack itemStack = new ItemStack(Material.AIR);
            if (tier == null) {
                return itemStack;
            }
            if (n == -1) {
                int n2 = Integer.parseInt(tier.getLevels().split("-")[0]);
                int n3 = Integer.parseInt(tier.getLevels().split("-")[1]);
                n = Utils.randInt(n2, n3);
            }
            Random random = new Random();
            itemStack.setType(tier.getMaterials().get(random.nextInt(tier.getMaterials().size())));
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<Integer> list = tier.getDatas();
            if (tier.getDataSpecial().containsKey(itemStack.getType().name())) {
                list = tier.getDataSpecial().get(itemStack.getType().name());
            }
            if (!list.isEmpty()) {
                if (tier.isDataReversed()) {
                    itemStack.setDurability((short)list.get(random.nextInt(list.size())).intValue());
                } else {
                    int n4 = Utils.randInt(1, itemStack.getType().getMaxDurability());
                    while (list.contains(n4)) {
                        n4 = Utils.randInt(1, itemStack.getType().getMaxDurability());
                    }
                    itemStack.setDurability((short)n4);
                }
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            itemMeta.setDisplayName(this.getDisplay());
            for (String object2 : this.getLore()) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2));
            }
            itemMeta.setLore(arrayList);
            itemMeta.addItemFlags(ItemFlag.values());
            itemMeta.spigot().setUnbreakable(true);
            itemStack.setItemMeta(itemMeta);
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_UNIT", String.valueOf(this.getId()) + ":" + tier.getId() + ":" + this.getTomeName() + ":" + n);
            itemStack = nBTItem.getItem();
            return new ItemStack(itemStack);
        }
    }

}

