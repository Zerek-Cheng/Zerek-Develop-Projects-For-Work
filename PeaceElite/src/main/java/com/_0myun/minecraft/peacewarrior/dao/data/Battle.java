package com._0myun.minecraft.peacewarrior.dao.data;

import com._0myun.minecraft.peacewarrior.dao.BattleDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "battle", daoClass = BattleDao.class)
public class Battle {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    /**
     * 地图名字
     */
    @DatabaseField(columnName = "map", dataType = DataType.STRING)
    String map;
    /**
     * 游戏状态
     */
    @DatabaseField(columnName = "stat", dataType = DataType.ENUM_INTEGER, defaultValue = "1")
    Stat stat;
    /**
     * 玩家数量
     */
    @DatabaseField(columnName = "player_amount", dataType = DataType.INTEGER, defaultValue = "0")
    int player_amount;
    /**
     * 幸存者数量
     */
    @DatabaseField(columnName = "alive", dataType = DataType.INTEGER, defaultValue = "0")
    int alive;
    /**
     * 游戏进度
     */
    @DatabaseField(columnName = "progress", dataType = DataType.INTEGER, defaultValue = "0")
    int progress;

    public enum Stat {
        /**
         * 被强制停止(管理员控制/蹦服重开)
         */
        STOP(1),
        /**
         * 大厅等待
         */
        WAIT(2),
        /**
         * 人数足够等待开始
         */
        READY(3),
        /**
         * 游戏中
         */
        PLAY(4),
        /**
         * 游戏结束
         */
        FINISH(5);

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
