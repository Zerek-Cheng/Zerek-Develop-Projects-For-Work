/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.EntityType
 *  org.bukkit.event.Listener
 */
package clear;

import clear.Main;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;

public class Names
implements Listener {
    private Main main;
    private static HashMap<Integer, String> entityHash;

    public Names(Main main) {
        this.main = main;
    }

    public static String getEntityName(int id) {
        try {
            String result = entityHash.get(id);
            if (result != null) {
                return result;
            }
            result = EntityType.fromId((int)id).name();
            if (result == null) {
                result = "";
            }
            return result;
        }
        catch (Exception e) {
            return "";
        }
    }

    public void loadConfig() {
        try {
            YamlConfiguration namesConfig = Main.loadConfigByUTF8(new File(String.valueOf(this.main.getPluginPath()) + File.separator + this.main.getPn() + File.separator + "names.yml"));
            if (namesConfig != null) {
                entityHash = new HashMap();
                for (String s : namesConfig.getStringList("names.entity")) {
                    int id = Integer.parseInt(s.split(" ")[0]);
                    String name = s.split(" ")[1];
                    entityHash.put(id, name);
                }
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

