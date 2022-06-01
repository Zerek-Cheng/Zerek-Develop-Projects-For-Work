/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package yo;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.al_0;
import yo.aq_0;
import yo.be_0;
import yo.by_0;

public class ao_0
extends BukkitRunnable {
    private static HashMap<String, HashMap<String, String>> a;
    private static HashMap<String, HashMap<String, String>> b;
    private static FileConfiguration c;
    private Plugin d;
    private long e = 0L;
    private File f;
    private String g;
    private static final String h = "http://198.199.127.128/rpgitems/index.php?page=localeget&lastupdate=";
    private static final String i = "http://www.rpgitems2.bugs3.com/locale/%s/%s.lang";

    private ao_0(Plugin plugin) {
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
        this.d = plugin;
        this.e = plugin.c().getLong("lastLocaleUpdate", 0L);
        this.f = plugin.getDataFolder();
        ao_0.a(plugin);
        if (!plugin.c().contains("localeDownload")) {
            plugin.c().set("localeDownload", true);
            plugin.saveConfig();
        }
    }

    public static Set<String> a() {
        return a.keySet();
    }

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
        if (!this.d.c().getBoolean("localeDownload", true)) {
            this.cancel();
        }
        try {
            URL updateURL = new URL(h + this.e);
            this.e = System.currentTimeMillis();
            URLConnection conn = updateURL.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            ArrayList<String> locales = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                locales.add(line);
            }
            reader.close();
            File localesFolder = new File(this.f, "locale/");
            localesFolder.mkdirs();
            for (String locale : locales) {
                int bytesRead;
                URL downloadURL = new URL(String.format(i, this.g, locale));
                File outFile = new File(this.f, "locale/" + locale + ".lang");
                InputStream in = downloadURL.openStream();
                FileOutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                while ((bytesRead = in.read(buf)) != -1) {
                    out.write(buf, 0, bytesRead);
                }
                in.close();
                out.close();
            }
        }
        catch (Exception e2) {
            return;
        }
        new BukkitRunnable(){

            public void run() {
                aq_0 config = ao_0.this.d.c();
                config.set("lastLocaleUpdate", (Object)ao_0.this.e);
                ao_0.this.d.saveConfig();
                ao_0.a(ao_0.this.d);
                for (RPGItem item : by_0.a.values()) {
                    item.a();
                }
            }
        }.runTask((org.bukkit.plugin.Plugin)this.d);
    }

    private static HashMap<String, String> a(String locale) {
        PotionEffectType[] pets;
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
        HashMap<String, String> petMap = new HashMap<String, String>();
        for (PotionEffectType pet : pets = PotionEffectType.values()) {
            if (pet == null) continue;
            petMap.put(al_0.POTION.getName() + ao_0.b(al_0.POTION, pet.getName(), locale).toLowerCase(), pet.getName().toLowerCase());
        }
        for (PotionEffectType em : Enchantment.values()) {
            if (em == null) continue;
            petMap.put(al_0.ENCHANT.getName() + ao_0.b(al_0.ENCHANT, em.getName(), locale).toLowerCase(), em.getName().toLowerCase());
        }
        return petMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
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
        a.clear();
        b.clear();
        a.put(ao_0.b(), ao_0.a(plugin.getResource("locale/zh_CN.lang")));
        b.put(ao_0.b(), ao_0.a(ao_0.b()));
        File localesFolder = new File(plugin.getDataFolder(), "locale/");
        localesFolder.mkdirs();
        for (File file : localesFolder.listFiles()) {
            if (file.isDirectory() || !file.getName().endsWith(".lang")) continue;
            FileInputStream in = null;
            try {
                String locale = file.getName().substring(0, file.getName().lastIndexOf(46));
                HashMap<String, String> map = a.get(locale);
                map = map == null ? new HashMap<String, String>() : map;
                in = new FileInputStream(file);
                ao_0.a(locale, in, map);
            }
            catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
            finally {
                try {
                    in.close();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    private static HashMap<String, String> a(String locale, InputStream in, HashMap<String, String> map) {
        ao_0.a(in, map);
        ao_0.a(a, locale, map);
        HashMap<String, String> petMap = ao_0.a(locale);
        ao_0.a(b, locale, petMap);
        return map;
    }

    private static void a(HashMap<String, HashMap<String, String>> topMap, String locale, HashMap<String, String> map) {
        HashMap<String, String> prev = topMap.get(locale);
        if (prev == null) {
            prev = new HashMap();
            topMap.put(locale, prev);
        }
        prev.putAll(map);
    }

    private static HashMap<String, String> a(InputStream in, HashMap<String, String> map) {
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
        int i = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                ++i;
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] args = line.split("=", 2);
                map.put(args[0].trim(), args[1].trim());
            }
            return map;
        }
        catch (Exception e2) {
            System.out.println("Error occurred while read line: " + i);
            e2.printStackTrace();
            return null;
        }
    }

    private static HashMap<String, String> a(InputStream in) {
        return ao_0.a(in, new HashMap<String, String>());
    }

    public static String b() {
        return c.getString("defaultLanguage", "zh_CN");
    }

    public static String a(Object object) {
        if (object != null && object instanceof Player) {
            return ao_0.a((Player)object);
        }
        return ao_0.b();
    }

    public static String a(Player player) {
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
        if (c.getBoolean("usePlayerLocale")) {
            try {
                Object ep = be_0.a((Object)player);
                Field f = ep.getClass().getDeclaredField("locale");
                f.setAccessible(true);
                return (String)f.get(ep);
            }
            catch (Exception ep) {
                // empty catch block
            }
        }
        return ao_0.b();
    }

    public static void b(Plugin plugin) {
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
        a = new HashMap();
        b = new HashMap();
        new ao_0(plugin).runTaskTimerAsynchronously((org.bukkit.plugin.Plugin)plugin, 0L, 1728000L);
    }

    public static String a(String key, String locale) {
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
        if (!a.containsKey(locale)) {
            return ao_0.b(key);
        }
        HashMap<String, String> strings = a.get(locale);
        if (strings == null || !strings.containsKey(key)) {
            return ao_0.b(key);
        }
        return strings.get(key);
    }

    private static String b(String key) {
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
        HashMap<String, String> strings = a.get(ao_0.b());
        if (!strings.containsKey(key)) {
            return "!" + key + "!";
        }
        return strings.get(key);
    }

    public static String a(al_0 type, String field, String locale) {
        String value;
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
        HashMap<String, String> strings = b.get(locale);
        if (strings != null && (value = strings.get(type.getName() + field.toLowerCase())) != null) {
            return value;
        }
        return null;
    }

    public static String b(al_0 type, String field, String locale) {
        String value;
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
        HashMap<String, String> strings = a.get(locale);
        if (strings != null && (value = strings.get("display.aliases." + type.getName() + "." + field.toLowerCase())) != null) {
            return value;
        }
        return field;
    }

    static {
        c = Plugin.d;
    }

}

