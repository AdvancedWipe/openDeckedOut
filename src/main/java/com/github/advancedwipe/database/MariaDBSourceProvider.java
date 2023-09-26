package com.github.advancedwipe.database;

import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.config.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;
import javax.sql.DataSource;

public class MariaDBSourceProvider extends DataSourceProvider<HikariDataSource> {

  public MariaDBSourceProvider(DatabaseConfig.DatabaseSettings config) throws SQLException {
    super(config);
  }

  @Override
  protected HikariDataSource initSource() {
//    Properties props = new Properties();
//    props.setProperty("dataSourceClassName", DatabaseType.MARIADB.getDriverClass());
//    DatabaseUtil.mapSettings(props, getConfig());
//    HikariConfig config = new HikariConfig();
//
//    config.setMaximumPoolSize(getConfig().getMaxConnections());
//    config.setConnectionTimeout(5 * 60 * 1000);
//
//    return new HikariDataSource(config);
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");
    config.setUsername("bart");
    config.setPassword("51mp50n");
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    return new HikariDataSource(config);
  }

  @Override
  protected void close(HikariDataSource source) {
    source.close();
  }

  @Override
  protected boolean testConnection(DataSource source) throws SQLException {
    try (Connection conn = source.getConnection()) {
      return conn.isValid(5 * 1000);
    }
  }
}
