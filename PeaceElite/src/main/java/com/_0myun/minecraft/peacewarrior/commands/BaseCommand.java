package com._0myun.minecraft.peacewarrior.commands;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.R;
import com._0myun.minecraft.peacewarrior.commands.team.TeamAcceptCommand;
import com._0myun.minecraft.peacewarrior.commands.team.TeamInfoCommand;
import com._0myun.minecraft.peacewarrior.commands.team.TeamInviteCommand;
import com._0myun.minecraft.peacewarrior.commands.team.TeamQuitCommand;
import com._0myun.minecraft.peacewarrior.dao.data.Battle;
import com._0myun.minecraft.peacewarrior.dao.data.PlayerData;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;

public class BaseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            R.INSTANCE.init();
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("itemstr")) {
            try {
                R.INSTANCE.getLogger().info("\r\n" + StreamSerializer.getDefault().serializeItemStack(((Player) sender).getItemInHand()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        Player p = (Player) sender;
        PlayerData playerData = null;
        Battle battle = null;

        try {
            playerData = DBManager.playerDataDao.queryForUUID(p.getUniqueId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (playerData.getStat().equals(PlayerData.Stat.NONE) || playerData.getStat().equals(PlayerData.Stat.BANNED)) {
            p.sendMessage(R.INSTANCE.lang("lang8"));
            return true;
        }
        try {
            battle = DBManager.battleDao.queryForMap(playerData.getMap(), Battle.Stat.WAIT, Battle.Stat.READY, Battle.Stat.PLAY, Battle.Stat.FINISH);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (playerData.getStat().equals(PlayerData.Stat.WAIT)){
            if (args.length >= 3 && args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("invite")) {
                new TeamInviteCommand().onCommand(sender, command, label, args);
                return true;
            }
            if (args.length >= 3 && args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("accept")) {
                new TeamAcceptCommand().onCommand(sender, command, label, args);
                return true;
            }
            if (args.length >= 2 && args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("info")) {
                new TeamInfoCommand().onCommand(sender, command, label, args);
                return true;
            }
            if (args.length >= 2 && args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("quit")) {
                new TeamQuitCommand().onCommand(sender, command, label, args);
                return true;
            }
        }
        if (battle.getStat().equals(Battle.Stat.READY) || battle.getStat().equals(Battle.Stat.PLAY)) {
            p.sendMessage(R.INSTANCE.lang("lang15"));
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("quit")) {
            new BattleQuitCommand().onCommand(sender, command, label, args);
            return true;
        }
        return true;
    }
}
