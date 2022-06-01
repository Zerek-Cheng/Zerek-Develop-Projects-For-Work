package com._0myun.minecraft.pixelmonknockout.dao.data;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.KPlayer;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.GamesDao;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.List;

/**
 * 场次数据库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "Games", daoClass = GamesDao.class)
public class Games {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    /**
     * 地图名字
     */
    @DatabaseField(columnName = "game", dataType = DataType.STRING)
    String game;
    /**
     * 游戏状态
     */
    @DatabaseField(columnName = "stat", dataType = DataType.ENUM_STRING, defaultValue = "WAIT")
    Stat stat;
    /**
     * 玩家数量
     */
    @DatabaseField(columnName = "playeramount", dataType = DataType.INTEGER, defaultValue = "0")
    int playerAmount;
    /**
     * 幸存者数量
     */
    @DatabaseField(columnName = "alive", dataType = DataType.INTEGER, defaultValue = "0")
    int alive;
    /**
     * 游戏进度
     */
    @DatabaseField(columnName = "progress", dataType = DataType.INTEGER, defaultValue = "1")
    int progress;

    /**
     * 上一次开始时间
     */
    @DatabaseField(columnName = "startTime", dataType = DataType.STRING)
    String starTime;


    public enum Stat {
        /**
         * 被强制暂停
         */
        STOP(1),
        /**
         * 等待
         */
        WAIT(2),
        /**
         * 游戏中
         */
        PLAY(3),
        /**
         * 游戏结束
         */
        FINISH(4);

        int stat;

        Stat(int stat) {
            this.stat = stat;
        }

        @Override
        public String toString() {
            return String.valueOf(stat);
        }

        public int getStat() {
            return stat;
        }
    }

    public Game getGameConfig() {
        return R.INSTANCE.getGame(getGame());
    }

    public void broadcastGame(String msg) {
        List<PlayerData> playerData = DB.playerData.queryForGame(getId());
        for (PlayerData data : playerData) {
            new KPlayer(data.getUuid()).getPlayer().sendMessage(msg);
        }
    }

}
