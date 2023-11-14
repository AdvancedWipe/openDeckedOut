package com.github.advancedwipe.opendeckedout.database;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import com.github.advancedwipe.opendeckedout.config.DatabaseConfig.DatabaseSettings;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import javax.sql.DataSource;
import org.apache.logging.log4j.Level;

public class DatabaseManager {

  private final OpenDeckedOut plugin;
  private DataSource source;
  private final String query = "SELECT * FROM player";

  public DatabaseManager(OpenDeckedOut plugin) {
    this.plugin = plugin;
  }


  public boolean connect() {
    var config = plugin.getConfig();
    DatabaseSettings settings = new DatabaseSettings(config.getString("database.address"),
        config.getString("database.port"),
        config.getString("database.database_name"), config.getString("database.user"),
        config.getString("database.password"), 5, 5);
    DataSourceProvider<HikariDataSource> dbSourceProvider;

    try {
      if (Objects.equals(config.getString("database.type"), "psql")) {
        dbSourceProvider = new PostgreSQLSourceProvider(settings);
      } else if (Objects.equals(config.getString("database.type"), "mariadb")) {
        dbSourceProvider = new MariaDBSourceProvider(settings);
      } else {
        OpenDeckedOut.LOGGER.log(Level.WARN,
            "database.type in config.yml does not match psql or mariadb");
        return false;
      }
      source = dbSourceProvider.getSource();
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, "Could not build data source.", e);
      return false;
    }
    OpenDeckedOut.LOGGER.log(Level.INFO, "Successfully connected to database");
    return true;
  }

  public void disconnect() {
    if (source != null) {
      try {
        source.getConnection().close();
      } catch (SQLException e) {
        OpenDeckedOut.LOGGER.log(Level.WARN, "Could not close connection to database!");
        OpenDeckedOut.LOGGER.log(Level.WARN, e.getMessage());
      }
    }
  }

  public boolean checkPostgresDatabaseExists(String databaseName) {
    String sqlQuery = "SELECT 1 FROM pg_database WHERE datname = ?";
    try (Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
      // Set the database name as a parameter
      statement.setString(1, databaseName);

      ResultSet resultSet = statement.executeQuery();

      // If the database exists, return true. Otherwise, return false.
      return resultSet.next();
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, e.getMessage());
    }
    return false;
  }

  public ResultSet fetchPlayers() {
    try (Connection connection = source.getConnection();
        PreparedStatement statement = connection.prepareStatement(this.query)) {
      return statement.executeQuery();
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, "Could not fetch from database: {}", e.getMessage());
    }
    return null;
  }

}
