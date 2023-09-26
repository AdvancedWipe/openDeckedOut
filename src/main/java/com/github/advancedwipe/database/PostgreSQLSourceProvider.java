package com.github.advancedwipe.database;

import com.github.advancedwipe.config.DatabaseConfig;
import com.github.advancedwipe.config.DatabaseType;
import com.github.advancedwipe.utils.DatabaseUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLSourceProvider extends DataSourceProvider<HikariDataSource> {

  public PostgreSQLSourceProvider(DatabaseConfig.DatabaseSettings config) throws SQLException {
    super(config);
  }

  @Override
  protected HikariDataSource initSource() {
    Properties props = new Properties();
    props.setProperty("dataSourceClassName", DatabaseType.POSTGRESQL.getDriverClass());
    DatabaseUtil.mapSettings(props, getConfig());
    HikariConfig config = new HikariConfig(props);

    config.setMaximumPoolSize(getConfig().getMaxConnections());
    return new HikariDataSource(config);
  }

  @Override
  protected void close(HikariDataSource source) {
    source.close();
  }
}
