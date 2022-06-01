package com._0myun.minecraft.pixelmonknockout.commands;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.GameUtils;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import com._0myun.minecraft.pixelmonknockout.task.game.GameRunner;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainCommand extends BaseCommand {

    @SubCommand(label = "test")
    public void test(Player p, List<String> args) {
        p.sendMessage("测试");
    }

    @SubCommand(label = "join", args = 1)
    public void join(Player p, List<String> args) throws SQLException, ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//2019年6月7日 20:00分
        String gameName = args.get(0);
        Games game = DB.games.queryForGame(gameName);
        if (game == null) {//场次未初始化
            p.sendMessage(R.INSTANCE.lang("lang3"));
            return;
        }
        if (!game.getStat().equals(Games.Stat.WAIT)) {
            p.sendMessage(R.INSTANCE.lang("lang12"));
            return;
        }
        Game config = game.getGameConfig();
        String starTime = game.getStarTime();
        long time = format.parse(starTime).getTime();
        long joinStart = config.getJoinStart();
        long joinEnd = config.getJoinEnd();
/*
        if (new Date().before(new Date(time - (joinStart * 1000)))) {//在开始前
            p.sendMessage(R.INSTANCE.lang("lang27"));
            return;
        }
        if (new Date().after(new Date(time - (joinEnd * 1000)))) {//在结束前
            p.sendMessage(R.INSTANCE.lang("lang28"));
            return;
        }
*/


        GameUtils.join(p, gameName);
    }

    @SubCommand(label = "leave")
    public void leave(Player p, List<String> args) throws SQLException {
        GameUtils.leave(p);
    }

    @SubCommand(label = "ready")
    public void ready(Player p, List<String> args) throws SQLException {
        PlayerData playerData = DB.playerData.queryForUUID(p.getUniqueId());
        if (playerData.getGame() == -1 || !playerData.getGames().getStat().equals(Games.Stat.PLAY)) {
            p.sendMessage(R.INSTANCE.lang("lang15"));
            return;
        }
        GameRunner runner = R.INSTANCE.getRunners().get(playerData.getGame());
        runner.getRound().ready(p.getUniqueId());
        p.sendMessage(R.INSTANCE.lang("lang16"));
    }

}
