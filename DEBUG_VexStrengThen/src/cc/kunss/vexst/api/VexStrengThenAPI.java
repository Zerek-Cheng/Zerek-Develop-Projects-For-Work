/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package cc.kunss.vexst.api;

import cc.kunss.vexst.event.StrengExpChangeEvent;
import cc.kunss.vexst.utils.Methods;
import cc.kunss.vexst.utils.PlayerFile;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;

public class VexStrengThenAPI {
    public static Object getPlayerData(Player p, String Path) {
        File f = PlayerFile.getPlayetData(p);
        YamlConfiguration data = YamlConfiguration.loadConfiguration((File)f);
        return data.get(Path);
    }

    public static void setPlayerData(Player p, String Path, Object vaule) {
        File f = PlayerFile.getPlayetData(p);
        YamlConfiguration data = YamlConfiguration.loadConfiguration((File)f);
        data.set(Path, vaule);
        try {
            data.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerStrengLevel(Player p, int vaule) {
        VexStrengThenAPI.setPlayerData(p, "Level", vaule);
    }

    public static void setPlayerStrengExp(Player p, double vaule) {
        StrengExpChangeEvent expChangeEvent = new StrengExpChangeEvent(p);
        if (expChangeEvent.isCancelled()) {
            return;
        }
        VexStrengThenAPI.setPlayerData(p, "Exp", vaule);
        Bukkit.getPluginManager().callEvent((Event)new StrengExpChangeEvent(p));
    }

    public static void setTPlayerStrengPrefix(Player p, String vaule) {
        VexStrengThenAPI.setPlayerData(p, "Prefix", vaule);
    }

    public static int getPlayerStrengLevel(Player p) {
        return (Integer)VexStrengThenAPI.getPlayerData(p, "Level");
    }

    public static void takePlayerExp(Player p, double vaule) {
        VexStrengThenAPI.setPlayerStrengExp(p, VexStrengThenAPI.getPlayerStrengExp(p) - vaule);
    }

    public static void givePlayerExp(Player p, double vaule) {
        VexStrengThenAPI.setPlayerStrengExp(p, VexStrengThenAPI.getPlayerStrengExp(p) + vaule);
    }

    public static double getPlayerStrengExp(Player p) {
        return (Double)VexStrengThenAPI.getPlayerData(p, "Exp");
    }

    public static String getPlayerStrengPrefix(Player p) {
        return Methods.getStrengLevelMap().get(VexStrengThenAPI.getPlayerStrengLevel(p)).getPrefix();
    }

    public static String getStrengPrefix(int i) {
        return Methods.getStrengLevelMap().get(i).getPrefix();
    }

    public static double getPlayerStringMaxExp(Player p) {
        return Methods.getStrengLevelMap().get(VexStrengThenAPI.getPlayerStrengLevel(p)).getExp();
    }
}

