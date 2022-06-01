package com._0myun.minecraft.peacewarrior.dao;

import com._0myun.minecraft.peacewarrior.dao.data.Sign;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

public class SignDao extends BaseDaoImpl<Sign, Integer> {
    public SignDao(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public SignDao(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public SignDao(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
