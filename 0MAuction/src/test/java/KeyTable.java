import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

@Data
@DatabaseTable(tableName = "key")
public class KeyTable {
    @DatabaseField(columnName = "id", dataType = DataType.INTEGER, generatedId = true)
    private int id;
    @DatabaseField(columnName = "name", dataType = DataType.STRING)
    private String key;
}
