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
@DatabaseTable(tableName = "PlayerGameData", daoClass = PlayerGameData.class)
public class PlayerGameData {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    /**
     * 玩家UUID
     */
    @DatabaseField(columnName = "uuid", dataType = DataType.UUID, uniqueCombo = true)
    UUID uuid;
    /**
     * 地图名字
     */
    @DatabaseField(columnName = "game", dataType = DataType.STRING, uniqueCombo = true)
    String game;
    /**
     * 参加总数
     */
    @DatabaseField(columnName = "total", dataType = DataType.INTEGER, defaultValue = "0")
    int total;
    /**
     * 冠军
     */
    @DatabaseField(columnName = "win", dataType = DataType.INTEGER, defaultValue = "0")
    int first;
    /**
     * 亚军
     */
    @DatabaseField(columnName = "second", dataType = DataType.INTEGER, defaultValue = "0")
    int second;
    /**
     * 季军
     */
    @DatabaseField(columnName = "third", dataType = DataType.INTEGER, defaultValue = "0")
    int third;

    public Games getGames() throws SQLException {
        return DB.games.queryForGame(getGame());
    }
}
