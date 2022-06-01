package com.WeiBoss.bossshoptr.Commands;

import com.WeiBoss.bossshoptr.Database.SQLManager;
import com.WeiBoss.bossshoptr.GUI.MainGUI;
import com.WeiBoss.bossshoptr.Main;
import com.WeiBoss.bossshoptr.Util.WeiUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class ReloadCommand extends WeiCommand {
    private static Main plugin = Main.instance;
    @Override
    public void perform(CommandSender weiCommandSender, String[] weiStrings) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("bossshoptr.admin")) return;
            if (p.getOpenInventory() != null && p.getOpenInventory().getType() == InventoryType.CHEST && p.getOpenInventory().getTitle().equals(WeiUtil.onReplace(plugin.config.getString("MainGUI")))) {
                p.closeInventory();
            }
        }
        plugin.shop.clear();
        plugin.itemWhite.clear();
        plugin.logList.clear();
        plugin.pages.clear();
        plugin.sqlBool = false;
        plugin.createFile();
        plugin.loadFile();
        SQLManager.createTable();
        SQLManager.readAllLog();
        plugin.mainGUI = new MainGUI(plugin);
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "bossshoptr.admin";
    }
}
