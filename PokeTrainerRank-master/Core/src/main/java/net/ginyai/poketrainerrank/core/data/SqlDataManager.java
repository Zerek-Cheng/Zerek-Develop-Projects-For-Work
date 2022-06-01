package net.ginyai.poketrainerrank.core.data;

import net.ginyai.poketrainerrank.core.battle.RankSeason;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SqlDataManager extends SeasonDataManager {

    private DataSource dataSource;

    public SqlDataManager(RankSeason season, String fullUrl) {
        super(season);
        dataSource = SqlDataSourceManager.INSTANCE.getDataSource(fullUrl);
    }

    @Override
    public void init() throws Exception {
        createTable();
        super.init();
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void createTable() throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection
                    .prepareStatement("CREATE TABLE IF NOT EXISTS Season_"+season.getName()+" (" +
                            "ID char(36) NOT NULL," +
                            "Name varchar(40) NOT NULL," +
                            "Score int NOT NULL," +
                            "HScore int NOT NULL," +
                            "HPos int NOT NULL," +
                            "Battles int NOT NULL," +
                            "Wins int NOT NULL," +
                            "Loses int NOT NULL," +
                            "Block BOOLEAN NOT NULL,PRIMARY KEY(ID))");
            statement.execute();
        }
    }

    private int getPos(int score) throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("select count(*) from Season_"+season.getName()+" where Score > ?");
            statement.setInt(1,score);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1)+1;
            } else {
                return -1;
            }
        }
    }

    @Override
    CompletableFuture<Optional<PlayerData>> getData(UUID uuid) {
        return CompletableFuture.supplyAsync(()->{
            try (Connection connection = getConnection()){
                PreparedStatement statement = connection.prepareStatement("select * from Season_"+season.getName()+" where id = ?");
                statement.setString(1,uuid.toString());
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    String name = resultSet.getString("Name");
                    int score = resultSet.getInt("Score");
                    int hScore = resultSet.getInt("HScore");
                    int pos = getPos(score);
                    int hPos = resultSet.getInt("HPos");
                    int battles = resultSet.getInt("Battles");
                    int wins = resultSet.getInt("Wins");
                    int loses = resultSet.getInt("Loses");
                    boolean block = resultSet.getBoolean("Block");
                    return Optional.of(new PlayerData(season, uuid, name, score, hScore, pos, hPos, battles, wins, loses, block));
                } else {
                    return Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    CompletableFuture<Void> updateData(PlayerData data) {
        return CompletableFuture.supplyAsync(()->{
            try (Connection connection = getConnection()){
                PreparedStatement statement = connection.prepareStatement("select * from Season_"+season.getName()+" where id = ?");
                statement.setString(1,data.getUuid().toString());
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    String name = resultSet.getString("Name");
                    int score = resultSet.getInt("Score");
                    int hScore = resultSet.getInt("HScore");
                    int pos = getPos(score);
                    int hPos = resultSet.getInt("HPos");
                    int battles = resultSet.getInt("Battles");
                    int wins = resultSet.getInt("Wins");
                    int loses = resultSet.getInt("Loses");
                    boolean block = resultSet.getBoolean("Block");
                    data.setName(name);
                    data.setHighestPos(hPos);
                    data.updatePos(pos);
                    data.setHighestScore(hScore);
                    data.updateScore(score);
                    data.setBattles(battles);
                    data.setWin(wins);
                    data.setLose(loses);
                    data.block(block);
                    return null;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    CompletableFuture<PlayerData> saveData(PlayerData data) {
        return CompletableFuture.supplyAsync(()->{
            try (Connection connection = getConnection()){
                PreparedStatement statement = connection.prepareStatement("select * from Season_"+season.getName()+" where id = ?");
                statement.setString(1,data.getUuid().toString());
                ResultSet resultSet = statement.executeQuery();
                String sql;
                if(resultSet.next()) {
                    sql = "update Season_" + season.getName() + " set Name = ?, Score = ?, HScore = ?, HPos = ?, Battles = ?, Wins = ?, Loses = ? ,Block = ? where id = ?";
                } else {
                    sql = "insert into Season_" + season.getName() + " (Name,Score,HScore,HPos,Battles,Wins,Loses,Block,Id) values (?,?,?,?,?,?,?,?,?)";
                }
                PreparedStatement save = connection.prepareStatement(sql);
                save.setString(1,data.getName());
                save.setInt(2,data.getScore());
                save.setInt(3,data.getHighestScore());
                save.setInt(4,data.getHighestPos());
                save.setInt(5,data.getBattles());
                save.setInt(6,data.getWin());
                save.setInt(7,data.getLose());
                save.setBoolean(8, data.isBlock());
                save.setString(9, data.getUuid().toString());
                save.executeUpdate();
                return data;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public CompletableFuture<List<PlayerData>> getTops(int top) {
        return CompletableFuture.supplyAsync(()-> {
            try (Connection connection = getConnection()){
                List<PlayerData> list = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement("select * from Season_"+season.getName()+" order by Score desc limit ?");
                statement.setInt(1,top);
                ResultSet resultSet = statement.executeQuery();
                int i = 1;
                int lastPos = 1;
                int lastScore = -1;
                while (resultSet.next()) {
                    UUID uuid = UUID.fromString(resultSet.getString("ID"));
                    String name = resultSet.getString("Name");
                    int score = resultSet.getInt("Score");
                    if(score == -1) {
                        break;
                    }
                    int hScore = resultSet.getInt("HScore");
                    if(lastScore == -1) {
                        lastScore = score;
                    } else if(lastScore < score) {
                        lastScore = score;
                        lastPos = i;
                    }
                    int pos = lastPos;
                    int hPos = resultSet.getInt("HPos");
                    int battles = resultSet.getInt("Battles");
                    int wins = resultSet.getInt("Wins");
                    int loses = resultSet.getInt("Loses");
                    boolean block = resultSet.getBoolean("Block");
                    list.add(new PlayerData(season, uuid, name, score, hScore, pos, hPos, battles, wins, loses, block));
                    i++;
                }
                return list;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
