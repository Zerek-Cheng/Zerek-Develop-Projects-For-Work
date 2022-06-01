/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.Main;
import cc.kunss.vexst.managers.AilerCuiLianManager;
import cc.kunss.vexst.managers.GiveExp;
import cc.kunss.vexst.managers.LevelAddition;
import cc.kunss.vexst.managers.LuckyStoneManager;
import cc.kunss.vexst.managers.ProtectionStoneManager;
import cc.kunss.vexst.managers.StoneManager;
import cc.kunss.vexst.managers.StrengLevel;
import cc.kunss.vexst.utils.GuiFile;
import cc.kunss.vexst.utils.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Methods {
    private static Map<String, AilerCuiLianManager> ailerCuiLianManagerMap = new HashMap<String, AilerCuiLianManager>();
    private static Map<String, StoneManager> stringStoneManagerMap = new HashMap<String, StoneManager>();
    private static Map<String, LuckyStoneManager> luckyStoneManagerMap = new HashMap<String, LuckyStoneManager>();
    private static Map<String, ProtectionStoneManager> protectionStoneManagerMap = new HashMap<String, ProtectionStoneManager>();
    private static Map<Integer, StrengLevel> strengLevelMap = new HashMap<Integer, StrengLevel>();

    public static Map<String, AilerCuiLianManager> getAilerCuiLianManagerMap() {
        return ailerCuiLianManagerMap;
    }

    public static Map<String, LuckyStoneManager> getLuckyStoneManagerMap() {
        return luckyStoneManagerMap;
    }

    public static Map<String, ProtectionStoneManager> getProtectionStoneManagerMap() {
        return protectionStoneManagerMap;
    }

    public static Map<String, StoneManager> getStringStoneManagerMap() {
        return stringStoneManagerMap;
    }

    public static void Default() {
        Main.getMain().saveDefaultConfig();
        Main.getMain().reloadConfig();
        Message.saveDefauleMessage();
        GuiFile.saveDefauleMessage();
        GuiFile.reloadMessage();
        Methods.loadStone();
        Methods.loadluckstone();
        Methods.loadpts();
        Methods.loadAilerCuiLian();
        Methods.loadStringLevel();
    }

    public static String getGuiPrefix() {
        return Main.getMain().getConfig().getString("GuiPrefix").replace("&", "\u00a7");
    }

    public static void loadStringLevel() {
        Methods.getStrengLevelMap().clear();
        for (String key : Main.getMain().getConfig().getConfigurationSection("StringLevel").getKeys(false)) {
            String Path2 = "StringLevel." + key + ".";
            int level = Integer.parseInt(key);
            StrengLevel strengLevel = new StrengLevel(level, Main.getMain().getConfig().getDouble(Path2 + "exp"), Main.getMain().getConfig().getString(Path2 + "prefix"));
            Methods.getStrengLevelMap().put(level, strengLevel);
        }
    }

    public static void loadAilerCuiLian() {
        ailerCuiLianManagerMap.clear();
        for (String key : Main.getMain().getConfig().getConfigurationSection("StrengList").getKeys(false)) {
            String Path2 = "StrengList." + key + ".";
            AilerCuiLianManager ailerCuiLianManager = new AilerCuiLianManager(key, Main.getMain().getConfig().getString(Path2 + "type").replace("&", "\u00a7"), Main.getMain().getConfig().getBoolean(Path2 + "Message.enable"), Main.getMain().getConfig().getDouble(Path2 + "Pay.Points"), Main.getMain().getConfig().getBoolean(Path2 + "LoseLevel.enable"), Main.getMain().getConfig().getString(Path2 + "LoseLevel.type"), Main.getMain().getConfig().getDouble(Path2 + "LoseLevel.random"), Main.getMain().getConfig().getString(Path2 + "lore").replace("&", "\u00a7"), Main.getMain().getConfig().getDouble(Path2 + "Pay.Vault"), Methods.getReplaceMap(Path2), Methods.getStoneList(Path2), Main.getMain().getConfig().getDouble(Path2 + "random"), Main.getMain().getConfig().getString(Path2 + "Permission.vaule"), Main.getMain().getConfig().getString(Path2 + "Permission.prefix"), Main.getMain().getConfig().getBoolean(Path2 + "Command.enable"), Main.getMain().getConfig().getStringList(Path2 + "Command.list"), Methods.getStrengLevelAddition(Path2), Main.getMain().getConfig().getInt(Path2 + "needLevel"), Methods.getStrengGiveExp(Path2));
            ailerCuiLianManagerMap.put(key, ailerCuiLianManager);
        }
    }

    public static GiveExp getStrengGiveExp(String Path2) {
        String[] Success = Main.getMain().getConfig().getString(Path2 + "GiveExp.Success").split("-");
        String[] Defeat = Main.getMain().getConfig().getString(Path2 + "GiveExp.Defeat").split("-");
        GiveExp exp = new GiveExp(Double.parseDouble(Success[0]), Double.parseDouble(Success[1]), Double.parseDouble(Defeat[0]), Double.parseDouble(Defeat[1]));
        return exp;
    }

    public static Map<Integer, LevelAddition> getStrengLevelAddition(String Path2) {
        HashMap<Integer, LevelAddition> getStrengLevelAdditions = new HashMap<Integer, LevelAddition>();
        for (String key : Main.getMain().getConfig().getConfigurationSection(Path2 + "LevelAddition").getKeys(false)) {
            int level = Integer.parseInt(key);
            String PATH = Path2 + "LevelAddition." + key;
            String[] adds = Main.getMain().getConfig().getString(PATH).split("-");
            LevelAddition addition = new LevelAddition(Double.parseDouble(adds[0]), Double.parseDouble(adds[1]), Double.parseDouble(adds[2]), Double.parseDouble(adds[3]));
            getStrengLevelAdditions.put(level, addition);
        }
        return getStrengLevelAdditions;
    }

    public static void ReplaceList(List<String> lore, String a, String b) {
        for (int i = 0; i < lore.size(); ++i) {
            lore.set(i, lore.get(i).replace(a, b));
        }
    }

    public static void loadluckstone() {
        luckyStoneManagerMap.clear();
        for (String key : Main.getMain().getConfig().getConfigurationSection("LuckyStone").getKeys(false)) {
            String path = "LuckyStone." + key + ".";
            LuckyStoneManager luckyStoneManager = new LuckyStoneManager(Main.getMain().getConfig().getString(path + ".lore").replace("&", "\u00a7"), Main.getMain().getConfig().getDouble(path + ".min"), Main.getMain().getConfig().getDouble(path + ".max"));
            luckyStoneManagerMap.put(key, luckyStoneManager);
        }
    }

    public static void loadpts() {
        protectionStoneManagerMap.clear();
        for (String key : Main.getMain().getConfig().getConfigurationSection("ProtectionStone").getKeys(false)) {
            String path = "ProtectionStone." + key + ".";
            ProtectionStoneManager protectionStoneManager = new ProtectionStoneManager(Main.getMain().getConfig().getString(path + ".lore").replace("&", "\u00a7"), Main.getMain().getConfig().getDouble(path + ".min"), Main.getMain().getConfig().getDouble(path + ".max"));
            protectionStoneManagerMap.put(key, protectionStoneManager);
        }
    }

    public static void loadStone() {
        stringStoneManagerMap.clear();
        for (String key : Main.getMain().getConfig().getConfigurationSection("StrengStone").getKeys(false)) {
            String path = "StrengStone." + key + ".";
            StoneManager stoneManager = new StoneManager(Main.getMain().getConfig().getString(path + ".lore").replace("&", "\u00a7"), key);
            stringStoneManagerMap.put(key, stoneManager);
        }
    }

    public static Map<List<String>, List<String>> getReplaceMap(String key) {
        HashMap<List<String>, List<String>> map = new HashMap<List<String>, List<String>>();
        for (String s : Main.getMain().getConfig().getConfigurationSection(key + ".settings").getKeys(false)) {
            String Path2 = key + ".settings." + s + ".";
            ArrayList ss = new ArrayList();
            Main.getMain().getConfig().getStringList(Path2 + ".replaceLore").forEach(v -> ss.add(v.replace("&", "\u00a7")));
            map.put(Arrays.asList(s.split(",")), ss);
        }
        return map;
    }

    public static Map<StoneManager, Integer> getStoneList(String key) {
        List<String> list = Main.getMain().getConfig().getStringList(key + ".stone");
        HashMap<StoneManager, Integer> map = new HashMap<StoneManager, Integer>();
        list.forEach(v -> map.put(stringStoneManagerMap.get(v.split("\\*")[0]), Integer.parseInt(v.split("\\*")[1])));
        return map;
    }

    public static ItemStack getItem(String Path2) {
        ItemStack item = new ItemStack(Main.getMain().getConfig().getInt("Gui." + Path2 + ".id"));
        ItemMeta m = item.getItemMeta();
        item.setDurability((short)Main.getMain().getConfig().getInt("Gui." + Path2 + ".data"));
        m.setDisplayName(Main.getMain().getConfig().getString("Gui." + Path2 + ".disname").replace("&", "\u00a7"));
        ArrayList lore = new ArrayList();
        Main.getMain().getConfig().getStringList("Gui." + Path2 + ".lore").forEach(v -> lore.add(v.replace("&", "\u00a7")));
        m.setLore(lore);
        item.setItemMeta(m);
        return item;
    }

    public static Map<Integer, StrengLevel> getStrengLevelMap() {
        return strengLevelMap;
    }
}

