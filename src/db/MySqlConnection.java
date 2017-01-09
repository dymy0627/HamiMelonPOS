package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			// Connect to MySQL
			Class.forName(DATABASE_DRIVER);
			sqlConnection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
			System.out.println("�s�����\MySQL");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�d�L����");
		}
	}

	public void disconnectSql() {
		try {
			sqlConnection.close();
			System.out.println("�_�}MySQL");
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
			System.out.println("���\�s�W�@��" + sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�d�L����");
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
