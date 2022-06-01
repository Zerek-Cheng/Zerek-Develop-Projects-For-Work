// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.jar.JarFile;
import java.net.URLClassLoader;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import java.lang.reflect.Field;
import org.bukkit.plugin.PluginManager;
import java.io.File;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import java.util.Map;
import java.util.List;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class bc
{
    public static boolean a(final String pluginName, final boolean delete, final CommandSender sender) throws Exception {
        final PluginManager manager = Bukkit.getServer().getPluginManager();
        final SimplePluginManager spmanager = (SimplePluginManager)manager;
        if (spmanager != null) {
            final Field pluginsField = spmanager.getClass().getDeclaredField("plugins");
            pluginsField.setAccessible(true);
            final List plugins = (List)pluginsField.get(spmanager);
            final Field lookupNamesField = spmanager.getClass().getDeclaredField("lookupNames");
            lookupNamesField.setAccessible(true);
            final Map lookupNames = (Map)lookupNamesField.get(spmanager);
            final Field commandMapField = spmanager.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            final SimpleCommandMap commandMap = (SimpleCommandMap)commandMapField.get(spmanager);
            Map knownCommands = null;
            if (commandMap != null) {
                final Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                knownCommands = (Map)knownCommandsField.get(commandMap);
            }
            for (final Plugin plugin : manager.getPlugins()) {
                if (plugin.getName().equalsIgnoreCase(pluginName)) {
                    try {
                        plugin.onDisable();
                    }
                    catch (Exception ex) {}
                    manager.disablePlugin(plugin);
                    if (plugins != null && plugins.contains(plugin)) {
                        plugins.remove(plugin);
                    }
                    if (lookupNames != null && lookupNames.containsKey(pluginName)) {
                        lookupNames.remove(pluginName);
                    }
                    if (commandMap != null) {
                        final Iterator it = knownCommands.entrySet().iterator();
                        while (it.hasNext()) {
                            final Map.Entry entry = it.next();
                            if (entry.getValue() instanceof PluginCommand) {
                                final PluginCommand command = entry.getValue();
                                if (command.getPlugin() != plugin) {
                                    continue;
                                }
                                command.unregister((CommandMap)commandMap);
                                it.remove();
                            }
                        }
                    }
                    try {
                        final String fileName = a(plugin, true);
                        if (delete) {
                            final File file = new File(fileName);
                            file.delete();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
        return true;
    }
    
    public static String a(final Plugin plugin, final boolean close) throws Exception {
        final URLClassLoader loader = (URLClassLoader)plugin.getClass().getClassLoader();
        final Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
        ucpField.setAccessible(true);
        final Object ucpObj = ucpField.get(loader);
        final URL[] list = loader.getURLs();
        String fileName = null;
        for (int i = 0; i < list.length; ++i) {
            Method m = ucpObj.getClass().getDeclaredMethod("getLoader", Integer.TYPE);
            m.setAccessible(true);
            final Object jarLoader = m.invoke(ucpObj, i);
            final String clsName = jarLoader.getClass().getName();
            if (clsName.contains("JarLoader")) {
                m = jarLoader.getClass().getDeclaredMethod("ensureOpen", (Class<?>[])new Class[0]);
                m.setAccessible(true);
                m.invoke(jarLoader, new Object[0]);
                m = jarLoader.getClass().getDeclaredMethod("getJarFile", (Class<?>[])new Class[0]);
                m.setAccessible(true);
                final JarFile jf = (JarFile)m.invoke(jarLoader, new Object[0]);
                if (close) {
                    jf.close();
                }
                fileName = jf.getName();
            }
        }
        if (close) {
            loader.close();
        }
        return fileName;
    }
    
    public static boolean a(final String fileName, final CommandSender sender) {
        try {
            final PluginManager manager = Bukkit.getServer().getPluginManager();
            final Plugin plugin = manager.loadPlugin(new File("plugins", fileName + ".jar"));
            if (plugin == null) {
                return false;
            }
            plugin.onLoad();
            manager.enablePlugin(plugin);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean b(String pluginName, final CommandSender sender) throws Exception {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin != null) {
            final String fileName = a(plugin, false);
            if (fileName != null) {
                pluginName = new File(fileName).getName();
                pluginName = pluginName.substring(0, pluginName.length() - 4);
                final boolean unload = a(pluginName, false, sender);
                final boolean load = a(pluginName, sender);
                return unload && load;
            }
        }
        return false;
    }
}
