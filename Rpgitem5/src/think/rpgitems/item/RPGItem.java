// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.item;

import yo.X;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.TreeType;
import yo.aU;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.LivingEntity;
import yo.bC;
import org.bukkit.potion.PotionEffectType;
import yo.ce;
import yo.aY;
import yo.cD;
import org.bukkit.Location;
import org.bukkit.metadata.Metadatable;
import yo.aR;
import org.bukkit.command.CommandSender;
import yo.cv;
import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import yo.a;
import yo.aN;
import yo.ba;
import org.apache.commons.lang.StringUtils;
import org.bukkit.enchantments.Enchantment;
import yo.aS;
import java.io.IOException;
import yo.bm;
import org.bukkit.ChatColor;
import yo.aZ;
import org.bukkit.entity.Player;
import java.util.Map;
import org.bukkit.inventory.ShapedRecipe;
import yo.by;
import org.bukkit.inventory.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemoryConfiguration;
import java.util.Iterator;
import java.util.Set;
import yo.bF;
import java.util.Collection;
import java.util.HashSet;
import yo.bb;
import yo.bi;
import yo.bk;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import yo.bg;
import org.bukkit.configuration.ConfigurationSection;
import java.io.Closeable;
import yo.aO;
import org.bukkit.Material;
import think.rpgitems.Plugin;
import java.util.Random;
import yo.bz;
import yo.Y;
import yo.bI;
import java.util.ArrayList;
import yo.aM;
import java.util.List;
import yo.bD;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import yo.bv;

public final class RPGItem implements bv
{
    private ItemStack q;
    private HashMap<String, ItemMeta> r;
    private int s;
    private String t;
    private String u;
    private boolean v;
    private String w;
    private String x;
    private bD y;
    public boolean a;
    private int z;
    private int A;
    private int B;
    private int C;
    private String D;
    private String E;
    private String F;
    private String G;
    public double b;
    public int c;
    public int d;
    public double e;
    private List<String> H;
    public List<String> f;
    public List<String> g;
    public boolean h;
    private HashMap<aM, ArrayList<bI>> I;
    public int i;
    public boolean j;
    public List<ItemStack> k;
    public Y<String> l;
    public int m;
    private Boolean keepLore;
    private Boolean keepEnchant;
    private int J;
    private boolean K;
    private boolean L;
    public bz n;
    public String o;
    public static final String p = "¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr";
    private static final String M = "¡ìb¡ìl¡ìo¡ìa¡ìm¡ì7¡ìf¡ì9";
    private static final Random N;
    
    public RPGItem(final String name, final int id) {
        this.r = new HashMap<String, ItemMeta>();
        this.y = bD.TRASH;
        this.a = true;
        this.z = 0;
        this.A = 3;
        this.B = 0;
        this.C = 0;
        this.D = "";
        this.E = Plugin.c.c().getString("defaults.sword", "Sword");
        this.F = Plugin.c.c().getString("defaults.hand", "One handed");
        this.G = "";
        this.b = 0.0;
        this.c = -1;
        this.d = 0;
        this.e = 0.0;
        this.H = new ArrayList<String>();
        this.f = new ArrayList<String>();
        this.g = new ArrayList<String>();
        this.h = false;
        this.I = new HashMap<aM, ArrayList<bI>>();
        this.i = 6;
        this.j = false;
        this.k = null;
        this.l = new Y<String>();
        this.m = 0;
        this.keepLore = false;
        this.keepEnchant = false;
        this.K = false;
        this.L = false;
        this.n = null;
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.t = name;
        this.s = id;
        this.u = b(id);
        this.q = new ItemStack(Material.WOOD_SWORD);
        this.x = this.q.getType().toString();
        this.a(aO.b(), this.q.getItemMeta());
        this.a();
    }
    
    public RPGItem(final ConfigurationSection s, final boolean rebuild) {
        this.r = new HashMap<String, ItemMeta>();
        this.y = bD.TRASH;
        this.a = true;
        this.z = 0;
        this.A = 3;
        this.B = 0;
        this.C = 0;
        this.D = "";
        this.E = Plugin.c.c().getString("defaults.sword", "Sword");
        this.F = Plugin.c.c().getString("defaults.hand", "One handed");
        this.G = "";
        this.b = 0.0;
        this.c = -1;
        this.d = 0;
        this.e = 0.0;
        this.H = new ArrayList<String>();
        this.f = new ArrayList<String>();
        this.g = new ArrayList<String>();
        this.h = false;
        this.I = new HashMap<aM, ArrayList<bI>>();
        this.i = 6;
        this.j = false;
        this.k = null;
        this.l = new Y<String>();
        this.m = 0;
        this.keepLore = false;
        this.keepEnchant = false;
        this.K = false;
        this.L = false;
        this.n = null;
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.t = s.getString("name");
        this.s = s.getInt("id");
        this.b(s.getString("display"), false);
        this.c(s.getString("type", Plugin.c.c().getString("defaults.sword", "Sword")), false);
        this.d(s.getString("hand", Plugin.c.c().getString("defaults.hand", "One handed")), false);
        this.f(s.getString("lore"), false);
        this.f = (List<String>)s.getStringList("description");
        for (int i = 0; i < this.f.size(); ++i) {
            this.f.set(i, bg.a((String)this.f.get(i)));
        }
        this.g = (List<String>)s.getStringList("customlore");
        for (int i = 0; i < this.g.size(); ++i) {
            this.g.set(i, bg.a((String)this.g.get(i)));
        }
        this.h = s.getBoolean("onlyShowDescription");
        this.y = bD.valueOf(s.getString("quality"));
        this.a = s.getBoolean("directDamage", true);
        this.z = s.getInt("damageMin");
        this.A = s.getInt("damageMax");
        this.B = s.getInt("armour", 0);
        this.C = s.getInt("armourNum", 0);
        this.q = new ItemStack(Material.valueOf(s.getString("item")));
        final ItemMeta meta = this.q.getItemMeta();
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)meta).setColor(Color.fromRGB(s.getInt("item_colour", 0)));
        }
        else {
            this.q.setDurability((short)s.getInt("item_data", 0));
        }
        for (final String locale : aO.a()) {
            this.a(locale, meta.clone());
        }
        this.G = s.getString("nbt", "");
        final bk protocolLib = bi.a(bk.class);
        if (protocolLib.b()) {
            try {
                this.G = bb.a.a(bb.a.a(this.G));
            }
            catch (Exception ex2) {}
        }
        this.b = s.getDouble("repairPrice");
        if (this.b < 0.0) {
            this.b = 0.0;
        }
        this.c = s.getInt("level", -1);
        this.d = s.getInt("cost.exp");
        this.e = s.getDouble("cost.money");
        this.H = new ArrayList<String>();
        Set<String> tempSets = new HashSet<String>();
        for (final String pl : s.getStringList("ignorePlugins")) {
            tempSets.add(pl.toLowerCase());
        }
        this.H.addAll(tempSets);
        tempSets.clear();
        tempSets = null;
        final ConfigurationSection powerList = s.getConfigurationSection("powers");
        if (powerList != null) {
            for (final String sectionKey : powerList.getKeys(false)) {
                final ConfigurationSection section = powerList.getConfigurationSection(sectionKey);
                try {
                    if (!bI.a.containsKey(section.getString("powerName"))) {
                        continue;
                    }
                    final bI pow = (bI)bI.a.get(section.getString("powerName")).newInstance();
                    pow.a(section);
                    (pow.c = this).a(pow, false);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        this.u = b(this.s);
        this.v = s.getBoolean("haspermission", false);
        this.w = s.getString("permission", "a.default.permission");
        this.i = s.getInt("recipechance", 6);
        this.j = s.getBoolean("hasRecipe", false);
        if (this.j) {
            this.k = (List<ItemStack>)s.getList("recipe");
        }
        final ConfigurationSection drops = s.getConfigurationSection("dropChances");
        if (drops != null) {
            for (final String key : drops.getKeys(false)) {
                double chance = drops.getDouble(key, 0.0);
                chance = Math.min(chance, 100.0);
                if (chance > 0.0) {
                    this.l.a(key, chance);
                    if (!bF.c.containsKey(key)) {
                        bF.c.put(key, new HashSet<Integer>());
                    }
                    final Set<Integer> set = bF.c.get(key);
                    set.add(this.getID());
                }
                else {
                    this.l.b_(key);
                    if (bF.c.containsKey(key)) {
                        final Set<Integer> set = bF.c.get(key);
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
        catch (Exception e2) {
            this.keepLore = null;
        }
        object = s.get("keep.enchant");
        try {
            this.keepEnchant = Boolean.valueOf(object.toString().toUpperCase());
        }
        catch (Exception e2) {
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
    
    public void a(final ConfigurationSection s) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        s.set("name", (Object)this.t);
        s.set("id", (Object)this.s);
        s.set("haspermission", (Object)this.v);
        s.set("permission", (Object)this.w);
        s.set("display", (Object)this.x.replaceAll("¡ì", "&"));
        s.set("quality", (Object)this.y.toString());
        s.set("directDamage", (Object)this.a);
        s.set("damageMin", (Object)this.z);
        s.set("damageMax", (Object)this.A);
        s.set("armour", (Object)this.B);
        s.set("armourNum", (Object)this.C);
        s.set("type", (Object)this.E.replaceAll("¡ì", "&"));
        s.set("hand", (Object)this.F.replaceAll("¡ì", "&"));
        s.set("lore", (Object)this.D.replaceAll("¡ì", "&"));
        s.set("nbt", (Object)this.G);
        s.set("repairPrice", (Object)this.b);
        s.set("level", (Object)this.c);
        s.set("cost.exp", (Object)this.d);
        s.set("cost.money", (Object)this.e);
        s.set("ignorePlugins", (Object)this.H);
        s.set("description", (Object)this.f);
        s.set("customlore", (Object)this.g);
        s.set("onlyShowDescription", (Object)this.h);
        s.set("item", (Object)this.q.getType().toString());
        final ItemMeta meta = this.r.get(aO.b());
        if (meta instanceof LeatherArmorMeta) {
            s.set("item_colour", (Object)((LeatherArmorMeta)meta).getColor().asRGB());
        }
        else {
            s.set("item_data", (Object)this.q.getDurability());
        }
        final ConfigurationSection powerConfigs = s.createSection("powers");
        int i = 0;
        for (final ArrayList<bI> ps : this.I.values()) {
            for (final bI p : ps) {
                final MemoryConfiguration pConfig = new MemoryConfiguration();
                pConfig.set("powerName", (Object)p.e());
                p.b((ConfigurationSection)pConfig);
                powerConfigs.set(Integer.toString(i), (Object)pConfig);
                ++i;
            }
        }
        s.set("recipechance", (Object)this.i);
        s.set("hasRecipe", (Object)this.j);
        if (this.j) {
            s.set("recipe", (Object)this.k);
        }
        final ConfigurationSection drops = s.createSection("dropChances");
        for (final String key : this.l.v_()) {
            drops.set(key, (Object)this.l.b(key));
        }
        s.set("disappearChance", (Object)this.m);
        s.set("keep.lore", (Object)this.keepLore);
        s.set("keep.enchant", (Object)this.keepEnchant);
        s.set("maxDurability", (Object)this.J);
        s.set("forceBar", (Object)this.L);
    }
    
    public void a(final int id) {
        this.s = id;
        this.u = b(id);
    }
    
    public void a(final boolean removeOld) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (removeOld) {
            final Iterator<Recipe> it = (Iterator<Recipe>)Bukkit.recipeIterator();
            while (it.hasNext()) {
                final Recipe rRecipe = it.next();
                final RPGItem rpgitem = by.a(rRecipe.getResult());
                if (rpgitem == null) {
                    continue;
                }
                if (rpgitem.getID() != this.getID()) {
                    continue;
                }
                it.remove();
            }
        }
        if (this.j) {
            final Set<ItemStack> iSet = new HashSet<ItemStack>();
            for (final ItemStack m : this.k) {
                iSet.add(m);
            }
            final ItemStack[] iList = iSet.toArray(new ItemStack[iSet.size()]);
            this.q.setItemMeta(this.b(aO.b()));
            final ShapedRecipe shapedRecipe = new ShapedRecipe(this.q);
            int i = 0;
            final Map<ItemStack, Character> iMap = new HashMap<ItemStack, Character>();
            for (final ItemStack j : iList) {
                iMap.put(j, (char)(65 + i));
                ++i;
            }
            iMap.put(null, ' ');
            final StringBuilder out = new StringBuilder();
            for (final ItemStack k : this.k) {
                out.append(iMap.get(k));
            }
            final String shape = out.toString();
            shapedRecipe.shape(new String[] { shape.substring(0, 3), shape.substring(3, 6), shape.substring(6, 9) });
            for (final Map.Entry<ItemStack, Character> e : iMap.entrySet()) {
                if (e.getKey() != null) {
                    shapedRecipe.setIngredient((char)e.getValue(), e.getKey().getType(), (int)e.getKey().getDurability());
                }
            }
            Bukkit.addRecipe((Recipe)shapedRecipe);
        }
    }
    
    public boolean a(final Player player) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final String locale = aO.a(player);
        if (this.d > 0 && aZ.b(player) < this.d) {
            player.sendMessage(ChatColor.RED + String.format(aO.a("message.exp.notenough", locale), this.d));
            return false;
        }
        if (this.e > 0.0 && !bi.a(bm.class).b(player.getName(), this.e, locale)) {
            return false;
        }
        if (this.d > 0) {
            aZ.a(player, aZ.b(player) - this.d);
            player.sendMessage(ChatColor.RED + String.format(aO.a("message.exp.withdraw", locale), this.d));
        }
        if (this.e > 0.0) {
            bi.a(bm.class).c(player.getName(), this.e, locale);
        }
        return true;
    }
    
    public ArrayList<bI> a(final aM type) {
        ArrayList<bI> pows = this.I.get(type);
        if (pows == null) {
            pows = new ArrayList<bI>();
            this.I.put(type, pows);
        }
        return pows;
    }
    
    public void a(final ItemStack itemHold) throws IOException {
        final bk protocolLib = bi.a(bk.class);
        if (protocolLib.b()) {
            this.G = bb.a.b(bb.a.b(itemHold));
        }
    }
    
    public void a() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        for (final String locale : aO.a()) {
            if (!this.r.containsKey(locale)) {
                this.a(locale, this.b(aO.b()));
            }
        }
        for (final String locale : aO.a()) {
            final List<String> lines = this.a(locale);
            final ItemMeta meta = this.b(locale);
            meta.setDisplayName((String)lines.get(0));
            lines.remove(0);
            meta.setLore((List)lines);
            this.a(locale, meta);
        }
        for (final Player player : bg.c()) {
            final Iterator<ItemStack> it = (Iterator<ItemStack>)player.getInventory().iterator();
            final String locale2 = aO.a(player);
            while (it.hasNext()) {
                final ItemStack rItem = it.next();
                if (by.a(rItem) != null) {
                    a(rItem, locale2, false);
                }
            }
            for (final ItemStack rItem2 : player.getInventory().getArmorContents()) {
                if (by.a(rItem2) != null) {
                    a(rItem2, locale2, false);
                }
            }
        }
        this.a(true);
    }
    
    public static aS b(final ItemStack item) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore() || item.getItemMeta().getLore().isEmpty()) {
            return new aS();
        }
        return aS.a(item.getItemMeta().getLore().get(0));
    }
    
    public static void a(final ItemStack item, final Player player) {
        a(item, aO.a(player), b(item));
    }
    
    public static void a(final ItemStack item, final String locale) {
        a(item, locale, b(item));
    }
    
    public static void a(final ItemStack item, final Player player, final aS rpgMeta) {
        a(item, aO.a(player), rpgMeta, false);
    }
    
    public static void a(final ItemStack item, final String locale, final aS rpgMeta) {
        a(item, locale, rpgMeta, false);
    }
    
    public static void a(final ItemStack item, final String locale, final boolean updateDurability) {
        a(item, locale, b(item), updateDurability);
    }
    
    public static void a(final ItemStack item, final String locale, final aS rpgMeta, final boolean updateDurability) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final RPGItem rItem = by.a(item);
        if (rItem == null) {
            return;
        }
        List<String> extraLores = new ArrayList<String>();
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            extraLores = a(rItem, item.getItemMeta().getLore());
        }
        item.setType(rItem.q.getType());
        final ItemMeta meta = rItem.b(locale);
        if (!(meta instanceof LeatherArmorMeta) && updateDurability) {
            item.setDurability(rItem.q.getDurability());
        }
        final List<String> lores = (List<String>)meta.getLore();
        rItem.a(rpgMeta, item, locale, lores);
        lores.set(0, meta.getLore().get(0) + rpgMeta.k());
        a(lores);
        lores.addAll(a(rItem, locale, rpgMeta));
        lores.addAll(extraLores);
        meta.setLore((List)lores);
        final Map<Enchantment, Integer> enchs = a(rItem, item.getItemMeta());
        item.setItemMeta(meta);
        if (!enchs.isEmpty()) {
            item.addUnsafeEnchantments((Map)enchs);
        }
        final bk protocolLib = bi.a(bk.class);
        if (protocolLib.b() && !rItem.G.isEmpty()) {
            try {
                bb.a.a(item, bb.a.a(bb.a.b(item), bb.a.a(rItem.G)));
            }
            catch (Exception ex2) {}
        }
    }
    
    public static List<String> a(final List<String> lores) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        String line = lores.get(0);
        while (line.startsWith("¡ìb¡ìl¡ìo¡ìa¡ìm¡ì7¡ìf¡ì9")) {
            try {
                line = line.substring("¡ìb¡ìl¡ìo¡ìa¡ìm¡ì7¡ìf¡ì9".length() + 16);
            }
            catch (Exception ex2) {}
        }
        lores.set(0, "¡ìb¡ìl¡ìo¡ìa¡ìm¡ì7¡ìf¡ì9" + b(lores.size()) + line);
        return lores;
    }
    
    public static Map<Enchantment, Integer> a(final RPGItem rItem, final ItemMeta meta) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return rItem.c("enchant") ? meta.getEnchants() : new HashMap<Enchantment, Integer>();
    }
    
    public static List<String> a(final RPGItem rItem, final List<String> lores) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final List<String> extraLores = new ArrayList<String>();
        if (!rItem.c("lore")) {
            return extraLores;
        }
        for (int i = 0; i < lores.size(); ++i) {
            final String line = lores.get(i);
            if (line.startsWith("¡ìb¡ìl¡ìo¡ìa¡ìm¡ì7¡ìf¡ì9")) {
                try {
                    final int size = by.d(line.substring("¡ìb¡ìl¡ìo¡ìa¡ìm¡ì7¡ìf¡ì9".length()));
                    i += size - 1;
                    continue;
                }
                catch (Exception ex2) {}
            }
            if (!line.endsWith("¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr")) {
                extraLores.add(line);
            }
        }
        if (extraLores.size() == lores.size()) {
            extraLores.clear();
        }
        return extraLores;
    }
    
    public void a(final aS rpgMeta, final ItemStack item, final String locale, final List<String> lore) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (this.J > 0) {
            if (!rpgMeta.c(0)) {
                ((X<Integer>)rpgMeta).a(0, Integer.valueOf(this.J));
            }
            final int durability = ((X<Number>)rpgMeta).j_(0).intValue();
            if (this.L) {
                final StringBuilder out = new StringBuilder();
                final int displayMode = bg.a(Plugin.c.c().getInt("durabilityBar.mode"), 0, 1);
                switch (displayMode) {
                    case 0: {
                        final int tooltipWidth = bg.a(Plugin.c.c().getInt("durabilityBar.graphLength"), 5, 150);
                        final char boxChar = '\u25a0';
                        final int boxCount = tooltipWidth / 4;
                        final int mid = boxCount * (durability / this.J);
                        for (int i = 0; i < boxCount; ++i) {
                            out.append((i < mid) ? ChatColor.GREEN : ((i == mid) ? ChatColor.YELLOW : ChatColor.RED));
                            out.append(boxChar);
                        }
                        break;
                    }
                    case 1: {
                        out.append(String.format(aO.a("display.durability.num", locale), durability, this.J));
                        break;
                    }
                }
                lore.add(out.toString());
            }
            if (this.K) {
                item.setDurability((short)this.J);
            }
        }
        else if (this.J <= 0) {
            item.setDurability((short)(this.K ? 0 : this.q.getDurability()));
        }
    }
    
    public List<String> a(final String locale) {
        return this.a(locale, false);
    }
    
    public List<String> a(final String locale, final boolean ignoreOnlyShow) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        int width = 80;
        int dWidth = n(ChatColor.stripColor(this.x));
        if (dWidth > width) {
            width = dWidth;
        }
        dWidth = m(ChatColor.stripColor(this.F + "     " + this.E));
        if (dWidth > width) {
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
            }
            else if (this.B == 0 && this.z == 0 && this.A == 0) {
                damageStr = null;
            }
            else if (this.z == this.A) {
                damageStr = this.z + " " + Plugin.c.c().getString("defaults.damage", "Damage");
            }
            else {
                damageStr = this.z + "-" + this.A + " " + Plugin.c.c().getString("defaults.damage", "Damage");
            }
            if (this.z != 0 || this.A != 0 || this.B != 0) {
                dWidth = m(damageStr);
                if (dWidth > width) {
                    width = dWidth;
                }
            }
            for (final ArrayList<bI> ps : this.I.values()) {
                for (final bI p : ps) {
                    dWidth = m(ChatColor.stripColor(p.a(locale)));
                    if (dWidth > width) {
                        width = dWidth;
                    }
                }
            }
        }
        for (final String s : this.f) {
            dWidth = m(ChatColor.stripColor(s));
            if (dWidth > width) {
                width = dWidth;
            }
        }
        final ArrayList<String> output = new ArrayList<String>();
        output.add(this.u + this.y.colour + ChatColor.BOLD + this.x);
        output.add(ChatColor.WHITE + this.F + StringUtils.repeat(" ", (width - m(ChatColor.stripColor(this.F + this.E))) / 10) + this.E);
        if (!ignoreOnlyShow && !this.h) {
            final ba<String, Integer> infoMaps = new ba<String, Integer>();
            if (this.m > 0) {
                final String line = (this.m > 0) ? (ChatColor.RED + String.format(aO.a("display.disappear", locale), this.m)) : "";
                infoMaps.put(line, m(ChatColor.stripColor(line)));
            }
            if (this.b > 0.0) {
                final String line = ChatColor.YELLOW + String.format(aO.a("display.repair.price", locale), this.b);
                infoMaps.put(line, m(ChatColor.stripColor(line)));
            }
            if (this.c > -1) {
                final String line = ChatColor.RED + String.format(aO.a("display.level", locale), this.c + 1);
                infoMaps.put(line, m(ChatColor.stripColor(line)));
            }
            if (this.d > 0) {
                final String line = ChatColor.RED + String.format(aO.a("display.exp.cost", locale), this.d);
                infoMaps.put(line, m(ChatColor.stripColor(line)));
            }
            if (this.e > 0.0) {
                final String line = ChatColor.RED + String.format(aO.a("display.money.cost", locale), this.e);
                infoMaps.put(line, m(ChatColor.stripColor(line)));
            }
            if (!infoMaps.isEmpty() && width < infoMaps.e()) {
                width = infoMaps.e();
            }
            if (damageStr != null) {
                output.add(ChatColor.WHITE + damageStr);
            }
            for (final String line2 : infoMaps.keySet()) {
                output.add(line2);
            }
            for (final ArrayList<bI> ps2 : this.I.values()) {
                for (final bI p2 : ps2) {
                    output.add(p2.a(locale));
                }
            }
            if (this.D.length() != 0) {
                int cWidth = 0;
                int tWidth = 0;
                StringBuilder out = new StringBuilder();
                StringBuilder temp = new StringBuilder();
                out.append(ChatColor.YELLOW);
                out.append(ChatColor.ITALIC);
                String currentColour = ChatColor.YELLOW.toString();
                final String dMsg = "''" + this.D + "''";
                for (int i = 0; i < dMsg.length(); ++i) {
                    final char c = dMsg.charAt(i);
                    temp.append(c);
                    if (c == '¡ì' || c == '&') {
                        ++i;
                        temp.append(dMsg.charAt(i));
                        currentColour = "¡ì" + dMsg.charAt(i);
                    }
                    else {
                        if (c == ' ') {
                            tWidth += 4;
                        }
                        else {
                            tWidth += aN.a[c] + 1;
                        }
                        if (c == ' ' || i == dMsg.length() - 1) {
                            if (cWidth + tWidth > width) {
                                cWidth = 0;
                                cWidth += tWidth;
                                tWidth = 0;
                                output.add(out.toString());
                                out = new StringBuilder();
                                out.append(currentColour);
                                out.append(ChatColor.ITALIC);
                                out.append((CharSequence)temp);
                                temp = new StringBuilder();
                            }
                            else {
                                out.append((CharSequence)temp);
                                temp = new StringBuilder();
                                cWidth += tWidth;
                                tWidth = 0;
                            }
                        }
                    }
                }
                out.append((CharSequence)temp);
                output.add(out.toString());
            }
        }
        for (final String s2 : this.f) {
            output.add(s2);
        }
        return output;
    }
    
    @a
    public ItemStack toItemStack() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.toItemStack(aO.b());
    }
    
    @a
    public ItemStack toItemStack(final String locale) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final ItemStack rStack = this.q.clone();
        final aS rpgMeta = new aS();
        final ItemMeta meta = this.b(locale);
        List<String> lore = (List<String>)meta.getLore();
        this.a(rpgMeta, rStack, locale, lore);
        lore.set(0, meta.getLore().get(0) + rpgMeta.k());
        lore = a(lore);
        lore.addAll(this.g);
        meta.setLore((List)lore);
        rStack.setItemMeta(meta);
        return rStack;
    }
    
    public ItemMeta b(final String locale) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        ItemMeta meta = this.r.get(locale);
        if (meta == null) {
            meta = this.r.get(aO.b());
        }
        return meta.clone();
    }
    
    public final void a(final String locale, final ItemMeta meta) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.r.put(locale, meta);
    }
    
    @a
    public String getName() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.t;
    }
    
    @a
    public int getID() {
        return this.s;
    }
    
    public String b() {
        return this.u;
    }
    
    public static String b(final int id) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final String hex = String.format("%08x", id);
        final StringBuilder out = new StringBuilder();
        for (final char h : hex.toCharArray()) {
            out.append('¡ì');
            out.append(h);
        }
        return out.toString();
    }
    
    private static int m(final String str) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        int width = 0;
        for (int i = 0; i < str.length(); ++i) {
            final char c = str.charAt(i);
            width += aN.a[c] + 1;
        }
        return width;
    }
    
    private static int n(final String str) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        int width = 0;
        for (int i = 0; i < str.length(); ++i) {
            final char c = str.charAt(i);
            width += aN.a[c] + 2;
        }
        return width;
    }
    
    public boolean c(String keepType) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        try {
            keepType = keepType.substring(0, 1).toUpperCase() + keepType.substring(1).toLowerCase();
            final Field field = this.getClass().getDeclaredField("keep" + keepType);
            field.setAccessible(true);
            final Object object = field.get(this);
            if (object != null) {
                return (boolean)object;
            }
        }
        catch (Exception ex2) {}
        return Plugin.d.getBoolean("keep." + keepType.toLowerCase(), true);
    }
    
    public List<String> c() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return (List<String>)Lists.newArrayList((Iterable)this.H);
    }
    
    public boolean a(final Class<? extends cv> clzz) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.d(clzz.getSimpleName());
    }
    
    public boolean d(final String pluginName) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.H.contains(pluginName.toLowerCase());
    }
    
    public void a(final cv ips) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (ips != null) {
            final String plugin = ips.toString().toLowerCase();
            if (!this.H.remove(plugin)) {
                this.H.add(plugin);
            }
        }
    }
    
    public void a(final CommandSender sender) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final String locale = aO.a(sender);
        final List<String> lines = this.a(locale, true);
        for (final String s : lines) {
            sender.sendMessage(s);
        }
        for (final String s : this.g) {
            sender.sendMessage(s);
        }
        sender.sendMessage(String.format(aO.a("message.print.durability", locale), this.J));
    }
    
    public void e(final String str) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.b(str, true);
    }
    
    public void b(final String str, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.x = bg.a(str);
        if (update) {
            this.a();
        }
    }
    
    @a
    public String getDisplay() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.y.colour + ChatColor.BOLD + this.x;
    }
    
    public void f(final String str) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.c(str, true);
    }
    
    public void c(final String str, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.E = bg.a(str);
        if (update) {
            this.a();
        }
    }
    
    public String d() {
        return this.E;
    }
    
    public void g(final String h) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.d(h, true);
    }
    
    public void d(final String h, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.F = bg.a(h);
        if (update) {
            this.a();
        }
    }
    
    public String e() {
        return this.F;
    }
    
    public void a(final int min, final int max) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.a(min, max, true);
    }
    
    public void a(final int min, final int max, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
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
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.i;
    }
    
    public void c(final int p) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.a(p, true);
    }
    
    public void a(final int p, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
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
    
    public void h(final String p) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.e(p, true);
    }
    
    public void e(final String p, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.w = p;
        if (update) {
            this.a();
        }
    }
    
    public void b(final boolean b) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.a(b, true);
    }
    
    public void a(final boolean b, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.v = b;
        if (update) {
            this.a();
        }
    }
    
    public boolean b(final Player player) {
        return this.a((CommandSender)player, true) && player.getLevel() >= this.c && !aR.b((Metadatable)player, aR.a.POWER_BLOCK) && this.a(player, player.getLocation());
    }
    
    public boolean a(final Player player, final Location loc) {
        final Collection<cv> ipss = cD.e();
        for (final cv ips : ipss) {
            if (!ips.c(player, loc) && !this.a(ips.f())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean b(final CommandSender sender) {
        return this.a(sender, false);
    }
    
    public boolean a(final CommandSender sender, final boolean printWarn) {
        if (this.j() && !sender.hasPermission(this.i())) {
            if (printWarn) {
                sender.sendMessage(ChatColor.RED + aO.a("message.error.permission", aO.a(sender)));
            }
            return false;
        }
        return true;
    }
    
    public void d(final int a) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.b(a, true);
    }
    
    public void b(final int a, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.B = a;
        final boolean b = false;
        this.A = (b ? 1 : 0);
        this.z = (b ? 1 : 0);
        if (update) {
            this.a();
        }
    }
    
    public void e(final int a) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.c(a, true);
    }
    
    public void c(final int a, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.C = a;
        final boolean b = false;
        this.A = (b ? 1 : 0);
        this.z = (b ? 1 : 0);
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
    
    public void i(final String str) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.f(str, true);
    }
    
    public void f(final String str, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.D = bg.a(str);
        if (update) {
            this.a();
        }
    }
    
    public String m() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.D;
    }
    
    public void a(final bD q) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.a(q, true);
    }
    
    public void a(final bD q, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.y = q;
        if (update) {
            this.a();
        }
    }
    
    public bD n() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.y;
    }
    
    public void a(final Material mat) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.a(mat, true);
    }
    
    public void a(final Material mat, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        if (this.J == this.q.getType().getMaxDurability()) {
            this.J = mat.getMaxDurability();
        }
        this.q.setType(mat);
        if (update) {
            this.a();
        }
    }
    
    public void a(final short value, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.q.setDurability(value);
        this.J = value;
        if (update) {
            this.a();
        }
    }
    
    public void a(final short value) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.q.setDurability(value);
        this.J = value;
    }
    
    public short o() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.q.getDurability();
    }
    
    public Material p() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return this.q.getType();
    }
    
    public void f(final int newVal) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.d(newVal, true);
    }
    
    public void d(final int newVal, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.J = newVal;
        if (update) {
            this.a();
        }
    }
    
    public int q() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        return (this.J <= 0) ? -1 : this.J;
    }
    
    public aY r() {
        return new aY((this.f() != this.g()) ? (this.f() + RPGItem.N.nextInt(this.g() - this.f())) : ((double)this.f()));
    }
    
    public void y(final Player player) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        bg.a(player, this.toItemStack(aO.a(player)));
    }
    
    public void a(final bI power) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.a(power, true);
    }
    
    public void a(final bI power, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.a(power.d).add(power);
        bI.b.a(power.e(), bI.b.b(power.e()) + 1);
        if (update) {
            this.a();
        }
    }
    
    public void s() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.I.clear();
        this.a();
    }
    
    public void b(final aM eventType) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final List<bI> pows = this.I.remove(eventType);
        if (pows != null) {
            pows.clear();
        }
        this.a();
    }
    
    public boolean j(final String pow) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        for (final ArrayList<bI> ps : this.I.values()) {
            final Iterator<bI> it = ps.iterator();
            while (it.hasNext()) {
                final bI p = it.next();
                if (p.e().equalsIgnoreCase(pow)) {
                    it.remove();
                    this.a();
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean a(final String pow, final aM eventType) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        final List<bI> pows = this.I.get(eventType);
        if (pows != null) {
            final Iterator<bI> it = pows.iterator();
            while (it.hasNext()) {
                final bI p = it.next();
                if (p.e().equalsIgnoreCase(pow)) {
                    it.remove();
                    this.a();
                    return true;
                }
            }
        }
        return false;
    }
    
    public Collection<bI> t() {
        final List<bI> pows = new ArrayList<bI>();
        for (final Collection<bI> ps : this.I.values()) {
            pows.addAll(ps);
        }
        return pows;
    }
    
    public <T extends bI> Collection<T> b(final Class<T> clzz) {
        final List list = new ArrayList(this.t());
        final List<T> result = new ArrayList<T>();
        for (final bI p : list) {
            if (p.getClass().isInstance(clzz)) {
                result.add((T)p);
            }
        }
        return result;
    }
    
    public <T extends bI> Collection<T> a(final Class<T> clzz, final aM eventType) {
        final List list = new ArrayList(this.a(eventType));
        final List<T> result = new ArrayList<T>();
        for (final bI p : list) {
            if (p.getClass().isInstance(clzz)) {
                result.add((T)p);
            }
        }
        return result;
    }
    
    public <T extends bI> Collection<T> a(final String powerName, final Class<T> clzz) {
        final List list = new ArrayList(this.t());
        final List<T> result = new ArrayList<T>();
        for (final bI p : list) {
            if (p.getClass().isInstance(clzz) && p.e().equalsIgnoreCase(powerName)) {
                result.add((T)p);
            }
        }
        return result;
    }
    
    public void k(final String str) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.g(str, true);
    }
    
    public void g(final String str, final boolean update) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.f.add(bg.a(str));
        if (update) {
            this.a();
        }
    }
    
    public void l(final String str) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.g.add(bg.a(str));
    }
    
    public void u() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.L = !this.L;
        this.a();
    }
    
    @Override
    public int c(final Player player) {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        for (final bI power : this.a(aM.TICK)) {
            if (power instanceof ce) {
                final PotionEffectType rType = ((ce)power).j;
                if (aR.b((Metadatable)player, aR.a.POWER_BLOCKHEAL)) {
                    if (rType == PotionEffectType.HEAL || rType == PotionEffectType.HEALTH_BOOST) {
                        continue;
                    }
                    if (rType == PotionEffectType.REGENERATION) {
                        continue;
                    }
                }
                else if (aR.b((Metadatable)player, aR.a.POWER_BLOCKBUFF) && bg.a(bC.BUFF).contains(rType)) {
                    continue;
                }
            }
            power.c(player);
        }
        return 0;
    }
    
    @Override
    public int b(final Player player, final LivingEntity target, final aY damage) {
        int success = 0;
        for (final bI p : this.a(aM.DAMAGE)) {
            success += p.b(player, target, damage);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final LivingEntity damager, final aY damage) {
        int success = 0;
        for (final bI p : this.a(aM.DAMAGED)) {
            success += p.a(player, damager, damage);
        }
        return success;
    }
    
    @Override
    public int d(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.SNEAK)) {
            success += p.d(player);
        }
        return success;
    }
    
    @Override
    public int e(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.SNEAKON)) {
            success += p.e(player);
        }
        return success;
    }
    
    @Override
    public int f(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.SNEAKOFF)) {
            success += p.f(player);
        }
        return success;
    }
    
    @Override
    public int g(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.SPRINT)) {
            success += p.g(player);
        }
        return success;
    }
    
    @Override
    public int h(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.SPRINTON)) {
            success += p.h(player);
        }
        return success;
    }
    
    @Override
    public int i(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.SPRINTOFF)) {
            success += p.i(player);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerInteractEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.INTERACT)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int b(final Player player, final PlayerInteractEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.RIGHTCLICK)) {
            success += p.b(player, event);
        }
        return success;
    }
    
    @Override
    public int c(final Player player, final PlayerInteractEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.LEFTCLICK)) {
            success += p.c(player, event);
        }
        return success;
    }
    
    @Override
    public int d(final Player player, final PlayerInteractEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.RIGHTCLICKAIR)) {
            success += p.d(player, event);
        }
        return success;
    }
    
    @Override
    public int e(final Player player, final PlayerInteractEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.LEFTCLICKAIR)) {
            success += p.e(player, event);
        }
        return success;
    }
    
    @Override
    public int f(final Player player, final PlayerInteractEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.RIGHTCLICKBLOCK)) {
            success += p.f(player, event);
        }
        return success;
    }
    
    @Override
    public int g(final Player player, final PlayerInteractEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.RIGHTCLICKBLOCK)) {
            success += p.g(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerInteractEntityEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.RIGHTCLICKENTITY)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final ItemStack consumeItem) {
        int success = 0;
        for (final bI p : this.a(aM.CONSUME)) {
            success += p.a(player, consumeItem);
        }
        return success;
    }
    
    @Override
    public int b(final Player player, final LivingEntity target) {
        int success = 0;
        for (final bI p : this.a(aM.KILL)) {
            success += p.b(player, target);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final LivingEntity killer) {
        int success = 0;
        for (final bI p : this.a(aM.KILLED)) {
            success += p.a(player, killer);
        }
        return success;
    }
    
    @Override
    public int b(final Player player, final Block block) {
        int success = 0;
        for (final bI p : this.a(aM.BLOCKPLACE)) {
            success += p.b(player, block);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final Block block) {
        int success = 0;
        for (final bI p : this.a(aM.BLOCKBREAK)) {
            success += p.a(player, block);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final Projectile arrow) {
        int success = 0;
        for (final bI p : this.a(aM.ARROWHIT)) {
            success += p.a(player, arrow);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final Entity target, final Projectile arrow) {
        int success = 0;
        for (final bI p : this.a(aM.ARROWENTITY)) {
            success += p.a(player, target, arrow);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerFishEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.FISH)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final aU weatherType) {
        int success = 0;
        for (final bI p : this.a(aM.WEATHER)) {
            success += p.a(player, weatherType);
        }
        return success;
    }
    
    @Override
    public int j(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.STORM)) {
            success += p.j(player);
        }
        return success;
    }
    
    @Override
    public int k(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.THUNDER)) {
            success += p.k(player);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final Location location, final TreeType species) {
        int success = 0;
        for (final bI p : this.a(aM.BONEGROW)) {
            success += p.a(player, location, species);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final int oldLevel, final int newLevel) {
        int success = 0;
        for (final bI p : this.a(aM.LEVELUP)) {
            success += p.a(player, oldLevel, newLevel);
        }
        return success;
    }
    
    @Override
    public int b(final Player player, final Location respawnLocation) {
        int success = 0;
        for (final bI p : this.a(aM.RESPAWN)) {
            success += p.b(player, respawnLocation);
        }
        return success;
    }
    
    @Override
    public int l(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKGROUND)) {
            success += p.l(player);
        }
        return success;
    }
    
    @Override
    public int m(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKFLY)) {
            success += p.m(player);
        }
        return success;
    }
    
    @Override
    public int n(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKLIQUID)) {
            success += p.n(player);
        }
        return success;
    }
    
    @Override
    public int o(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKWATER)) {
            success += p.o(player);
        }
        return success;
    }
    
    @Override
    public int p(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKLAVA)) {
            success += p.p(player);
        }
        return success;
    }
    
    @Override
    public int q(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKSLEEP)) {
            success += p.q(player);
        }
        return success;
    }
    
    @Override
    public int r(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKSNEAK)) {
            success += p.r(player);
        }
        return success;
    }
    
    @Override
    public int s(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKSPRINT)) {
            success += p.s(player);
        }
        return success;
    }
    
    @Override
    public int t(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKVEHICLE)) {
            success += p.t(player);
        }
        return success;
    }
    
    @Override
    public int u(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKWEATHER)) {
            success += p.u(player);
        }
        return success;
    }
    
    @Override
    public int v(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKSUN)) {
            success += p.v(player);
        }
        return success;
    }
    
    @Override
    public int w(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKTHUNDER)) {
            success += p.w(player);
        }
        return success;
    }
    
    @Override
    public int x(final Player player) {
        int success = 0;
        for (final bI p : this.a(aM.TICKSTORM)) {
            success += p.x(player);
        }
        return success;
    }
    
    @Override
    public int c(final Player player, final Block bed) {
        int success = 0;
        for (final bI p : this.a(aM.BED)) {
            success += p.c(player, bed);
        }
        return success;
    }
    
    @Override
    public int d(final Player player, final Block bed) {
        int success = 0;
        for (final bI p : this.a(aM.BEDENTER)) {
            success += p.d(player, bed);
        }
        return success;
    }
    
    @Override
    public int e(final Player player, final Block bed) {
        int success = 0;
        for (final bI p : this.a(aM.BEDLEFT)) {
            success += p.e(player, bed);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerLeashEntityEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.LEASH)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerUnleashEntityEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.UNLEASH)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerEditBookEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.EDITBOOK)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final EntityRegainHealthEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.REGAIN)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final HorseJumpEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.HORSEJUMP)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerBucketFillEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.BUCKETFILL)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerBucketEmptyEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.BUCKETEMPTY)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerItemBreakEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.ITEMBREAK)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final PlayerTeleportEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.TELEPORT)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final EntityCombustEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.COMBUST)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final EntityTameEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.TAME)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int a(final Player player, final EnchantItemEvent event) {
        int success = 0;
        for (final bI p : this.a(aM.ENCHANT)) {
            success += p.a(player, event);
        }
        return success;
    }
    
    @Override
    public int hashCode() {
        return this.s;
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj != null && this.getClass() == obj.getClass() && this.s == ((RPGItem)obj).s;
    }
    
    @a
    public boolean isSimilar(final RPGItem item) {
        return this.equals(item);
    }
    
    @a
    public boolean isSimilar(final ItemStack item) {
        return this.equals(by.a(item));
    }
    
    public static List<String> a(final RPGItem rItem, final String locale, final aS rpgMeta) {
        final List<String> list = new ArrayList<String>();
        if (rItem.n != null && rpgMeta.g(4)) {
            try {
                final int setPartId = ((X<Number>)rpgMeta).j_(4).intValue();
                final bz.a setPart = rItem.n.a(setPartId);
                if (setPart != null) {
                    String setsDisplayLore = aO.a("display.sets.top", locale);
                    if (!setsDisplayLore.isEmpty()) {
                        list.add(String.format(setsDisplayLore, setPart.a.a) + "¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr");
                    }
                    for (final RPGItem need : rItem.n.b) {
                        ChatColor color;
                        if (setPart.c.contains(need)) {
                            color = ChatColor.GREEN;
                        }
                        else {
                            color = ChatColor.GRAY;
                        }
                        list.add(color + bg.b(need.getDisplay()) + "¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr");
                    }
                    setsDisplayLore = aO.a("display.sets.bottom", locale);
                    if (!setsDisplayLore.isEmpty()) {
                        list.add(setsDisplayLore + "¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr");
                    }
                    if (!setPart.e.isEmpty()) {
                        setsDisplayLore = aO.a("display.sets.lore.top", locale);
                        if (!setsDisplayLore.isEmpty()) {
                            list.add(setsDisplayLore + "¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr");
                        }
                    }
                    for (final String setLore : setPart.e) {
                        list.add(setLore + "¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr");
                    }
                    if (!setPart.e.isEmpty()) {
                        setsDisplayLore = aO.a("display.sets.lore.bottom", locale);
                        if (!setsDisplayLore.isEmpty()) {
                            list.add(setsDisplayLore + "¡ìc¡ìc¡ìb¡ìk¡ìm¡ìr");
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
        return list;
    }
    
    static {
        N = new Random();
    }
}
