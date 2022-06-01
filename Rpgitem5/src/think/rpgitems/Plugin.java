// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.command.CommandExecutor;
import yo.aG;
import yo.aD;
import yo.aE;
import yo.aF;
import yo.by;
import yo.bG;
import yo.aT;
import yo.bi;
import yo.cD;
import yo.aO;
import java.io.Closeable;
import yo.ct;
import yo.cs;
import yo.cr;
import yo.cn;
import yo.cp;
import yo.co;
import yo.cm;
import yo.cl;
import yo.ck;
import yo.cj;
import yo.ci;
import yo.ch;
import yo.cf;
import yo.ce;
import yo.cd;
import yo.cc;
import yo.cb;
import yo.ca;
import yo.bZ;
import yo.bY;
import yo.bX;
import yo.bW;
import yo.bV;
import yo.bU;
import yo.bT;
import yo.bS;
import yo.bR;
import yo.bQ;
import yo.bP;
import yo.bO;
import yo.bN;
import yo.bM;
import yo.bL;
import yo.bK;
import yo.bI;
import yo.bJ;
import yo.aN;
import java.io.File;
import yo.cq;
import yo.aQ;
import yo.bE;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin
{
    public static final Logger a;
    public static final String b = "[RPGItems] ";
    public static Plugin c;
    private bE e;
    public static aQ d;
    private cq f;
    
    public Plugin() {
        this.f = null;
    }
    
    public void onLoad() {
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
        Plugin.c = this;
        Plugin.d = new aQ(new File(this.getDataFolder(), "config.yml"));
        this.reloadConfig();
        aN.a();
        this.a(bJ.class).a(bK.class).a(bL.class).a(bM.class).a(bN.class).a(bO.class).a(bP.class).a(bQ.class).a(bR.class).a(bS.class).a(bT.class).a(bU.class).a(bV.class).a(bW.class).a(bX.class).a(bY.class).a(bZ.class).a(ca.class).a(cb.class).a(cc.class).a(cd.class).a(ce.class).a(cf.class).a(ch.class).a(ci.class).a(cj.class).a(ck.class).a(cl.class).a(cm.class).a(co.class).a(cp.class).a(cn.class).a(cr.class).a(cs.class).a(ct.class);
    }
    
    private Plugin a(final Class<? extends bI> clzz) {
        try {
            bI.a.put(((bI)clzz.newInstance()).e(), clzz);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }
    
    public void onEnable() {
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
        aO.b(this);
        cD.a();
        bi.a();
        aT.a();
        final ConfigurationSection conf = (ConfigurationSection)this.c();
        if (conf.getBoolean("localeInv", false)) {
            bG.a = true;
        }
        this.e = new bE(this);
        by.a(this);
        aE.a(new aF());
        aE.a(new aG());
        this.getCommand("rpgitem").setExecutor((CommandExecutor)this);
        this.b();
    }
    
    public bE a() {
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
        return this.e;
    }
    
    public void saveConfig() {
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
        final FileConfiguration config = (FileConfiguration)this.c();
        FileOutputStream out = null;
        try {
            final File f = new File(this.getDataFolder(), "config.yml");
            if (!f.exists()) {
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            out.write(config.saveToString().getBytes("UTF-8"));
        }
        catch (FileNotFoundException ex2) {}
        catch (UnsupportedEncodingException ex3) {}
        catch (IOException ex4) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void reloadConfig() {
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
        Plugin.d.d();
    }
    
    public void b() {
        try {
            if (this.f != null) {
                this.f.cancel();
            }
        }
        catch (Exception ex) {}
        (this.f = new cq()).runTaskTimer((org.bukkit.plugin.Plugin)this, 0L, Plugin.d.getLong("tickInterval"));
    }
    
    public aQ c() {
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
        return Plugin.d;
    }
    
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks((org.bukkit.plugin.Plugin)this);
        this.a().b();
        aE.a();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
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
        final StringBuilder out = new StringBuilder();
        out.append(cmd.getName()).append(' ');
        for (final String arg : args) {
            out.append(arg).append(' ');
        }
        aE.a(sender, out.toString());
        return true;
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
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
        final StringBuilder out = new StringBuilder();
        out.append(command.getName()).append(' ');
        for (final String arg : args) {
            out.append(arg).append(' ');
        }
        return aE.b(sender, out.toString());
    }
    
    static {
        a = Logger.getLogger("RPGItems");
    }
}
