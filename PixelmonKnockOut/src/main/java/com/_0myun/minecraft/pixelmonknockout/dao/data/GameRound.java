package com._0myun.minecraft.pixelmonknockout.dao.data;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.dao.GamesDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.sql.SQLException;
import java.util.UUID;

/**
 * 回合数据库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "GameRound", daoClass = GamesDao.class)
public class GameRound {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    /**
     * 地图名字
     */
    @DatabaseField(columnName = "game", dataType = DataType.INTEGER, uniqueCombo = true)
    int game;
    /**
     * 游戏状态
     */
    @DatabaseField(columnName = "stat", dataType = DataType.ENUM_STRING, defaultValue = "WAIT")
    Stat stat;
    /**
     * 玩家1
     */
    @DatabaseField(columnName = "player1", dataType = DataType.UUID, uniqueCombo = true)
    UUID player1;
    /**
     * 玩家2
     */
    @DatabaseField(columnName = "player1", dataType = DataType.UUID, uniqueCombo = true)
    UUID player2;
    /**
     * 胜利者
     */
    @DatabaseField(columnName = "player1", dataType = DataType.UUID, uniqueCombo = true)
    UUID winner;


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


    public Games getGames() throws SQLException {
        return DB.games.queryForId(this.getGame());
    }

}
