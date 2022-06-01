/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.util.FileUtil
 *  yo.bz
 */
package yo;

import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.FileUtil;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.ao_0;
import yo.aq_0;
import yo.aw_1;
import yo.bg_1;
import yo.bi_1;
import yo.bq_0;
import yo.bz;
import yo.bz_1;
import yo.z_0;

public class by_0 {
    public static TreeMap<Integer, RPGItem> a;
    public static TreeMap<String, RPGItem> b;
    public static HashMap<String, List<RPGItem>> c;
    public static HashMap<String, bz_1> d;

    public static void a(Plugin plugin) {
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
        a = new TreeMap();
        b = new TreeMap(new bq_0(true));
        c = new HashMap();
        for (String fileName : bg_1.a(new File(plugin.getDataFolder(), "items").getAbsolutePath(), bg_1.b)) {
            by_0.a(fileName);
        }
        Plugin.a.info("Total " + b.values().size() + " rpgitems loaded");
        by_0.b(plugin);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(String fileName) {
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
            aq_0 itemStorage = new aq_0(new File(fileName));
            itemStorage.d();
            ConfigurationSection section = itemStorage.getConfigurationSection("items");
            if (section == null) {
                return;
            }
            ArrayList<RPGItem> rpgitems = new ArrayList<RPGItem>();
            for (String key : section.getKeys(false)) {
                RPGItem item = new RPGItem(section.getConfigurationSection(key), true);
                item.o = fileName;
                a.put(item.getID(), item);
                b.put(item.getName(), item);
                rpgitems.add(item);
                for (bi_1 p : item.t()) {
                    bi_1.b.a(p.e(), bi_1.b.b(p.e()) + 1);
                }
            }
            c.put(fileName, rpgitems);
            Plugin.a.info(rpgitems.size() + " rpgitems loaded from " + fileName);
        }
        catch (Exception e2) {
            e2.printStackTrace();
            Plugin.a.severe("Error loading " + fileName + ". Creating backup");
            File file = new File(fileName);
            long time = System.currentTimeMillis();
            File backup = new File(file.getParentFile(), time + "-" + file.getName() + ".backup");
            FileUtil.copy((File)file, (File)backup);
            File log = new File(Plugin.c.getDataFolder(), time + "-log.txt");
            PrintStream ps = null;
            try {
                ps = new PrintStream(log);
                ps.printf("RPGItems (%s) Error loading " + fileName + "\r\n", Plugin.c.getDescription().getVersion());
                e2.printStackTrace(ps);
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            finally {
                ps.close();
            }
        }
    }

    public static void b(Plugin plugin) {
        d = new HashMap();
        aq_0 storage = new aq_0(new File(plugin.getDataFolder(), "sets.yml"));
        storage.d();
        ConfigurationSection mainCon = storage.getConfigurationSection("sets");
        if (mainCon != null) {
            block0 : for (String setName : mainCon.getKeys(false)) {
                ConfigurationSection setMain = storage.getConfigurationSection("sets." + setName);
                if (setMain == null) continue;
                ArrayList<RPGItem> needsRPGItem = new ArrayList<RPGItem>();
                List needsString = setMain.getStringList("needs");
                for (String next : needsString) {
                    RPGItem item = by_0.c(next);
                    if (item == null) continue;
                    if (item.n == null) {
                        needsRPGItem.add(item);
                        continue;
                    }
                    Plugin.a.warning("RPGItem " + next + " in Set " + setName + " already has its own set, please check");
                    continue block0;
                }
                List mapList = setMain.getMapList("parts");
                ArrayList<bz_1.a> parts = new ArrayList<bz_1.a>();
                for (Map next : mapList) {
                    bz_1.a part = bz_1.a.a(next);
                    if (part == null) continue;
                    parts.add(part);
                }
                if (parts.isEmpty() || needsRPGItem.isEmpty()) continue;
                bz set = new bz(setName, needsRPGItem, parts);
                for (bz_1.a part : set.c.values()) {
                    part.a = set;
                }
                d.put(setName, (bz_1)set);
            }
        }
        for (bz set : d.values()) {
            for (RPGItem item : set.b) {
                item.n = set;
            }
        }
        Plugin.a.info(d.values().size() + " sets loaded");
    }

    public static synchronized List<String> a(String fileName, String locale) {
        ArrayList<String> list = new ArrayList<String>();
        File file = new File(fileName);
        if (file.exists()) {
            try {
                aq_0 itemStorage = new aq_0(file);
                itemStorage.d();
                ConfigurationSection section = itemStorage.getConfigurationSection("items");
                if (section == null) {
                    list.add((Object)ChatColor.RED + String.format(ao_0.a("message.import.error.empty", locale), fileName));
                    return list;
                }
                int success = 0;
                int fail = 0;
                for (String key : section.getKeys(false)) {
                    try {
                        ConfigurationSection con = section.getConfigurationSection(key);
                        RPGItem item = new RPGItem(con, false);
                        if (b.get(item.getName()) != null) {
                            list.add((Object)ChatColor.RED + String.format(ao_0.a("message.import.error.duplicname", locale), item.getName()));
                            ++fail;
                            continue;
                        }
                        item.a(by_0.a());
                        a.put(item.getID(), item);
                        b.put(item.getName(), item);
                        List<RPGItem> rpgitems = c.get(fileName);
                        if (rpgitems == null) {
                            rpgitems = new ArrayList<RPGItem>();
                            c.put(fileName, rpgitems);
                        }
                        rpgitems.add(item);
                        for (bi_1 p : item.t()) {
                            bi_1.b.a(p.e(), bi_1.b.b(p.e()) + 1);
                        }
                        ++success;
                    }
                    catch (Exception e2) {
                        ++fail;
                    }
                }
                by_0.c(Plugin.c);
                list.add((Object)ChatColor.GREEN + String.format(ao_0.a("message.import.over", locale), fileName, success + fail, success, fail));
            }
            catch (Exception e3) {
                list.add((Object)ChatColor.RED + "Error loading " + fileName);
            }
        } else {
            list.add((Object)ChatColor.RED + String.format(ao_0.a("message.file.notexist", locale), fileName));
        }
        return list;
    }

    public static synchronized void a(RPGItem rpgitem) {
        List<RPGItem> rpgitems = c.get(rpgitem.o);
        if (rpgitems == null) {
            rpgitems = new ArrayList<RPGItem>();
            c.put(rpgitem.o, rpgitems);
            rpgitems.add(rpgitem);
        }
        by_0.e(rpgitem.o);
    }

    private static void e(String fileName) {
        List<RPGItem> rpgitems = c.get(fileName);
        if (rpgitems != null) {
            aq_0 itemStorage = new aq_0(new File(fileName));
            itemStorage.d();
            ConfigurationSection newSection = itemStorage.createSection("items");
            for (RPGItem item : rpgitems) {
                ConfigurationSection itemSection = newSection.getConfigurationSection(item.getName());
                if (itemSection == null) {
                    itemSection = newSection.createSection(item.getName());
                }
                item.a(itemSection);
            }
            itemStorage.c();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void c(Plugin plugin) {
        HashMap<String, List<RPGItem>> asdhqjefhusfer22;
        try {
            asdhqjefhusfer22 = null;
            Throwable throwable = null;
            if (asdhqjefhusfer22 != null) {
                if (throwable != null) {
                    try {
                        asdhqjefhusfer22.close();
                    }
                    catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer22.close();
                }
            }
        }
        catch (Exception asdhqjefhusfer22) {
            // empty catch block
        }
        asdhqjefhusfer22 = c;
        synchronized (asdhqjefhusfer22) {
            for (String fileName : c.keySet()) {
                by_0.e(fileName);
            }
        }
    }

    public static RPGItem a(ItemStack item) {
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
        if (item == null) {
            return null;
        }
        if (!item.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) {
            return null;
        }
        try {
            int id = by_0.d(meta.getDisplayName());
            RPGItem rItem = by_0.a(id);
            return rItem;
        }
        catch (Exception e2) {
            return null;
        }
    }

    public static int a() {
        int id = 0;
        try {
            id = a.lastKey() + 1;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return id;
    }

    public static RPGItem b(String name) {
        return by_0.b(name, Plugin.c.getDataFolder().getAbsolutePath() + File.separator + "items" + File.separator + "items.yml");
    }

    public static RPGItem b(String name, String fileName) {
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
        name = name.toLowerCase();
        if (b.containsKey(name)) {
            return null;
        }
        int id = by_0.a();
        RPGItem item = new RPGItem(name, id);
        item.o = fileName;
        a.put(id, item);
        b.put(name, item);
        List<RPGItem> rpgitems = c.get(fileName);
        if (rpgitems == null) {
            rpgitems = new ArrayList<RPGItem>();
            c.put(fileName, rpgitems);
        }
        rpgitems.add(item);
        return item;
    }

    public static RPGItem a(int id) {
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
        return a.get(id);
    }

    public static RPGItem c(String uid) {
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
        return b.get(uid);
    }

    public static int d(String str) throws Exception {
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
        if (str.length() < 16) {
            throw new Exception();
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 16; ++i) {
            if (str.charAt(i) != '\u00a7') {
                throw new Exception();
            }
            out.append(str.charAt(++i));
        }
        return Integer.parseInt(out.toString(), 16);
    }

    public static void b(RPGItem item) {
        boolean removed;
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
        b.remove(item.getName());
        List<RPGItem> rpgitems = c.get(item.o);
        if (rpgitems != null) {
            rpgitems.remove(item);
        }
        boolean bl = removed = a.remove(item.getID()) != null;
        if (removed) {
            bg_1.a(new aw_1(item));
            try {
                bz set = (bz)d.remove(item.n.a);
                for (RPGItem rItem : set.b) {
                    rItem.n = null;
                    if (rItem.equals(item)) continue;
                    rItem.a();
                }
            }
            catch (Exception set) {
                // empty catch block
            }
        }
    }
}

