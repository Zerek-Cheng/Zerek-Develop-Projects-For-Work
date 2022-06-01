package net.ginyai.poketrainerrank.core.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ginyai.poketrainerrank.core.PokeTrainerRankMod;
import org.h2.engine.ConnectionInfo;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlDataSourceManager {
    public static final SqlDataSourceManager INSTANCE = new SqlDataSourceManager();
    private static final Pattern URL_REGEX = Pattern.compile("(?:jdbc:)?([^:]+):(//)?(?:([^:]+)(?::([^@]+))?@)?(.*)");
    private Map<String, HikariDataSource> dataSourceMap = new HashMap<>();
    private Map<String, HikariDataSource> tempMap;

    private boolean isReloading;

    private SqlDataSourceManager() {
    }

    public void startReload() {
        isReloading = true;
        tempMap = new HashMap<>();
    }

    public void endReload() {
        dataSourceMap.values().forEach(HikariDataSource::close);
        dataSourceMap.clear();
        dataSourceMap = tempMap;
        isReloading = false;
    }

    public DataSource getDataSource(String url) {
        if (isReloading) {
            if (dataSourceMap.containsKey(url)) {
                return tempMap.computeIfAbsent(url, dataSourceMap::remove);
            } else {
                return tempMap.computeIfAbsent(url, this::createDataSource);
            }
        } else {
            return dataSourceMap.computeIfAbsent(url, this::createDataSource);
        }
    }

    private HikariDataSource createDataSource(String url) {
        Matcher match = URL_REGEX.matcher(url);
        if (!match.matches()) {
            throw new IllegalArgumentException("URL " + url + " is not a valid JDBC URL");
        }
        String protocol = match.group(1);
        boolean hasSlashes = match.group(2) != null;
        String user = match.group(3);
        String password = match.group(4);
        String serverDatabaseSpecifier = match.group(5);
        boolean isH2;
        String driverClass;
        if ("h2".equals(protocol)) {
            ConnectionInfo h2Info = new ConnectionInfo(serverDatabaseSpecifier);
            if (h2Info.isPersistent() && !h2Info.isRemote()) {
                if (serverDatabaseSpecifier.startsWith("file:")) {
                    serverDatabaseSpecifier = serverDatabaseSpecifier.substring("file:".length());
                }
                Path origPath = Paths.get(serverDatabaseSpecifier);
                if (origPath.isAbsolute()) {
                    serverDatabaseSpecifier = origPath.toString();
                } else {
                    serverDatabaseSpecifier = PokeTrainerRankMod.instance.getConfigDir()
                            .resolve(serverDatabaseSpecifier).toAbsolutePath().toString();
                }
            }
            driverClass = org.h2.Driver.class.getName();
            isH2 = true;
        } else {
            driverClass = org.mariadb.jdbc.Driver.class.getName();
            isH2 = false;
        }
        String authlessUrl = "jdbc:" + protocol + (hasSlashes ? "://" : ":") + serverDatabaseSpecifier;
        HikariConfig config = new HikariConfig();
        config.setUsername(user);
        config.setPassword(password);
        config.setJdbcUrl(authlessUrl);
        config.setDriverClassName(driverClass);
        if (!isH2) {
            Properties mySqlProps = new Properties();
            mySqlProps.setProperty("useConfigs", "maxPerformance");
            config.setDataSourceProperties(mySqlProps);
        }
        config.setMaximumPoolSize((Runtime.getRuntime().availableProcessors() * 2) + 1);
        return new HikariDataSource(config);
    }

}
