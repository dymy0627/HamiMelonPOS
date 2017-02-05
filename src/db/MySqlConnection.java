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

	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	// AZURE
	@SuppressWarnings("unused")
	private static final String AZURE_DATABASE_URL = "jdbc:mysql://ap-cdbr-azure-southeast-b.cloudapp.net/hamimelon";
	@SuppressWarnings("unused")
	private static final String AZURE_USER = "b63738672a9134";
	@SuppressWarnings("unused")
	private static final String AZURE_PASSWORD = "555d06ab";

	// AWS
	private static final String AWS_DATABASE_URL = "jdbc:mysql://hamimelon.cnkthuortelr.us-west-2.rds.amazonaws.com:3306/hamimelon";
	private static final String AWW_USER = "hamimelon";
	private static final String AWS_PASSWORD = "hami1qaz2wsx";

	private static final String DATABASE_URL = AWS_DATABASE_URL;
	private static final String USER = AWW_USER;
	private static final String PASSWORD = AWS_PASSWORD;

	private Connection mSqlConnection;
	private Statement mStatement;
	private ResultSet mResultSet;

	public void connectSql() {
		try {
			System.out.println("連接MySQL中...");
			// Connect to MySQL
			Class.forName(DATABASE_DRIVER);
			mSqlConnection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			System.out.println("--- 連接成功MySQL ---");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("查無此豬");
		}
	}

	public Connection getSqlConnection() {
		if (mSqlConnection == null) {
			connectSql(); // connect again
		}
		return mSqlConnection;
	}

	public void disconnectSql() {
		try {
			mSqlConnection.close();
			mSqlConnection = null;
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
			if (mSqlConnection == null)
				return false;
			mStatement = mSqlConnection.createStatement();
			mStatement.execute(sql);
			System.out.println("成功執行" + sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查無此諸");
			return false;
		} finally {
			try {
				if (mStatement != null) {
					mStatement.close();
					mStatement = null;
				}
			} catch (SQLException e) {
				System.out.println("Close Exception :" + e.toString());
			}
		}
	}

	public List<String> selectManufacturer() {
		List<String> manufacturerList = new ArrayList<>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("select * from hamimelon.manufacturer");
			while (mResultSet.next()) {
				manufacturerList.add(mResultSet.getString("Name"));
				System.out.println(mResultSet.getString("Name"));
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			try {
				if (mResultSet != null) {
					mResultSet.close();
					mResultSet = null;
				}
				if (mStatement != null) {
					mStatement.close();
					mStatement = null;
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
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("select * from shipping");
			while (mResultSet.next()) {
				StockBean stock = new StockBean();
				stock.setId(mResultSet.getInt("id"));
				stock.setManufacturer(mResultSet.getString("Manufacturers"));
				stock.setName(mResultSet.getString("Meat"));
				stock.setAmount(mResultSet.getInt("cost"));
				stockList.add(stock);
			}
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			try {
				if (mResultSet != null) {
					mResultSet.close();
					mResultSet = null;
				}
				if (mStatement != null) {
					mStatement.close();
					mStatement = null;
				}
			} catch (SQLException e) {
				System.out.println("Close Exception :" + e.toString());
			}
		}
		return stockList;
	}

}