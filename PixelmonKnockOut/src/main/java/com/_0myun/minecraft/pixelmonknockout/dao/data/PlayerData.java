package com._0myun.minecraft.pixelmonknockout.dao.data;

import com._0myun.minecraft.pixelmonknockout.DB;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.sql.SQLException;
import java.util.UUID;

/**
 * 玩家数据库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "PlayerData", daoClass = PlayerData.class)
public class PlayerData {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    /**
     * 玩家UUID
     */
    @DatabaseField(columnName = "uuid", dataType = DataType.UUID, unique = true)
    UUID uuid;
    /**
     * 地图名字
     */
    @DatabaseField(columnName = "game", dataType = DataType.INTEGER, defaultValue = "-1")
    int game;
    /**
     * 游戏状态
     */
    @DatabaseField(columnName = "stat", dataType = DataType.ENUM_STRING, defaultValue = "NONE")
    Stat stat;

    public enum Stat {
        /**
         * 空闲
         */
        NONE(1),
        /**
         * 被封禁
         */
        BANNED(2);
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
