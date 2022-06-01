/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandMap
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginIdentifiableCommand
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 */
package su.nightexpress.divineitems.cmds;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandRegister
extends Command
implements PluginIdentifiableCommand {
    protected Plugin plugin;
    protected final CommandExecutor owner;
    protected final Object registeredWith;

    public CommandRegister(String[] arrstring, String string, String string2, CommandExecutor commandExecutor, Object object, Plugin plugin) {
        super(arrstring[0], string, string2, Arrays.asList(arrstring));
        this.owner = commandExecutor;
        this.plugin = plugin;
        this.registeredWith = object;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public boolean execute(CommandSender commandSender, String string, String[] arrstring) {
        return this.owner.onCommand(commandSender, (Command)this, string, arrstring);
    }

    public Object getRegisteredWith() {
        return this.registeredWith;
    }

    public static void reg(Plugin plugin, CommandExecutor commandExecutor, String[] arrstring, String string, String string2) {
        try {
            CommandRegister commandRegister = new CommandRegister(arrstring, string, string2, commandExecutor, new Object(), plugin);
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap)field.get((Object)Bukkit.getServer());
            commandMap.register(plugin.getDescription().getName(), (Command)commandRegister);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

