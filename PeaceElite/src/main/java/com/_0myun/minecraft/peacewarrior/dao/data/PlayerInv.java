package com._0myun.minecraft.peacewarrior.dao.data;

import com._0myun.minecraft.peacewarrior.dao.PlayerInvDao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DatabaseTable(tableName = "playerinv", daoClass = PlayerInvDao.class)
public class PlayerInv {

    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    int id;
    @DatabaseField(columnName = "uuid", dataType = DataType.STRING)
    String uuid;
    @DatabaseField(columnName = "type", dataType = DataType.ENUM_INTEGER)
    ItemType type;
    @DatabaseField(columnName = "data", dataType = DataType.LONG_STRING)
    String data;

    public static enum ItemType {
        INVENTORY(0),
        EQUIPMENT(1);
        int type;

        ItemType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }
}
