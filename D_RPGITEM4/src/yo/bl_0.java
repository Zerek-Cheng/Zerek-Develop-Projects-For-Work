/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.chat.Chat
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package yo;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import yo.bj_0;

public class bl_0
extends bj_0 {
    private static Chat b = null;

    @Override
    public void a() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if (plugin == null || !plugin.isEnabled()) {
            return;
        }
        RegisteredServiceProvider chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            b = (Chat)chatProvider.getProvider();
        }
        this.a = b != null;
    }

    public Chat d() {
        return b;
    }
}

