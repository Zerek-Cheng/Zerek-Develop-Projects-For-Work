/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitScheduler
 */
package clear;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

public class Main
        extends JavaPlugin
        implements Listener {
    private static final Pattern PLAIN_PATTERN = Pattern.compile("lang_\\d{1,5}");
    private static final Pattern FORMAT_PATTERN = Pattern.compile("lang-\\d{1,5}");
    private String pn;
    private BukkitScheduler scheduler;
    private String mainPath;
    private String pluginPath;
    private String dataFolder;
    private String pluginVersion;
    private Tps tps;
    private Names names;
    private Time time;
    private ServerManager serverManager;
    private Clear clear;
    private RedStone redStone;
    private Crop crop;
    private Liquid liquid;
    private HashMap<Integer, String> plainHash;
    private HashMap<String, String> formatHash;
    private List<Pattern> filter = new ArrayList<Pattern>();

    public void onEnable() {
        //saveDefaultConfig();
        this.initBasic();
        this.initConfig();
        this.tps = new Tps(this);
        this.names = new Names(this);
        this.time = new Time(this);
        this.serverManager = new ServerManager(this);
        this.redStone = new RedStone(this);
        this.crop = new Crop(this);
        this.liquid = new Liquid(this);
        this.clear = new Clear(this);
        this.loadConfig();
        Main.sendConsoleMessage(this.format("pluginEnabled", this.pn, this.pluginVersion));
    }

    public void onDisable() {
        if (this.scheduler != null) {
            this.scheduler.cancelAllTasks();
        }
        Main.sendConsoleMessage(this.format("pluginDisabled", this.pn, this.pluginVersion));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        block9:
        {
            block11:
            {
                int length;
                block12:
                {
                    block14:
                    {
                        block13:
                        {
                            block10:
                            {
                                Player p = null;
                                if (sender instanceof Player) {
                                    p = (Player) sender;
                                }
                                String cmdName = cmd.getName();
                                length = args.length;
                                if (!cmdName.equalsIgnoreCase("clean")) break block9;
                                sender.sendMessage("\u00a74\u00a7l\u4f5c\u8005:fyxridd \u8bba\u575b: www.minecraft001.com");
                                if (p == null || p.isOp()) break block10;
                                p.sendMessage(this.get(5));
                                return true;
                            }
                            if (length == 1 && args[0].equalsIgnoreCase("?")) break block11;
                            if (length != 1) break block12;
                            if (!args[0].equalsIgnoreCase("reload")) break block13;
                            this.loadConfig();
                            sender.sendMessage(this.get(7));
                            return true;
                        }
                        if (!args[0].equalsIgnoreCase("info")) break block14;
                        this.clear.info(sender);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("start")) {
                        this.clear.clear(true, -1);
                        return true;
                    }
                    break block11;
                }
                if (length != 2 || !args[0].equalsIgnoreCase("start")) break block11;
                this.clear.clear(true, Integer.parseInt(args[1]));
                return true;
            }
            try {
                sender.sendMessage(this.format("cmdHelpHeader", this.get(10)));
                sender.sendMessage(this.format("cmdHelpItem", this.get(15), this.get(20)));
                sender.sendMessage(this.format("cmdHelpItem", this.get(1215), this.get(1220)));
                sender.sendMessage(this.format("cmdHelpItem", this.get(1225), this.get(1230)));
            } catch (NumberFormatException e) {
                sender.sendMessage(this.format("fail", this.get(190)));
            }
        }
        return true;
    }

    public static double getDouble(double num, int accuracy) {
        String s;
        if (accuracy < 0) {
            accuracy = 0;
        }
        if ((s = String.valueOf(num)).split("\\.").length == 2) {
            String[] ss = s.split("\\.");
            return Double.parseDouble(String.valueOf(ss[0]) + "." + ss[1].substring(0, Math.min(accuracy, ss[1].length())));
        }
        return num;
    }

    public static String convert(String s) {
        if (s == null) {
            return null;
        }
        s = s.replace("//", "\u0001");
        s = s.replace("/&", "\u0002");
        s = s.replace("&", String.valueOf('\u00a7'));
        s = s.replace("\u0002", "&");
        s = s.replace("\u0001", "/");
        return s;
    }

    public static String convertBr(String s) {
        if (s == null) {
            return null;
        }
        s = s.replace("\n ", "\n");
        return s;
    }

    public static void sendConsoleMessage(String msg) {
        try {
            if (Bukkit.getConsoleSender() != null) {
                Bukkit.getConsoleSender().sendMessage(msg);
            } else {
                Bukkit.getLogger().info(msg);
            }
        } catch (Exception e) {
            System.out.println(msg);
        }
    }

    /*
     * Exception decompiling
     */
    public static String getPluginVersion(File plugin) {
        JarInputStream jis = null;
        try {
            jis = new JarInputStream(new FileInputStream(plugin));
            JarEntry entry;
            while ((entry = jis.getNextJarEntry()) != null) {
                String fileName = entry.getName();
                if (fileName.equalsIgnoreCase("plugin.yml")) {
                    YamlConfiguration config = new YamlConfiguration();
                    config.load(String.valueOf(jis));
                    return config.getString("version", null);
                }
            }
        } catch (FileNotFoundException localFileNotFoundException) {
        } catch (IOException localIOException1) {
        } catch (InvalidConfigurationException localInvalidConfigurationException) {
        } finally {
            try {
                if (jis != null) {
                    jis.close();
                }
            } catch (IOException e) {
                return null;
            }
        }
        try {
            if (jis != null) {
                jis.close();
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public static YamlConfiguration loadConfigByUTF8(File file) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            StringBuilder builder;
            InputStreamReader reader = new InputStreamReader((InputStream) new FileInputStream(file), Charset.forName("utf-8"));
            builder = new StringBuilder();
            BufferedReader input = new BufferedReader((Reader) reader);
            try {
                String line;
                while ((line = input.readLine()) != null) {
                    builder.append(line);
                    builder.append('\n');
                }
            } finally {
                input.close();
            }
            config.loadFromString(builder.toString());
            return config;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (InvalidConfigurationException e) {
            return null;
        }
    }

    public static boolean generateFiles(File sourceJarFile, String destPath, List<Pattern> filter) {
        ZipInputStream jis = null;
        FileOutputStream fos = null;
        try {
            JarEntry entry;
            new File(destPath).mkdirs();
            jis = new JarInputStream(new FileInputStream(sourceJarFile));
            byte[] buff = new byte[1024];
            while ((entry = ((JarInputStream) jis).getNextJarEntry()) != null) {
                String fileName = entry.getName();
                for (Pattern pattern : filter) {
                    int read;
                    Matcher matcher = pattern.matcher(fileName);
                    if (!matcher.find() || new File(String.valueOf(destPath) + File.separator + fileName).exists())
                        continue;
                    fos = new FileOutputStream(String.valueOf(destPath) + File.separator + fileName);
                    while ((read = jis.read(buff)) > 0) {
                        fos.write(buff, 0, read);
                    }
                    fos.close();
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (jis != null) {
                    jis.close();
                }
            } catch (IOException e) {
                return false;
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                return false;
            }
        }
    }

    public String get(int id) {
        try {
            return this.plainHash.get(id);
        } catch (Exception exception) {
            return "";
        }
    }

    public /* varargs */ String format(String type, Object... args) {
        String result = this.formatHash.get(type);
        if (result != null) {
            int i = 0;
            while (i < args.length) {
                if (args[i] == null) {
                    args[i] = "";
                }
                result = result.replace("{" + i + "}", args[i].toString());
                ++i;
            }
            return result;
        }
        result = this.formatHash.get(type);
        if (result != null) {
            int i = 0;
            while (i < args.length) {
                if (args[i] == null) {
                    args[i] = "";
                }
                result = result.replace("{" + i + "}", args[i].toString());
                ++i;
            }
            return result;
        }
        return "";
    }

    public String getPn() {
        return this.pn;
    }

    public ServerManager getServerManager() {
        return this.serverManager;
    }

    public RedStone getRedStone() {
        return this.redStone;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public String getMainPath() {
        return this.mainPath;
    }

    public Liquid getLiquid() {
        return this.liquid;
    }

    public String getPluginPath() {
        return this.pluginPath;
    }

    public Time getTime() {
        return this.time;
    }

    public Tps getTps() {
        return this.tps;
    }

    private void initBasic() {
        this.pn = this.getName();
        this.scheduler = Bukkit.getScheduler();
        this.mainPath = System.getProperty("user.dir");
        this.pluginPath = this.getFile().getParentFile().getAbsolutePath();
        this.dataFolder = String.valueOf(this.pluginPath) + File.separator + this.pn;
        this.pluginVersion = Main.getPluginVersion(this.getFile());
    }

    private void initConfig() {
        this.filter.add(Pattern.compile("config.yml"));
        this.filter.add(Pattern.compile("config_[a-zA-Z]+.yml"));
        this.filter.add(Pattern.compile("language.yml"));
        this.filter.add(Pattern.compile("language_[a-zA-Z]+.yml"));
        this.filter.add(Pattern.compile("hibernate.cfg.xml"));
        this.filter.add(Pattern.compile("names.yml"));
    }

    private void loadConfig() {
        Main.generateFiles(new File(String.valueOf(this.pluginPath) + File.separator + this.pn + ".jar"), this.dataFolder, this.filter);
        YamlConfiguration config = Main.loadConfigByUTF8(new File(String.valueOf(this.getPluginPath()) + File.separator + this.pn + File.separator + "config.yml"));
        if (config != null) {
            this.loadConfig0();
            if (this.clear != null) {
                this.clear.loadConfig(config);
            }
            if (this.crop != null) {
                this.crop.loadConfig(config);
            }
            if (this.liquid != null) {
                this.liquid.loadConfig(config);
            }
            if (this.names != null) {
                this.names.loadConfig();
            }
            if (this.redStone != null) {
                this.redStone.loadConfig(config);
            }
            if (this.serverManager != null) {
                this.serverManager.loadConfig(config);
            }
        }
    }

    private void loadConfig0() {
        this.plainHash = new HashMap();
        this.formatHash = new HashMap();
        YamlConfiguration languageConfig = Main.loadConfigByUTF8(new File(String.valueOf(this.pluginPath) + File.separator + this.pn + File.separator + "language.yml"));
        if (languageConfig != null) {
            for (String key : languageConfig.getKeys(true)) {
                if (PLAIN_PATTERN.matcher(key).matches()) {
                    this.plainHash.put(this.getId(key), Main.convertBr(Main.convert(languageConfig.getString(key))));
                    continue;
                }
                if (!FORMAT_PATTERN.matcher(key).matches()) continue;
                String s = languageConfig.getString(key);
                int index = s.indexOf(":");
                if (index < 1) {
                    return;
                }
                String name = s.substring(0, index);
                if (name.isEmpty()) {
                    return;
                }
                if (index == -1) {
                    return;
                }
                String value = Main.convertBr(Main.convert(s.substring(index + 1, s.length())));
                this.formatHash.put(name, value);
            }
        }
    }

    private int getId(String s) {
        try {
            int i = 0;
            while (i < s.length()) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    return Integer.parseInt(s.substring(i, s.length()));
                }
                ++i;
            }
        } catch (NumberFormatException i) {
            // empty catch block
        }
        return -1;
    }
}

