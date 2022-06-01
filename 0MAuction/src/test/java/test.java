import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class test {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            // this uses h2 by default but change to match your database
            String databaseUrl = "jdbc:sqlite:C://test.db";
            // create a connection source to our database
            ConnectionSource connectionSource =
                    new JdbcConnectionSource(databaseUrl);

            // instantiate the dao
            Dao<KeyTable, String> accountDao =
                    DaoManager.createDao(connectionSource, KeyTable.class);

            // if you need to create the 'accounts' table make this call
            TableUtils.createTableIfNotExists(connectionSource, KeyTable.class);
            KeyTable keyTable = new KeyTable();
            keyTable.setKey("okok");
            accountDao.create(keyTable);

            System.out.println(accountDao.queryForEq("name", "okok").get(0).getId());

            connectionSource.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
