package com.github.advancedwipe.database;

import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.config.DatabaseConfig.DatabaseSettings;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.logging.log4j.Level;

public class DatabaseManager {

  private DataSource source;


  public void connect() {
    DatabaseSettings settings = new DatabaseSettings("mysql", "3306",
        OpenDeckedOut.getInstance().getDataFolder() + "mydatabase", "username",
        "password", 5, 5);
    try {
      MariaDBSourceProvider dbSourceProvider = new MariaDBSourceProvider(settings);
      source = dbSourceProvider.getSource();
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, "Could not build data source.", e);
      return;
    }

    try (PreparedStatement stmt = source.getConnection()
        .prepareStatement("Insert into some_table(id, message) VALUES(?, ?)");) {
      stmt.executeUpdate("CREATE DATABASE databasename");
      stmt.setInt(1, 10);
      OpenDeckedOut.LOGGER.info("Inserted a new message");
      {

      }
    } catch (SQLException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, "Could not insert data.", e);
    }
  }

}
