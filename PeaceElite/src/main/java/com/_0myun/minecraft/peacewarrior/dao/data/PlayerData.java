package com._0myun.minecraft.peacewarrior.dao.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "playerdata", daoClass = PlayerData.class)
public class PlayerData {

    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    @DatabaseField(columnName = "player", dataType = DataType.STRING, unique = true)
    String player;
    @DatabaseField(columnName = "uuid", dataType = DataType.STRING, unique = true)
    String uuid;
    @DatabaseField(columnName = "kill", dataType = DataType.INTEGER, defaultValue = "0")
    int kill;
    @DatabaseField(columnName = "death", dataType = DataType.INTEGER, defaultValue = "0")
    int death;
    @DatabaseField(columnName = "save", dataType = DataType.INTEGER, defaultValue = "0")
    int save;
    @DatabaseField(columnName = "win", dataType = DataType.INTEGER, defaultValue = "0")
    int win;
    @DatabaseField(columnName = "total", dataType = DataType.INTEGER, defaultValue = "0")
    int total;
    @DatabaseField(columnName = "score", dataType = DataType.INTEGER, defaultValue = "0")
    int score;
    /**
     * 游戏状态
     */
    @DatabaseField(columnName = "stat", dataType = DataType.ENUM_INTEGER, defaultValue = "0")
    Stat stat;
    @DatabaseField(columnName = "map", dataType = DataType.STRING)
    String map;


    public enum Stat {
        /**
         * 空闲
         */
        NONE(1),
        /**
         * 大厅等待
         */
        WAIT(2),
        /**
         * 游戏中
         */
        PLAY(3),
        /**
         * 被封禁
         */
        BANNED(4);
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
}
