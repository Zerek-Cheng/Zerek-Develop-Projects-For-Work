/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandMap
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.command.SimpleCommandMap
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.SimplePluginManager
 */
package yo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

public class bc_1 {
    public static boolean a(String pluginName, boolean delete, CommandSender sender) throws Exception {
        PluginManager manager = Bukkit.getServer().getPluginManager();
        SimplePluginManager spmanager = (SimplePluginManager)manager;
        if (spmanager != null) {
            Field pluginsField = spmanager.getClass().getDeclaredField("plugins");
            pluginsField.setAccessible(true);
            List plugins = (List)pluginsField.get((Object)spmanager);
            Field lookupNamesField = spmanager.getClass().getDeclaredField("lookupNames");
            lookupNamesField.setAccessible(true);
            Map lookupNames = (Map)lookupNamesField.get((Object)spmanager);
            Field commandMapField = spmanager.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap)commandMapField.get((Object)spmanager);
            Map knownCommands = null;
            if (commandMap != null) {
                Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                knownCommands = (Map)knownCommandsField.get((Object)commandMap);
            }
            for (Plugin plugin : manager.getPlugins()) {
                if (!plugin.getName().equalsIgnoreCase(pluginName)) continue;
                try {
                    plugin.onDisable();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                manager.disablePlugin(plugin);
                if (plugins != null && plugins.contains((Object)plugin)) {
                    plugins.remove((Object)plugin);
                }
                if (lookupNames != null && lookupNames.containsKey(pluginName)) {
                    lookupNames.remove(pluginName);
                }
                if (commandMap != null) {
                    Iterator it = knownCommands.entrySet().iterator();
                    while (it.hasNext()) {
                        PluginCommand command;
                        Map.Entry entry = it.next();
                        if (!(entry.getValue() instanceof PluginCommand) || (command = (PluginCommand)entry.getValue()).getPlugin() != plugin) continue;
                        command.unregister((CommandMap)commandMap);
                        it.remove();
                    }
                }
                try {
                    String fileName = bc_1.a(plugin, true);
                    if (!delete) continue;
                    File file = new File(fileName);
                    file.delete();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            return true;
        }
        return true;
    }

    public static String a(Plugin plugin, boolean close) throws Exception {
        URLClassLoader loader = (URLClassLoader)plugin.getClass().getClassLoader();
        Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
        ucpField.setAccessible(true);
        Object ucpObj = ucpField.get(loader);
        URL[] list = loader.getURLs();
        String fileName = null;
        for (int i = 0; i < list.length; ++i) {
            Method m = ucpObj.getClass().getDeclaredMethod("getLoader", Integer.TYPE);
            m.setAccessible(true);
            Object jarLoader = m.invoke(ucpObj, i);
            String clsName = jarLoader.getClass().getName();
            if (!clsName.contains("JarLoader")) continue;
            m = jarLoader.getClass().getDeclaredMethod("ensureOpen", new Class[0]);
            m.setAccessible(true);
            m.invoke(jarLoader, new Object[0]);
            m = jarLoader.getClass().getDeclaredMethod("getJarFile", new Class[0]);
            m.setAccessible(true);
            JarFile jf = (JarFile)m.invoke(jarLoader, new Object[0]);
            if (close) {
                jf.close();
            }
            fileName = jf.getName();
        }
        if (close) {
            loader.close();
        }
        return fileName;
    }

    public static boolean a(String fileName, CommandSender sender) {
        try {
            PluginManager manager = Bukkit.getServer().getPluginManager();
            Plugin plugin = manager.loadPlugin(new File("plugins", fileName + ".jar"));
            if (plugin == null) {
                return false;
            }
            plugin.onLoad();
            manager.enablePlugin(plugin);
        }
        catch (Exception e2) {
            return false;
        }
        return true;
    }

    public static boolean b(String pluginName, CommandSender sender) throws Exception {
        String fileName;
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin != null && (fileName = bc_1.a(plugin, false)) != null) {
            pluginName = new File(fileName).getName();
            pluginName = pluginName.substring(0, pluginName.length() - 4);
            boolean unload = bc_1.a(pluginName, false, sender);
            boolean load = bc_1.a(pluginName, sender);
            return unload && load;
        }
        return false;
    }
}

