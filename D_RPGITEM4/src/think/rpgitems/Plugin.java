/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitTask
 */
package think.rpgitems;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import yo.ae_1;
import yo.af_0;
import yo.ag_0;
import yo.an_0;
import yo.ao_0;
import yo.aq_0;
import yo.at_0;
import yo.be_1;
import yo.bg_0;
import yo.bi_0;
import yo.bi_1;
import yo.bj_1;
import yo.bk_1;
import yo.bl_1;
import yo.bm_1;
import yo.bn_1;
import yo.bo_1;
import yo.bp_1;
import yo.bq_1;
import yo.br_1;
import yo.bs_0;
import yo.bt_0;
import yo.bu_0;
import yo.bv_1;
import yo.bw_0;
import yo.bx_1;
import yo.by_0;
import yo.by_1;
import yo.bz_0;
import yo.ca_1;
import yo.cb_1;
import yo.cc_1;
import yo.cd_0;
import yo.cd_1;
import yo.ce_1;
import yo.cf_1;
import yo.ch;
import yo.ci;
import yo.cj;
import yo.ck;
import yo.cl;
import yo.cm;
import yo.cn;
import yo.co;
import yo.cp;
import yo.cq;
import yo.cr;
import yo.cs;
import yo.ct;

public class Plugin
extends JavaPlugin {
    public static final Logger a = Logger.getLogger("RPGItems");
    public static final String b = "[RPGItems] ";
    public static Plugin c;
    private be_1 e;
    public static aq_0 d;
    private cq f = null;

    public void onLoad() {
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
        c = this;
        d = new aq_0(new File(this.getDataFolder(), "config.yml"));
        this.reloadConfig();
        an_0.a();
        this.a(bj_1.class).a(bk_1.class).a(bl_1.class).a(bm_1.class).a(bn_1.class).a(bo_1.class).a(bp_1.class).a(bq_1.class).a(br_1.class).a(bs_0.class).a(bt_0.class).a(bu_0.class).a(bv_1.class).a(bw_0.class).a(bx_1.class).a(by_1.class).a(bz_0.class).a(ca_1.class).a(cb_1.class).a(cc_1.class).a(cd_1.class).a(ce_1.class).a(cf_1.class).a(ch.class).a(ci.class).a(cj.class).a(ck.class).a(cl.class).a(cm.class).a(co.class).a(cp.class).a(cn.class).a(cr.class).a(cs.class).a(ct.class);
    }

    private Plugin a(Class<? extends bi_1> clzz) {
        try {
            bi_1.a.put(clzz.newInstance().e(), clzz);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public void onEnable() {
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
        ao_0.b(this);
        cd_0.a();
        bi_0.a();
        at_0.a();
        aq_0 conf = this.c();
        if (conf.getBoolean("localeInv", false)) {
            bg_0.a = true;
        }
        this.e = new be_1(this);
        by_0.a(this);
        ae_1.a(new af_0());
        ae_1.a(new ag_0());
        this.getCommand("rpgitem").setExecutor((CommandExecutor)this);
        this.b();
    }

    public be_1 a() {
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
        return this.e;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveConfig() {
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
        aq_0 config = this.c();
        FileOutputStream out = null;
        try {
            File f = new File(this.getDataFolder(), "config.yml");
            if (!f.exists()) {
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            out.write(config.saveToString().getBytes("UTF-8"));
        }
        catch (FileNotFoundException e2) {
        }
        catch (UnsupportedEncodingException e3) {
        }
        catch (IOException e4) {
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void reloadConfig() {
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
        d.d();
    }

    public void b() {
        try {
            if (this.f != null) {
                this.f.cancel();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.f = new cq();
        this.f.runTaskTimer((org.bukkit.plugin.Plugin)this, 0L, d.getLong("tickInterval"));
    }

    public aq_0 c() {
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
        return d;
    }

    public void onDisable() {
        Bukkit.getScheduler().cancelTasks((org.bukkit.plugin.Plugin)this);
        this.a().b();
        ae_1.a();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
        StringBuilder out = new StringBuilder();
        out.append(cmd.getName()).append(' ');
        for (String arg : args) {
            out.append(arg).append(' ');
        }
        ae_1.a(sender, out.toString());
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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
        StringBuilder out = new StringBuilder();
        out.append(command.getName()).append(' ');
        for (String arg : args) {
            out.append(arg).append(' ');
        }
        return ae_1.b(sender, out.toString());
    }

    public /* synthetic */ FileConfiguration getConfig() {
        return this.c();
    }
}

