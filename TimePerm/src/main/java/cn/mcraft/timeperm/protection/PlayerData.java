/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 */
package cn.mcraft.timeperm.protection;

import cn.mcraft.timeperm.TimePerm;
import cn.mcraft.timeperm.utils.RConfig;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerData
        extends RConfig {
    private final String playerName;
    private final HashMap<String, Long> perms = new HashMap();

    public PlayerData(String playerName) {
        this(new File(TimePerm.getInstance().getDataFolder(), "data" + File.separator + playerName.toLowerCase() + ".yml"));
    }

    public PlayerData(File file) {
        super(file);

        this.playerName = file.getName().split("\\.")[0].toLowerCase();
        this.load();
    }

    @Override
    public RConfig load() {
        super.load();
        ConfigurationSection con = this.getConfigurationSection("data");
        if (con != null) {
            boolean modify = false;
            for (String node : con.getKeys(false)) {
                long time = con.getLong(node + ".time");
                node = node.replace("@", ".");
                insertTimePerm(node, (time - System.currentTimeMillis()) );
                //System.out.println(node);
                if (time <= System.currentTimeMillis()) {
                    //con.set(node, null);
                    //TimePerm.removePerm(this.playerName, node);
                    //modify = true;
                    continue;
                }
                this.perms.put(node, time);
                TimePerm.addPerm(this.playerName, node);
            }
            if (modify) {
                this.forceSave();
            }
        }
        return this;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void insertTimePerm(String node, long timeAfter) {
        this.insertTimePerm(node, timeAfter, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void insertTimePerm(String node, long timeAfter, boolean saveData) {
        long time = System.currentTimeMillis() + timeAfter;
        this.perms.put(node, time);
        TimePerm.addPerm(this.playerName, node);
        if (saveData) {
            this.set("data." + node.replace(".", "@") + ".time", time);
            this.forceSave();
        }
    }

    public void checkTimePerm(String node) {
        this.checkTimePerm(node, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void checkTimePerm(String node, boolean removeData) {
        Long time = this.perms.get(node);
        if (time != null && time <= System.currentTimeMillis()) {
            this.perms.remove(node);
            if (removeData) {
                this.set("data." + node.replace(".", "@"), null);
                this.forceSave();
                TimePerm.removePerm(this.playerName, node);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void checkAllTimePerms(boolean removeData) {
        Iterator<Map.Entry<String, Long>> it = this.perms.entrySet().iterator();
        boolean modify = false;
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            String node = entry.getKey();
            long time = entry.getValue();
            if (time > System.currentTimeMillis()) continue;
            System.out.println(node + " timeout! remove!");
            it.remove();
            if (!removeData) continue;
            modify=true;
            TimePerm.removePerm(this.playerName, node);
            this.set("data." + node.replace(".", "@"), null);
        }
        if (modify) {
            this.forceSave();
        }
    }
}

