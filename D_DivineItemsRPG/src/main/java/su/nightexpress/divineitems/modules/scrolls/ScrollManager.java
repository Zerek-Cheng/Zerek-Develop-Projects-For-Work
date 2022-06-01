/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.inventory.EquipmentSlot
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
package su.nightexpress.divineitems.modules.scrolls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
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
import su.nightexpress.divineitems.api.DivineItemsAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.ScrollsCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.utils.ActionTitle;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.Utils;

public class ScrollManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private MyConfig settingsCfg;
    private MyConfig scrollsCfg;
    private HashMap<String, Scroll> scrolls;
    private HashMap<Player, Set<ScrollCD>> scd;
    private HashMap<Player, Cast> cast;
    private ScrollSettings ss;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_SCROLL = "DIVINE_SCROLL_ID";
    private final String NBT_KEY_USES = "DIVINE_SCROLL_USES";

    public ScrollManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
        this.scd = new HashMap();
        this.cast = new HashMap();
    }

    @Override
    public void loadConfig() {
        this.scrolls = new HashMap();
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.scrollsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "scrolls.yml");
        this.setupSettings();
        this.setupScrolls();
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
        return "Scrolls";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new ScrollsCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            for (Cast cast : this.cast.values()) {
                cast.stap();
            }
            this.scd.clear();
            this.cast.clear();
            this.scd.clear();
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
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        String string = ChatColor.stripColor((String)fileConfiguration.getString("Usage.Bar.Symbol"));
        String string2 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Usage.Bar.FirstColor"));
        String string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Usage.Bar.SecondColor"));
        boolean bl = fileConfiguration.getBoolean("Usage.Cancel.OnMove");
        boolean bl2 = fileConfiguration.getBoolean("Usage.Cancel.OnInteract");
        boolean bl3 = fileConfiguration.getBoolean("Usage.Cancel.OnDrop");
        boolean bl4 = fileConfiguration.getBoolean("Usage.Cancel.OnInventory");
        this.ss = new ScrollSettings(string, string2, string3, bl, bl2, bl3, bl4);
    }

    private void setupScrolls() {
        FileConfiguration fileConfiguration = this.scrollsCfg.getConfig();
        for (String string : fileConfiguration.getConfigurationSection("Scrolls").getKeys(false)) {
            Object object;
            Object object22;
            Object object32;
            String string2 = string.toString();
            String string3 = "Scrolls." + string2 + ".";
            String[] arrstring = fileConfiguration.getString(String.valueOf(string3) + "Material").split(":");
            short s = 0;
            Material material = Material.getMaterial((String)arrstring[0]);
            if (material == null) {
                material = Material.MAP;
                ErrorLog.sendError(this, String.valueOf(string3) + "Material", "Invalid Material name!", true);
                fileConfiguration.set(String.valueOf(string3) + "Material", (Object)"MAP");
            }
            if (arrstring.length == 2) {
                s = (short)Integer.parseInt(arrstring[1]);
            }
            ItemStack itemStack = new ItemStack(material, 1, s);
            String string4 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string3) + "Display"));
            List list = fileConfiguration.getStringList(String.valueOf(string3) + "Lore");
            ArrayList<String> arrayList = new ArrayList<String>();
            for (Object object22 : list) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object22));
            }
            object22 = new HashMap();
            if (fileConfiguration.contains(String.valueOf(string3) + "Variables")) {
                for (Object object32 : fileConfiguration.getConfigurationSection(String.valueOf(string3) + "Variables").getKeys(false)) {
                    Object object4 = fileConfiguration.get(String.valueOf(string3) + "Variables." + (String)object32);
                    object22.put(object32, object4);
                }
            }
            object32 = new HashMap();
            if (fileConfiguration.contains(String.valueOf(string3) + "VariablesPerLvl")) {
                for (Object object5 : fileConfiguration.getConfigurationSection(String.valueOf(string3) + "VariablesPerLvl").getKeys(false)) {
                    object = fileConfiguration.get(String.valueOf(string3) + "VariablesPerLvl." + (String)object5);
                    object32.put(object5, object);
                }
            }
            int n = fileConfiguration.getInt(String.valueOf(string3) + "MinLevel");
            int n2 = fileConfiguration.getInt(String.valueOf(string3) + "MaxLevel");
            object = fileConfiguration.getStringList(String.valueOf(string3) + "Actions");
            int n3 = fileConfiguration.getInt(String.valueOf(string3) + "UseTime");
            int n4 = fileConfiguration.getInt(String.valueOf(string3) + "Cooldown");
            int n5 = fileConfiguration.getInt(String.valueOf(string3) + "Uses");
            Scroll scroll = new Scroll(string2.toLowerCase(), itemStack, string4, arrayList, (HashMap<String, Object>)object22, (HashMap<String, Object>)object32, n, n2, (List<String>)object, n3, n4, n5);
            this.scrolls.put(string2.toLowerCase(), scroll);
        }
        this.scrollsCfg.save();
    }

    public boolean isScroll(ItemStack itemStack) {
        return new NBTItem(itemStack).hasKey("DIVINE_SCROLL_ID");
    }

    public String getScrollId(ItemStack itemStack) {
        return new NBTItem(itemStack).getString("DIVINE_SCROLL_ID").split(":")[0];
    }

    public int getScrollLvl(ItemStack itemStack) {
        return Integer.parseInt(new NBTItem(itemStack).getString("DIVINE_SCROLL_ID").split(":")[1]);
    }

    public ItemStack takeUse(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_SCROLL_USES").booleanValue()) {
            return itemStack;
        }
        int n = nBTItem.getInteger("DIVINE_SCROLL_USES") - 1;
        if (n <= 0) {
            return new ItemStack(Material.AIR);
        }
        String[] arrstring = nBTItem.getString("DIVINE_SCROLL_ID").split(":");
        String string = arrstring[0];
        int n2 = Integer.parseInt(arrstring[1]);
        Scroll scroll = new Scroll(this.getScrollById(string));
        ArrayList<String> arrayList = new ArrayList<String>();
        nBTItem.setInteger("DIVINE_SCROLL_USES", n);
        itemStack = nBTItem.getItem();
        for (String string2 : scroll.getLore()) {
            void object2;
            for (String string3 : scroll.getVariables().keySet()) {
                String string4 = this.replaceVars((String)object2, string3, scroll, n2);
            }
            arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2.replace("%lvl%", String.valueOf(n2)).replace("%rlvl%", Utils.IntegerToRomanNumeral(n2)).replace("%uses%", String.valueOf(n))));
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(arrayList);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void setCD(Player player, String string) {
        if (!this.scrolls.containsKey(string = string.toLowerCase()) || this.isOnCooldown(player, string)) {
            return;
        }
        Scroll scroll = this.scrolls.get(string);
        ScrollCD scrollCD = new ScrollCD(string, System.currentTimeMillis() + (long)scroll.getCD() * 1000L);
        Set<ScrollCD> set = new HashSet<ScrollCD>();
        if (this.scd.containsKey((Object)player)) {
            set = this.scd.get((Object)player);
        }
        set.add(scrollCD);
        this.scd.put(player, set);
    }

    public boolean isOnCooldown(Player player, String string) {
        if (!this.scd.containsKey((Object)player)) {
            return false;
        }
        Set<ScrollCD> set = this.scd.get((Object)player);
        for (ScrollCD scrollCD : set) {
            if (!scrollCD.getScrollId().equalsIgnoreCase(string)) continue;
            if (scrollCD.getTimeEnd() > System.currentTimeMillis()) {
                return true;
            }
            set.remove(scrollCD);
            break;
        }
        if (set.isEmpty()) {
            this.scd.remove((Object)player);
        }
        return false;
    }

    public int getScrollCD(Player player, String string) {
        int n = 0;
        if (!this.isOnCooldown(player, string)) {
            return n;
        }
        Set<ScrollCD> set = this.scd.get((Object)player);
        for (ScrollCD scrollCD : set) {
            if (!scrollCD.getScrollId().equalsIgnoreCase(string)) continue;
            n = (int)((scrollCD.getTimeEnd() - System.currentTimeMillis()) / 1000L);
        }
        return n;
    }

    public Scroll getScrollById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<Scroll>(this.getScrolls()).get(this.r.nextInt(this.getScrolls().size()));
        }
        return this.scrolls.get(string.toLowerCase());
    }

    public Collection<Scroll> getScrolls() {
        return this.scrolls.values();
    }

    public List<String> getScrollNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Scroll scroll : this.getScrolls()) {
            arrayList.add(scroll.getId());
        }
        return arrayList;
    }

    public ScrollSettings getSettings() {
        return this.ss;
    }

    private String replaceVars(String string, String string2, Scroll scroll, int n) {
        Object object = scroll.getVariables().get(string2);
        String string3 = object.toString();
        if (scroll.getVariablesLvl().containsKey(string2)) {
            if (object instanceof Double || object instanceof Integer) {
                double d = Double.parseDouble(object.toString());
                string3 = String.valueOf(Utils.round3(d += (double)(n - 1) * Double.parseDouble(scroll.getVariablesLvl().get(string2).toString())));
            } else {
                string3 = scroll.getVariablesLvl().get(string2).toString();
            }
        }
        return string.replace("%var_" + string2 + "%", string3);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent playerInteractEvent) {
        if (!playerInteractEvent.getAction().name().contains("RIGHT") && playerInteractEvent.getAction() != Action.PHYSICAL) {
            return;
        }
        if (playerInteractEvent.getItem() == null || playerInteractEvent.getItem().getType() == Material.AIR) {
            return;
        }
        ItemStack itemStack = new ItemStack(playerInteractEvent.getItem());
        NBTItem nBTItem = new NBTItem(itemStack);
        if (!nBTItem.hasKey("DIVINE_SCROLL_ID").booleanValue()) {
            return;
        }
        Player player = playerInteractEvent.getPlayer();
        if (!ItemAPI.canUse(itemStack, player)) {
            return;
        }
        playerInteractEvent.setCancelled(true);
        if (this.cast.containsKey((Object)player)) {
            return;
        }
        String[] arrstring = nBTItem.getString("DIVINE_SCROLL_ID").split(":");
        String string = arrstring[0];
        int n = 1;
        if (arrstring.length == 2) {
            n = Integer.parseInt(arrstring[1]);
        }
        if (this.isOnCooldown(player, string)) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Scrolls_Cooldown.toMsg().replace("%s", new StringBuilder(String.valueOf(this.getScrollCD(player, string))).toString()));
            return;
        }
        if (this.getScrollById(string) == null) {
            player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_Internal.toMsg());
            return;
        }
        Scroll scroll = new Scroll(this.getScrollById(string));
        ArrayList<String> arrayList = new ArrayList<String>(scroll.getActions());
        ArrayList<String> arrayList2 = new ArrayList<String>();
        for (String string2 : arrayList) {
            for (String string3 : scroll.getVariables().keySet()) {
                string2 = this.replaceVars(string2, string3, scroll, n);
            }
            arrayList2.add(string2);
        }
        scroll.setActions(arrayList2);
        if (playerInteractEvent.getHand() == EquipmentSlot.OFF_HAND) {
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.getInventory().setItemInOffHand(itemStack);
        } else {
            itemStack.setAmount(1);
            player.getInventory().removeItem(new ItemStack[]{itemStack});
        }
        itemStack.setAmount(1);
        if (scroll.getUseTime() > 0) {
            this.goCast(player, scroll, itemStack);
        } else {
            DivineItemsAPI.executeActions((Entity)player, arrayList2, itemStack);
            this.setCD(player, string);
            player.getInventory().addItem(new ItemStack[]{this.takeUse(itemStack)});
        }
    }

    public void goCast(Player player, Scroll scroll, ItemStack itemStack) {
        double d = 20 / scroll.getUseTime();
        double d2 = 20.0 / d;
        Cast cast = new Cast(player, scroll, itemStack, (int)d2);
        cast.runTaskTimer((Plugin)DivineItems.instance, 0L, (long)((int)d2));
        this.cast.put(player, cast);
    }

    @EventHandler(ignoreCancelled=true)
    public void onTp(PlayerTeleportEvent playerTeleportEvent) {
        if (!this.getSettings().isCancelOnMove()) {
            return;
        }
        Player player = playerTeleportEvent.getPlayer();
        if (this.cast.containsKey((Object)player)) {
            Cast cast = this.cast.get((Object)player);
            cast.stap();
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onDrop(PlayerDropItemEvent playerDropItemEvent) {
        if (!this.getSettings().isCancelOnDrop()) {
            return;
        }
        Player player = playerDropItemEvent.getPlayer();
        if (this.cast.containsKey((Object)player)) {
            Cast cast = this.cast.get((Object)player);
            cast.stap();
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onMove(InventoryOpenEvent inventoryOpenEvent) {
        if (!this.getSettings().isCancelOnInv()) {
            return;
        }
        Player player = (Player)inventoryOpenEvent.getPlayer();
        if (this.cast.containsKey((Object)player)) {
            Cast cast = this.cast.get((Object)player);
            cast.stap();
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onInt(PlayerInteractEvent playerInteractEvent) {
        if (!this.getSettings().isCancelOnInteract()) {
            return;
        }
        Player player = playerInteractEvent.getPlayer();
        if (this.cast.containsKey((Object)player)) {
            Cast cast = this.cast.get((Object)player);
            cast.stap();
        }
    }

    public class Cast
    extends BukkitRunnable {
        DivineItems plugin = DivineItems.instance;
        Player p;
        Scroll scroll;
        int i = 0;
        int j;
        String bar;
        Location start;
        ItemStack item;

        public Cast(Player player, Scroll scroll, ItemStack itemStack, int n) {
            this.j = n;
            this.p = player;
            this.start = player.getLocation();
            this.scroll = scroll;
            this.item = itemStack;
            String string = ScrollManager.this.getSettings().getBarSymbol();
            StringBuffer stringBuffer = new StringBuffer();
            int n2 = 0;
            while (n2 < 20) {
                stringBuffer.insert(n2, string);
                ++n2;
            }
            this.bar = stringBuffer.toString();
        }

        public void run() {
            if (ScrollManager.this.getSettings().isCancelOnMove() && (this.start.getX() != this.p.getLocation().getX() || this.start.getY() != this.p.getLocation().getY() || this.start.getZ() != this.p.getLocation().getZ())) {
                this.stap();
            }
            if (this.i == 20) {
                this.finish();
            }
            this.paintBar();
            ++this.i;
        }

        private void paintBar() {
            String string = this.bar;
            StringBuffer stringBuffer = new StringBuffer(string);
            String string2 = ScrollManager.this.getSettings().getBar1Color();
            String string3 = ScrollManager.this.getSettings().getBar2Color();
            stringBuffer.setLength(stringBuffer.length());
            stringBuffer.insert(0, string2);
            if (this.i < 20) {
                stringBuffer.insert(this.i + string3.length() + 1, string3);
            }
            string = stringBuffer.toString();
            ActionTitle.sendTitles(this.p, string, Lang.Scrolls_Using.toMsg(), 0, this.j, 5);
        }

        public void stap() {
            this.cancel();
            ScrollManager.this.cast.remove((Object)this.p);
            this.item.setAmount(1);
            this.p.getInventory().addItem(new ItemStack[]{this.item});
            new BukkitRunnable(){

                public void run() {
                    ActionTitle.sendTitles(Cast.this.p, Lang.Scrolls_Cancelled.toMsg(), "\u00a7r", 5, 30, 5);
                }
            }.runTaskLater((Plugin)this.plugin, 1L);
        }

        public void finish() {
            ScrollManager.this.setCD(this.p, this.scroll.getId());
            ScrollManager.this.cast.remove((Object)this.p);
            DivineItemsAPI.executeActions((Entity)this.p, this.scroll.getActions(), this.item);
            this.p.getInventory().addItem(new ItemStack[]{ScrollManager.this.takeUse(this.item)});
            this.cancel();
        }

    }

    public class Scroll {
        private String id;
        private ItemStack item;
        private String display;
        private List<String> lore;
        private HashMap<String, Object> vars;
        private HashMap<String, Object> vars_lvl;
        private int min_lvl;
        private int max_lvl;
        private List<String> actions;
        private int use;
        private int cd;
        private int uses;

        public Scroll(String string, ItemStack itemStack, String string2, List<String> list, HashMap<String, Object> hashMap, HashMap<String, Object> hashMap2, int n, int n2, List<String> list2, int n3, int n4, int n5) {
            this.setId(string);
            this.setItem(itemStack);
            this.setDisplay(string2);
            this.setLore(list);
            this.setVariables(hashMap);
            this.setVariablesLvl(hashMap2);
            this.setMinLevel(n);
            this.setMaxLevel(n2);
            this.setActions(list2);
            this.setUseTime(n3);
            this.setCD(n4);
            this.setUses(n5);
        }

        public Scroll(Scroll scroll) {
            this.setId(scroll.getId());
            this.setItem(scroll.getItem());
            this.setDisplay(scroll.getDisplay());
            this.setLore(scroll.getLore());
            this.setVariables(scroll.getVariables());
            this.setVariablesLvl(scroll.getVariablesLvl());
            this.setMinLevel(scroll.getMinLevel());
            this.setMaxLevel(scroll.getMaxLevel());
            this.setActions(scroll.getActions());
            this.setUseTime(scroll.getUseTime());
            this.setCD(scroll.getCD());
            this.setUses(scroll.getUses());
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public ItemStack getItem() {
            return new ItemStack(this.item);
        }

        public void setItem(ItemStack itemStack) {
            this.item = new ItemStack(itemStack);
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

        public HashMap<String, Object> getVariables() {
            return this.vars;
        }

        public void setVariables(HashMap<String, Object> hashMap) {
            this.vars = hashMap;
        }

        public HashMap<String, Object> getVariablesLvl() {
            return this.vars_lvl;
        }

        public void setVariablesLvl(HashMap<String, Object> hashMap) {
            this.vars_lvl = hashMap;
        }

        public int getMinLevel() {
            return this.min_lvl;
        }

        public void setMinLevel(int n) {
            this.min_lvl = n;
        }

        public int getMaxLevel() {
            return this.max_lvl;
        }

        public void setMaxLevel(int n) {
            this.max_lvl = n;
        }

        public List<String> getActions() {
            return this.actions;
        }

        public void setActions(List<String> list) {
            this.actions = list;
        }

        public int getUseTime() {
            return this.use;
        }

        public void setUseTime(int n) {
            this.use = n;
        }

        public int getCD() {
            return this.cd;
        }

        public void setCD(int n) {
            this.cd = n;
        }

        public int getUses() {
            return this.uses;
        }

        public void setUses(int n) {
            this.uses = n;
        }

        public ItemStack create(int n) {
            if (n == -1) {
                n = Utils.randInt(this.getMinLevel(), this.getMaxLevel());
            } else if (n > this.getMaxLevel()) {
                n = this.getMaxLevel();
            } else if (n < 1) {
                n = this.getMinLevel();
            }
            ItemStack itemStack = new ItemStack(this.getItem());
            ItemMeta itemMeta = itemStack.getItemMeta();
            String string = this.getDisplay().replace("%lvl%", String.valueOf(n)).replace("%rlvl%", Utils.IntegerToRomanNumeral(n));
            itemMeta.setDisplayName(string);
            ArrayList<String> arrayList = new ArrayList<String>(this.getLore());
            ArrayList<String> arrayList2 = new ArrayList<String>();
            for (String string2 : arrayList) {
                void object2;
                for (String string3 : this.getVariables().keySet()) {
                    String string4 = ScrollManager.this.replaceVars((String)object2, string3, this, n);
                }
                arrayList2.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object2.replace("%lvl%", String.valueOf(n)).replace("%rlvl%", Utils.IntegerToRomanNumeral(n)).replace("%uses%", String.valueOf(this.getUses()))));
            }
            itemMeta.setLore(arrayList2);
            itemMeta.spigot().setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.values());
            itemStack.setItemMeta(itemMeta);
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_SCROLL_ID", String.valueOf(this.id) + ":" + n);
            nBTItem.setInteger("DIVINE_SCROLL_USES", this.getUses());
            return nBTItem.getItem();
        }
    }

    public class ScrollCD {
        private String id;
        private long end;

        public ScrollCD(String string, long l) {
            this.setScrollId(string);
            this.setTimeEnd(l);
        }

        public String getScrollId() {
            return this.id;
        }

        public void setScrollId(String string) {
            this.id = string;
        }

        public long getTimeEnd() {
            return this.end;
        }

        public void setTimeEnd(long l) {
            this.end = l;
        }
    }

    public class ScrollSettings {
        private String bar_symbol;
        private String bar_c1;
        private String bar_c2;
        private boolean cancel_move;
        private boolean cancel_inter;
        private boolean cancel_drop;
        private boolean cancel_inv;

        public ScrollSettings(String string, String string2, String string3, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
            this.setBarSymbol(string);
            this.setBar1Color(string2);
            this.setBar2Color(string3);
            this.setCancelOnMove(bl);
            this.setCancelOnInteract(bl2);
            this.setCancelOnDrop(bl3);
            this.setCancelOnInv(bl4);
        }

        public String getBarSymbol() {
            return this.bar_symbol;
        }

        public void setBarSymbol(String string) {
            this.bar_symbol = string;
        }

        public String getBar1Color() {
            return this.bar_c1;
        }

        public void setBar1Color(String string) {
            this.bar_c1 = string;
        }

        public String getBar2Color() {
            return this.bar_c2;
        }

        public void setBar2Color(String string) {
            this.bar_c2 = string;
        }

        public boolean isCancelOnMove() {
            return this.cancel_move;
        }

        public void setCancelOnMove(boolean bl) {
            this.cancel_move = bl;
        }

        public boolean isCancelOnInteract() {
            return this.cancel_inter;
        }

        public void setCancelOnInteract(boolean bl) {
            this.cancel_inter = bl;
        }

        public boolean isCancelOnDrop() {
            return this.cancel_drop;
        }

        public void setCancelOnDrop(boolean bl) {
            this.cancel_drop = bl;
        }

        public boolean isCancelOnInv() {
            return this.cancel_inv;
        }

        public void setCancelOnInv(boolean bl) {
            this.cancel_inv = bl;
        }
    }

}

