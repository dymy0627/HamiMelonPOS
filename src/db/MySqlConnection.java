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

	public void disconnectSql() {
		try {
			sqlConnection.close();
			System.out.println("--- 斷開MySQL ---");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertShippingData(String facturer, String name, int cost) {
		String sql = new String("insert into hamimelon.shipping(Manufacturers,cost,Meat)values('" + facturer + "',"
				+ cost + ",'" + name + "')");
		try {
			stat = sqlConnection.createStatement();
			stat.execute(sql);
			System.out.println("成功新增一筆" + sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查無此諸");
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
			rs = stat.executeQuery("select * from shipping");
			while (rs.next()) {
				manufacturerList.add(rs.getString("Manufacturers"));
				System.out.println(rs.getString("Manufacturers"));
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

	public void selectTable() {
		try {
			stat = sqlConnection.createStatement();
			// rs = stat.executeQuery(
			// "select COLUMN_NAME from information_schema.columns where
			// table_name = 'shipping' and table_schema = 'hamimelon'");
			rs = stat.executeQuery("select * from shipping");
			System.out.println("Manufacturers\tMeat\t\tcost");
			while (rs.next()) {
				System.out.println(
						rs.getString("Manufacturers") + "\t\t" + rs.getString("Meat") + "\t\t" + rs.getString("cost"));
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
	}

}
