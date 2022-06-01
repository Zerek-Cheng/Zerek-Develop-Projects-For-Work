/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Material
 *  org.bukkit.NamespacedKey
 *  org.bukkit.Server
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemConsumeEvent
 *  org.bukkit.inventory.EquipmentSlot
 *  org.bukkit.inventory.FurnaceRecipe
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.Recipe
 *  org.bukkit.inventory.ShapedRecipe
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.ItemMeta$Spigot
 *  org.bukkit.inventory.meta.PotionMeta
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.consumes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.DivineItemsAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.ConsumesCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.nms.VersionUtils;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.Utils;

public class ConsumeManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private MyConfig settingsCfg;
    private MyConfig consCfg;
    private ConsumeSettings settings;
    private HashMap<String, Consume> consumes;
    private HashMap<String, List<ConsumeCD>> cd;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_CONS = "DIVINE_CONSUME_ID";

    public ConsumeManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
    }

    @Override
    public void loadConfig() {
        this.consumes = new HashMap();
        this.cd = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.consCfg = new MyConfig(this.plugin, "/modules/" + this.n, "consumables.yml");
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
        return true;
    }

    @Override
    public String name() {
        return "Consumables";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new ConsumesCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.consumes.clear();
            this.settings = null;
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
        this.setupConsumes();
    }

    private void setupSettings() {
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        boolean bl = fileConfiguration.getBoolean("General.PreventEatIfFullHP");
        boolean bl2 = fileConfiguration.getBoolean("General.PreventEatIfFullHunger");
        this.settings = new ConsumeSettings(bl, bl2);
    }

    private void setupConsumes() {
        FileConfiguration fileConfiguration = this.consCfg.getConfig();
        if (fileConfiguration.contains("Consumables")) {
            for (String string : fileConfiguration.getConfigurationSection("Consumables").getKeys(false)) {
                String string22;
                String string3 = "Consumables." + string + ".";
                int n = fileConfiguration.getInt(String.valueOf(string3) + "Cooldown");
                String[] arrstring = fileConfiguration.getString(String.valueOf(string3) + "Item.Material").split(":");
                Material material = Material.getMaterial((String)arrstring[0]);
                if (material == null) {
                    ErrorLog.sendError(this, String.valueOf(string3) + "Item.Material", "Invalid Material!", false);
                    continue;
                }
                ItemStack itemStack = new ItemStack(material, 1, (short)Integer.parseInt(arrstring[1]));
                String string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string3) + "Item.Display"));
                ArrayList<String> arrayList = new ArrayList<String>();
                for (String string22 : fileConfiguration.getStringList(String.valueOf(string3) + "Item.Lore")) {
                    arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)string22));
                }
                string22 = fileConfiguration.getString(String.valueOf(string3) + "Item.Extras.Color");
                boolean bl = fileConfiguration.getBoolean(String.valueOf(string3) + "Item.Extras.Enchanted");
                String string5 = fileConfiguration.getString(String.valueOf(string3) + "Item.Extras.SkullHash");
                List list = fileConfiguration.getStringList(String.valueOf(string3) + "Item.Extras.Flags");
                boolean bl2 = fileConfiguration.getBoolean(String.valueOf(string3) + "Item.Extras.Unbreakable");
                double d = fileConfiguration.getDouble(String.valueOf(string3) + "Effects.Health");
                double d2 = fileConfiguration.getDouble(String.valueOf(string3) + "Effects.Hunger");
                ArrayList<String> arrayList2 = new ArrayList<String>(fileConfiguration.getStringList(String.valueOf(string3) + "Effects.Actions"));
                boolean bl3 = fileConfiguration.getBoolean(String.valueOf(string3) + "Craft.Workbench.Enabled");
                List list2 = fileConfiguration.getStringList(String.valueOf(string3) + "Craft.Workbench.Template");
                boolean bl4 = fileConfiguration.getBoolean(String.valueOf(string3) + "Craft.Furnace.Enabled");
                String string6 = fileConfiguration.getString(String.valueOf(string3) + "Craft.Furnace.Input");
                Consume consume = new Consume(string.toLowerCase(), n, itemStack, string4, arrayList, string22, bl, string5, list, bl2, d, d2, arrayList2, bl3, list2, bl4, string6);
                consume.registerRecipes();
                this.consumes.put(string.toLowerCase(), consume);
            }
        }
    }

    public Consume getConsumeById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<Consume>(this.getConsumes()).get(this.r.nextInt(this.getConsumes().size()));
        }
        return this.consumes.get(string.toLowerCase());
    }

    public boolean isConsume(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_CONSUME_ID");
    }

    public String getConsumeId(ItemStack itemStack) {
        return new NBTItem(itemStack).getString("DIVINE_CONSUME_ID");
    }

    public Collection<Consume> getConsumes() {
        return this.consumes.values();
    }

    public List<String> getConsumeNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Consume consume : this.getConsumes()) {
            arrayList.add(consume.getId());
        }
        return arrayList;
    }

    public boolean isOnCd(Player player, String string) {
        if (!this.cd.containsKey(player.getName())) {
            return false;
        }
        for (ConsumeCD consumeCD : this.cd.get(player.getName())) {
            if (!consumeCD.getFoodId().equalsIgnoreCase(string)) continue;
            if (System.currentTimeMillis() > consumeCD.getTimeEnd()) {
                this.cd.get(player.getName()).remove(consumeCD);
                return false;
            }
            return true;
        }
        return false;
    }

    public int getCooldown(Player player, String string) {
        int n = 0;
        for (ConsumeCD consumeCD : this.cd.get(player.getName())) {
            if (!consumeCD.getFoodId().equalsIgnoreCase(string)) continue;
            return Utils.transSec(consumeCD.getTimeEnd());
        }
        return n;
    }

    public void setCd(Player player, Consume consume) {
        ArrayList<ConsumeCD> arrayList = new ArrayList<ConsumeCD>();
        if (this.cd.containsKey(player.getName())) {
            arrayList = new ArrayList(this.cd.get(player.getName()));
        }
        ConsumeCD consumeCD = new ConsumeCD(consume.getId(), System.currentTimeMillis() + (long)consume.getCooldown() * 1000L);
        arrayList.add(consumeCD);
        this.cd.put(player.getName(), arrayList);
    }

    private boolean consume(ItemStack itemStack, Player player) {
        if (!this.isConsume(itemStack)) {
            return true;
        }
        String string = this.getConsumeId(itemStack);
        Consume consume = this.getConsumeById(string);
        if (this.isOnCd(player, string)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Consumables_Cooldown.toMsg().replace("%s", new StringBuilder().append(this.getCooldown(player, string)).toString()));
            return false;
        }
        if (consume.getHealth() > 0.0 && this.settings.prevent_FullHp && player.getHealth() == player.getMaxHealth()) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Consumables_FullHp.toMsg());
            return false;
        }
        if (consume.getHunger() > 0.0 && this.settings.prevent_FullFood && player.getFoodLevel() >= 20) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Consumables_FullHunger.toMsg());
            return false;
        }
        consume.applyEffects(player);
        if (consume.getCooldown() > 0) {
            this.setCd(player, consume);
        }
        return true;
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent playerItemConsumeEvent) {
        ItemStack itemStack = playerItemConsumeEvent.getItem();
        if (!this.consume(itemStack, playerItemConsumeEvent.getPlayer())) {
            playerItemConsumeEvent.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onConsume(PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        ItemStack itemStack = playerInteractEvent.getItem();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }
        if (itemStack.getType().isEdible()) {
            return;
        }
        if (!this.consume(itemStack, playerInteractEvent.getPlayer())) {
            playerInteractEvent.setCancelled(true);
            return;
        }
        if (!this.isConsume(itemStack)) {
            return;
        }
        itemStack.setAmount(itemStack.getAmount() - 1);
    }

    public class Consume {
        private String id;
        private int cd;
        private ItemStack mat;
        private String display;
        private List<String> lore;
        private String color;
        private boolean enchant;
        private String hash;
        private List<String> flags;
        private boolean unbreak;
        private double hp;
        private double hunger;
        private List<String> actions;
        private boolean wb;
        private List<String> wb_rec;
        private boolean fur;
        private String fur_rec;

        public Consume(String string, int n, ItemStack itemStack, String string2, List<String> list, String string3, boolean bl, String string4, List<String> list2, boolean bl2, double d, double d2, List<String> list3, boolean bl3, List<String> list4, boolean bl4, String string5) {
            this.setId(string);
            this.setCooldown(n);
            this.setMaterial(itemStack);
            this.setDisplay(string2);
            this.setLore(list);
            this.setColor(string3);
            this.setEnchanted(bl);
            this.setSkullHash(string4);
            this.setFlags(list2);
            this.setUnbreakable(bl2);
            this.setHealth(d);
            this.setHunger(d2);
            this.setActions(list3);
            this.setWorkbench(bl3);
            this.setWorkbenchRecipe(list4);
            this.setFurnace(bl4);
            this.setFurnaceRecipe(string5);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public int getCooldown() {
            return this.cd;
        }

        public void setCooldown(int n) {
            this.cd = n;
        }

        public ItemStack getMaterial() {
            return this.mat;
        }

        public void setMaterial(ItemStack itemStack) {
            this.mat = itemStack;
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

        public String getColor() {
            return this.color;
        }

        public void setColor(String string) {
            this.color = string;
        }

        public boolean isEnchanted() {
            return this.enchant;
        }

        public void setEnchanted(boolean bl) {
            this.enchant = bl;
        }

        public String getSkullHash() {
            return this.hash;
        }

        public void setSkullHash(String string) {
            this.hash = string;
        }

        public List<String> getFlags() {
            return this.flags;
        }

        public void setFlags(List<String> list) {
            this.flags = list;
        }

        public boolean isUnbreakable() {
            return this.unbreak;
        }

        public void setUnbreakable(boolean bl) {
            this.unbreak = bl;
        }

        public double getHealth() {
            return this.hp;
        }

        public void setHealth(double d) {
            this.hp = d;
        }

        public double getHunger() {
            return this.hunger;
        }

        public void setHunger(double d) {
            this.hunger = d;
        }

        public List<String> getActions() {
            return this.actions;
        }

        public void setActions(List<String> list) {
            this.actions = list;
        }

        public boolean isWorkbench() {
            return this.wb;
        }

        public void setWorkbench(boolean bl) {
            this.wb = bl;
        }

        public List<String> getWorkbenchRecicpe() {
            return this.wb_rec;
        }

        public void setWorkbenchRecipe(List<String> list) {
            this.wb_rec = list;
        }

        public boolean isFurnace() {
            return this.fur;
        }

        public void setFurnace(boolean bl) {
            this.fur = bl;
        }

        public String getFurnaceRecipe() {
            return this.fur_rec;
        }

        public void setFurnaceRecipe(String string) {
            this.fur_rec = string;
        }

        public ItemStack build() {
            String[] arrstring;
            ItemStack itemStack = this.getMaterial().clone();
            UUID uUID = ConsumeManager.this.plugin.getCM().getItemHash(this.id);
            itemStack = new ItemStack(Utils.getHashed(itemStack, this.getSkullHash(), uUID));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(this.getDisplay());
            itemMeta.setLore(this.getLore());
            for (String object2 : this.getFlags()) {
                try {
                    arrstring = ItemFlag.valueOf((String)object2.toUpperCase());
                    itemMeta.addItemFlags(new ItemFlag[]{arrstring});
                }
                catch (IllegalArgumentException n) {
                    // empty catch block
                }
            }
            itemMeta.spigot().setUnbreakable(this.isUnbreakable());
            itemStack.setItemMeta(itemMeta);
            if (this.isEnchanted()) {
                itemStack.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);
            }
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_CONSUME_ID", this.getId());
            itemStack = nBTItem.getItem();
            if (itemStack.getType().name().contains("POTION")) {
                PotionMeta potionMeta = (PotionMeta)itemStack.getItemMeta();
                arrstring = this.getColor().split(",");
                int n = Integer.parseInt(arrstring[0]);
                int n2 = Integer.parseInt(arrstring[1]);
                int n3 = Integer.parseInt(arrstring[2]);
                potionMeta.setColor(Color.fromRGB((int)n, (int)n2, (int)n3));
                itemStack.setItemMeta((ItemMeta)potionMeta);
            }
            return nBTItem.getItem().clone();
        }

        public void applyEffects(Player player) {
            player.setHealth(Math.min(player.getHealth() + this.getHealth(), player.getMaxHealth()));
            player.setFoodLevel((int)((double)player.getFoodLevel() + this.getHunger()));
            DivineItemsAPI.executeActions((Entity)player, this.actions, null);
        }

        public void registerRecipes() {
            char[] arrc;
            ShapedRecipe shapedRecipe;
            Bukkit.getRecipesFor((ItemStack)this.build()).clear();
            if (this.isWorkbench()) {
                if (VersionUtils.Version.getCurrent().isHigher(VersionUtils.Version.v1_11_R1)) {
                    try {
                        arrc = new char[]((Plugin)ConsumeManager.this.plugin, "DIVINE_CONSUME_ID" + this.id);
                        shapedRecipe = new ShapedRecipe((NamespacedKey)arrc, this.build());
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        ConsumeManager.this.plugin.getServer().getConsoleSender().sendMessage("\u00a77[\u00a7c!\u00a77] \u00a7cUnable to register craft recipe for: \u00a7f" + this.id);
                        return;
                    }
                } else {
                    shapedRecipe = new ShapedRecipe(this.build());
                }
                shapedRecipe.shape(new String[]{"ABC", "DEF", "GHI"});
                arrc = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
                int n = 0;
                for (String string : this.getWorkbenchRecicpe()) {
                    String[] arrstring;
                    String[] arrstring2 = arrstring = string.split(" , ");
                    int n2 = arrstring2.length;
                    int n3 = 0;
                    while (n3 < n2) {
                        String string2 = arrstring2[n3];
                        String[] arrstring3 = string2.split(":");
                        ItemStack itemStack = new ItemStack(Material.getMaterial((String)arrstring3[0]), 1, (short)Integer.parseInt(arrstring3[1]));
                        shapedRecipe.setIngredient(arrc[n], itemStack.getData());
                        ++n;
                        ++n3;
                    }
                }
                try {
                    Bukkit.getServer().addRecipe((Recipe)shapedRecipe);
                }
                catch (IllegalStateException illegalStateException) {
                    // empty catch block
                }
            }
            if (this.isFurnace()) {
                shapedRecipe = this.getFurnaceRecipe().split(":");
                arrc = new ItemStack(Material.getMaterial((String)shapedRecipe[0]), 1, (short)Integer.parseInt((String)shapedRecipe[1]));
                FurnaceRecipe furnaceRecipe = new FurnaceRecipe(this.build(), arrc.getData());
                try {
                    Bukkit.getServer().addRecipe((Recipe)furnaceRecipe);
                }
                catch (IllegalStateException illegalStateException) {
                    // empty catch block
                }
            }
        }
    }

    public class ConsumeCD {
        private String id;
        private long time;

        public ConsumeCD(String string, long l) {
            this.id = string;
            this.time = l;
        }

        public String getFoodId() {
            return this.id;
        }

        public long getTimeEnd() {
            return this.time;
        }
    }

    public class ConsumeSettings {
        private boolean prevent_FullHp;
        private boolean prevent_FullFood;

        public ConsumeSettings(boolean bl, boolean bl2) {
            this.setPreventOnFullHp(bl);
            this.setPreventOnFullHunger(bl2);
        }

        public boolean isPreventOnFullHp() {
            return this.prevent_FullHp;
        }

        public void setPreventOnFullHp(boolean bl) {
            this.prevent_FullHp = bl;
        }

        public boolean isPreventOnFullHunger() {
            return this.prevent_FullFood;
        }

        public void setPreventOnFullHunger(boolean bl) {
            this.prevent_FullFood = bl;
        }
    }

}

