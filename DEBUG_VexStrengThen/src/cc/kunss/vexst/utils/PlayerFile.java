/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.Main;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PlayerFile {
    private static File PlayerData = new File(Main.getMain().getDataFolder(), "PlayerData");

    public static boolean isPlayer(Player p) {
        File f;
        if (!PlayerData.exists()) {
            PlayerData.mkdirs();
        }
        if (!(f = new File(PlayerData, p.getUniqueId() + ".yml")).exists()) {
            return false;
        }
        return true;
    }

    public static void CreatePlayerFile(Player p) {
        File f = new File(PlayerData, p.getUniqueId() + ".yml");
        try {
            f.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        YamlConfiguration data = YamlConfiguration.loadConfiguration((File)f);
        data.set("Level", (Object)0);
        data.set("Exp", (Object)0.0);
        try {
            data.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getPlayetData(Player p) {
        File f = new File(PlayerData, p.getUniqueId() + ".yml");
        return f;
    }
}

