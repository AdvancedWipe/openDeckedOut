package com.github.advancedwipe.database;

import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.config.DatabaseConfig.DatabaseSettings;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import javax.sql.DataSource;
import org.apache.logging.log4j.Level;

public class DatabaseManager {

  private DataSource source;


  public void connect() {
    var plugin = OpenDeckedOut.getInstance();
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
        return;
      }
      source = dbSourceProvider.getSource();
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, "Could not build data source.", e);
      return;
    }

    var a = checkPostgresDatabaseExists(source, "my_new_database");

    try (Connection connection = source.getConnection()) {
      // Execute a SQL query to create the new database.
      Statement statement = connection.createStatement();
      statement.executeUpdate("CREATE DATABASE my_new_database");
      OpenDeckedOut.LOGGER.info("Inserted a new message");
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, "Could not insert data.", e);
    }
  }

  public static boolean checkPostgresDatabaseExists(DataSource dataSource, String databaseName) {
    try (Connection connection = dataSource.getConnection()) {
      // Execute a SQL query to check if the database exists.
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(
          "SELECT datname FROM pg_catalog.pg_database WHERE datname = '" + databaseName + "'");

      // If the database exists, return true. Otherwise, return false.
      return resultSet.next();
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, e.getMessage());
    }
    return false;
  }

}
