package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.RedPackage;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.event.PlayerRedPackageSendEvent;
import lk.vexview.api.VexViewAPI;
import lk.vexview.hud.VexImageShow;
import lk.vexview.hud.VexTextShow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class RedPackageListener implements org.bukkit.event.Listener {
    @EventHandler
    public void onSendRedPackage(PlayerRedPackageSendEvent e) {
        DataManager.setLastWord(e.getWord());
        if (!ConfigManager.broadcast()) return;
        RedPackage red = e.getRedPackage();
        UUID owner = red.getOwner();
        String ownerName = owner != null ? Bukkit.getOfflinePlayer(owner).getName() : "后台";
        int amount = red.getAmount();
        String title = red.getTitle();
        String word = e.getWord();
        Bukkit.broadcastMessage(LangUtil.get("lang12").replace("<red.owner.name>", ownerName)
                .replace("<red.amount>", String.valueOf(amount))
                .replace("<red.title>", title)
                .replace("<word>", word));


        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        VexImageShow vBg = new VexImageShow(10000301, ConfigManager.getConfig().getString("ui.hud.img")
                , ConfigManager.getConfig().getInt("ui.hud.x")
                , ConfigManager.getConfig().getInt("ui.hud.y")
                , ConfigManager.getConfig().getInt("ui.hud.w")
                , ConfigManager.getConfig().getInt("ui.hud.h")
                , ConfigManager.getConfig().getInt("ui.hud.w")
                , ConfigManager.getConfig().getInt("ui.hud.h")
                , ConfigManager.getConfig().getInt("ui.hud.time"));
        List<String> titleLists = ConfigManager.getConfig().getStringList("ui.hud.title.str");

        players.forEach((p) -> {
            VexViewAPI.removeHUD(p, vBg.getID());
            VexViewAPI.sendHUD(p, vBg);

            titleLists.forEach((str) -> {
                int indexOf = titleLists.indexOf(str);
                titleLists.set(indexOf, LangUtil.variable(str, red));
                str = titleLists.get(indexOf);

                VexViewAPI.removeHUD(p, 10000302 + indexOf);
                VexTextShow vTitle = new VexTextShow(10000302 + indexOf, ConfigManager.getConfig().getInt("ui.hud.title.x") + ConfigManager.getConfig().getInt("ui.hud.x")
                        , ConfigManager.getConfig().getInt("ui.hud.title.y") + ConfigManager.getConfig().getInt("ui.hud.y") + 10 * indexOf
                        , Arrays.asList(str)
                        , ConfigManager.getConfig().getInt("ui.hud.time"));
                VexViewAPI.sendHUD(p, vTitle);
            });
        });
    }
}
