package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.stock.StockBean;

public class MySqlConnection {

	private Connection sqlConnection;
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://ap-cdbr-azure-southeast-b.cloudapp.net/hamimelon";
	private static final String USER = "b63738672a9134";
	private static final String PASSWORD = "555d06ab";

	private Statement stat;
	private ResultSet rs;

	public void connectSql() {
		try {
			System.out.println("連接MySQL中...");
			// Connect to MySQL
			Class.forName(DATABASE_DRIVER);
			sqlConnection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			System.out.println("--- 連接成功MySQL ---");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("查無此豬");
		}
	}

	public Connection getSqlConnection() {
		if (sqlConnection == null) {
			try {
				sqlConnection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sqlConnection;
	}

	public void disconnectSql() {
		try {
			sqlConnection.close();
			sqlConnection = null;
			System.out.println("--- 斷開MySQL ---");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteShippingDate(StockBean stock) {
		return deleteShippingDate(stock.getId());
	}

	public boolean deleteShippingDate(int id) {
		String sql = new String("DELETE FROM hamimelon.shipping WHERE id='" + id + "'");
		return executeSql(sql);
	}

	public boolean deleteShippingDate(int id, String facturer, String name, int cost) {
		String sql = new String("DELETE FROM hamimelon.shipping WHERE id='" + id + "' Manufacturers='" + facturer
				+ "' and Meat='" + name + "' and cost='" + cost + "'");
		return executeSql(sql);
	}

	public boolean insertShippingData(StockBean stock) {
		return insertShippingData(stock.getManufacturer(), stock.getName(), stock.getAmount());
	}

	public boolean insertShippingData(String facturer, String name, int cost) {
		String sql = new String("insert into hamimelon.shipping(Manufacturers,cost,Meat)values('" + facturer + "',"
				+ cost + ",'" + name + "')");
		return executeSql(sql);
	}

	public boolean executeSql(String sql) {
		try {
			if (sqlConnection == null)
				return false;
			stat = sqlConnection.createStatement();
			stat.execute(sql);
			System.out.println("成功執行" + sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查無此諸");
			return false;
		} finally {
			try {
				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (SQLException e) {
				System.out.println("Close Exception :" + e.toString());
			}
		}
	}

	public List<String> selectManufacturer() {
		List<String> manufacturerList = new ArrayList<>();
		try {
			stat = sqlConnection.createStatement();
			rs = stat.executeQuery("select * from hamimelon.manufacturer");
			while (rs.next()) {
				manufacturerList.add(rs.getString("Name"));
				System.out.println(rs.getString("Name"));
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (SQLException e) {
				System.out.println("Close Exception :" + e.toString());
			}
		}
		return manufacturerList;
	}

	public List<StockBean> selectAllStock() {
		List<StockBean> stockList = new ArrayList<>();
		try {
			stat = sqlConnection.createStatement();
			rs = stat.executeQuery("select * from shipping");
			while (rs.next()) {
				StockBean stock = new StockBean();
				stock.setId(rs.getInt("id"));
				stock.setManufacturer(rs.getString("Manufacturers"));
				stock.setName(rs.getString("Meat"));
				stock.setAmount(rs.getInt("cost"));
				stockList.add(stock);
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stat != null) {
					stat.close();
					stat = null;
				}
			} catch (SQLException e) {
				System.out.println("Close Exception :" + e.toString());
			}
		}
		return stockList;
	}

}
