package com.github.advancedwipe.database;

import com.github.advancedwipe.config.DatabaseConfig;
import java.sql.SQLException;
import javax.sql.DataSource;

public abstract class DataSourceProvider<T extends DataSource> {

  private final T source;
  private final DatabaseConfig.DatabaseSettings config;

  public DataSourceProvider(DatabaseConfig.DatabaseSettings config) throws SQLException {
    this.config = config;
    this.source = initSource();
    testConnection(source);
  }

  protected abstract T initSource();

  /**
   * Close the data pool and all underlying connections.
   */
  public final void shutdown() {
    close(source);
  }

  /**
   * Get the stored data Source
   *
   * @return the stored data source
   */
  public DataSource getSource() {
    return source;
  }

  public DatabaseConfig.DatabaseSettings getConfig() {
    return config;
  }

  protected abstract void close(T source);

  protected boolean testConnection(DataSource source) throws SQLException {
    return source.getConnection().isValid(5 * 1000);
  }
}
