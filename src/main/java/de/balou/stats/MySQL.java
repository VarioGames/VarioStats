package de.balou.stats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import net.md_5.bungee.api.ChatColor;

public class MySQL
{
  public static String HOST = "";
  public static String DATABASE = "";
  public static String USER = "";
  public static String PASSWORD = "";
  private Connection con;
  
  public MySQL(String host, String database, String user, String password)
  {
    HOST = host;
    DATABASE = database;
    USER = user;
    PASSWORD = password;
    
    connect();
  }
  
  public void connect()
  {
    try
    {
      this.con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
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
