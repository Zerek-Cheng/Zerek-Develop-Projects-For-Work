package com._0myun.minecraft.pixelmonknockout.dao.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.UUID;

/**
 * 禁赛数据库
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "Baned", daoClass = Baned.class)
public class Baned {
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
     * 解禁时间
     */
    @DatabaseField(columnName = "end", dataType = DataType.STRING)
    String end;

}
