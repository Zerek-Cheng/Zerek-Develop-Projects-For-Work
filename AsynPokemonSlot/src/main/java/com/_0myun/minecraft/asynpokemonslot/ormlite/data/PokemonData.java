package com._0myun.minecraft.asynpokemonslot.ormlite.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "pokemondata", daoClass = PokemonDataDao.class)
public class PokemonData {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    @DatabaseField(columnName = "uuid", dataType = DataType.LONG_STRING)
    String uuid;
    @DatabaseField(columnName = "p1", dataType = DataType.LONG_STRING)
    String p1;
    @DatabaseField(columnName = "p2", dataType = DataType.LONG_STRING)
    String p2;
    @DatabaseField(columnName = "p3", dataType = DataType.LONG_STRING)
    String p3;
    @DatabaseField(columnName = "p4", dataType = DataType.LONG_STRING)
    String p4;
    @DatabaseField(columnName = "p5", dataType = DataType.LONG_STRING)
    String p5;
    @DatabaseField(columnName = "p6", dataType = DataType.LONG_STRING)
    String p6;
    @DatabaseField(columnName = "pc", columnDefinition = "LONGTEXT")
    String pc;

}
