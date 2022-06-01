/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  net.milkbowl.vault.chat.Chat
 *  org.bukkit.Server
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package xin.tianwc.tprefix;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main
        extends JavaPlugin {
    public static Main INSTANCE;
    public static Chat chat = null;

    public Main() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        File file;
        this.getLogger().info("Tprefix已启用，作者：tianwc，QQ：654864564");
        this.getLogger().info("目前版本:v1.1");
        saveDefaultConfig();
        setupChat();
        this.getServer().getPluginManager().registerEvents( new Listeners(this),  this);
        this.getCommand("hao").setExecutor( new Commands(this));
        new Souter().runTaskLater(this,10);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Tprefix disable");
    }

    @Override
    public void onLoad() {
        this.getLogger().info("Tprefix reload");
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
}

