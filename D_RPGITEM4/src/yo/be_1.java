/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Server
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.HandlerList
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package yo;

import java.io.Closeable;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import think.rpgitems.Plugin;
import think.rpgitems.item.RPGItem;
import yo.aq_0;
import yo.bf_1;
import yo.bg_0;
import yo.bg_1;
import yo.bh_1;
import yo.by_0;

public class be_1 {
    private bf_1 a;
    private bg_0 b;
    private bh_1 c;
    private a d = null;
    private b e = null;
    private Plugin f;

    public be_1(Plugin plugin) {
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
        this.f = plugin;
        bf_1.a();
        this.a = new bf_1();
        this.b = new bg_0();
        this.c = new bh_1();
        if (bg_1.a().a("1.8") && plugin.c().getBoolean("showItemNameOnDrops")) {
            this.d = new a();
        }
        if (plugin.c().getBoolean("preventEssentialsHat")) {
            this.e = new b();
        }
        this.a();
    }

    public void a() {
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
        this.f.getServer().getPluginManager().registerEvents((Listener)this.a, (org.bukkit.plugin.Plugin)this.f);
        this.f.getServer().getPluginManager().registerEvents((Listener)this.b, (org.bukkit.plugin.Plugin)this.f);
        this.f.getServer().getPluginManager().registerEvents((Listener)this.c, (org.bukkit.plugin.Plugin)this.f);
        if (this.d != null) {
            this.f.getServer().getPluginManager().registerEvents((Listener)this.d, (org.bukkit.plugin.Plugin)this.f);
        }
        if (this.e != null) {
            this.f.getServer().getPluginManager().registerEvents((Listener)this.e, (org.bukkit.plugin.Plugin)this.f);
        }
    }

    public void b() {
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
        HandlerList.unregisterAll((org.bukkit.plugin.Plugin)this.f);
        this.b = null;
        this.a = null;
        this.c = null;
        this.d = null;
        this.e = null;
    }

    static class a
    implements Listener {
        private a() {
        }

        @EventHandler(ignoreCancelled=true)
        private void a(ItemSpawnEvent e2) {
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
            RPGItem ritem = by_0.a(e2.getEntity().getItemStack());
            if (ritem != null) {
                e2.getEntity().setCustomName("RPGItem - " + ritem.getDisplay());
                e2.getEntity().setCustomNameVisible(true);
            }
        }

        @EventHandler(ignoreCancelled=true)
        private void a(PlayerDropItemEvent e2) {
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
            RPGItem ritem = by_0.a(e2.getItemDrop().getItemStack());
            if (ritem != null) {
                e2.getItemDrop().setCustomName("RPGItem - " + ritem.getDisplay());
                e2.getItemDrop().setCustomNameVisible(true);
            }
        }
    }

    static class b
    implements Listener {
        private b() {
        }

        @EventHandler(ignoreCancelled=true)
        private void a(PlayerCommandPreprocessEvent e2) {
            PluginCommand plcmd;
            String cmd;
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
            RPGItem ritem = by_0.a(e2.getPlayer().getItemInHand());
            if (ritem != null && (plcmd = Bukkit.getPluginCommand((String)(cmd = e2.getMessage().substring(1).split(" ")[0]))) != null && plcmd.getName().equalsIgnoreCase("hat") && plcmd.getPlugin().getName().equalsIgnoreCase("Essentials")) {
                e2.setCancelled(true);
            }
        }
    }

}

