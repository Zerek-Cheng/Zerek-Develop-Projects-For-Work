/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  com.mojang.authlib.properties.PropertyMap
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.ClickEvent
 *  net.md_5.bungee.api.chat.ClickEvent$Action
 *  net.md_5.bungee.api.chat.ComponentBuilder
 *  net.md_5.bungee.api.chat.ComponentBuilder$FormatRetention
 *  net.md_5.bungee.api.chat.HoverEvent
 *  net.md_5.bungee.api.chat.HoverEvent$Action
 *  org.bukkit.Bukkit
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Builder
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Particle
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Firework
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Player$Spigot
 *  org.bukkit.entity.Projectile
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.FireworkMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 *  org.bukkit.projectiles.ProjectileSource
 */
package su.nightexpress.divineitems.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.projectiles.ProjectileSource;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.nms.VersionUtils;

public class Utils {
    private static DivineItems plugin = DivineItems.getInstance();
    private static Random r = new Random();

    public static ItemStack buildItem(String[] arrstring, String string) {
        Material material = Material.getMaterial((String) arrstring[0]);
        if (material == null) {
            return null;
        }
        int n = 0;
        if (arrstring.length >= 2) {
            n = Integer.parseInt(arrstring[1]);
        }
        ItemStack itemStack = new ItemStack(material, 1, (short) n);
        if (arrstring.length == 3) {
            UUID uUID = plugin.getCM().getItemHash(string);
            itemStack = Utils.getHashed(itemStack, arrstring[2], uUID);
        }
        return itemStack.clone();
    }

    public static double round3(double d) {
        double d2 = d * 100.0;
        int n = (int) (d * 100.0);
        if (d2 - (double) n >= 0.5) {
            ++n;
        }
        d2 = n;
        return d2 / 100.0;
    }

    public static int randInt(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        n = Math.min(n3, n4);
        n2 = Math.max(n3, n4);
        return r.nextInt(n2 - n + 1) + n;
    }

    public static String getEntityName(Entity entity) {
        Projectile projectile;
        String string = entity.getType().name();
        if (entity instanceof Projectile && (projectile = (Projectile) entity).getShooter() != null && projectile.getShooter() instanceof LivingEntity) {
            entity = (LivingEntity) projectile.getShooter();
        }
        string = entity instanceof Player ? ((Player) entity).getName() : (entity instanceof LivingEntity ? (((LivingEntity) entity).getCustomName() != null ? ((LivingEntity) entity).getCustomName() : Lang.getCustom("EntityNames." + entity.getType().name())) : "Unknown Object");
        return string;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        LinkedList<Map.Entry<K, V>> linkedList = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(linkedList, new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Map.Entry<K, V> entry, Map.Entry<K, V> entry2) {
                return ((Comparable) entry.getValue()).compareTo(entry2.getValue());
            }
        });
        LinkedHashMap<K, Comparable> linkedHashMap = new LinkedHashMap<K, Comparable>();
        for (Map.Entry<K, V> entry : linkedList) {
            linkedHashMap.put(entry.getKey(), (Comparable) entry.getValue());
        }
        return (Map<K, V>) linkedHashMap;
    }

    public static <T> List<List<T>> split(List<T> list, int n) {
        ArrayList<List<T>> arrayList = new ArrayList<List<T>>();
        int n2 = 0;
        while (n2 < list.size()) {
            arrayList.add(list.subList(n2, Math.min(n2 + n, list.size())));
            n2 += n;
        }
        return arrayList;
    }

    public static String IntegerToRomanNumeral(int n) {
        if (n < 1 || n > 3999) {
            return "Invalid Roman Number Value";
        }
        String string = "";
        while (n >= 1000) {
            string = String.valueOf(string) + "M";
            n -= 1000;
        }
        while (n >= 900) {
            string = String.valueOf(string) + "CM";
            n -= 900;
        }
        while (n >= 500) {
            string = String.valueOf(string) + "D";
            n -= 500;
        }
        while (n >= 400) {
            string = String.valueOf(string) + "CD";
            n -= 400;
        }
        while (n >= 100) {
            string = String.valueOf(string) + "C";
            n -= 100;
        }
        while (n >= 90) {
            string = String.valueOf(string) + "XC";
            n -= 90;
        }
        while (n >= 50) {
            string = String.valueOf(string) + "L";
            n -= 50;
        }
        while (n >= 40) {
            string = String.valueOf(string) + "XL";
            n -= 40;
        }
        while (n >= 10) {
            string = String.valueOf(string) + "X";
            n -= 10;
        }
        while (n >= 9) {
            string = String.valueOf(string) + "IX";
            n -= 9;
        }
        while (n >= 5) {
            string = String.valueOf(string) + "V";
            n -= 5;
        }
        while (n >= 4) {
            string = String.valueOf(string) + "IV";
            n -= 4;
        }
        while (n >= 1) {
            string = String.valueOf(string) + "I";
            --n;
        }
        return string;
    }

    public static int romanToDecimal(String string) {
        int n = 0;
        int n2 = 0;
        String string2 = string.toUpperCase();
        int n3 = string2.length() - 1;
        while (n3 >= 0) {
            char c = string2.charAt(n3);
            switch (c) {
                case 'M': {
                    n = Utils.processDecimal(1000, n2, n);
                    n2 = 1000;
                    break;
                }
                case 'D': {
                    n = Utils.processDecimal(500, n2, n);
                    n2 = 500;
                    break;
                }
                case 'C': {
                    n = Utils.processDecimal(100, n2, n);
                    n2 = 100;
                    break;
                }
                case 'L': {
                    n = Utils.processDecimal(50, n2, n);
                    n2 = 50;
                    break;
                }
                case 'X': {
                    n = Utils.processDecimal(10, n2, n);
                    n2 = 10;
                    break;
                }
                case 'V': {
                    n = Utils.processDecimal(5, n2, n);
                    n2 = 5;
                    break;
                }
                case 'I': {
                    n = Utils.processDecimal(1, n2, n);
                    n2 = 1;
                }
            }
            --n3;
        }
        return n;
    }

    public static String getEnums(Class<?> class_, String string, String string2) {
        String string3 = "";
        if (class_.isEnum()) {
            Object[] arrobj = class_.getEnumConstants();
            int n = arrobj.length;
            int n2 = 0;
            while (n2 < n) {
                Object obj = arrobj[n2];
                string3 = String.valueOf(string3) + string + obj.toString() + string2 + ", ";
                ++n2;
            }
            if (string3.length() > 4) {
                string3 = string3.substring(0, string3.length() - 4);
            }
        }
        return string3;
    }

    public static List<String> getEnumsList(Class<?> class_) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (class_.isEnum()) {
            Object[] arrobj = class_.getEnumConstants();
            int n = arrobj.length;
            int n2 = 0;
            while (n2 < n) {
                Object obj = arrobj[n2];
                arrayList.add(obj.toString());
                ++n2;
            }
        }
        return arrayList;
    }

    public static List<String> getWorldNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (World world : Bukkit.getWorlds()) {
            arrayList.add(world.getName());
        }
        return arrayList;
    }

    public static int processDecimal(int n, int n2, int n3) {
        if (n2 > n) {
            return n3 - n;
        }
        return n3 + n;
    }

    public static double getRandDouble(double d, double d2) {
        return d + (d2 - d) * r.nextDouble();
    }

    public static double getRandDoubleNega(double d, double d2) {
        double d3 = d2 - d;
        double d4 = r.nextDouble() * d3;
        double d5 = d4 + d;
        return d5;
    }

    public static ArrayList<Player> getNearbyEntities(double d, Location location) {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!location.getWorld().equals((Object) player.getLocation().getWorld()) || location.distance(player.getLocation()) >= d)
                continue;
            arrayList.add(player);
        }
        return arrayList;
    }

    public static boolean check(Location location) {
        return !Utils.getNearbyEntities(50.0, location).isEmpty();
    }

    public static Location getPointOnCircle(Location location, boolean bl, double d, double d2, double d3) {
        return (bl ? location.clone() : location).add(Math.cos(d) * d2, d3, Math.sin(d) * d2);
    }

    public static Location getPointOnCircle(Location location, double d, double d2, double d3) {
        return Utils.getPointOnCircle(location, true, d, d2, d3);
    }

    public static Location getCenter(Location location) {
        return new Location(location.getWorld(), Utils.getRelativeCoord(location.getBlockX()), Utils.getRelativeCoord(location.getBlockY()), Utils.getRelativeCoord(location.getBlockZ()));
    }

    private static double getRelativeCoord(int n) {
        double d = n;
        d = d < 0.0 ? d - 0.5 : d + 0.5;
        return d;
    }

    public static double getClose(double d, List<Double> list) {
        if (list.isEmpty()) {
            return -1.0;
        }
        double d2 = Math.abs(list.get(0) - d);
        int n = 0;
        int n2 = 1;
        while (n2 < list.size()) {
            double d3 = Math.abs(list.get(n2) - d);
            if (d3 < d2) {
                n = n2;
                d2 = d3;
            }
            ++n2;
        }
        return list.get(n);
    }

    public static BaseComponent[] myHoverText(String string) {
        ComponentBuilder componentBuilder = new ComponentBuilder("");
        componentBuilder.append(string);
        return componentBuilder.create();
    }

    public static Firework spawnRandomFirework(Location location) {
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        int n = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (n == 1) {
            type = FireworkEffect.Type.BALL;
        }
        if (n == 2) {
            type = FireworkEffect.Type.BALL_LARGE;
        }
        if (n == 3) {
            type = FireworkEffect.Type.BURST;
        }
        if (n == 4) {
            type = FireworkEffect.Type.CREEPER;
        }
        if (n == 5) {
            type = FireworkEffect.Type.STAR;
        }
        int n2 = r.nextInt(250) + 1;
        int n3 = r.nextInt(250) + 1;
        Color color = Color.fromBGR((int) n2, (int) n3, (int) n2);
        Color color2 = Color.fromBGR((int) n3, (int) n2, (int) n3);
        FireworkEffect fireworkEffect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(color).withFade(color2).with(type).trail(r.nextBoolean()).build();
        fireworkMeta.addEffect(fireworkEffect);
        int n4 = r.nextInt(2) + 1;
        fireworkMeta.setPower(n4);
        firework.setFireworkMeta(fireworkMeta);
        return firework;
    }

    public static ItemStack getHashed(ItemStack itemStack, String string, UUID uUID) {
        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            GameProfile gameProfile = new GameProfile(uUID, null);
            gameProfile.getProperties().put("textures", new Property("textures", new String(string)));
            Field field = null;
            try {
                field = skullMeta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set((Object) skullMeta, (Object) gameProfile);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException exception) {
                exception.printStackTrace();
            }
            itemStack.setItemMeta((ItemMeta) skullMeta);
        }
        return itemStack;
    }

    public static int transSec(long l) {
        return (int) ((l - System.currentTimeMillis()) / 1000L);
    }

    public static void interactiveList(CommandSender commandSender, int n, List<String> list, String string, String string2) {
        List<String> list2 = new ArrayList<String>(list);
        int n2 = Utils.split(list2, 10).size();
        if (n > n2) {
            n = n2;
        }
        list2 = n2 < 1 ? new ArrayList() : Utils.split(list2, 10).get(n - 1);
        String string3 = string.toLowerCase().replace(" ", "").replace("_", " ");
        commandSender.sendMessage("\u00a76\u00a7m--------\u00a7e " + string + " List \u00a76\u00a7m---------");
        int n3 = 10 * (n - 1) + 1;
        for (String string4 : list2) {
            BaseComponent[] arrbaseComponent = null;
            ComponentBuilder componentBuilder = new ComponentBuilder("");
            componentBuilder.append("\u00a7c" + n3 + ". \u00a77", ComponentBuilder.FormatRetention.NONE);
            componentBuilder.append(string4);
            componentBuilder.append("   ", ComponentBuilder.FormatRetention.NONE);
            componentBuilder.append("\u00a7a[Get Item]");
            componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Utils.myHoverText("\u00a7fGive the item into your inventory")));
            componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/di " + string3 + " get " + string4 + " " + string2));
            componentBuilder.append("  ", ComponentBuilder.FormatRetention.NONE);
            arrbaseComponent = componentBuilder.create();
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                player.spigot().sendMessage(arrbaseComponent);
            } else {
                commandSender.sendMessage("\u00a7c" + n3 + ". \u00a77" + string4);
            }
            ++n3;
        }
        commandSender.sendMessage("");
        if (n < n2) {
            commandSender.sendMessage("\u00a7eType \u00a76/di " + string3 + " list " + (n + 1) + " \u00a7eto next page.");
        }
        commandSender.sendMessage("\u00a76\u00a7m------ \u00a7e Page \u00a77" + n + "\u00a7e of \u00a77" + n2 + " \u00a76\u00a7m------");
    }

    public static String getDirection(Float f) {
        f = Float.valueOf(f.floatValue() / 90.0f);
        if ((f = Float.valueOf(Math.round(f.floatValue()))).floatValue() == -4.0f || f.floatValue() == 0.0f || f.floatValue() == 4.0f) {
            return "SOUTH";
        }
        if (f.floatValue() == -1.0f || f.floatValue() == 3.0f) {
            return "EAST";
        }
        if (f.floatValue() == -2.0f || f.floatValue() == 2.0f) {
            return "NORTH";
        }
        if (f.floatValue() == -3.0f || f.floatValue() == 1.0f) {
            return "WEST";
        }
        return "";
    }

    public static String getCardinalDirection(Player player) {
        double d = (player.getLocation().getYaw() - 90.0f) % 360.0f;
        if (d < 0.0) {
            d += 360.0;
        }
        if (0.0 <= d && d < 22.5) {
            return "N";
        }
        if (22.5 <= d && d < 67.5) {
            return "NE";
        }
        if (67.5 <= d && d < 112.5) {
            return "E";
        }
        if (112.5 <= d && d < 157.5) {
            return "SE";
        }
        if (157.5 <= d && d < 202.5) {
            return "S";
        }
        if (202.5 <= d && d < 247.5) {
            return "SW";
        }
        if (247.5 <= d && d < 292.5) {
            return "W";
        }
        if (292.5 <= d && d < 337.5) {
            return "NW";
        }
        if (337.5 <= d && d < 360.0) {
            return "N";
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    public static Color getColorOfChar(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:416)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:196)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:141)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:379)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:867)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:768)
        // org.benf.cfr.reader.Main.doJar(Main.java:140)
        // org.benf.cfr.reader.Main.main(Main.java:241)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static Color getColorByName(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:416)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:196)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:141)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:379)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:867)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:768)
        // org.benf.cfr.reader.Main.doJar(Main.java:140)
        // org.benf.cfr.reader.Main.main(Main.java:241)
        throw new IllegalStateException("Decompilation failed");
    }

    public static void playEffect(String string, double d, double d2, double d3, double d4, int n, Location location) {

    }

}

