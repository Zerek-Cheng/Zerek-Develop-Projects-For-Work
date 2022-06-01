// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.util.ArrayList;
import think.rpgitems.item.RPGItem;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.Event;
import java.util.Iterator;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import think.rpgitems.Plugin;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import java.util.HashSet;
import org.bukkit.block.Block;
import java.util.Set;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import java.lang.reflect.Array;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import java.lang.reflect.Method;
import java.io.Closeable;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.io.FileFilter;
import org.bukkit.potion.PotionEffectType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class bg
{
    private static int c;
    private static final bh d;
    public static final Random a;
    private static final HashMap<bC, Collection<PotionEffectType>> e;
    public static final FileFilter b;
    
    public static bh a() {
        return bg.d;
    }
    
    public static int b() {
        return bg.c;
    }
    
    public static Collection<? extends Player> c() {
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
        if (a().c("1.7.10")) {
            try {
                final Method method = Bukkit.class.getDeclaredMethod("getOnlinePlayers", (Class<?>[])new Class[0]);
                final Player[] players = (Player[])method.invoke(null, new Object[0]);
                return Arrays.asList(players);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (Collection<? extends Player>)Bukkit.getOnlinePlayers();
    }
    
    public static PotionEffectType a(final String field, final String locale) {
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
        PotionEffectType pet = PotionEffectType.getByName(field);
        if (pet == null) {
            final String original = aO.a(aL.POTION, field, locale);
            if (original != null) {
                pet = PotionEffectType.getByName(original);
            }
        }
        return pet;
    }
    
    public static String a(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    
    public static String b(final String text) {
        return ChatColor.stripColor(text);
    }
    
    public static int a(final int max) {
        return bg.a.nextInt(max);
    }
    
    public static int a(final int min, final int max) {
        final int _min = Math.min(min, max);
        final int _max = Math.max(min, max);
        return bg.a.nextInt(_max - _min + 1) + _min;
    }
    
    public static double a(final double min, final double max) {
        final double _min = Math.min(min, max);
        final double _max = Math.max(min, max);
        return bg.a.nextDouble() * (_max - _min) + _min;
    }
    
    public static int a(final int num, final int range1, final int range2) {
        final int min = Math.min(range1, range2);
        final int max = Math.max(range1, range2);
        if (num > max) {
            return max;
        }
        if (num < min) {
            return min;
        }
        return num;
    }
    
    public static ItemStack[] a(final Player player) {
        final ItemStack[] armors = new ItemStack[5];
        int i = 0;
        for (final ItemStack item : player.getInventory().getArmorContents()) {
            armors[i] = item;
            ++i;
        }
        armors[4] = player.getItemInHand();
        return armors;
    }
    
    public static ItemStack[] b(final Player player) {
        return player.getInventory().getArmorContents();
    }
    
    public static <T> T[] a(final T[] arrays, final int size) {
        final T[] newArrays = a(arrays.getClass().getComponentType(), size);
        final int maxSize = (arrays.length > size) ? size : arrays.length;
        System.arraycopy(arrays, 0, newArrays, 0, maxSize);
        return newArrays;
    }
    
    public static <T> T[] a(final Class<T> type, final int size) {
        return (T[])Array.newInstance(type, size);
    }
    
    public static boolean a(final ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
    
    public static Block a(final LivingEntity lnt, final Set sets, final int distance) {
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
        catch (Exception ex2) {}
        try {
            final Method method = lnt.getClass().getMethod("getTargetBlock", Set.class, Integer.TYPE);
            return (Block)method.invoke(lnt, sets, distance);
        }
        catch (Exception e) {
            try {
                final Method method2 = lnt.getClass().getMethod("getTargetBlock", HashSet.class, Integer.TYPE);
                return (Block)method2.invoke(lnt, sets, distance);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
    public static Collection<PotionEffectType> a(final bC type) {
        return bg.e.get(type);
    }
    
    public static Location a(final Location loc, final Vector vector) {
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
        final double _2PI = 6.283185307179586;
        final double x3 = vector.getX();
        final double z = vector.getZ();
        if (x3 == 0.0 && z == 0.0) {
            loc.setPitch((vector.getY() > 0.0) ? -90.0f : 90.0f);
            return loc;
        }
        final double theta = Math.atan2(-x3, z);
        loc.setYaw((float)Math.toDegrees((theta + _2PI) % _2PI));
        final double x4 = x3 * x3;
        final double z2 = z * z;
        final double xz = Math.sqrt(x4 + z2);
        loc.setPitch((float)Math.toDegrees(Math.atan(-vector.getY() / xz)));
        return loc;
    }
    
    public static b a(final LivingEntity shooter, final Object arrow, Location from, final Location target, final float power) {
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
        if (arrow == null) {
            return null;
        }
        Entity eArrow = null;
        Vector v = null;
        final boolean isClass = arrow instanceof Class;
        final boolean isEntityClass = isClass && ((Class)arrow).isAssignableFrom(Entity.class);
        final boolean isItemStack = !isClass && arrow instanceof ItemStack;
        if (from != null) {
            v = target.clone().toVector().subtract(from.toVector()).normalize();
            from = a(from.clone(), v);
            if (isEntityClass) {
                eArrow = from.getWorld().spawn(from, (Class)arrow);
            }
            else if (isItemStack) {
                eArrow = (Entity)from.getWorld().dropItem(from, (ItemStack)arrow);
            }
            else {
                if (!(arrow instanceof Entity)) {
                    return null;
                }
                eArrow = (Entity)arrow;
            }
            if (shooter != null && isEntityClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                a((Projectile)eArrow, shooter);
            }
        }
        else {
            if (shooter == null) {
                return null;
            }
            if (isClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                eArrow = (Entity)shooter.launchProjectile((Class)arrow);
            }
            else {
                if (from == null) {
                    return null;
                }
                v = target.clone().toVector().subtract(from.toVector()).normalize();
                from = a(from.clone(), v);
                if (isEntityClass) {
                    eArrow = from.getWorld().spawn(from, (Class)arrow);
                }
                else if (isItemStack) {
                    eArrow = (Entity)from.getWorld().dropItem(from, (ItemStack)arrow);
                }
                else {
                    if (!(arrow instanceof Entity)) {
                        return null;
                    }
                    eArrow = (Entity)arrow;
                }
            }
            if (v == null) {
                v = shooter.getLocation().getDirection();
            }
        }
        v.multiply(1.0f * power);
        eArrow.setVelocity(v);
        final b homingArrowMonitor = new b(new a((Entity)shooter, eArrow, target, power));
        final int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((org.bukkit.plugin.Plugin)Plugin.c, (Runnable)homingArrowMonitor, 1L, 1L);
        homingArrowMonitor.a(taskId);
        return homingArrowMonitor;
    }
    
    public static b a(final LivingEntity shooter, final Object arrow, Location from, final LivingEntity target, final float power) {
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
        if (arrow == null) {
            return null;
        }
        Entity eArrow = null;
        Vector v = null;
        final boolean isClass = arrow instanceof Class;
        final boolean isEntityClass = isClass && ((Class)arrow).isAssignableFrom(Entity.class);
        final boolean isItemStack = !isClass && arrow instanceof ItemStack;
        if (from != null) {
            v = target.getLocation().toVector().subtract(from.toVector()).normalize();
            from = a(from.clone(), v);
            if (isEntityClass) {
                eArrow = from.getWorld().spawn(from, (Class)arrow);
            }
            else if (isItemStack) {
                eArrow = (Entity)from.getWorld().dropItem(from, (ItemStack)arrow);
            }
            else {
                if (!(arrow instanceof Entity)) {
                    return null;
                }
                eArrow = (Entity)arrow;
            }
            if (shooter != null && isEntityClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                a((Projectile)eArrow, shooter);
            }
        }
        else {
            if (shooter == null) {
                return null;
            }
            v = shooter.getLocation().getDirection();
            if (isClass && ((Class)arrow).isAssignableFrom(Projectile.class)) {
                eArrow = (Entity)shooter.launchProjectile((Class)arrow);
            }
            else {
                if (from == null) {
                    return null;
                }
                from = a(from.clone(), v);
                if (isEntityClass) {
                    eArrow = from.getWorld().spawn(from, (Class)arrow);
                }
                else if (isItemStack) {
                    eArrow = (Entity)from.getWorld().dropItem(from, (ItemStack)arrow);
                }
                else {
                    if (!(arrow instanceof Entity)) {
                        return null;
                    }
                    eArrow = (Entity)arrow;
                }
            }
        }
        v.multiply(1.0f * power);
        eArrow.setVelocity(v);
        final b homingArrowMonitor = new b(new a((Entity)shooter, eArrow, target, power));
        final int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((org.bukkit.plugin.Plugin)Plugin.c, (Runnable)homingArrowMonitor, 1L, 1L);
        homingArrowMonitor.a(taskId);
        return homingArrowMonitor;
    }
    
    public static Object a(final Projectile projectile) {
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
            final Method method = projectile.getClass().getMethod("getShooter", (Class<?>[])new Class[0]);
            method.setAccessible(true);
            return method.invoke(projectile, new Object[0]);
        }
        catch (Exception ex2) {
            return null;
        }
    }
    
    public static Entity a(final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent)e;
            if (ee.getDamager() instanceof Player) {
                return ee.getDamager();
            }
            if (ee.getDamager() instanceof Projectile) {
                final Object oo = a((Projectile)ee.getDamager());
                if (oo != null && oo instanceof Entity) {
                    return (Entity)oo;
                }
            }
        }
        return null;
    }
    
    public static void a(final Projectile projectile, final Object object) {
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
            final Method method = be.a(projectile.getClass(), "setShooter", Void.class).get(0);
            method.setAccessible(true);
            method.invoke(projectile, object);
        }
        catch (Exception ex2) {}
    }
    
    public static void a(final Player player, final Collection<ItemStack> items) {
        a(player, (ItemStack[])items.toArray(new ItemStack[0]));
    }
    
    public static void a(final Player player, final ItemStack... items) {
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
        final Collection<ItemStack> stacks = player.getInventory().addItem(items).values();
        final Location location = player.getLocation();
        for (final ItemStack drop : stacks) {
            location.getWorld().dropItem(location, drop);
        }
    }
    
    private static Class a(final Class clzz) {
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
        Class clz = clzz;
        int dimensions = 0;
        while (clz.isArray()) {
            ++dimensions;
            clz = clzz.getComponentType();
        }
        return (dimensions > 0) ? clz : null;
    }
    
    public static String a(final String concatSymbol, final Object[] objs) {
        return a(concatSymbol, 0, objs);
    }
    
    public static String a(final String concatSymbol, final int startIndex, final Object[] objs) {
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
        String string = "";
        for (final Object obj : objs) {
            string = string + concatSymbol + obj.toString();
        }
        return string.isEmpty() ? string : string.substring(concatSymbol.length());
    }
    
    public static <T extends Event> T a(final T event) {
        Bukkit.getPluginManager().callEvent((Event)event);
        return event;
    }
    
    public static int a(final Inventory inv, final RPGItem require, final int amount) {
        int find = 0;
        for (int i = 0; i < inv.getSize(); ++i) {
            ItemStack item = inv.getItem(i);
            if (require.isSimilar(item)) {
                int substract = 0;
                if (item.getAmount() + find > amount) {
                    substract = amount - find;
                }
                else {
                    substract = item.getAmount();
                }
                item.setAmount(item.getAmount() - substract);
                if (item.getAmount() < 1) {
                    item = null;
                }
                inv.setItem(i, item);
                find += substract;
            }
            if (find == amount) {
                break;
            }
        }
        return find;
    }
    
    public static ArrayList<String> a(final String filePath, final FileFilter filter) {
        final ArrayList<String> fileList = new ArrayList<String>();
        final File root = new File(filePath);
        if (root.exists()) {
            if (root.isDirectory()) {
                for (final File file : root.listFiles(filter)) {
                    fileList.addAll(c(file.getAbsolutePath()));
                }
            }
            else {
                fileList.add(filePath);
            }
        }
        return fileList;
    }
    
    public static ArrayList<String> c(final String filePath) {
        final ArrayList<String> fileList = new ArrayList<String>();
        final File root = new File(filePath);
        if (root.exists()) {
            if (root.isDirectory()) {
                for (final File file : root.listFiles()) {
                    fileList.addAll(c(file.getAbsolutePath()));
                }
            }
            else {
                fileList.add(filePath);
            }
        }
        return fileList;
    }
    
    static {
        final String packageName = Bukkit.getServer().getClass().getPackage().getName();
        final Pattern p = Pattern.compile("[^0-9]");
        final Matcher m = p.matcher(packageName.substring(packageName.lastIndexOf(46) + 1));
        try {
            bg.c = Integer.parseInt(m.replaceAll(""));
        }
        catch (Exception e2) {
            bg.c = 0;
        }
        String verStr = Bukkit.getVersion().split("MC: ")[1];
        verStr = verStr.substring(0, verStr.length() - 1);
        d = new bh(verStr);
        a = new Random();
        (e = new HashMap<bC, Collection<PotionEffectType>>()).put(bC.DEBUFF, new ArrayList<PotionEffectType>());
        bg.e.put(bC.BUFF, new ArrayList<PotionEffectType>());
        bg.e.put(bC.ALL, new ArrayList<PotionEffectType>());
        for (final PotionEffectType pt : PotionEffectType.values()) {
            if (pt != null) {
                bC p2 = bC.DEBUFF;
                if (pt == PotionEffectType.SPEED) {
                    p2 = bC.BUFF;
                }
                else if (pt != PotionEffectType.SLOW) {
                    if (pt == PotionEffectType.FAST_DIGGING) {
                        p2 = bC.BUFF;
                    }
                    else if (pt != PotionEffectType.SLOW_DIGGING) {
                        if (pt == PotionEffectType.INCREASE_DAMAGE) {
                            p2 = bC.BUFF;
                        }
                        else if (pt == PotionEffectType.HEAL) {
                            p2 = bC.BUFF;
                        }
                        else if (pt != PotionEffectType.HARM) {
                            if (pt == PotionEffectType.JUMP) {
                                p2 = bC.BUFF;
                            }
                            else if (pt != PotionEffectType.CONFUSION) {
                                if (pt == PotionEffectType.REGENERATION) {
                                    p2 = bC.BUFF;
                                }
                                else if (pt == PotionEffectType.DAMAGE_RESISTANCE) {
                                    p2 = bC.BUFF;
                                }
                                else if (pt == PotionEffectType.FIRE_RESISTANCE) {
                                    p2 = bC.BUFF;
                                }
                                else if (pt == PotionEffectType.WATER_BREATHING) {
                                    p2 = bC.BUFF;
                                }
                                else if (pt == PotionEffectType.INVISIBILITY) {
                                    p2 = bC.BUFF;
                                }
                                else if (pt != PotionEffectType.BLINDNESS) {
                                    if (pt == PotionEffectType.NIGHT_VISION) {
                                        p2 = bC.BUFF;
                                    }
                                    else if (pt != PotionEffectType.HUNGER) {
                                        if (pt != PotionEffectType.WEAKNESS) {
                                            if (pt != PotionEffectType.POISON) {
                                                if (pt != PotionEffectType.WITHER) {
                                                    if (pt == PotionEffectType.HEALTH_BOOST) {
                                                        p2 = bC.BUFF;
                                                    }
                                                    else if (pt == PotionEffectType.ABSORPTION) {
                                                        p2 = bC.BUFF;
                                                    }
                                                    else if (pt == PotionEffectType.SATURATION) {
                                                        p2 = bC.BUFF;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                bg.e.get(p2).add(pt);
                bg.e.get(bC.ALL).add(pt);
            }
        }
        b = new c();
    }
    
    public static class b implements Runnable
    {
        private a b;
        private int c;
        private final HashMap<aX, Runnable> d;
        int a;
        
        public b(final a arrow) {
            this.a = 0;
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
            if (arrow.b instanceof Item) {
                ((Item)arrow.b).setPickupDelay(60);
            }
            this.b = arrow;
            this.d = new HashMap<aX, Runnable>();
        }
        
        public a a() {
            return this.b;
        }
        
        protected void a(final int id) {
            this.c = id;
        }
        
        public void a(final aX result, final Runnable runnable) {
            this.d.put(result, runnable);
        }
        
        public Runnable a(final aX result) {
            return this.d.get(result);
        }
        
        public void b(final aX result) {
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
            final Runnable runn = this.a(result);
            if (runn != null) {
                runn.run();
            }
        }
        
        @Override
        public void run() {
            try {
                final Closeable asdhqjefhusfer = null;
                final Throwable t2 = null;
                if (asdhqjefhusfer != null) {
                    if (t2 != null) {
                        try {
                            asdhqjefhusfer.close();
                        }
                        catch (Throwable x2) {
                            t2.addSuppressed(x2);
                        }
                    }
                    else {
                        asdhqjefhusfer.close();
                    }
                }
            }
            catch (Exception ex) {}
            if (this.b.b.isDead() || this.b.b.isOnGround()) {
                this.b();
                this.b(aX.HIT);
            }
            else if (this.b.c instanceof LivingEntity && ((LivingEntity)this.b.c).isDead()) {
                this.b();
                this.b(aX.FAIL);
            }
            else if (this.b.c instanceof LivingEntity && !((LivingEntity)this.b.c).isValid()) {
                this.b();
                this.b(aX.FAIL);
            }
            else {
                this.b(aX.LOOP);
                Location t;
                if (this.b.c instanceof LivingEntity) {
                    t = ((LivingEntity)this.b.c).getLocation();
                }
                else {
                    if (!(this.b.c instanceof Location)) {
                        this.b();
                        this.b(aX.FAIL);
                        return;
                    }
                    t = ((Location)this.b.c).clone();
                }
                final Vector v = t.add(0.0, 0.75, 0.0).toVector().subtract(this.b.b.getLocation().toVector()).normalize();
                v.multiply(1.0f * this.b.d);
                v.setY(v.getY() + 0.15);
                this.b.b.setVelocity(v);
                if (this.b.b instanceof Fireball) {}
                ++this.a;
            }
        }
        
        public void b() {
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
            Bukkit.getScheduler().cancelTask(this.c);
            this.b(aX.OVER);
        }
    }
    
    public static class a
    {
        Entity a;
        Entity b;
        Object c;
        float d;
        
        protected a(final Entity shooter, final Entity arrow, final Object target, final float power) {
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
    
    static class c implements FileFilter
    {
        @Override
        public boolean accept(final File pathname) {
            return pathname.getName().endsWith(".yml");
        }
    }
}
