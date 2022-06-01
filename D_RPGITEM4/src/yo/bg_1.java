/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Fireball
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package yo;

import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.al_0;
import yo.ao_0;
import yo.ax_1;
import yo.bc_0;
import yo.be_0;
import yo.bh_0;

public class bg_1 {
    private static int c;
    private static final bh_0 d;
    public static final Random a;
    private static final HashMap<bc_0, Collection<PotionEffectType>> e;
    public static final FileFilter b;

    public static bh_0 a() {
        return d;
    }

    public static int b() {
        return c;
    }

    public static Collection<? extends Player> c() {
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
        if (bg_1.a().c("1.7.10")) {
            try {
                Method method = Bukkit.class.getDeclaredMethod("getOnlinePlayers", new Class[0]);
                Player[] players = (Player[])method.invoke(null, new Object[0]);
                return Arrays.asList(players);
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return Bukkit.getOnlinePlayers();
    }

    public static PotionEffectType a(String field, String locale) {
        String original;
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
        PotionEffectType pet = PotionEffectType.getByName((String)field);
        if (pet == null && (original = ao_0.a(al_0.POTION, field, locale)) != null) {
            pet = PotionEffectType.getByName((String)original);
        }
        return pet;
    }

    public static String a(String text) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)text);
    }

    public static String b(String text) {
        return ChatColor.stripColor((String)text);
    }

    public static int a(int max) {
        return a.nextInt(max);
    }

    public static int a(int min, int max) {
        int _min = Math.min(min, max);
        int _max = Math.max(min, max);
        return a.nextInt(_max - _min + 1) + _min;
    }

    public static double a(double min, double max) {
        double _min = Math.min(min, max);
        double _max = Math.max(min, max);
        return a.nextDouble() * (_max - _min) + _min;
    }

    public static int a(int num, int range1, int range2) {
        int min = Math.min(range1, range2);
        int max = Math.max(range1, range2);
        if (num > max) {
            return max;
        }
        if (num < min) {
            return min;
        }
        return num;
    }

    public static ItemStack[] a(Player player) {
        ItemStack[] armors = new ItemStack[5];
        int i = 0;
        ItemStack[] arr$ = player.getInventory().getArmorContents();
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            ItemStack item;
            armors[i] = item = arr$[i$];
            ++i;
        }
        armors[4] = player.getItemInHand();
        return armors;
    }

    public static ItemStack[] b(Player player) {
        return player.getInventory().getArmorContents();
    }

    public static <T> T[] a(T[] arrays, int size) {
        ?[] newArrays = bg_1.a(arrays.getClass().getComponentType(), size);
        int maxSize = arrays.length > size ? size : arrays.length;
        System.arraycopy(arrays, 0, newArrays, 0, maxSize);
        return newArrays;
    }

    public static <T> T[] a(Class<T> type, int size) {
        return (Object[])Array.newInstance(type, size);
    }

    public static boolean a(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }

    public static Block a(LivingEntity lnt, Set sets, int distance) {
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
            Method method = lnt.getClass().getMethod("getTargetBlock", Set.class, Integer.TYPE);
            return (Block)method.invoke((Object)lnt, sets, distance);
        }
        catch (Exception e2) {
            try {
                Method method = lnt.getClass().getMethod("getTargetBlock", HashSet.class, Integer.TYPE);
                return (Block)method.invoke((Object)lnt, sets, distance);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public static Collection<PotionEffectType> a(bc_0 type) {
        return e.get((Object)type);
    }

    public static Location a(Location loc, Vector vector) {
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
        double _2PI = 6.283185307179586;
        double x = vector.getX();
        double z = vector.getZ();
        if (x == 0.0 && z == 0.0) {
            loc.setPitch(vector.getY() > 0.0 ? -90.0f : 90.0f);
            return loc;
        }
        double theta = Math.atan2(- x, z);
        loc.setYaw((float)Math.toDegrees((theta + _2PI) % _2PI));
        double x2 = x * x;
        double z2 = z * z;
        double xz = Math.sqrt(x2 + z2);
        loc.setPitch((float)Math.toDegrees(Math.atan((- vector.getY()) / xz)));
        return loc;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static b a(LivingEntity shooter, Object arrow, Location from, Location target, float power) {
        boolean isItemStack;
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
        if (arrow == null) {
            return null;
        }
        Projectile eArrow = null;
        Vector v = null;
        boolean isClass = arrow instanceof Class;
        boolean isEntityClass = isClass && ((Class)arrow).isAssignableFrom(Entity.class);
        boolean bl = isItemStack = !isClass && arrow instanceof ItemStack;
        if (from != null) {
            v = target.clone().toVector().subtract(from.toVector()).normalize();
            from = bg_1.a(from.clone(), v);
            if (isEntityClass) {
                eArrow = from.getWorld().spawn(from, (Class)arrow);
            } else if (isItemStack) {
                eArrow = from.getWorld().dropItem(from, (ItemStack)arrow);
            } else {
                if (!(arrow instanceof Entity)) return null;
                eArrow = (Entity)arrow;
            }
            if (shooter != null && isEntityClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                bg_1.a(eArrow, (Object)shooter);
            }
        } else {
            if (shooter == null) return null;
            if (isClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                eArrow = shooter.launchProjectile((Class)arrow);
            } else {
                if (from == null) return null;
                v = target.clone().toVector().subtract(from.toVector()).normalize();
                from = bg_1.a(from.clone(), v);
                if (isEntityClass) {
                    eArrow = from.getWorld().spawn(from, (Class)arrow);
                } else if (isItemStack) {
                    eArrow = from.getWorld().dropItem(from, (ItemStack)arrow);
                } else {
                    if (!(arrow instanceof Entity)) return null;
                    eArrow = (Entity)arrow;
                }
            }
            if (v == null) {
                v = shooter.getLocation().getDirection();
            }
        }
        v.multiply(1.0f * power);
        eArrow.setVelocity(v);
        b homingArrowMonitor = new b(new a((Entity)shooter, (Entity)eArrow, (Object)target, power));
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((org.bukkit.plugin.Plugin)Plugin.c, (Runnable)homingArrowMonitor, 1L, 1L);
        homingArrowMonitor.a(taskId);
        return homingArrowMonitor;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static b a(LivingEntity shooter, Object arrow, Location from, LivingEntity target, float power) {
        boolean isItemStack;
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
        if (arrow == null) {
            return null;
        }
        Projectile eArrow = null;
        Vector v = null;
        boolean isClass = arrow instanceof Class;
        boolean isEntityClass = isClass && ((Class)arrow).isAssignableFrom(Entity.class);
        boolean bl = isItemStack = !isClass && arrow instanceof ItemStack;
        if (from != null) {
            v = target.getLocation().toVector().subtract(from.toVector()).normalize();
            from = bg_1.a(from.clone(), v);
            if (isEntityClass) {
                eArrow = from.getWorld().spawn(from, (Class)arrow);
            } else if (isItemStack) {
                eArrow = from.getWorld().dropItem(from, (ItemStack)arrow);
            } else {
                if (!(arrow instanceof Entity)) return null;
                eArrow = (Entity)arrow;
            }
            if (shooter != null && isEntityClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                bg_1.a(eArrow, (Object)shooter);
            }
        } else {
            if (shooter == null) return null;
            v = shooter.getLocation().getDirection();
            if (isClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                eArrow = shooter.launchProjectile((Class)arrow);
            } else {
                if (from == null) return null;
                from = bg_1.a(from.clone(), v);
                if (isEntityClass) {
                    eArrow = from.getWorld().spawn(from, (Class)arrow);
                } else if (isItemStack) {
                    eArrow = from.getWorld().dropItem(from, (ItemStack)arrow);
                } else {
                    if (!(arrow instanceof Entity)) return null;
                    eArrow = (Entity)arrow;
                }
            }
        }
        v.multiply(1.0f * power);
        eArrow.setVelocity(v);
        b homingArrowMonitor = new b(new a((Entity)shooter, (Entity)eArrow, (Object)target, power));
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((org.bukkit.plugin.Plugin)Plugin.c, (Runnable)homingArrowMonitor, 1L, 1L);
        homingArrowMonitor.a(taskId);
        return homingArrowMonitor;
    }

    public static Object a(Projectile projectile) {
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
            Method method = projectile.getClass().getMethod("getShooter", new Class[0]);
            method.setAccessible(true);
            return method.invoke((Object)projectile, new Object[0]);
        }
        catch (Exception method) {
            return null;
        }
    }

    public static Entity a(EntityDamageEvent e2) {
        if (e2 instanceof EntityDamageByEntityEvent) {
            Object oo;
            EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent)e2;
            if (ee.getDamager() instanceof Player) {
                return ee.getDamager();
            }
            if (ee.getDamager() instanceof Projectile && (oo = bg_1.a((Projectile)ee.getDamager())) != null && oo instanceof Entity) {
                return (Entity)oo;
            }
        }
        return null;
    }

    public static void a(Projectile projectile, Object object) {
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
            Method method = be_0.a(projectile.getClass(), "setShooter", Void.class).get(0);
            method.setAccessible(true);
            method.invoke((Object)projectile, object);
        }
        catch (Exception method) {
            // empty catch block
        }
    }

    public static void a(Player player, Collection<ItemStack> items) {
        bg_1.a(player, items.toArray((T[])new ItemStack[0]));
    }

    public static /* varargs */ void a(Player player, ItemStack ... items) {
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
        Collection stacks = player.getInventory().addItem(items).values();
        Location location = player.getLocation();
        for (ItemStack drop : stacks) {
            location.getWorld().dropItem(location, drop);
        }
    }

    private static Class a(Class clzz) {
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
        Class<?> clz = clzz;
        int dimensions = 0;
        while (clz.isArray()) {
            ++dimensions;
            clz = clzz.getComponentType();
        }
        return dimensions > 0 ? clz : null;
    }

    public static String a(String concatSymbol, Object[] objs) {
        return bg_1.a(concatSymbol, 0, objs);
    }

    public static String a(String concatSymbol, int startIndex, Object[] objs) {
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
        String string = "";
        for (Object obj : objs) {
            string = string + concatSymbol + obj.toString();
        }
        return string.isEmpty() ? string : string.substring(concatSymbol.length());
    }

    public static <T extends Event> T a(T event) {
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

    public static int a(Inventory inv, RPGItem require, int amount) {
        int find = 0;
        for (int i = 0; i < inv.getSize(); ++i) {
            ItemStack item = inv.getItem(i);
            if (require.isSimilar(item)) {
                int substract = 0;
                substract = item.getAmount() + find > amount ? amount - find : item.getAmount();
                item.setAmount(item.getAmount() - substract);
                if (item.getAmount() < 1) {
                    item = null;
                }
                inv.setItem(i, item);
                find += substract;
            }
            if (find == amount) break;
        }
        return find;
    }

    public static ArrayList<String> a(String filePath, FileFilter filter) {
        ArrayList<String> fileList = new ArrayList<String>();
        File root = new File(filePath);
        if (root.exists()) {
            if (root.isDirectory()) {
                for (File file : root.listFiles(filter)) {
                    fileList.addAll(bg_1.c(file.getAbsolutePath()));
                }
            } else {
                fileList.add(filePath);
            }
        }
        return fileList;
    }

    public static ArrayList<String> c(String filePath) {
        ArrayList<String> fileList = new ArrayList<String>();
        File root = new File(filePath);
        if (root.exists()) {
            if (root.isDirectory()) {
                for (File file : root.listFiles()) {
                    fileList.addAll(bg_1.c(file.getAbsolutePath()));
                }
            } else {
                fileList.add(filePath);
            }
        }
        return fileList;
    }

    static {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(packageName.substring(packageName.lastIndexOf(46) + 1));
        try {
            c = Integer.parseInt(m.replaceAll(""));
        }
        catch (Exception e2) {
            c = 0;
        }
        String verStr = Bukkit.getVersion().split("MC: ")[1];
        verStr = verStr.substring(0, verStr.length() - 1);
        d = new bh_0(verStr);
        a = new Random();
        e = new HashMap();
        e.put(bc_0.DEBUFF, new ArrayList());
        e.put(bc_0.BUFF, new ArrayList());
        e.put(bc_0.ALL, new ArrayList());
        for (PotionEffectType pt : PotionEffectType.values()) {
            if (pt == null) continue;
            bc_0 p2 = bc_0.DEBUFF;
            if (pt == PotionEffectType.SPEED) {
                p2 = bc_0.BUFF;
            } else if (pt != PotionEffectType.SLOW) {
                if (pt == PotionEffectType.FAST_DIGGING) {
                    p2 = bc_0.BUFF;
                } else if (pt != PotionEffectType.SLOW_DIGGING) {
                    if (pt == PotionEffectType.INCREASE_DAMAGE) {
                        p2 = bc_0.BUFF;
                    } else if (pt == PotionEffectType.HEAL) {
                        p2 = bc_0.BUFF;
                    } else if (pt != PotionEffectType.HARM) {
                        if (pt == PotionEffectType.JUMP) {
                            p2 = bc_0.BUFF;
                        } else if (pt != PotionEffectType.CONFUSION) {
                            if (pt == PotionEffectType.REGENERATION) {
                                p2 = bc_0.BUFF;
                            } else if (pt == PotionEffectType.DAMAGE_RESISTANCE) {
                                p2 = bc_0.BUFF;
                            } else if (pt == PotionEffectType.FIRE_RESISTANCE) {
                                p2 = bc_0.BUFF;
                            } else if (pt == PotionEffectType.WATER_BREATHING) {
                                p2 = bc_0.BUFF;
                            } else if (pt == PotionEffectType.INVISIBILITY) {
                                p2 = bc_0.BUFF;
                            } else if (pt != PotionEffectType.BLINDNESS) {
                                if (pt == PotionEffectType.NIGHT_VISION) {
                                    p2 = bc_0.BUFF;
                                } else if (pt != PotionEffectType.HUNGER && pt != PotionEffectType.WEAKNESS && pt != PotionEffectType.POISON && pt != PotionEffectType.WITHER) {
                                    if (pt == PotionEffectType.HEALTH_BOOST) {
                                        p2 = bc_0.BUFF;
                                    } else if (pt == PotionEffectType.ABSORPTION) {
                                        p2 = bc_0.BUFF;
                                    } else if (pt == PotionEffectType.SATURATION) {
                                        p2 = bc_0.BUFF;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            e.get((Object)p2).add(pt);
            e.get((Object)bc_0.ALL).add(pt);
        }
        b = new c();
    }

    static class c
    implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".yml");
        }
    }

    public static class a {
        Entity a;
        Entity b;
        Object c;
        float d;

        protected a(Entity shooter, Entity arrow, Object target, float power) {
            this.a = shooter;
            this.b = arrow;
            this.c = target;
            this.d = power;
        }

        public Entity a() {
            return this.a;
        }

        public Entity b() {
            return this.b;
        }

        public Object c() {
            return this.c;
        }

        public float d() {
            return this.d;
        }
    }

    public static class b
    implements Runnable {
        private a b;
        private int c;
        private final HashMap<ax_1, Runnable> d;
        int a = 0;

        public b(a arrow) {
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
            if (arrow.b instanceof Item) {
                ((Item)arrow.b).setPickupDelay(60);
            }
            this.b = arrow;
            this.d = new HashMap();
        }

        public a a() {
            return this.b;
        }

        protected void a(int id) {
            this.c = id;
        }

        public void a(ax_1 result, Runnable runnable) {
            this.d.put(result, runnable);
        }

        public Runnable a(ax_1 result) {
            return this.d.get((Object)result);
        }

        public void b(ax_1 result) {
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
            Runnable runn = this.a(result);
            if (runn != null) {
                runn.run();
            }
        }

        @Override
        public void run() {
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
            if (this.b.b.isDead() || this.b.b.isOnGround()) {
                this.b();
                this.b(ax_1.HIT);
            } else if (this.b.c instanceof LivingEntity && ((LivingEntity)this.b.c).isDead()) {
                this.b();
                this.b(ax_1.FAIL);
            } else if (this.b.c instanceof LivingEntity && !((LivingEntity)this.b.c).isValid()) {
                this.b();
                this.b(ax_1.FAIL);
            } else {
                Location t;
                this.b(ax_1.LOOP);
                if (this.b.c instanceof LivingEntity) {
                    t = ((LivingEntity)this.b.c).getLocation();
                } else if (this.b.c instanceof Location) {
                    t = ((Location)this.b.c).clone();
                } else {
                    this.b();
                    this.b(ax_1.FAIL);
                    return;
                }
                Vector v = t.add(0.0, 0.75, 0.0).toVector().subtract(this.b.b.getLocation().toVector()).normalize();
                v.multiply(1.0f * this.b.d);
                v.setY(v.getY() + 0.15);
                this.b.b.setVelocity(v);
                if (this.b.b instanceof Fireball) {
                    // empty if block
                }
                ++this.a;
            }
        }

        public void b() {
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
            Bukkit.getScheduler().cancelTask(this.c);
            this.b(ax_1.OVER);
        }
    }

}

