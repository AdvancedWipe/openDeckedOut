package com.github.advancedwipe.opendeckedout.database;

import com.github.advancedwipe.opendeckedout.config.DatabaseConfig.DatabaseSettings;
import java.sql.SQLException;
import javax.sql.DataSource;

public abstract class DataSourceProvider<T extends DataSource> {

  private final T source;
  private final DatabaseSettings config;

  public DataSourceProvider(DatabaseSettings config) throws SQLException {
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

  public DatabaseSettings getConfig() {
    return config;
  }

  protected abstract void close(T source);

  protected boolean testConnection(DataSource source) throws SQLException {
    return source.getConnection().isValid(5 * 1000);
  }
}
