/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.wrappers.nbt.NbtCompound
 *  com.google.common.collect.Lists
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Color
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.TreeType
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.MemoryConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.enchantment.EnchantItemEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityTameEvent
 *  org.bukkit.event.entity.HorseJumpEvent
 *  org.bukkit.event.entity.PlayerLeashEntityEvent
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.event.player.PlayerEditBookEvent
 *  org.bukkit.event.player.PlayerFishEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerUnleashEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.Recipe
 *  org.bukkit.inventory.ShapedRecipe
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.LeatherArmorMeta
 *  org.bukkit.metadata.Metadatable
 *  org.bukkit.potion.PotionEffectType
 *  yo.aR
 *  yo.bz
 */
package think.rpgitems.item;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.google.common.collect.Lists;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffectType;
import think.rpgitems.Plugin;
import yo.aR;
import yo.a_0;
import yo.am_0;
import yo.an_0;
import yo.ao_0;
import yo.aq_0;
import yo.ar_0;
import yo.as_0;
import yo.au_0;
import yo.ay_1;
import yo.az_1;
import yo.ba_1;
import yo.bb_1;
import yo.bc_0;
import yo.bd_0;
import yo.bf_1;
import yo.bg_1;
import yo.bi_0;
import yo.bi_1;
import yo.bk_0;
import yo.bm_0;
import yo.bv_0;
import yo.by_0;
import yo.bz;
import yo.bz_1;
import yo.cd_0;
import yo.ce_1;
import yo.cv;
import yo.y_0;
import yo.z_0;

public final class RPGItem
implements bv_0 {
    private ItemStack q;
    private HashMap<String, ItemMeta> r = new HashMap();
    private int s;
    private String t;
    private String u;
    private boolean v;
    private String w;
    private String x;
    private bd_0 y = bd_0.TRASH;
    public boolean a = true;
    private int z = 0;
    private int A = 3;
    private int B = 0;
    private int C = 0;
    private String D = "";
    private String E = Plugin.c.c().getString("defaults.sword", "Sword");
    private String F = Plugin.c.c().getString("defaults.hand", "One handed");
    private String G = "";
    public double b = 0.0;
    public int c = -1;
    public int d = 0;
    public double e = 0.0;
    private List<String> H = new ArrayList<String>();
    public List<String> f = new ArrayList<String>();
    public List<String> g = new ArrayList<String>();
    public boolean h = false;
    private HashMap<am_0, ArrayList<bi_1>> I = new HashMap();
    public int i = 6;
    public boolean j = false;
    public List<ItemStack> k = null;
    public y_0<String> l = new y_0();
    public int m = 0;
    private Boolean keepLore = false;
    private Boolean keepEnchant = false;
    private int J;
    private boolean K = false;
    private boolean L = false;
    public bz_1 n = null;
    public String o;
    public static final String p = "\u00a7c\u00a7c\u00a7b\u00a7k\u00a7m\u00a7r";
    private static final String M = "\u00a7b\u00a7l\u00a7o\u00a7a\u00a7m\u00a77\u00a7f\u00a79";
    private static final Random N = new Random();

    public RPGItem(String name, int id) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.t = name;
        this.s = id;
        this.u = RPGItem.b(id);
        this.q = new ItemStack(Material.WOOD_SWORD);
        this.x = this.q.getType().toString();
        this.a(ao_0.b(), this.q.getItemMeta());
        this.a();
    }

    public RPGItem(ConfigurationSection s, boolean rebuild) {
        ConfigurationSection drops;
        int i;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.t = s.getString("name");
        this.s = s.getInt("id");
        this.b(s.getString("display"), false);
        this.c(s.getString("type", Plugin.c.c().getString("defaults.sword", "Sword")), false);
        this.d(s.getString("hand", Plugin.c.c().getString("defaults.hand", "One handed")), false);
        this.f(s.getString("lore"), false);
        this.f = s.getStringList("description");
        for (i = 0; i < this.f.size(); ++i) {
            this.f.set(i, bg_1.a(this.f.get(i)));
        }
        this.g = s.getStringList("customlore");
        for (i = 0; i < this.g.size(); ++i) {
            this.g.set(i, bg_1.a(this.g.get(i)));
        }
        this.h = s.getBoolean("onlyShowDescription");
        this.y = bd_0.valueOf(s.getString("quality"));
        this.a = s.getBoolean("directDamage", true);
        this.z = s.getInt("damageMin");
        this.A = s.getInt("damageMax");
        this.B = s.getInt("armour", 0);
        this.C = s.getInt("armourNum", 0);
        this.q = new ItemStack(Material.valueOf((String)s.getString("item")));
        ItemMeta meta = this.q.getItemMeta();
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB((int)s.getInt("item_colour", 0)));
        } else {
            this.q.setDurability((short)s.getInt("item_data", 0));
        }
        for (String locale : ao_0.a()) {
            this.a(locale, meta.clone());
        }
        this.G = s.getString("nbt", "");
        bk_0 protocolLib = bi_0.a(bk_0.class);
        if (protocolLib.b()) {
            try {
                this.G = bb_1.a.a(bb_1.a.a(this.G));
            }
            catch (Exception locale) {
                // empty catch block
            }
        }
        this.b = s.getDouble("repairPrice");
        if (this.b < 0.0) {
            this.b = 0.0;
        }
        this.c = s.getInt("level", -1);
        this.d = s.getInt("cost.exp");
        this.e = s.getDouble("cost.money");
        this.H = new ArrayList<String>();
        HashSet<String> tempSets = new HashSet<String>();
        for (String pl : s.getStringList("ignorePlugins")) {
            tempSets.add(pl.toLowerCase());
        }
        this.H.addAll(tempSets);
        tempSets.clear();
        tempSets = null;
        ConfigurationSection powerList = s.getConfigurationSection("powers");
        if (powerList != null) {
            for (String sectionKey : powerList.getKeys(false)) {
                ConfigurationSection section = powerList.getConfigurationSection(sectionKey);
                try {
                    if (!bi_1.a.containsKey(section.getString("powerName"))) continue;
                    bi_1 pow = bi_1.a.get(section.getString("powerName")).newInstance();
                    pow.a(section);
                    pow.c = this;
                    this.a(pow, false);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        this.u = RPGItem.b(this.s);
        this.v = s.getBoolean("haspermission", false);
        this.w = s.getString("permission", "a.default.permission");
        this.i = s.getInt("recipechance", 6);
        this.j = s.getBoolean("hasRecipe", false);
        if (this.j) {
            this.k = s.getList("recipe");
        }
        if ((drops = s.getConfigurationSection("dropChances")) != null) {
            for (String key : drops.getKeys(false)) {
                Set<Integer> set;
                double chance = drops.getDouble(key, 0.0);
                if ((chance = Math.min(chance, 100.0)) > 0.0) {
                    this.l.a(key, chance);
                    if (!bf_1.c.containsKey(key)) {
                        bf_1.c.put(key, new HashSet());
                    }
                    set = bf_1.c.get(key);
                    set.add(this.getID());
                } else {
                    this.l.b_(key);
                    if (bf_1.c.containsKey(key)) {
                        set = bf_1.c.get(key);
                        set.remove(this.getID());
                    }
                }
                this.l.a(key, chance);
            }
        }
        this.m = s.getInt("disappearChance");
        Object object = s.get("keep.lore");
        try {
            this.keepLore = Boolean.valueOf(object.toString().toUpperCase());
        }
        catch (Exception e3) {
            this.keepLore = null;
        }
        object = s.get("keep.enchant");
        try {
            this.keepEnchant = Boolean.valueOf(object.toString().toUpperCase());
        }
        catch (Exception e4) {
            this.keepEnchant = null;
        }
        if (this.q.getType().getMaxDurability() != 0) {
            this.K = true;
        }
        this.J = s.getInt("maxDurability", (int)this.q.getType().getMaxDurability());
        this.L = s.getBoolean("forceBar", false);
        if (this.J == 0) {
            this.J = -1;
        }
        this.a();
    }

    public void a(ConfigurationSection s) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        s.set("name", (Object)this.t);
        s.set("id", (Object)this.s);
        s.set("haspermission", (Object)this.v);
        s.set("permission", (Object)this.w);
        s.set("display", (Object)this.x.replaceAll("\u00a7", "&"));
        s.set("quality", (Object)this.y.toString());
        s.set("directDamage", (Object)this.a);
        s.set("damageMin", (Object)this.z);
        s.set("damageMax", (Object)this.A);
        s.set("armour", (Object)this.B);
        s.set("armourNum", (Object)this.C);
        s.set("type", (Object)this.E.replaceAll("\u00a7", "&"));
        s.set("hand", (Object)this.F.replaceAll("\u00a7", "&"));
        s.set("lore", (Object)this.D.replaceAll("\u00a7", "&"));
        s.set("nbt", (Object)this.G);
        s.set("repairPrice", (Object)this.b);
        s.set("level", (Object)this.c);
        s.set("cost.exp", (Object)this.d);
        s.set("cost.money", (Object)this.e);
        s.set("ignorePlugins", this.H);
        s.set("description", this.f);
        s.set("customlore", this.g);
        s.set("onlyShowDescription", (Object)this.h);
        s.set("item", (Object)this.q.getType().toString());
        ItemMeta meta = this.r.get(ao_0.b());
        if (meta instanceof LeatherArmorMeta) {
            s.set("item_colour", (Object)((LeatherArmorMeta)meta).getColor().asRGB());
        } else {
            s.set("item_data", (Object)this.q.getDurability());
        }
        ConfigurationSection powerConfigs = s.createSection("powers");
        int i = 0;
        for (ArrayList<bi_1> ps : this.I.values()) {
            for (bi_1 p : ps) {
                MemoryConfiguration pConfig = new MemoryConfiguration();
                pConfig.set("powerName", (Object)p.e());
                p.b((ConfigurationSection)pConfig);
                powerConfigs.set(Integer.toString(i), (Object)pConfig);
                ++i;
            }
        }
        s.set("recipechance", (Object)this.i);
        s.set("hasRecipe", (Object)this.j);
        if (this.j) {
            s.set("recipe", this.k);
        }
        ConfigurationSection drops = s.createSection("dropChances");
        for (String key : this.l.v_()) {
            drops.set(key, (Object)this.l.b(key));
        }
        s.set("disappearChance", (Object)this.m);
        s.set("keep.lore", (Object)this.keepLore);
        s.set("keep.enchant", (Object)this.keepEnchant);
        s.set("maxDurability", (Object)this.J);
        s.set("forceBar", (Object)this.L);
    }

    public void a(int id) {
        this.s = id;
        this.u = RPGItem.b(id);
    }

    public void a(boolean removeOld) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (removeOld) {
            Iterator it = Bukkit.recipeIterator();
            while (it.hasNext()) {
                Recipe rRecipe = (Recipe)it.next();
                RPGItem rpgitem = by_0.a(rRecipe.getResult());
                if (rpgitem == null || rpgitem.getID() != this.getID()) continue;
                it.remove();
            }
        }
        if (this.j) {
            HashSet<ItemStack> iSet = new HashSet<ItemStack>();
            for (ItemStack m : this.k) {
                iSet.add(m);
            }
            ItemStack[] iList = iSet.toArray((T[])new ItemStack[iSet.size()]);
            this.q.setItemMeta(this.b(ao_0.b()));
            ShapedRecipe shapedRecipe = new ShapedRecipe(this.q);
            int i = 0;
            HashMap<ItemStack, Character> iMap = new HashMap<ItemStack, Character>();
            for (ItemStack m : iList) {
                iMap.put(m, Character.valueOf((char)(65 + i)));
                ++i;
            }
            iMap.put(null, Character.valueOf(' '));
            StringBuilder out = new StringBuilder();
            for (ItemStack m : this.k) {
                out.append(iMap.get((Object)m));
            }
            String shape = out.toString();
            shapedRecipe.shape(new String[]{shape.substring(0, 3), shape.substring(3, 6), shape.substring(6, 9)});
            for (Map.Entry e2 : iMap.entrySet()) {
                if (e2.getKey() == null) continue;
                shapedRecipe.setIngredient(((Character)e2.getValue()).charValue(), ((ItemStack)e2.getKey()).getType(), (int)((ItemStack)e2.getKey()).getDurability());
            }
            Bukkit.addRecipe((Recipe)shapedRecipe);
        }
    }

    public boolean a(Player player) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        String locale = ao_0.a(player);
        if (this.d > 0 && az_1.b(player) < this.d) {
            player.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.exp.notenough", locale), this.d));
            return false;
        }
        if (this.e > 0.0 && !bi_0.a(bm_0.class).b(player.getName(), this.e, locale)) {
            return false;
        }
        if (this.d > 0) {
            az_1.a(player, az_1.b(player) - this.d);
            player.sendMessage((Object)ChatColor.RED + String.format(ao_0.a("message.exp.withdraw", locale), this.d));
        }
        if (this.e > 0.0) {
            bi_0.a(bm_0.class).c(player.getName(), this.e, locale);
        }
        return true;
    }

    public ArrayList<bi_1> a(am_0 type) {
        ArrayList<bi_1> pows = this.I.get((Object)type);
        if (pows == null) {
            pows = new ArrayList();
            this.I.put(type, pows);
        }
        return pows;
    }

    public void a(ItemStack itemHold) throws IOException {
        bk_0 protocolLib = bi_0.a(bk_0.class);
        if (protocolLib.b()) {
            this.G = bb_1.a.b(bb_1.a.b(itemHold));
        }
    }

    public void a() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        for (String locale : ao_0.a()) {
            if (this.r.containsKey(locale)) continue;
            this.a(locale, this.b(ao_0.b()));
        }
        for (String locale : ao_0.a()) {
            List<String> lines = this.a(locale);
            ItemMeta meta = this.b(locale);
            meta.setDisplayName(lines.get(0));
            lines.remove(0);
            meta.setLore(lines);
            this.a(locale, meta);
        }
        for (Player player : bg_1.c()) {
            ListIterator it = player.getInventory().iterator();
            String locale = ao_0.a(player);
            while (it.hasNext()) {
                ItemStack rItem = (ItemStack)it.next();
                if (by_0.a(rItem) == null) continue;
                RPGItem.a(rItem, locale, false);
            }
            for (ItemStack rItem : player.getInventory().getArmorContents()) {
                if (by_0.a(rItem) == null) continue;
                RPGItem.a(rItem, locale, false);
            }
        }
        this.a(true);
    }

    public static as_0 b(ItemStack item) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore() || item.getItemMeta().getLore().isEmpty()) {
            return new as_0();
        }
        return as_0.a((String)item.getItemMeta().getLore().get(0));
    }

    public static void a(ItemStack item, Player player) {
        RPGItem.a(item, ao_0.a(player), RPGItem.b(item));
    }

    public static void a(ItemStack item, String locale) {
        RPGItem.a(item, locale, RPGItem.b(item));
    }

    public static void a(ItemStack item, Player player, as_0 rpgMeta) {
        RPGItem.a(item, ao_0.a(player), rpgMeta, false);
    }

    public static void a(ItemStack item, String locale, as_0 rpgMeta) {
        RPGItem.a(item, locale, rpgMeta, false);
    }

    public static void a(ItemStack item, String locale, boolean updateDurability) {
        RPGItem.a(item, locale, RPGItem.b(item), updateDurability);
    }

    public static void a(ItemStack item, String locale, as_0 rpgMeta, boolean updateDurability) {
        bk_0 protocolLib;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        RPGItem rItem = by_0.a(item);
        if (rItem == null) {
            return;
        }
        List<Object> extraLores = new ArrayList();
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            extraLores = RPGItem.a(rItem, item.getItemMeta().getLore());
        }
        item.setType(rItem.q.getType());
        ItemMeta meta = rItem.b(locale);
        if (!(meta instanceof LeatherArmorMeta) && updateDurability) {
            item.setDurability(rItem.q.getDurability());
        }
        List lores = meta.getLore();
        rItem.a(rpgMeta, item, locale, lores);
        lores.set(0, (String)meta.getLore().get(0) + rpgMeta.k());
        RPGItem.a(lores);
        lores.addAll(RPGItem.a(rItem, locale, rpgMeta));
        lores.addAll(extraLores);
        meta.setLore(lores);
        Map<Enchantment, Integer> enchs = RPGItem.a(rItem, item.getItemMeta());
        item.setItemMeta(meta);
        if (!enchs.isEmpty()) {
            item.addUnsafeEnchantments(enchs);
        }
        if ((protocolLib = bi_0.a(bk_0.class)).b() && !rItem.G.isEmpty()) {
            try {
                bb_1.a.a(item, bb_1.a.a(bb_1.a.b(item), bb_1.a.a(rItem.G)));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static List<String> a(List<String> lores) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        String line = lores.get(0);
        while (line.startsWith(M)) {
            try {
                line = line.substring(M.length() + 16);
            }
            catch (Exception exception) {}
        }
        lores.set(0, M + RPGItem.b(lores.size()) + line);
        return lores;
    }

    public static Map<Enchantment, Integer> a(RPGItem rItem, ItemMeta meta) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return rItem.c("enchant") ? meta.getEnchants() : new HashMap();
    }

    public static List<String> a(RPGItem rItem, List<String> lores) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        ArrayList<String> extraLores = new ArrayList<String>();
        if (!rItem.c("lore")) {
            return extraLores;
        }
        for (int i = 0; i < lores.size(); ++i) {
            String line = lores.get(i);
            if (line.startsWith(M)) {
                try {
                    int size = by_0.d(line.substring(M.length()));
                    i += size - 1;
                    continue;
                }
                catch (Exception size) {
                    // empty catch block
                }
            }
            if (line.endsWith(p)) continue;
            extraLores.add(line);
        }
        if (extraLores.size() == lores.size()) {
            extraLores.clear();
        }
        return extraLores;
    }

    public void a(as_0 rpgMeta, ItemStack item, String locale, List<String> lore) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (this.J > 0) {
            if (!rpgMeta.c(0)) {
                rpgMeta.a(0, Integer.valueOf(this.J));
            }
            int durability = ((Number)rpgMeta.j_(0)).intValue();
            if (this.L) {
                StringBuilder out = new StringBuilder();
                int displayMode = bg_1.a(Plugin.c.c().getInt("durabilityBar.mode"), 0, 1);
                switch (displayMode) {
                    case 0: {
                        int tooltipWidth = bg_1.a(Plugin.c.c().getInt("durabilityBar.graphLength"), 5, 150);
                        char boxChar = '\u25a0';
                        int boxCount = tooltipWidth / 4;
                        int mid = (int)((double)boxCount * ((double)durability / (double)this.J));
                        for (int i = 0; i < boxCount; ++i) {
                            out.append((Object)(i < mid ? ChatColor.GREEN : (i == mid ? ChatColor.YELLOW : ChatColor.RED)));
                            out.append(boxChar);
                        }
                        break;
                    }
                    case 1: {
                        out.append(String.format(ao_0.a("display.durability.num", locale), durability, this.J));
                    }
                }
                lore.add(out.toString());
            }
            if (this.K) {
                item.setDurability((short)this.J);
            }
        } else if (this.J <= 0) {
            item.setDurability(this.K ? (short)0 : this.q.getDurability());
        }
    }

    public List<String> a(String locale) {
        return this.a(locale, false);
    }

    public List<String> a(String locale, boolean ignoreOnlyShow) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        int width = 80;
        int dWidth = RPGItem.n(ChatColor.stripColor((String)this.x));
        if (dWidth > width) {
            width = dWidth;
        }
        if ((dWidth = RPGItem.m(ChatColor.stripColor((String)(this.F + "     " + this.E)))) > width) {
            width = dWidth;
        }
        String damageStr = "";
        if (!ignoreOnlyShow && !this.h) {
            if (this.z == 0 && this.A == 0 && (this.B != 0 || this.C != 0)) {
                damageStr = "";
                if (this.C != 0) {
                    damageStr = damageStr + this.C + " " + Plugin.c.c().getString("defaults.armourNum", "Armour") + "   ";
                }
                if (this.B != 0) {
                    damageStr = damageStr + this.B + "% " + Plugin.c.c().getString("defaults.armour", "Armour");
                }
            } else {
                damageStr = this.B == 0 && this.z == 0 && this.A == 0 ? null : (this.z == this.A ? this.z + " " + Plugin.c.c().getString("defaults.damage", "Damage") : this.z + "-" + this.A + " " + Plugin.c.c().getString("defaults.damage", "Damage"));
            }
            if ((this.z != 0 || this.A != 0 || this.B != 0) && (dWidth = RPGItem.m(damageStr)) > width) {
                width = dWidth;
            }
            for (ArrayList ps : this.I.values()) {
                for (bi_1 p : ps) {
                    dWidth = RPGItem.m(ChatColor.stripColor((String)p.a(locale)));
                    if (dWidth <= width) continue;
                    width = dWidth;
                }
            }
        }
        for (String s : this.f) {
            dWidth = RPGItem.m(ChatColor.stripColor((String)s));
            if (dWidth <= width) continue;
            width = dWidth;
        }
        ArrayList<String> output = new ArrayList<String>();
        output.add(this.u + this.y.colour + (Object)ChatColor.BOLD + this.x);
        output.add((Object)ChatColor.WHITE + this.F + StringUtils.repeat((String)" ", (int)((width - RPGItem.m(ChatColor.stripColor((String)new StringBuilder().append(this.F).append(this.E).toString()))) / 10)) + this.E);
        if (!ignoreOnlyShow && !this.h) {
            String line;
            ba_1<String, Integer> infoMaps = new ba_1<String, Integer>();
            if (this.m > 0) {
                line = this.m > 0 ? (Object)ChatColor.RED + String.format(ao_0.a("display.disappear", locale), this.m) : "";
                infoMaps.put(line, RPGItem.m(ChatColor.stripColor((String)line)));
            }
            if (this.b > 0.0) {
                line = (Object)ChatColor.YELLOW + String.format(ao_0.a("display.repair.price", locale), this.b);
                infoMaps.put(line, RPGItem.m(ChatColor.stripColor((String)line)));
            }
            if (this.c > -1) {
                line = (Object)ChatColor.RED + String.format(ao_0.a("display.level", locale), this.c + 1);
                infoMaps.put(line, RPGItem.m(ChatColor.stripColor((String)line)));
            }
            if (this.d > 0) {
                line = (Object)ChatColor.RED + String.format(ao_0.a("display.exp.cost", locale), this.d);
                infoMaps.put(line, RPGItem.m(ChatColor.stripColor((String)line)));
            }
            if (this.e > 0.0) {
                line = (Object)ChatColor.RED + String.format(ao_0.a("display.money.cost", locale), this.e);
                infoMaps.put(line, RPGItem.m(ChatColor.stripColor((String)line)));
            }
            if (!infoMaps.isEmpty() && width < (Integer)infoMaps.e()) {
                width = (Integer)infoMaps.e();
            }
            if (damageStr != null) {
                output.add((Object)ChatColor.WHITE + damageStr);
            }
            for (String line2 : infoMaps.keySet()) {
                output.add(line2);
            }
            for (ArrayList ps : this.I.values()) {
                for (bi_1 p : ps) {
                    output.add(p.a(locale));
                }
            }
            if (this.D.length() != 0) {
                int cWidth = 0;
                boolean tWidth2 = false;
                StringBuilder out = new StringBuilder();
                StringBuilder temp = new StringBuilder();
                out.append((Object)ChatColor.YELLOW);
                out.append((Object)ChatColor.ITALIC);
                String currentColour = ChatColor.YELLOW.toString();
                String dMsg = "''" + this.D + "''";
                for (int i = 0; i < dMsg.length(); ++i) {
                    char c2 = dMsg.charAt(i);
                    temp.append(c2);
                    if (c2 == '\u00a7' || c2 == '&') {
                        temp.append(dMsg.charAt(++i));
                        currentColour = "\u00a7" + dMsg.charAt(i);
                        continue;
                    }
                    int tWidth2 = c2 == ' ' ? (tWidth2 += 4) : (tWidth2 += an_0.a[c2] + 1);
                    if (c2 != ' ' && i != dMsg.length() - 1) continue;
                    if (cWidth + tWidth2 > width) {
                        cWidth = 0;
                        cWidth += tWidth2;
                        tWidth2 = 0;
                        output.add(out.toString());
                        out = new StringBuilder();
                        out.append(currentColour);
                        out.append((Object)ChatColor.ITALIC);
                        out.append(temp);
                        temp = new StringBuilder();
                        continue;
                    }
                    out.append(temp);
                    temp = new StringBuilder();
                    cWidth += tWidth2;
                    tWidth2 = 0;
                }
                out.append(temp);
                output.add(out.toString());
            }
        }
        for (String s : this.f) {
            output.add(s);
        }
        return output;
    }

    @a_0
    public ItemStack toItemStack() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.toItemStack(ao_0.b());
    }

    @a_0
    public ItemStack toItemStack(String locale) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        ItemStack rStack = this.q.clone();
        as_0 rpgMeta = new as_0();
        ItemMeta meta = this.b(locale);
        List<String> lore = meta.getLore();
        this.a(rpgMeta, rStack, locale, lore);
        lore.set(0, (String)meta.getLore().get(0) + rpgMeta.k());
        lore = RPGItem.a(lore);
        lore.addAll(this.g);
        meta.setLore(lore);
        rStack.setItemMeta(meta);
        return rStack;
    }

    public ItemMeta b(String locale) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        ItemMeta meta = this.r.get(locale);
        if (meta == null) {
            meta = this.r.get(ao_0.b());
        }
        return meta.clone();
    }

    public final void a(String locale, ItemMeta meta) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.r.put(locale, meta);
    }

    @a_0
    public String getName() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.t;
    }

    @a_0
    public int getID() {
        return this.s;
    }

    public String b() {
        return this.u;
    }

    public static String b(int id) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        String hex = String.format("%08x", id);
        StringBuilder out = new StringBuilder();
        for (char h : hex.toCharArray()) {
            out.append('\u00a7');
            out.append(h);
        }
        return out.toString();
    }

    private static int m(String str) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        int width = 0;
        for (int i = 0; i < str.length(); ++i) {
            char c2 = str.charAt(i);
            width += an_0.a[c2] + 1;
        }
        return width;
    }

    private static int n(String str) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        int width = 0;
        for (int i = 0; i < str.length(); ++i) {
            char c2 = str.charAt(i);
            width += an_0.a[c2] + 2;
        }
        return width;
    }

    public boolean c(String keepType) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        try {
            keepType = keepType.substring(0, 1).toUpperCase() + keepType.substring(1).toLowerCase();
            Field field = this.getClass().getDeclaredField("keep" + keepType);
            field.setAccessible(true);
            Object object = field.get(this);
            if (object != null) {
                return (Boolean)object;
            }
        }
        catch (Exception field) {
            // empty catch block
        }
        return Plugin.d.getBoolean("keep." + keepType.toLowerCase(), true);
    }

    public List<String> c() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return Lists.newArrayList(this.H);
    }

    public boolean a(Class<? extends cv> clzz) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.d(clzz.getSimpleName());
    }

    public boolean d(String pluginName) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.H.contains(pluginName.toLowerCase());
    }

    public void a(cv ips) {
        String plugin;
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (ips != null && !this.H.remove(plugin = ips.toString().toLowerCase())) {
            this.H.add(plugin);
        }
    }

    public void a(CommandSender sender) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        String locale = ao_0.a((Object)sender);
        List<String> lines = this.a(locale, true);
        for (String s : lines) {
            sender.sendMessage(s);
        }
        for (String s : this.g) {
            sender.sendMessage(s);
        }
        sender.sendMessage(String.format(ao_0.a("message.print.durability", locale), this.J));
    }

    public void e(String str) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.b(str, true);
    }

    public void b(String str, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.x = bg_1.a(str);
        if (update) {
            this.a();
        }
    }

    @a_0
    public String getDisplay() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.y.colour + (Object)ChatColor.BOLD + this.x;
    }

    public void f(String str) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.c(str, true);
    }

    public void c(String str, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.E = bg_1.a(str);
        if (update) {
            this.a();
        }
    }

    public String d() {
        return this.E;
    }

    public void g(String h) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.d(h, true);
    }

    public void d(String h, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.F = bg_1.a(h);
        if (update) {
            this.a();
        }
    }

    public String e() {
        return this.F;
    }

    public void a(int min, int max) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.a(min, max, true);
    }

    public void a(int min, int max, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.z = min;
        this.A = max;
        this.B = 0;
        if (update) {
            this.a();
        }
    }

    public int f() {
        return this.z;
    }

    public int g() {
        return this.A;
    }

    public int h() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.i;
    }

    public void c(int p) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.a(p, true);
    }

    public void a(int p, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.i = p;
        if (update) {
            this.a();
        }
    }

    public String i() {
        return this.w;
    }

    public boolean j() {
        return this.v;
    }

    public void h(String p) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.e(p, true);
    }

    public void e(String p, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.w = p;
        if (update) {
            this.a();
        }
    }

    public void b(boolean b2) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.a(b2, true);
    }

    public void a(boolean b2, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.v = b2;
        if (update) {
            this.a();
        }
    }

    public boolean b(Player player) {
        if (this.a((CommandSender)player, true) && player.getLevel() >= this.c && !aR.b((Metadatable)player, (ar_0.a)ar_0.a.POWER_BLOCK) && this.a(player, player.getLocation())) {
            return true;
        }
        return false;
    }

    public boolean a(Player player, Location loc) {
        Collection<cv> ipss = cd_0.e();
        for (cv ips : ipss) {
            if (ips.c(player, loc) || this.a(ips.f())) continue;
            return false;
        }
        return true;
    }

    public boolean b(CommandSender sender) {
        return this.a(sender, false);
    }

    public boolean a(CommandSender sender, boolean printWarn) {
        if (this.j() && !sender.hasPermission(this.i())) {
            if (printWarn) {
                sender.sendMessage((Object)ChatColor.RED + ao_0.a("message.error.permission", ao_0.a((Object)sender)));
            }
            return false;
        }
        return true;
    }

    public void d(int a2) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.b(a2, true);
    }

    public void b(int a2, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.B = a2;
        this.A = 0;
        this.z = 0;
        if (update) {
            this.a();
        }
    }

    public void e(int a2) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.c(a2, true);
    }

    public void c(int a2, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.C = a2;
        this.A = 0;
        this.z = 0;
        if (update) {
            this.a();
        }
    }

    public int k() {
        return this.B;
    }

    public int l() {
        return this.C;
    }

    public void i(String str) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.f(str, true);
    }

    public void f(String str, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.D = bg_1.a(str);
        if (update) {
            this.a();
        }
    }

    public String m() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.D;
    }

    public void a(bd_0 q) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.a(q, true);
    }

    public void a(bd_0 q, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.y = q;
        if (update) {
            this.a();
        }
    }

    public bd_0 n() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.y;
    }

    public void a(Material mat) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.a(mat, true);
    }

    public void a(Material mat, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        if (this.J == this.q.getType().getMaxDurability()) {
            this.J = mat.getMaxDurability();
        }
        this.q.setType(mat);
        if (update) {
            this.a();
        }
    }

    public void a(short value, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.q.setDurability(value);
        this.J = value;
        if (update) {
            this.a();
        }
    }

    public void a(short value) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.q.setDurability(value);
        this.J = value;
    }

    public short o() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.q.getDurability();
    }

    public Material p() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.q.getType();
    }

    public void f(int newVal) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.d(newVal, true);
    }

    public void d(int newVal, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.J = newVal;
        if (update) {
            this.a();
        }
    }

    public int q() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        return this.J <= 0 ? -1 : this.J;
    }

    public ay_1 r() {
        return new ay_1(this.f() != this.g() ? (double)(this.f() + N.nextInt(this.g() - this.f())) : (double)this.f());
    }

    public void y(Player player) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        bg_1.a(player, this.toItemStack(ao_0.a(player)));
    }

    public void a(bi_1 power) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.a(power, true);
    }

    public void a(bi_1 power, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.a(power.d).add(power);
        bi_1.b.a(power.e(), bi_1.b.b(power.e()) + 1);
        if (update) {
            this.a();
        }
    }

    public void s() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.I.clear();
        this.a();
    }

    public void b(am_0 eventType) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        List pows = this.I.remove((Object)eventType);
        if (pows != null) {
            pows.clear();
        }
        this.a();
    }

    public boolean j(String pow) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        for (ArrayList<bi_1> ps : this.I.values()) {
            Iterator<bi_1> it = ps.iterator();
            while (it.hasNext()) {
                bi_1 p = it.next();
                if (!p.e().equalsIgnoreCase(pow)) continue;
                it.remove();
                this.a();
                return true;
            }
        }
        return false;
    }

    public boolean a(String pow, am_0 eventType) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        List pows = this.I.get((Object)eventType);
        if (pows != null) {
            Iterator it = pows.iterator();
            while (it.hasNext()) {
                bi_1 p = (bi_1)it.next();
                if (!p.e().equalsIgnoreCase(pow)) continue;
                it.remove();
                this.a();
                return true;
            }
        }
        return false;
    }

    public Collection<bi_1> t() {
        ArrayList<bi_1> pows = new ArrayList<bi_1>();
        for (Collection ps : this.I.values()) {
            pows.addAll(ps);
        }
        return pows;
    }

    public <T extends bi_1> Collection<T> b(Class<T> clzz) {
        ArrayList<bi_1> list = new ArrayList<bi_1>(this.t());
        ArrayList<bi_1> result = new ArrayList<bi_1>();
        for (bi_1 p : list) {
            if (!p.getClass().isInstance(clzz)) continue;
            result.add(p);
        }
        return result;
    }

    public <T extends bi_1> Collection<T> a(Class<T> clzz, am_0 eventType) {
        ArrayList<bi_1> list = new ArrayList<bi_1>(this.a(eventType));
        ArrayList<bi_1> result = new ArrayList<bi_1>();
        for (bi_1 p : list) {
            if (!p.getClass().isInstance(clzz)) continue;
            result.add(p);
        }
        return result;
    }

    public <T extends bi_1> Collection<T> a(String powerName, Class<T> clzz) {
        ArrayList<bi_1> list = new ArrayList<bi_1>(this.t());
        ArrayList<bi_1> result = new ArrayList<bi_1>();
        for (bi_1 p : list) {
            if (!p.getClass().isInstance(clzz) || !p.e().equalsIgnoreCase(powerName)) continue;
            result.add(p);
        }
        return result;
    }

    public void k(String str) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.g(str, true);
    }

    public void g(String str, boolean update) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.f.add(bg_1.a(str));
        if (update) {
            this.a();
        }
    }

    public void l(String str) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.g.add(bg_1.a(str));
    }

    public void u() {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        this.L = !this.L;
        this.a();
    }

    @Override
    public int c(Player player) {
        try {
            Closeable asdhqjefhusfer = null;
            Throwable throwable = null;
            if (asdhqjefhusfer != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer) {
            // empty catch block
        }
        for (bi_1 power : this.a(am_0.TICK)) {
            if (power instanceof ce_1) {
                PotionEffectType rType = ((ce_1)power).j;
                if (aR.b((Metadatable)player, (ar_0.a)ar_0.a.POWER_BLOCKHEAL) ? rType == PotionEffectType.HEAL || rType == PotionEffectType.HEALTH_BOOST || rType == PotionEffectType.REGENERATION : aR.b((Metadatable)player, (ar_0.a)ar_0.a.POWER_BLOCKBUFF) && bg_1.a(bc_0.BUFF).contains((Object)rType)) continue;
            }
            power.c(player);
        }
        return 0;
    }

    @Override
    public int b(Player player, LivingEntity target, ay_1 damage) {
        int success = 0;
        for (bi_1 p : this.a(am_0.DAMAGE)) {
            success += p.b(player, target, damage);
        }
        return success;
    }

    @Override
    public int a(Player player, LivingEntity damager, ay_1 damage) {
        int success = 0;
        for (bi_1 p : this.a(am_0.DAMAGED)) {
            success += p.a(player, damager, damage);
        }
        return success;
    }

    @Override
    public int d(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.SNEAK)) {
            success += p.d(player);
        }
        return success;
    }

    @Override
    public int e(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.SNEAKON)) {
            success += p.e(player);
        }
        return success;
    }

    @Override
    public int f(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.SNEAKOFF)) {
            success += p.f(player);
        }
        return success;
    }

    @Override
    public int g(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.SPRINT)) {
            success += p.g(player);
        }
        return success;
    }

    @Override
    public int h(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.SPRINTON)) {
            success += p.h(player);
        }
        return success;
    }

    @Override
    public int i(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.SPRINTOFF)) {
            success += p.i(player);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerInteractEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.INTERACT)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int b(Player player, PlayerInteractEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.RIGHTCLICK)) {
            success += p.b(player, event);
        }
        return success;
    }

    @Override
    public int c(Player player, PlayerInteractEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.LEFTCLICK)) {
            success += p.c(player, event);
        }
        return success;
    }

    @Override
    public int d(Player player, PlayerInteractEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.RIGHTCLICKAIR)) {
            success += p.d(player, event);
        }
        return success;
    }

    @Override
    public int e(Player player, PlayerInteractEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.LEFTCLICKAIR)) {
            success += p.e(player, event);
        }
        return success;
    }

    @Override
    public int f(Player player, PlayerInteractEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.RIGHTCLICKBLOCK)) {
            success += p.f(player, event);
        }
        return success;
    }

    @Override
    public int g(Player player, PlayerInteractEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.RIGHTCLICKBLOCK)) {
            success += p.g(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerInteractEntityEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.RIGHTCLICKENTITY)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, ItemStack consumeItem) {
        int success = 0;
        for (bi_1 p : this.a(am_0.CONSUME)) {
            success += p.a(player, consumeItem);
        }
        return success;
    }

    @Override
    public int b(Player player, LivingEntity target) {
        int success = 0;
        for (bi_1 p : this.a(am_0.KILL)) {
            success += p.b(player, target);
        }
        return success;
    }

    @Override
    public int a(Player player, LivingEntity killer) {
        int success = 0;
        for (bi_1 p : this.a(am_0.KILLED)) {
            success += p.a(player, killer);
        }
        return success;
    }

    @Override
    public int b(Player player, Block block) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BLOCKPLACE)) {
            success += p.b(player, block);
        }
        return success;
    }

    @Override
    public int a(Player player, Block block) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BLOCKBREAK)) {
            success += p.a(player, block);
        }
        return success;
    }

    @Override
    public int a(Player player, Projectile arrow) {
        int success = 0;
        for (bi_1 p : this.a(am_0.ARROWHIT)) {
            success += p.a(player, arrow);
        }
        return success;
    }

    @Override
    public int a(Player player, Entity target, Projectile arrow) {
        int success = 0;
        for (bi_1 p : this.a(am_0.ARROWENTITY)) {
            success += p.a(player, target, arrow);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerFishEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.FISH)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, au_0 weatherType) {
        int success = 0;
        for (bi_1 p : this.a(am_0.WEATHER)) {
            success += p.a(player, weatherType);
        }
        return success;
    }

    @Override
    public int j(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.STORM)) {
            success += p.j(player);
        }
        return success;
    }

    @Override
    public int k(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.THUNDER)) {
            success += p.k(player);
        }
        return success;
    }

    @Override
    public int a(Player player, Location location, TreeType species) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BONEGROW)) {
            success += p.a(player, location, species);
        }
        return success;
    }

    @Override
    public int a(Player player, int oldLevel, int newLevel) {
        int success = 0;
        for (bi_1 p : this.a(am_0.LEVELUP)) {
            success += p.a(player, oldLevel, newLevel);
        }
        return success;
    }

    @Override
    public int b(Player player, Location respawnLocation) {
        int success = 0;
        for (bi_1 p : this.a(am_0.RESPAWN)) {
            success += p.b(player, respawnLocation);
        }
        return success;
    }

    @Override
    public int l(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKGROUND)) {
            success += p.l(player);
        }
        return success;
    }

    @Override
    public int m(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKFLY)) {
            success += p.m(player);
        }
        return success;
    }

    @Override
    public int n(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKLIQUID)) {
            success += p.n(player);
        }
        return success;
    }

    @Override
    public int o(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKWATER)) {
            success += p.o(player);
        }
        return success;
    }

    @Override
    public int p(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKLAVA)) {
            success += p.p(player);
        }
        return success;
    }

    @Override
    public int q(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKSLEEP)) {
            success += p.q(player);
        }
        return success;
    }

    @Override
    public int r(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKSNEAK)) {
            success += p.r(player);
        }
        return success;
    }

    @Override
    public int s(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKSPRINT)) {
            success += p.s(player);
        }
        return success;
    }

    @Override
    public int t(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKVEHICLE)) {
            success += p.t(player);
        }
        return success;
    }

    @Override
    public int u(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKWEATHER)) {
            success += p.u(player);
        }
        return success;
    }

    @Override
    public int v(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKSUN)) {
            success += p.v(player);
        }
        return success;
    }

    @Override
    public int w(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKTHUNDER)) {
            success += p.w(player);
        }
        return success;
    }

    @Override
    public int x(Player player) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TICKSTORM)) {
            success += p.x(player);
        }
        return success;
    }

    @Override
    public int c(Player player, Block bed) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BED)) {
            success += p.c(player, bed);
        }
        return success;
    }

    @Override
    public int d(Player player, Block bed) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BEDENTER)) {
            success += p.d(player, bed);
        }
        return success;
    }

    @Override
    public int e(Player player, Block bed) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BEDLEFT)) {
            success += p.e(player, bed);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerLeashEntityEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.LEASH)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerUnleashEntityEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.UNLEASH)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerEditBookEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.EDITBOOK)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, EntityRegainHealthEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.REGAIN)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, HorseJumpEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.HORSEJUMP)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerBucketFillEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BUCKETFILL)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerBucketEmptyEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.BUCKETEMPTY)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerItemBreakEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.ITEMBREAK)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, PlayerTeleportEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TELEPORT)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, EntityCombustEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.COMBUST)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, EntityTameEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.TAME)) {
            success += p.a(player, event);
        }
        return success;
    }

    @Override
    public int a(Player player, EnchantItemEvent event) {
        int success = 0;
        for (bi_1 p : this.a(am_0.ENCHANT)) {
            success += p.a(player, event);
        }
        return success;
    }

    public int hashCode() {
        return this.s;
    }

    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return this.s == ((RPGItem)obj).s;
    }

    @a_0
    public boolean isSimilar(RPGItem item) {
        return this.equals(item);
    }

    @a_0
    public boolean isSimilar(ItemStack item) {
        return this.equals(by_0.a(item));
    }

    public static List<String> a(RPGItem rItem, String locale, as_0 rpgMeta) {
        ArrayList<String> list = new ArrayList<String>();
        if (rItem.n != null && rpgMeta.g(4)) {
            try {
                int setPartId = ((Number)rpgMeta.j_(4)).intValue();
                bz_1.a setPart = rItem.n.a(setPartId);
                if (setPart != null) {
                    String setsDisplayLore = ao_0.a("display.sets.top", locale);
                    if (!setsDisplayLore.isEmpty()) {
                        list.add(String.format(setsDisplayLore, setPart.a.a) + p);
                    }
                    for (RPGItem need : rItem.n.b) {
                        ChatColor color = setPart.c.contains(need) ? ChatColor.GREEN : ChatColor.GRAY;
                        list.add((Object)color + bg_1.b(need.getDisplay()) + p);
                    }
                    setsDisplayLore = ao_0.a("display.sets.bottom", locale);
                    if (!setsDisplayLore.isEmpty()) {
                        list.add(setsDisplayLore + p);
                    }
                    if (!setPart.e.isEmpty() && !(setsDisplayLore = ao_0.a("display.sets.lore.top", locale)).isEmpty()) {
                        list.add(setsDisplayLore + p);
                    }
                    for (String setLore : setPart.e) {
                        list.add(setLore + p);
                    }
                    if (!setPart.e.isEmpty() && !(setsDisplayLore = ao_0.a("display.sets.lore.bottom", locale)).isEmpty()) {
                        list.add(setsDisplayLore + p);
                    }
                }
            }
            catch (Exception setPartId) {
                // empty catch block
            }
        }
        return list;
    }
}

