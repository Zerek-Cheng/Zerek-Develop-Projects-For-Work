/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package su.nightexpress.divineitems.modules.resolve;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.ResolveMainCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.ability.AbilityManager;
import su.nightexpress.divineitems.modules.abyssdust.AbyssDustManager;
import su.nightexpress.divineitems.modules.arrows.ArrowManager;
import su.nightexpress.divineitems.modules.enchant.EnchantManager;
import su.nightexpress.divineitems.modules.gems.GemManager;
import su.nightexpress.divineitems.modules.magicdust.MagicDustManager;
import su.nightexpress.divineitems.modules.runes.RuneManager;
import su.nightexpress.divineitems.modules.scrolls.ScrollManager;
import su.nightexpress.divineitems.modules.tiers.TierManager;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.Utils;

public class ResolveManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private MyConfig settingsCfg;
    private ResolveSettings ss;
    private HashMap<String, HashMap<String, ResolveObject>> objects;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_RES = "RESOLVE_2206";

    public ResolveManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.setup();
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
        return "Resolve";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new ResolveMainCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.ss = null;
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    public void setup() {
        this.setupSettings();
        this.setupObjects();
    }

    private void setupObjects() {
        this.objects = new HashMap();
        for (Module module : this.plugin.getMM().getModules()) {
            if (!module.isDropable() || !module.isActive()) continue;
            String string = module.name().toLowerCase().replace(" ", "_");
            File file = new File(this.plugin.getDataFolder() + "/modules/resolve/", String.valueOf(string) + ".yml");
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
            if (!file.exists()) continue;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            if (yamlConfiguration.contains("Objects")) {
                for (String string2 : yamlConfiguration.getConfigurationSection("Objects").getKeys(false)) {
                    boolean bl;
                    String string3;
                    Object object;
                    ArrayList<String> arrayList;
                    String string4;
                    Object object22;
                    Object object32;
                    short s;
                    Object object4;
                    int n;
                    double d;
                    Material material;
                    String string5 = "Objects." + string2 + ".Out.";
                    ArrayList<ResolveItem> arrayList2 = new ArrayList<ResolveItem>();
                    ArrayList<ResolveCommand> arrayList3 = new ArrayList<ResolveCommand>();
                    if (yamlConfiguration.contains(String.valueOf(string5) + "Items")) {
                        for (Object object32 : yamlConfiguration.getConfigurationSection(String.valueOf(string5) + "Items").getKeys(false)) {
                            Object object5;
                            string4 = String.valueOf(string5) + "Items." + (String)object32 + ".";
                            if (!yamlConfiguration.contains(String.valueOf(string4) + "Chance")) {
                                yamlConfiguration.set(String.valueOf(string4) + "Chance", (Object)100.0);
                            }
                            d = yamlConfiguration.getDouble(String.valueOf(string4) + "Chance");
                            material = Material.getMaterial((String)yamlConfiguration.getString(String.valueOf(string4) + "Material").toUpperCase());
                            if (material == null) {
                                ErrorLog.sendError(this, String.valueOf(string4) + "Material", "Invalid material!", false);
                                continue;
                            }
                            s = (short)yamlConfiguration.getInt(String.valueOf(string4) + "Data");
                            n = yamlConfiguration.getInt(String.valueOf(string4) + "Amount");
                            bl = yamlConfiguration.getBoolean(String.valueOf(string4) + "Meta.Enabled");
                            string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)yamlConfiguration.getString(String.valueOf(string4) + "Meta.Name"));
                            arrayList = new ArrayList<String>();
                            for (Object object22 : yamlConfiguration.getStringList(String.valueOf(string4) + "Meta.Lore")) {
                                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object22));
                            }
                            object22 = yamlConfiguration.getStringList(String.valueOf(string4) + "Meta.Flags");
                            object = new HashMap();
                            if (yamlConfiguration.contains(String.valueOf(string4) + "Meta.Enchants")) {
                                for (String string6 : yamlConfiguration.getConfigurationSection(String.valueOf(string4) + "Meta.Enchants").getKeys(false)) {
                                    object5 = Enchantment.getByName((String)string6.toUpperCase());
                                    if (object5 == null) {
                                        ErrorLog.sendError(this, String.valueOf(string4) + "Meta.Enchants", "Invalid enchantment!", false);
                                        continue;
                                    }
                                    int n2 = yamlConfiguration.getInt(String.valueOf(string4) + "Meta.Enchants." + string6);
                                    object.put(object5, n2);
                                }
                            }
                            boolean bl2 = yamlConfiguration.getBoolean(String.valueOf(string4) + "Meta.Unbreakable");
                            object4 = new ItemStack(material, n, s);
                            if (bl) {
                                object5 = object4.getItemMeta();
                                object5.setDisplayName(string3);
                                object5.setLore(arrayList);
                                Iterator<Object> iterator = object22.iterator();
                                while (iterator.hasNext()) {
                                    String string7 = (String)iterator.next();
                                    try {
                                        object5.addItemFlags(new ItemFlag[]{ItemFlag.valueOf((String)string7.toUpperCase())});
                                    }
                                    catch (IllegalArgumentException illegalArgumentException) {
                                        ErrorLog.sendError(this, String.valueOf(string4) + "Meta.Flags." + string7, "Invalid item flag!", false);
                                    }
                                }
                                object5.spigot().setUnbreakable(bl2);
                                object4.setItemMeta(object5);
                                for (Enchantment enchantment : object.keySet()) {
                                    object4.addUnsafeEnchantment(enchantment, ((Integer)object.get((Object)enchantment)).intValue());
                                }
                            }
                            object5 = new ResolveItem((String)object32, d, (ItemStack)object4);
                            arrayList2.add((ResolveItem)object5);
                        }
                    }
                    if (yamlConfiguration.contains(String.valueOf(string5) + "Commands")) {
                        for (Object object32 : yamlConfiguration.getConfigurationSection(String.valueOf(string5) + "Commands").getKeys(false)) {
                            string4 = String.valueOf(string5) + "Commands." + (String)object32 + ".";
                            if (!yamlConfiguration.contains(String.valueOf(string4) + "Chance")) {
                                yamlConfiguration.set(String.valueOf(string4) + "Chance", (Object)100.0);
                            }
                            d = yamlConfiguration.getDouble(String.valueOf(string4) + "Chance");
                            material = Material.getMaterial((String)yamlConfiguration.getString(String.valueOf(string4) + "Display.Material").toUpperCase());
                            if (material == null) {
                                ErrorLog.sendError(this, String.valueOf(string4) + "Display.Material", "Invalid material!", false);
                                continue;
                            }
                            s = (short)yamlConfiguration.getInt(String.valueOf(string4) + "Display.Data");
                            n = yamlConfiguration.getInt(String.valueOf(string4) + "Display.Amount");
                            bl = yamlConfiguration.getBoolean(String.valueOf(string4) + "Display.Enchanted");
                            string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)yamlConfiguration.getString(String.valueOf(string4) + "Display.Name"));
                            arrayList = new ArrayList();
                            for (Object object22 : yamlConfiguration.getStringList(String.valueOf(string4) + "Display.Lore")) {
                                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object22));
                            }
                            object22 = new ItemStack(material, n, s);
                            object = object22.getItemMeta();
                            object.setDisplayName(string3);
                            object.setLore(arrayList);
                            object.addItemFlags(ItemFlag.values());
                            object.spigot().setUnbreakable(true);
                            object22.setItemMeta((ItemMeta)object);
                            if (bl) {
                                object22.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
                            }
                            List list = yamlConfiguration.getStringList(String.valueOf(string4) + "Commands");
                            object4 = new ResolveCommand((String)object32, d, (ItemStack)object22, list);
                            arrayList3.add((ResolveCommand)object4);
                        }
                    }
                    object32 = new ResolveObject(string2.toLowerCase(), arrayList2, arrayList3);
                    hashMap.put(string2.toLowerCase(), object32);
                }
                try {
                    yamlConfiguration.save(file);
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
            this.objects.put(string, hashMap);
        }
    }

    private void setupSettings() {
        Object object2;
        String string6;
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        String string2 = org.bukkit.ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("GUI.Title"));
        String string3 = "GUI.ResolveButton.Resolve.";
        Material material = Material.getMaterial((String)fileConfiguration.getString(String.valueOf(string3) + "Material").toUpperCase());
        short s = (short)fileConfiguration.getInt(String.valueOf(string3) + "Data");
        if (material == null) {
            material = Material.STAINED_GLASS_PANE;
            ErrorLog.sendError(this, String.valueOf(string3) + "Resolve", "Invalid material!", true);
        }
        String string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string3) + "Name"));
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string5 : fileConfiguration.getStringList(String.valueOf(string3) + "Lore")) {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string5));
        }
        boolean bl = fileConfiguration.getBoolean(String.valueOf(string3) + "Enchanted");
        ItemStack itemStack = new ItemStack(material, 1, s);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(string4);
        itemMeta.setLore(arrayList);
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        if (bl) {
            itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        }
        string3 = "GUI.ResolveButton.Unresolvable.";
        material = Material.getMaterial((String)fileConfiguration.getString(String.valueOf(string3) + "Material").toUpperCase());
        s = (short)fileConfiguration.getInt(String.valueOf(string3) + "Data");
        if (material == null) {
            material = Material.STAINED_GLASS_PANE;
            ErrorLog.sendError(this, String.valueOf(string3) + "Resolve", "Invalid material!", true);
        }
        string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string3) + "Name"));
        arrayList = new ArrayList();
        for (String string6 : fileConfiguration.getStringList(String.valueOf(string3) + "Lore")) {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string6));
        }
        bl = fileConfiguration.getBoolean(String.valueOf(string3) + "Enchanted");
        string6 = new ItemStack(material, 1, s);
        itemMeta = string6.getItemMeta();
        itemMeta.setDisplayName(string4);
        itemMeta.setLore(arrayList);
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.spigot().setUnbreakable(true);
        string6.setItemMeta(itemMeta);
        if (bl) {
            string6.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        }
        string3 = "GUI.ResolveButton.Nothing.";
        material = Material.getMaterial((String)fileConfiguration.getString(String.valueOf(string3) + "Material").toUpperCase());
        s = (short)fileConfiguration.getInt(String.valueOf(string3) + "Data");
        if (material == null) {
            material = Material.STAINED_GLASS_PANE;
            ErrorLog.sendError(this, String.valueOf(string3) + "Resolve", "Invalid material!", true);
        }
        string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string3) + "Name"));
        arrayList = new ArrayList();
        for (Object object2 : fileConfiguration.getStringList(String.valueOf(string3) + "Lore")) {
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2));
        }
        bl = fileConfiguration.getBoolean(String.valueOf(string3) + "Enchanted");
        object2 = new ItemStack(material, 1, s);
        itemMeta = object2.getItemMeta();
        itemMeta.setDisplayName(string4);
        itemMeta.setLore(arrayList);
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.spigot().setUnbreakable(true);
        object2.setItemMeta(itemMeta);
        if (bl) {
            object2.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        }
        this.ss = new ResolveSettings(false, string2, itemStack, (ItemStack)string6, (ItemStack)object2);
    }

    public void openResolveGUI(Player player) {
        Inventory inventory = this.plugin.getServer().createInventory(null, 27, this.ss.getGUITitle());
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        inventory.setItem(0, itemStack);
        inventory.setItem(6, itemStack);
        inventory.setItem(1, itemStack);
        inventory.setItem(7, itemStack);
        inventory.setItem(2, itemStack);
        inventory.setItem(8, itemStack);
        inventory.setItem(9, itemStack);
        inventory.setItem(11, itemStack);
        inventory.setItem(15, itemStack);
        inventory.setItem(17, itemStack);
        inventory.setItem(18, itemStack);
        inventory.setItem(24, itemStack);
        inventory.setItem(19, itemStack);
        inventory.setItem(25, itemStack);
        inventory.setItem(20, itemStack);
        inventory.setItem(26, itemStack);
        int[] arrn = new int[]{3, 4, 5, 12, 13, 14, 21, 22, 23};
        ItemStack itemStack2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, 15);
        int[] arrn2 = arrn;
        int n = arrn2.length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = arrn2[n2];
            inventory.setItem(n3, itemStack2);
            ++n2;
        }
        inventory.setItem(16, this.ss.getButtonX());
        player.openInventory(inventory);
    }

    private void update(Inventory inventory) {
        int[] arrn = new int[]{3, 4, 5, 12, 13, 14, 21, 22, 23};
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, 15);
        Object object2 = arrn;
        int n = ((int[])object2).length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = object2[n2];
            inventory.setItem(n3, itemStack);
            ++n2;
        }
        ItemStack itemStack2 = inventory.getItem(10);
        if (itemStack2 == null || itemStack2.getType() == Material.AIR) {
            inventory.setItem(16, this.ss.getButtonX());
            return;
        }
        DivineListener divineListener = null;
        String string = "";
        if (this.plugin.getMM().getAbilityManager().isAbility(itemStack2)) {
            string = this.plugin.getMM().getAbilityManager().getAbilityId(itemStack2);
            divineListener = this.plugin.getMM().getAbilityManager();
        } else if (this.plugin.getMM().getAbyssDustManager().isAbyssDust(itemStack2)) {
            string = this.plugin.getMM().getAbyssDustManager().getDustId(itemStack2);
            divineListener = this.plugin.getMM().getAbyssDustManager();
        } else if (this.plugin.getMM().getArrowManager().isDivineArrow(itemStack2)) {
            string = this.plugin.getMM().getArrowManager().getArrowId(itemStack2);
            divineListener = this.plugin.getMM().getArrowManager();
        } else if (this.plugin.getMM().getEnchantManager().isEnchant(itemStack2)) {
            string = this.plugin.getMM().getEnchantManager().getEnchantBookId(itemStack2);
            divineListener = this.plugin.getMM().getEnchantManager();
        } else if (this.plugin.getMM().getGemManager().isGem(itemStack2)) {
            string = this.plugin.getMM().getGemManager().getGemId(itemStack2);
            divineListener = this.plugin.getMM().getGemManager();
        } else if (this.plugin.getMM().getMagicDustManager().isMagicDust(itemStack2)) {
            string = this.plugin.getMM().getMagicDustManager().getDustId(itemStack2);
            divineListener = this.plugin.getMM().getMagicDustManager();
        } else if (this.plugin.getMM().getRuneManager().isRune(itemStack2)) {
            string = this.plugin.getMM().getRuneManager().getRuneId(itemStack2);
            divineListener = this.plugin.getMM().getRuneManager();
        } else if (this.plugin.getMM().getScrollManager().isScroll(itemStack2)) {
            string = this.plugin.getMM().getScrollManager().getScrollId(itemStack2);
            divineListener = this.plugin.getMM().getScrollManager();
        } else if (this.plugin.getMM().getTierManager().isTiered(itemStack2)) {
            string = this.plugin.getMM().getTierManager().getTierId(itemStack2);
            divineListener = this.plugin.getMM().getTierManager();
        }
        if (divineListener == null || string.isEmpty()) {
            inventory.setItem(16, this.ss.getButtonNo());
            return;
        }
        object2 = divineListener.name().toLowerCase().replace(" ", "_");
        ResolveObject resolveObject = this.objects.get(object2).get(string.toLowerCase());
        if (resolveObject == null) {
            inventory.setItem(16, this.ss.getButtonNo());
            return;
        }
        int n4 = 0;
        for (ResolveItem object3 : resolveObject.getItems()) {
            if (n4 >= 9) break;
            inventory.setItem(arrn[n4], object3.getItem());
            ++n4;
        }
        for (ResolveCommand resolveCommand : resolveObject.getCommands()) {
            if (n4 >= 9) break;
            inventory.setItem(arrn[n4], resolveCommand.getDisplayItem());
            ++n4;
        }
        ItemStack itemStack3 = this.ss.getButtonYes();
        NBTItem nBTItem = new NBTItem(itemStack3);
        nBTItem.setString("RESOLVE_2206", String.valueOf(object2) + ":" + string.toLowerCase());
        inventory.setItem(16, nBTItem.getItem());
    }

    @EventHandler
    public void onClick(final InventoryClickEvent inventoryClickEvent) {
        if (!inventoryClickEvent.getInventory().getTitle().equals(this.ss.getGUITitle())) {
            return;
        }
        int n = inventoryClickEvent.getRawSlot();
        if (n == 10 || n > 26) {
            inventoryClickEvent.setCancelled(false);
        } else {
            inventoryClickEvent.setCancelled(true);
        }
        if (n > 26 && inventoryClickEvent.isShiftClick()) {
            inventoryClickEvent.setCancelled(true);
        }
        if (n == 16) {
            double d;
            ItemStack itemStack = inventoryClickEvent.getCurrentItem();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                return;
            }
            NBTItem nBTItem = new NBTItem(itemStack);
            if (!nBTItem.hasKey("RESOLVE_2206").booleanValue()) {
                return;
            }
            Player player = (Player)inventoryClickEvent.getWhoClicked();
            String string = nBTItem.getString("RESOLVE_2206").split(":")[0];
            String string2 = nBTItem.getString("RESOLVE_2206").split(":")[1];
            ResolveObject resolveObject = this.objects.get(string).get(string2.toLowerCase());
            if (resolveObject != null && resolveObject.getItems() != null) {
                for (ResolveItem object2 : resolveObject.getItems()) {
                    d = Utils.getRandDouble(0.0, 100.0);
                    if (d > object2.getChance()) continue;
                    ItemStack itemStack2 = object2.getItem();
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStack2);
                        continue;
                    }
                    player.getInventory().addItem(new ItemStack[]{itemStack2});
                }
            }
            if (resolveObject != null && resolveObject.getCommands() != null) {
                for (ResolveCommand resolveCommand : resolveObject.getCommands()) {
                    d = Utils.getRandDouble(0.0, 100.0);
                    if (d > resolveCommand.getChance()) continue;
                    for (String string3 : resolveCommand.getCommands()) {
                        this.plugin.getServer().dispatchCommand((CommandSender)this.plugin.getServer().getConsoleSender(), string3.replace("%p", player.getName()));
                    }
                }
            }
            ItemStack itemStack3 = inventoryClickEvent.getInventory().getItem(10);
            Object object4 = itemStack3.getType().name();
            if (itemStack3.hasItemMeta() && itemStack3.getItemMeta().hasDisplayName()) {
                object4 = itemStack3.getItemMeta().getDisplayName();
            } else {
                this.plugin.getCM().getDefaultItemName(itemStack3);
            }
            this.plugin.getNMS().sendTitles(player, Lang.Resolve_Title_Resolved.toMsg(), Lang.Resolve_SubTitle_Resolved.toMsg().replace("%item%", (CharSequence)object4), 10, 40, 10);
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
            inventoryClickEvent.getInventory().setItem(10, new ItemStack(Material.AIR));
            player.closeInventory();
            return;
        }
        new BukkitRunnable(){

            public void run() {
                ResolveManager.this.update(inventoryClickEvent.getInventory());
            }
        }.runTaskLater((Plugin)this.plugin, 1L);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        if (!inventoryCloseEvent.getInventory().getTitle().equals(this.ss.getGUITitle())) {
            return;
        }
        Player player = (Player)inventoryCloseEvent.getPlayer();
        ItemStack itemStack = inventoryCloseEvent.getInventory().getItem(10);
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(new ItemStack[]{itemStack});
        }
    }

    public class ResolveCommand {
        private String id;
        private double chance;
        private ItemStack item;
        private List<String> cmds;

        public ResolveCommand(String string, double d, ItemStack itemStack, List<String> list) {
            this.setId(string);
            this.setChance(d);
            this.setDisplayItem(itemStack);
            this.setCommands(list);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public double getChance() {
            return this.chance;
        }

        public void setChance(double d) {
            this.chance = d;
        }

        public ItemStack getDisplayItem() {
            return this.item.clone();
        }

        public void setDisplayItem(ItemStack itemStack) {
            this.item = itemStack.clone();
        }

        public List<String> getCommands() {
            return this.cmds;
        }

        public void setCommands(List<String> list) {
            this.cmds = list;
        }
    }

    public class ResolveItem {
        private String id;
        private double chance;
        private ItemStack item;

        public ResolveItem(String string, double d, ItemStack itemStack) {
            this.setId(string);
            this.setChance(d);
            this.setItem(itemStack);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public double getChance() {
            return this.chance;
        }

        public void setChance(double d) {
            this.chance = d;
        }

        public ItemStack getItem() {
            return this.item.clone();
        }

        public void setItem(ItemStack itemStack) {
            this.item = itemStack.clone();
        }
    }

    public class ResolveObject {
        private String id;
        private List<ResolveItem> items;
        private List<ResolveCommand> cmds;

        public ResolveObject(String string, List<ResolveItem> list, List<ResolveCommand> list2) {
            this.setId(string);
            this.setItems(list);
            this.setCommands(list2);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public List<ResolveItem> getItems() {
            return this.items;
        }

        public void setItems(List<ResolveItem> list) {
            this.items = list;
        }

        public List<ResolveCommand> getCommands() {
            return this.cmds;
        }

        public void setCommands(List<ResolveCommand> list) {
            this.cmds = list;
        }
    }

    public class ResolveSettings {
        private boolean g_autoFill;
        private String gui_title;
        private ItemStack button_yes;
        private ItemStack button_no;
        private ItemStack button_x;

        public ResolveSettings(boolean bl, String string, ItemStack itemStack, ItemStack itemStack2, ItemStack itemStack3) {
            this.setAutoFill(bl);
            this.setGUITitle(string);
            this.setButtonYes(itemStack);
            this.setButtonNo(itemStack2);
            this.setButtonX(itemStack3);
        }

        public boolean isAutoFill() {
            return this.g_autoFill;
        }

        public void setAutoFill(boolean bl) {
            this.g_autoFill = bl;
        }

        public String getGUITitle() {
            return this.gui_title;
        }

        public void setGUITitle(String string) {
            this.gui_title = string;
        }

        public ItemStack getButtonYes() {
            return this.button_yes.clone();
        }

        public void setButtonYes(ItemStack itemStack) {
            this.button_yes = itemStack.clone();
        }

        public ItemStack getButtonNo() {
            return this.button_no.clone();
        }

        public void setButtonNo(ItemStack itemStack) {
            this.button_no = itemStack.clone();
        }

        public ItemStack getButtonX() {
            return this.button_x.clone();
        }

        public void setButtonX(ItemStack itemStack) {
            this.button_x = itemStack.clone();
        }
    }

}

