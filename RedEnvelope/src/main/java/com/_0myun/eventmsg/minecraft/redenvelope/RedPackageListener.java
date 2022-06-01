package com._0myun.eventmsg.minecraft.redenvelope;

import com._0myun.eventmsg.minecraft.redenvelope.bin.RedPackage;
import com._0myun.eventmsg.minecraft.redenvelope.event.PlayerRedPackageSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

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
        String tellraw = "tellraw @p [\"\",{\"text\":\"点击领取红包[%s]\",\"color\":\"red\",\"bold\":true,\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/red get %s\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"点击领取红包\"}]}}}]";
        tellraw = String.format(tellraw, word, word);
        String finalTellraw = tellraw;
        Bukkit.getOnlinePlayers().forEach(p -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalTellraw.replace("@p", p.getName())));
    }
}
