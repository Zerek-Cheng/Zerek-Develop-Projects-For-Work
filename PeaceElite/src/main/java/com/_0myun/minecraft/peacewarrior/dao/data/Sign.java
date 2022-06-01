package com._0myun.minecraft.peacewarrior.dao.data;

import com._0myun.minecraft.peacewarrior.dao.SignDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "sign", daoClass = SignDao.class)
public class Sign {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    @DatabaseField(columnName = "world", dataType = DataType.STRING)
    String world;
    @DatabaseField(columnName = "x", dataType = DataType.INTEGER)
    int x;
    @DatabaseField(columnName = "y", dataType = DataType.INTEGER)
    int y;
    @DatabaseField(columnName = "z", dataType = DataType.INTEGER)
    int z;
    @DatabaseField(columnName = "map", dataType = DataType.STRING)
    String map;
}
