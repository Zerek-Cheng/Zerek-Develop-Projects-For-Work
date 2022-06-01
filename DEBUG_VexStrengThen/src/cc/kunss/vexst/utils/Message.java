/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.configuration.file.YamlConfiguration;

public class Message {
    private static File f = new File(Main.getMain().getDataFolder(), "Message.yml");
    private static YamlConfiguration data = YamlConfiguration.loadConfiguration((File)f);

    public static String getMessageString(String Path2) {
        return data.getString(Path2).replace("&", "\u00a7");
    }

    public static int getMessageInt(String Path2) {
        return data.getInt(Path2);
    }

    public static List<String> getMessageList(String Path2) {
        ArrayList<String> ss = new ArrayList<String>();
        data.getStringList(Path2).forEach(v -> ss.add(v.replace("&", "\u00a7")));
        return ss;
    }

    public static void saveMessage() {
        try {
            data.save(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDefauleMessage() {
        if (!f.exists()) {
            Main.getMain().saveResource(f.getName(), false);
        }
        data = YamlConfiguration.loadConfiguration((File)f);
    }

    public static void reloadMessage() {
        Message.saveMessage();
        data = YamlConfiguration.loadConfiguration((File)f);
    }

    public static YamlConfiguration getData() {
        return data;
    }
}

