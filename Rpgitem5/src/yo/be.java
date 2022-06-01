// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.command.PluginCommand;
import think.rpgitems.item.RPGItem;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import java.io.Closeable;
import think.rpgitems.Plugin;

public class bE
{
    private bF a;
    private bG b;
    private bH c;
    private a d;
    private b e;
    private Plugin f;
    
    public bE(final Plugin plugin) {
        this.d = null;
        this.e = null;
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        this.f = plugin;
        bF.a();
        this.a = new bF();
        this.b = new bG();
        this.c = new bH();
        if (bg.a().a("1.8") && plugin.c().getBoolean("showItemNameOnDrops")) {
            this.d = new a();
        }
        if (plugin.c().getBoolean("preventEssentialsHat")) {
            this.e = new b();
        }
        this.a();
    }
    
    public void a() {
        try {
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
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
            final Closeable asdhqjefhusfer = null;
            final Throwable t = null;
            if (asdhqjefhusfer != null) {
                if (t != null) {
                    try {
                        asdhqjefhusfer.close();
                    }
                    catch (Throwable x2) {
                        t.addSuppressed(x2);
                    }
                }
                else {
                    asdhqjefhusfer.close();
                }
            }
        }
        catch (Exception ex) {}
        HandlerList.unregisterAll((org.bukkit.plugin.Plugin)this.f);
        this.b = null;
        this.a = null;
        this.c = null;
        this.d = null;
        this.e = null;
    }
    
    static class b implements Listener
    {
        @EventHandler(ignoreCancelled = true)
        private void a(final PlayerCommandPreprocessEvent e) {
            try {
                final Closeable asdhqjefhusfer = null;
                final Throwable t = null;
                if (asdhqjefhusfer != null) {
                    if (t != null) {
                        try {
                            asdhqjefhusfer.close();
                        }
                        catch (Throwable x2) {
                            t.addSuppressed(x2);
                        }
                    }
                    else {
                        asdhqjefhusfer.close();
                    }
                }
            }
            catch (Exception ex) {}
            final RPGItem ritem = by.a(e.getPlayer().getItemInHand());
            if (ritem != null) {
                final String cmd = e.getMessage().substring(1).split(" ")[0];
                final PluginCommand plcmd = Bukkit.getPluginCommand(cmd);
                if (plcmd != null && plcmd.getName().equalsIgnoreCase("hat") && plcmd.getPlugin().getName().equalsIgnoreCase("Essentials")) {
                    e.setCancelled(true);
                }
            }
        }
    }
    
    static class a implements Listener
    {
        @EventHandler(ignoreCancelled = true)
        private void a(final ItemSpawnEvent e) {
            try {
                final Closeable asdhqjefhusfer = null;
                final Throwable t = null;
                if (asdhqjefhusfer != null) {
                    if (t != null) {
                        try {
                            asdhqjefhusfer.close();
                        }
                        catch (Throwable x2) {
                            t.addSuppressed(x2);
                        }
                    }
                    else {
                        asdhqjefhusfer.close();
                    }
                }
            }
            catch (Exception ex) {}
            final RPGItem ritem = by.a(e.getEntity().getItemStack());
            if (ritem != null) {
                e.getEntity().setCustomName("RPGItem - " + ritem.getDisplay());
                e.getEntity().setCustomNameVisible(true);
            }
        }
        
        @EventHandler(ignoreCancelled = true)
        private void a(final PlayerDropItemEvent e) {
            try {
                final Closeable asdhqjefhusfer = null;
                final Throwable t = null;
                if (asdhqjefhusfer != null) {
                    if (t != null) {
                        try {
                            asdhqjefhusfer.close();
                        }
                        catch (Throwable x2) {
                            t.addSuppressed(x2);
                        }
                    }
                    else {
                        asdhqjefhusfer.close();
                    }
                }
            }
            catch (Exception ex) {}
            final RPGItem ritem = by.a(e.getItemDrop().getItemStack());
            if (ritem != null) {
                e.getItemDrop().setCustomName("RPGItem - " + ritem.getDisplay());
                e.getItemDrop().setCustomNameVisible(true);
            }
        }
    }
}
