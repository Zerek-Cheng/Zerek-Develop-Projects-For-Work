package com._0myun.minecraft.pixelmonknockout.data;

import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 锦标赛配置文件
 */
@Data
@ToString
@NoArgsConstructor
public class Game extends DataBase {

    /**
     * 名称
     */
    String name;
    /**
     * 显示名
     */
    String display;
    /**
     * 开始时间周期类型
     */
    String timeType;
    /**
     * 开始时间数据
     */
    String timeData;
    /**
     * 开始前多久可以报名
     */
    long joinStart;
    /**
     * 开始前多久结束
     */
    long joinEnd;
    /**
     * 最少参与的玩家
     */
    int minPlayer;
    /**
     * 最大参与玩家
     */
    int maxPlayer;
    /**
     * 规则
     */
    String rules;
    /**
     * 禁止的神奇宝贝名字
     */
    List<String> pokemonBan;
    /**
     * 赛制
     */
    String gameMode;
    /**
     * 随机模式时候开启
     */
    boolean randomMode;
    /**
     * 随机神奇宝贝NBT数据
     */
    List<String> randomPokemon;
    /**
     * 胜利运行的命令
     */
    List<String> cmdWin;
    /**
     * 失败运行的命令
     */
    List<String> cmdFail;
    /**
     * 最终奖励命令
     */
    List<Map> cmdReward;
    /**
     * 每回合等待时间
     */
    long roundWait;
    long roundMaxTime;
    /**
     * 累计战绩建立
     */
    List<Map> reward;

    public Game(Map<String, Object> args) {
        super(args);
    }

    public String getNextStartTime(String last) throws Exception {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//2019年6月7日 20:00分
        Date lastDate = last == null ? format.parse(this.getTimeData()) : format.parse(last);
        cal.setTime(lastDate);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if (getTimeType().equalsIgnoreCase("week")) {
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(new Date());
            Date lastTime = cal.getTime();

            cal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), nowCal.get(Calendar.DAY_OF_MONTH)
                    , lastTime.getHours(), lastTime.getMinutes(), lastTime.getSeconds());
            cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

            format.format(cal.getTime());
            while (cal.before(nowCal))
                cal.add(Calendar.WEEK_OF_MONTH, 1);
            cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            format.format(cal.getTime());
            return format.format(cal.getTime());
        }
        if (getTimeType().equalsIgnoreCase("day")) {
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(new Date());
            Date lastTime = cal.getTime();

            cal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), nowCal.get(Calendar.DAY_OF_MONTH)
                    , lastTime.getHours(), lastTime.getMinutes(), lastTime.getSeconds());
            format.format(cal.getTime());
            while (cal.before(nowCal))
                cal.add(Calendar.DAY_OF_MONTH, 1);
            format.format(cal.getTime());
            return format.format(cal.getTime());
        }
        if (getTimeType().equalsIgnoreCase("static")) {
            return last == null ? getTimeData() : last;
        }
        return null;
    }

    public BattleRules getRules() {
        return new BattleRules(this.rules);
    }

    public List<String> getReward(int rank) {
        for (Map reward : this.getCmdReward()) {
            int min = (int) reward.get("min");
            int max = (int) reward.get("max");
            List<String> cmds = (List<String>) reward.get("cmd");
            if (rank >= min && rank <= max) return cmds;
        }
        return null;
    }

    public Map getRankAmountReward(String type, int amount) {
        for (Map reward : this.getReward()) {
            if (!String.valueOf(reward.get("type")).equalsIgnoreCase(type)) continue;
            if (Integer.valueOf(String.valueOf(reward.get("data"))) != amount) continue;
            return reward;
        }
        return null;
    }

}
