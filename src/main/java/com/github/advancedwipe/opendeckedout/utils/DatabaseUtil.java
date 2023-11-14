package com.github.advancedwipe.opendeckedout.utils;

import com.github.advancedwipe.opendeckedout.config.DatabaseConfig;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {

  private DatabaseUtil() {
  }

  public static String prettyException(SQLException ex) {
    return "SQLException: " + ex.getMessage() + "\n"
        + "SQLState: " + ex.getSQLState() + "\n"
        + "VendorError: " + ex.getErrorCode();
  }

  public static void mapSettings(Properties props, DatabaseConfig.DatabaseSettings settings) {
    props.setProperty("dataSource.serverName", settings.getAddress());
    props.setProperty("dataSource.portNumber", settings.getPort());
    props.setProperty("dataSource.user", settings.getUser());
    props.setProperty("dataSource.password", settings.getPassword());
    props.setProperty("dataSource.databaseName", settings.getDatabase());
  }
}
