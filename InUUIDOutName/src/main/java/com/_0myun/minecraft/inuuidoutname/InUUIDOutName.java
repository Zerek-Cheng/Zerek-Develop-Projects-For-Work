package com._0myun.minecraft.inuuidoutname;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public final class InUUIDOutName extends JavaPlugin {

    @Override
    public void onEnable() {
        new BukkitRunnable() {

            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    getLogger().warning("定制插件找灵梦云科技");
                }
            }
        }.runTaskLater(this, 10);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("/iuon <name>");
            return true;
        }
        String raw = "[\"\",{\"text\":\"点击自动输入\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"{uuid}\"},\"insertion\":\"点击自动输入\"}]";
        File json = new File(this.getDataFolder().getAbsoluteFile().getParentFile().getParentFile().getPath() + "/usernamecache.json");
        final String[] uuid = {"你是sb么"};
        try {
            FileInputStream is = new FileInputStream(json);
            InputStreamReader reader = new InputStreamReader(is);
            JsonElement jsonElement = new JsonParser().parse(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            jsonObject.entrySet().forEach(e -> {
                String key = e.getKey();
                JsonElement value = e.getValue();
                if (value.getAsString().equalsIgnoreCase(args[0])) uuid[0] = key;
            });
            reader.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            //java.util.UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
            //String uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + args[0]).getBytes(Charsets.UTF_8)).toString();
            sender.sendMessage(uuid[0]);
            raw = raw.replace("{uuid}", uuid[0]);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + raw);
            //     sender.sendMessage(Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
        } catch (Exception ex) {
            sender.sendMessage("输错了");
        }
        return true;
    }
}
