package com.WeiBoss.bossshoptr.Database;

import java.sql.Connection;

public abstract class MySQL {
    public abstract void openConnection();

    public abstract boolean checkConnection();

    public abstract Connection getConnection();

    public abstract void closeConnection();
}