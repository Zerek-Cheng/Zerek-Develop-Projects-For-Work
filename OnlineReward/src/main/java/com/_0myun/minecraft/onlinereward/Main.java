package com._0myun.minecraft.onlinereward;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public final class Main extends JavaPlugin implements Listener {
    static Main INSTANCE;
    YamlConfiguration data = new YamlConfiguration();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        saveResource("data.yml", false);
        try {
            data.load(getDataFolder() + "/data.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(this, this);
        new Timer().runTaskTimerAsynchronously(this, 20, 20);
    }

    @Override
    public void onDisable() {
        this.saveConfig();
        try {
            this.data.save(getDataFolder() + "/data.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            p.sendMessage(String.format(getConfig().getString("lang1"), secToHMS(getTime(p.getUniqueId()))));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("top")) {
            p.sendMessage(getConfig().getString("lang2"));
            TreeMap<UUID, Integer> topList = getTopList();
            Iterator<Map.Entry<UUID, Integer>> iter = topList.entrySet().iterator();
            for (int i = 0; iter.hasNext() && i < 10; i++) {
                Map.Entry<UUID, Integer> next = iter.next();
                p.sendMessage(getConfig().getString("lang3").replace("<rank>", String.valueOf(i + 1))
                        .replace("<player>", Bukkit.getOfflinePlayer(next.getKey()).getName())
                        .replace("<time>", secToHMS(next.getValue())));
            }
        }
        return true;
    }

    public synchronized String getToday() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public synchronized void refreshData() {
        //String today = data.getString("today");
        if (data.getString("today") == null || !data.getString("today").equalsIgnoreCase(getToday())) {
            giveDailyReward();
            getLogger().log(Level.INFO, "天黑请闭眼.....数据清空中.....");
            data.getKeys(false).forEach(key -> data.set(key, null));
            data.set("today", getToday());
            saveConfig();
        }
    }

    public void giveDailyReward() {
        getLogger().log(Level.INFO, "你👨灵梦云科技来发每日奖励了！！！！！");
        TreeMap<UUID, Integer> top = getTopList();
        Iterator<Map.Entry<UUID, Integer>> iter = top.entrySet().iterator();
        if (!iter.hasNext()) return;
        Map.Entry<UUID, Integer> next = iter.next();
        getConfig().getStringList("top").forEach(command -> {
            addRunCommand(next.getKey(), command);
        });
    }

    public void addRunCommand(UUID uuid, String command) {
        List<String> commands = getRunCommand(uuid);
        commands.add(command);
        getConfig().set("daily." + uuid.toString(), commands);
    }
    public void resetRunCommand(UUID uuid) {
        getConfig().set("daily." + uuid.toString(), new ArrayList<>());
    }

    public List<String> getRunCommand(UUID uuid) {
        if (!getConfig().isSet("daily." + uuid.toString()))
            getConfig().set("daily." + uuid.toString(), new ArrayList<>());
        return getConfig().getStringList("daily." + uuid.toString());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        List<String> commands = getRunCommand(p.getUniqueId());
        if (commands == null || commands.size() <= 0) return;
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            commands.forEach(command -> p.performCommand(command.replace("<player>", p.getName())));
        } finally {
            if (!op) p.setOp(false);
        }
        resetRunCommand(p.getUniqueId());
    }

    public synchronized void addTime(UUID uuid) {
        data.set(uuid.toString() + "-online", getTime(uuid) + 1);
    }

    public int getTime(UUID uuid) {
        return data.getInt(uuid.toString() + "-online");
    }

    public boolean hasGeted(UUID uuid, String name) {
        return data.getBoolean(uuid.toString() + "-" + name + "-once");
    }

    public void setGeted(UUID uuid, String name) {
        data.set(uuid.toString() + "-" + name + "-once", true);
    }

    public int getLast(UUID uuid, String name) {
        return data.getInt(uuid.toString() + "-" + name + "-last");
    }

    public void setLast(UUID uuid, String name) {
        data.set(uuid.toString() + "-" + name + "-last", getTime(uuid));
    }


    public boolean canNext(UUID uuid, String name, int needWait) {
        int time = getTime(uuid);
        int last = getLast(uuid, name);
        return (time - last) >= needWait;
    }

    public TreeMap<UUID, Integer> getTopList() {
        HashMap<UUID, Integer> tmp = new HashMap<>();
        data.getKeys(false).forEach(key -> {
            if (!key.endsWith("-online")) return;
            UUID uuid = UUID.fromString(key.replace("-online", ""));
            tmp.put(uuid, getTime(uuid));
        });
        TreeMap<UUID, Integer> result = new TreeMap<>(new ValueComparator(tmp));
        result.putAll(tmp);

        return result;
    }

    static String secToHMS(int time) {

        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分" + unitFormat(second) + "秒";
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    class ValueComparator implements Comparator<UUID> {

        Map<UUID, Integer> base;

        public ValueComparator(HashMap<UUID, Integer> tmp) {
            this.base = tmp;
        }

        @Override
        public int compare(UUID o1, UUID o2) {
            Integer int1 = base.get(o1);
            Integer int2 = base.get(o2);
            if (int1 == null) int1 = 0;
            if (int2 == null) int2 = 0;
            if (int1 >= int2) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
