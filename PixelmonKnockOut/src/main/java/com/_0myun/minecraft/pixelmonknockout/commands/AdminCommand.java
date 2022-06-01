package com._0myun.minecraft.pixelmonknockout.commands;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.GameUtils;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.Baned;
import com._0myun.minecraft.pixelmonknockout.dao.data.Games;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminCommand extends BaseCommand {

    @SubCommand(label = "test", permission = "pixelmonknockout.admin.test")
    public void test(Player p, List<String> args) {
        p.sendMessage("测试");
    }

    @SubCommand(label = "block", args = 3, permission = "pixelmonknockout.admin.block")
    public void block(Player p, List<String> args) throws SQLException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//2019年6月7日 20:00分
        String player = args.get(0);
        String gameName = args.get(1);
        int hour = Integer.valueOf(args.get(2));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, hour);
        Baned baned = new Baned();
        baned.setUuid(Bukkit.getOfflinePlayer(player).getUniqueId());
        baned.setGame(gameName);
        baned.setEnd(format.format(cal.getTime()));
        DB.baned.createOrUpdate(baned);

        p.sendMessage(String.format(R.INSTANCE.lang("lang11"), baned.getEnd()));
    }

    @SubCommand(label = "fjoin", args = 2, permission = "pixelmonknockout.admin.fjoin")
    public void fjoin(Player p, List<String> args) {
        try {
            String player = args.get(0);
            String gameName = args.get(1);
            GameUtils.join(Bukkit.getPlayer(player), gameName);
            p.sendMessage(R.INSTANCE.lang("lang13"));
        } catch (Exception ex) {
            p.sendMessage(R.INSTANCE.lang("lang13"));
        }
    }

    @SubCommand(label = "fleave", args = 1, permission = "pixelmonknockout.admin.fleave")
    public void fleave(Player p, List<String> args) {
        try {
            String player = args.get(0);
            GameUtils.leave(Bukkit.getPlayer(player));
            p.sendMessage(R.INSTANCE.lang("lang13"));
        } catch (Exception ex) {
            p.sendMessage(R.INSTANCE.lang("lang13"));
        }
    }

    @SubCommand(label = "begin", args = 1, permission = "pixelmonknockout.admin.begin")
    public void begin(Player p, List<String> args) {
        try {
            String game = args.get(0);
            Games games = DB.games.queryForGame(game, Games.Stat.WAIT).get(0);
            GameUtils.start(games.getId());
            p.sendMessage(R.INSTANCE.lang("lang13"));
        } catch (Exception ex) {
            p.sendMessage(R.INSTANCE.lang("lang13"));
        }
    }

    @SubCommand(label = "sign", args = 1, permission = "pixelmonknockout.admin.sign")
    public void sign(Player p, List<String> args) {
        try {
            String gameName = args.get(0);
            Game game = R.INSTANCE.getGame(gameName);
            game.setJoinStart(Long.MAX_VALUE);
            p.sendMessage(R.INSTANCE.lang("lang13"));
        } catch (Exception ex) {
            p.sendMessage(R.INSTANCE.lang("lang13"));
        }
    }
}
