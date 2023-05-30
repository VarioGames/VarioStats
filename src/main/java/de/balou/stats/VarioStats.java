package de.balou.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VarioStats {
	
	private static MySQL api = Main.api;
	
	public static void createTable(String table) {
		api.update("CREATE TABLE IF NOT EXISTS " + table.toLowerCase() + " (UUID VARCHAR(100), TYPE TEXT, VALUE INT)");
	}
	
	private static boolean playerExists(String UUID, String table, String type) {
	    try
	    {
	      ResultSet rs = api.query("SELECT * FROM " + table.toLowerCase() + " WHERE UUID='" + UUID + "' AND TYPE = '" + type.toUpperCase() + "'");
	      if (rs.next()) {
	        return rs.getString("UUID") != null;
	      }
	    }
	    catch (SQLException localSQLException) {}
	    return false;
	  }
	
	public static void createPlayer(String UUID, String table, String type) {
		if (!playerExists(UUID, table, type)) {
			api.update("INSERT INTO " + table.toLowerCase() + "(UUID, TYPE, VALUE) VALUES ('"+UUID+"', '"+type.toUpperCase()+"', '0')");
		}
	}
	
	public static void setInt(String UUID, String table, String type, int value) {
		if (playerExists(UUID, table, type)) {
			api.update("UPDATE "+table.toLowerCase()+" SET VALUE = '"+value+"' WHERE UUID='"+UUID+"' AND TYPE = '"+type.toUpperCase()+"'");
		} else {
			createPlayer(UUID, table, type);
			setInt(UUID, table, type, value);
		}
	}
	
	public static int getInt(String UUID, String table, String type) {
		if (playerExists(UUID, table, type)) {
			try {
				ResultSet rs = api.query("SELECT * FROM "+table.toLowerCase()+" WHERE UUID = '"+UUID+"' AND TYPE = '"+type.toUpperCase()+"'");
				if (!rs.next()) {
					return 0;
				} else {
					return rs.getInt("VALUE");
				}
			} catch (SQLException slqex) {}
		} else {
			createPlayer(UUID, table, type);
			return 0;
		}
		return 0;
	}
	
	public static void addInt(String UUID, String table, String type, int add) {
		if (playerExists(UUID, table, type)) {
			setInt(UUID, table, type, getInt(UUID, table, type) + add);
		} else {
			createPlayer(UUID, table, type);
			addInt(UUID, table, type, add);
		}
	}
	
	public static void subtractInt(String UUID, String table, String type, int sub) {
		if (playerExists(UUID, table, type)) {
			setInt(UUID, table, type, getInt(UUID, table, type) - sub);
		} else {
			createPlayer(UUID, table, type);
			subtractInt(UUID, table, type, sub);
		}
	}
	
	public static Integer getRankingFromUUID(String uuid, String table, String type)
	  {
	    boolean done = false;
	    int n = 0;
	    try
	    {
	    	ResultSet rs = api.query("SELECT UUID FROM " + table.toLowerCase() + " WHERE TYPE = '"+type.toUpperCase()+"' ORDER BY VALUE DESC");
	      while ((rs.next()) && (!done))
	      {
	        n++;
	        if (rs.getString("UUID").equalsIgnoreCase(uuid.toString())) {
	          done = true;
	        }
	      }
	      rs.close();
	    }
	    catch (Exception var81)
	    {
	      var81.printStackTrace();
	    }
	    return Integer.valueOf(n);
	  }
	
	public static String getUUIDfromRank(String table, String type, int rank) {
		try {
			int i = 0;
			ResultSet rs = api.query("SELECT * FROM "+table.toLowerCase()+" WHERE TYPE = '"+type.toUpperCase()+"' ORDER BY VALUE DESC");
			
			while (rs.next()) {
				i++;
				if (i == rank) {
					return rs.getString("UUID");
				}
			}
			return "###";
		} catch (SQLException e) {}
		return "";
	}
	

}
