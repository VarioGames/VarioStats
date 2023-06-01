package de.balou.stats;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL
{
  public static String HOST = "";
  public static String DATABASE = "";
  public static String USER = "";
  public static String PASSWORD = "";
  public static int PORT = 3306;
  private Connection con;

  public MySQL(String host, String database, String user, String password, int port) {
    HOST = host;
    DATABASE = database;
    USER = user;
    PASSWORD = password;
    PORT = port;
    connect();
  }
  
  public void connect()
  {
    try
    {
      this.con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
      Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + "[StatsAPI] Die Verbindung mit der MySQL Datenbank wurde hergestellt");
    }
    catch (SQLException e)
    {
    	e.printStackTrace();
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[StatsAPI] Es konnte keine Verbindung zur MySQL Datenbank hergestellt werden es ist folgender Fehler aufgetreten: " + e.getMessage());
    }
  }
  
  public void close()
  {
    try
    {
      if (this.con != null) {
        this.con.close();
      }
    }
    catch (SQLException localSQLException) {}
  }
  
  public void update(String qry)
  {
    try
    {
      Statement st = this.con.createStatement();
      st.executeUpdate(qry);
      st.close();
    }
    catch (SQLException e)
    {
      connect();
      System.err.println(e);
    }
  }
  
  public ResultSet query(String qry)
  {
    ResultSet rs = null;
    try
    {
      Statement st = this.con.createStatement();
      rs = st.executeQuery(qry);
      
    }
    catch (SQLException e)
    {
      connect();
      System.err.println(e);
    }
    return rs;
  }
}
