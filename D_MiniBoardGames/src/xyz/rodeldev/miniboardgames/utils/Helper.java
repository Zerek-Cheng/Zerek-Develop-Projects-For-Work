//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package xyz.rodeldev.miniboardgames.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.banner.Pattern;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import xyz.rodeldev.miniboardgames.MBG;
import xyz.rodeldev.miniboardgames.api.PokerDeck;
import xyz.rodeldev.miniboardgames.configuration.ConfigurationManager;
import xyz.rodeldev.miniboardgames.games.MBGGame;

public class Helper {
    public static final PokerDeck publicDeck = (new PokerDeck()).fill();
    public static final SplittableRandom random = new SplittableRandom();
    private static JsonParser jsonParser = new JsonParser();

    public Helper() {
    }

    public static Material getMaterialFallback2(String... var0) {
        String[] var2 = var0;
        int var3 = var0.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            Material var1 = Material.getMaterial(var5);
            if (var1 != null) {
                return var1;
            }
        }

        return Material.AIR;
    }

    public static DyeColor getSilverDye() {
        return DyeColor.getByWoolData((byte) 8);
    }

    public static ItemStack headizer(String var0, ItemStack var1) {
        SkullMeta var2 = (SkullMeta) var1.getItemMeta();
        var2.setOwner(var0);
        var1.setItemMeta(var2);
        return var1;
    }

    public static ItemStack headizer(UUID var0, ItemStack var1) {
        return headizer(Bukkit.getOfflinePlayer(var0).getName(), var1);
    }

    public static String unheadizer(ItemStack var0) {
        if (var0.getType().equals(MaterialFallback.PLAYER_HEAD.material())) {
            SkullMeta var1 = (SkullMeta) var0.getItemMeta();
            return var1.getOwner();
        } else {
            return null;
        }
    }

    public static ItemStack glowItem(boolean var0, ItemStack var1) {
        ItemMeta var2 = var1.getItemMeta();
        if (var0) {
            var2.addEnchant(Enchantment.DURABILITY, 1, false);
        } else {
            var2.removeEnchant(Enchantment.DURABILITY);
        }

        var1.setItemMeta(var2);
        return var1;
    }

    public static List<String> colorizeString(List<String> var0) {
        ArrayList var1 = new ArrayList();
        Iterator var2 = var0.iterator();

        while (var2.hasNext()) {
            String var3 = (String) var2.next();
            String[] var4 = var3.split("\n");
            String[] var5 = var4;
            int var6 = var4.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                String var8 = var5[var7];
                var1.add(ChatColor.translateAlternateColorCodes('&', var8));
            }
        }

        return var1;
    }

    public static ItemStack setItemName(ItemStack var0, String var1) {
        ItemMeta var2 = var0.getItemMeta();
        var2.setDisplayName(ChatColor.translateAlternateColorCodes('&', var1));
        var0.setItemMeta(var2);
        return var0;
    }

    public static ItemStack setItemLore(ItemStack var0, String... var1) {
        ItemMeta var2 = var0.getItemMeta();
        var2.setLore(colorizeString(Arrays.asList(var1)));
        var0.setItemMeta(var2);
        return var0;
    }

    public static ItemStack addItemLore(ItemStack var0, String... var1) {
        ItemMeta var2 = var0.getItemMeta();
        if (!var2.hasLore()) {
            var2.setLore(new ArrayList());
        }

        List var3 = var2.getLore();
        var3.addAll(colorizeString(Arrays.asList(var1)));
        var2.setLore(var3);
        var0.setItemMeta(var2);
        return var0;
    }

    public static void commandExecutor(String var0, String var1) {
        String[] var2 = var0.split(";");
        String[] var3 = var2;
        int var4 = var2.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), var6.replace("{player}", var1));
        }

    }

    public static String getChatColorFromDye(DyeColor var0) {
        if (!var0.toString().equals("SILVER") && !var0.toString().equals("LIGHT_GRAY")) {
            switch (var0) {
                case WHITE:
                    return "f";
                case ORANGE:
                    return "6";
                case MAGENTA:
                    return "5";
                case LIGHT_BLUE:
                    return "9";
                case YELLOW:
                    return "e";
                case LIME:
                    return "a";
                case PINK:
                    return "d";
                case GRAY:
                    return "8";
                case CYAN:
                    return "3";
                case PURPLE:
                    return "5";
                case BLUE:
                    return "1";
                case BROWN:
                    return "6";
                case GREEN:
                    return "2";
                case RED:
                    return "c";
                case BLACK:
                    return "0";
                default:
                    return "f";
            }
        } else {
            return "7";
        }
    }

    public static String upperCaser(String var0) {
        return var0.substring(0, 1).toUpperCase() + var0.substring(1).toLowerCase();
    }

    public static String getDebugBanner(ItemStack var0) {
        if (!var0.getType().name().contains("BANNER")) {
            return "&cThis item isn't banner!";
        } else {
            BannerMeta var1 = (BannerMeta) var0.getItemMeta();
            String var2 = "BannerBuilder.build(DyeColor." + var1.getBaseColor();

            Pattern var4;
            for (Iterator var3 = var1.getPatterns().iterator(); var3.hasNext(); var2 = var2 + ", new Pattern(DyeColor." + var4.getColor() + ", PatternType." + var4.getPattern() + ")") {
                var4 = (Pattern) var3.next();
            }

            var2 = var2.replace("null", "WHITE");
            var2 = var2 + ");";
            StringSelection var5 = new StringSelection(var2);
            Clipboard var6 = Toolkit.getDefaultToolkit().getSystemClipboard();
            var6.setContents(var5, var5);
            return var2;
        }
    }

    public static String arrayStringer(List<?> var0) {
        if (var0.isEmpty()) {
            return "";
        } else {
            String var1 = "";

            Object var3;
            for (Iterator var2 = var0.iterator(); var2.hasNext(); var1 = var1 + var3.toString() + ", ") {
                var3 = var2.next();
            }

            return var1.substring(0, var1.length() - 2);
        }
    }

    public static ItemStack createItem(Material var0, int var1, int var2, String var3) {
        ItemStack var4 = new ItemStack(var0, var2, (short) var1);
        if (var0 != Material.AIR) {
            ItemMeta var5 = var4.getItemMeta();
            if (!var3.equals("")) {
                var5.setDisplayName(ChatColor.translateAlternateColorCodes('&', var3));
            }

            var4.setItemMeta(var5);
        }

        return var4;
    }

    public static List<String> smartTabCompleter(List<String> var0, String var1) {
        ArrayList var2 = new ArrayList();
        Iterator var3 = var0.iterator();

        while (var3.hasNext()) {
            String var4 = (String) var3.next();
            if (var4.toLowerCase().startsWith(var1.toLowerCase())) {
                var2.add(var4);
            }
        }

        return var2;
    }

    public static String getPlayerFromGame(MBGGame var0) {
        return var0.getMaxPlayers() == var0.getMinPlayers() ? String.valueOf(var0.getMinPlayers()) : var0.getMinPlayers() + "-" + var0.getMaxPlayers();
    }

    public static void spawnFirework(final Location var0, int var1) {
        for (int var2 = 0; var2 < var1; ++var2) {
            Bukkit.getServer().getScheduler().runTaskLater(MBG.getInstance(), new Runnable() {
                public void run() {
                    Helper.spawnFirework(var0);
                }
            }, (long) (5 * var2));
        }

    }

    public static Firework spawnFirework(Location var0) {
        if ((Boolean) ConfigurationManager.getConfigNode("mbg.fireworks", Boolean.class)) {
            Firework var1 = (Firework) var0.getWorld().spawnEntity(var0, EntityType.FIREWORK);
            FireworkMeta var2 = var1.getFireworkMeta();
            Random var3 = new Random();
            int var4 = var3.nextInt(5) + 1;
            Type var5 = Type.BALL;
            if (var4 == 1) {
                var5 = Type.BALL;
            }

            if (var4 == 2) {
                var5 = Type.BALL_LARGE;
            }

            if (var4 == 3) {
                var5 = Type.BURST;
            }

            if (var4 == 4) {
                var5 = Type.CREEPER;
            }

            if (var4 == 5) {
                var5 = Type.STAR;
            }

            int var6 = var3.nextInt(256);
            int var7 = var3.nextInt(256);
            int var8 = var3.nextInt(256);
            Color var9 = Color.fromRGB(var6, var8, var7);
            var6 = var3.nextInt(256);
            var7 = var3.nextInt(256);
            var8 = var3.nextInt(256);
            Color var10 = Color.fromRGB(var6, var8, var7);
            FireworkEffect var11 = FireworkEffect.builder().flicker(var3.nextBoolean()).withColor(var9).withFade(var10).with(var5).trail(var3.nextBoolean()).build();
            var2.addEffect(var11);
            var2.setPower(0);
            var1.setFireworkMeta(var2);
            return var1;
        } else {
            return null;
        }
    }

    public static boolean isNumeric(String var0) {
        if (var0.contains("-")) {
            return false;
        } else {
            char[] var1 = var0.toCharArray();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                char var4 = var1[var3];
                if (!Character.isDigit(var4)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static void executeCommandLine(String var0, CommandSender var1) {
        if (!var0.isEmpty()) {
            String[] var2 = var0.split(";");
            String[] var3 = var2;
            int var4 = var2.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String var6 = var3[var5];
                Bukkit.dispatchCommand(var1, var6);
            }
        }

    }

    public static String stringBetterer(String var0) {
        String var1 = "";
        char[] var2 = var0.toCharArray();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            char var5 = var2[var4];
            if (Character.isUpperCase(var5)) {
                var1 = var1 + " " + var5;
            } else {
                var1 = var1 + var5;
            }
        }

        if (var1.startsWith(" ")) {
            return var1.substring(1);
        } else {
            return var1;
        }
    }

    public static String enumStringer(String var0) {
        String var1 = "";
        String[] var2 = var0.split("_");
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            var1 = var1 + var5.charAt(0) + var5.substring(1, var5.length()).toLowerCase() + " ";
        }

        return var1.substring(0, var1.length() - 1);
    }

    public static MBGGame findGameFromDisplay(String var0) {
        Iterator var1 = MBG.getInstance().getAllGamesHM().entrySet().iterator();

        Entry var2;
        String var4;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            var2 = (Entry) var1.next();
            String var3 = (String) ConfigurationManager.getGameNode(((MBGGame) var2.getValue()).getProgramName(), "displayName", String.class);
            var4 = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', var3));
        } while (!var4.equalsIgnoreCase(var0));

        return (MBGGame) var2.getValue();
    }

    public static MBGGame getGameFromProgramName(String var0) {
        List var1 = (List) MBG.getInstance().getAllGamesHM().values().stream().filter((var1x) -> {
            return var1x.getProgramName().equalsIgnoreCase(var0);
        }).collect(Collectors.toList());
        return var1.isEmpty() ? null : (MBGGame) var1.get(0);
    }

    public static String getCacheName(UUID var0) {
        try {
            JSONParser var1 = new JSONParser();
            Object var2 = var1.parse(new FileReader((new File("")).getAbsolutePath() + File.separator + "usercache.json"));
            JSONArray var3 = (JSONArray) var2;
            Iterator var4 = var3.iterator();

            while (var4.hasNext()) {
                JSONObject var5 = (JSONObject) var4.next();
                if (var5.get("uuid").equals(var0.toString())) {
                    return var5.get("name").toString();
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return "";
    }

    public static <E, T> Entry<T, E> containsIgnoreCase(HashMap<T, E> var0, boolean var1, String var2) {
        Iterator var3 = var0.entrySet().iterator();

        while (var3.hasNext()) {
            Entry var4 = (Entry) var3.next();
            if (var1) {
                if (var4.getValue().toString().equalsIgnoreCase(var2)) {
                    return var4;
                }
            } else if (var4.getKey().toString().equalsIgnoreCase(var2)) {
                return var4;
            }
        }

        return null;
    }

    public static ItemStack addLore(ItemStack var0, String... var1) {
        ItemMeta var2 = var0.getItemMeta();
        ArrayList var3 = new ArrayList();
        if (var0.hasItemMeta() && var0.getItemMeta().hasLore()) {
            var3.addAll(var2.getLore());
        }

        String[] var4 = var1;
        int var5 = var1.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            var3.add(ChatColor.translateAlternateColorCodes('&', var7));
        }

        var2.setLore(var3);
        var0.setItemMeta(var2);
        return var0;
    }

    public static int getMonth() {
        return Calendar.getInstance().get(2);
    }

    public static <K, V> Entry<K, V> entryBuilder(final K var0, final V var1) {
        return new Entry<K, V>() {
            K k = var0;
            V v = var1;

            public K getKey() {
                return this.k;
            }

            public V getValue() {
                return this.v;
            }

            public V setValue(V var1x) {
                this.v = var1x;
                return null;
            }
        };
    }

    public static ItemStack zeroOrOne(Inventory var0, int var1, ItemStack var2) {
        var0.setItem(var1, var2);
        if (var0.getItem(var1) == null || var0.getItem(var1).getType() == Material.AIR) {
            var2.setAmount(var2.getAmount() + 1);
            var0.setItem(var1, var2);
        }

        return var0.getItem(var1);
    }

    public static ItemStack createMap(Player var0, MapRenderer... var1) {
        MapView var2 = Bukkit.createMap(var0.getWorld());
        Iterator var3 = var2.getRenderers().iterator();

        while (var3.hasNext()) {
            MapRenderer var4 = (MapRenderer) var3.next();
            var2.removeRenderer(var4);
        }

        MapRenderer[] var7 = var1;
        int var8 = var1.length;

        for (int var5 = 0; var5 < var8; ++var5) {
            MapRenderer var6 = var7[var5];
            var2.addRenderer(var6);
        }

        return new ItemStack(Material.MAP, 1, var2.getId());
    }

    public static int booleanInteger(boolean var0) {
        return var0 ? 1 : 0;
    }

    public static int randomRange(int var0, int var1) {
        return (int) (Math.floor(Math.random() * (double) (var1 - var0 + 1)) + (double) var0);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void changeInventoryTitle(Player var0, String var1) {
        try {
            var1 = unlargeString(var1, 32);
            Object var2 = getClass("org.bukkit.craftbukkit.{version}.entity.CraftPlayer").cast(var0);
            Object var3 = var2.getClass().getMethod("getHandle").invoke(var2);
            Object var4 = getClass("net.minecraft.server.{version}.ChatMessage").getConstructor(String.class, Object[].class).newInstance(ChatColor.translateAlternateColorCodes('&', var1), new Object[0]);
            Object var5 = var3.getClass().getField("activeContainer").get(var3);
            int var6 = (Integer) var5.getClass().getField("windowId").get(var5);
            Object var7 = getClass("net.minecraft.server.{version}.PacketPlayOutOpenWindow").getConstructor(Integer.TYPE, String.class, getClass("net.minecraft.server.{version}.IChatBaseComponent"), Integer.TYPE).newInstance(var6, "minecraft:chest", var4, var0.getOpenInventory().getTopInventory().getSize());
            Object var8 = var3.getClass().getField("playerConnection").get(var3);
            var8.getClass().getMethod("sendPacket", getClass("net.minecraft.server.{version}.Packet")).invoke(var8, var7);
            var3.getClass().getMethod("updateInventory", getClass("net.minecraft.server.{version}.Container")).invoke(var3, var5);
        } catch (Exception var9) {
            MBG.getInstance().getLogger().warning("Error on change title " + var0.getName() + " " + var0.isOnline() + " " + var1 + " " + Bukkit.getServer().getClass().getPackage().getName());
            var9.printStackTrace();
        }

    }

    public static Class<?> getClass(String var0) {
        try {
            String var1 = Bukkit.getServer().getClass().getPackage().getName();
            String var2 = var1.substring(var1.lastIndexOf(".") + 1);
            return Class.forName(var0.replace("{version}", var2));
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String unlargeString(String var0, int var1) {
        if (var0.length() > var1) {
            var0 = var0.substring(0, var1 - 5) + "...";
        }

        return var0;
    }

    public static <T> T containsUnique(List<T> var0) {
        HashSet var1 = new HashSet();
        Iterator<T> var2 = var0.iterator();

        T var3;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            var3 = var2.next();
        } while (var1.add(var3));

        return var3;
    }

    public static int clamp(int var0, int var1, int var2) {
        return Math.max(var1, Math.min(var2, var0));
    }

    public static float clamp(float var0, float var1, float var2) {
        return Math.max(var1, Math.min(var2, var0));
    }

    public static int movingClamp(int var0, int var1, int var2) {
        if (var0 > var2) {
            return var1;
        } else {
            return var0 < var1 ? var2 : var0;
        }
    }

    public static String format(String var0, String... var1) {
        String var2 = var0;
        String[] var3 = var1;
        int var4 = var1.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            String[] var7 = var6.split(":");
            var2 = var2.replace(var7[0], var7[1]);
        }

        return var2;
    }

    public static String doubleFormat(String var0, String... var1) {
        String var2 = var0;
        String[] var3 = var1;
        int var4 = var1.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            String[] var7 = var6.split("::");
            var2 = var2.replace(var7[0], var7[1]);
        }

        return var2;
    }

    public static void useGameMetrics(String var0) {/*
        if (getGameFromProgramName(var0) == null) {
            MBG.getInstance().getLogger().warning(var0 + " is a invalid game, cannot submit to MBG Global Stats API, report this error to rodel77!");
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(MBG.getInstance(), () -> {
                try {
                    URL var1 = new URL("https://rodeldev.xyz/mbgstats/submit.php");
                    HttpsURLConnection var2 = (HttpsURLConnection) var1.openConnection();
                    var2.setDoOutput(true);
                    var2.setRequestMethod("POST");
                    var2.getOutputStream().write(("game=" + var0).getBytes());
                    String var3 = (new BufferedReader(new InputStreamReader(var2.getInputStream()))).readLine();
                    JsonObject var4 = jsonParser.parse(var3).getAsJsonObject();
                    if (var4.has("error")) {
                        MBG.getInstance().getLogger().warning("Error submitting " + var0 + " stats to MBG Global Stats API, please report the following error to rodel77: " + var4.get("error").getAsString());
                    }
                } catch (Exception var5) {
                    MBG.getInstance().getLogger().warning("Error submiting " + var0 + " stats to MBG Global Stats API, please report the following error to rodel77:");
                    var5.printStackTrace();
                }

            });
        }*/
    }

    public static float lerp(float var0, float var1, float var2) {
        return var0 + (var1 - var0) * var2;
    }

    public static Location getFixedLocation(Location var0, Player var1) {
        return new Location(var0.getWorld(), var0.getX(), var0.getY(), var0.getZ(), var1.getLocation().getYaw(), var1.getLocation().getPitch());
    }
}
