/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.permission.Permission
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 */
package yo;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import yo.bj_0;

public class bn_0
extends bj_0 {
    private static Permission b = null;

    @Override
    public void a() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        if (plugin == null || !plugin.isEnabled()) {
            return;
        }
        RegisteredServiceProvider permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            b = (Permission)permissionProvider.getProvider();
        }
        this.a = b != null;
    }

    public Permission d() {
        return b;
    }
}

