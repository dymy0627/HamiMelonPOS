package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.GenerateDailyTask;
import application.report.DailyReportBean;
import application.stock.PurchaseBean;
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

	public boolean insertListData(String type, int people_num, int list_money) {
		String list_sql = new String(
				"insert into hamimelon.detail_list_meals(Consumption_type,cost,number_of_meals)values('" + type + "',"
						+ list_money + "," + people_num + ")");
		return executeSql(list_sql);
	}

	public DailyReportBean getDailyReport() {
		DailyReportBean day = new DailyReportBean();
		String systemtime = GenerateDailyTask.getDateTime();
		String[] timeArray = systemtime.split(" ");
		String time = timeArray[0];
		System.out.println("time=" + time);
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("SELECT * FROM hamimelon.daily where teppanyaki_date='" + time + "'");
			while (mResultSet.next()) {

				day.setDailySales(mResultSet.getInt("Turnover"));
				int dailyOutsideSales = mResultSet.getInt("L_Outsourcing") + mResultSet.getInt("D_Outsourcing");
				int dailyDeliverSales = mResultSet.getInt("L_delivery") + mResultSet.getInt("D_delivery");
				day.setOutsideSales(dailyOutsideSales);
				day.setDeliverSales(dailyDeliverSales);
				int dailyLunchSales = mResultSet.getInt("Lunch_Turnover");
				int dailyDinnerSales = mResultSet.getInt("Dinner_Turnover");
				day.setLunchSales(dailyLunchSales);
				day.setDinnerSales(dailyDinnerSales);
				int dailyInsideSales = dailyLunchSales + dailyDinnerSales;
				day.setInsideSales(dailyInsideSales);
				int dailyTotalNum = mResultSet.getInt("L_Number_of_visitors")
						+ mResultSet.getInt("D_Number_of_visitors");
				day.setTotalNum(dailyTotalNum);
				int avgSales = dailyInsideSales / (dailyTotalNum > 0 ? dailyTotalNum : 1);
				day.setAvgSales(avgSales);
				day.setDoubleNum(mResultSet.getInt("Double_Package"));
				day.setSpecialNum(mResultSet.getInt("Special_meals"));
				day.setWindAndRainNum(mResultSet.getInt("wind_and_rain"));
				day.setLuxuryNum(0);
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
		return day;
	}

	public ArrayList<String[]> getMonthlyTurnOver(String time) {

		ArrayList<String[]> mResultArray = new ArrayList<String[]>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery(
					"select teppanyaki_date, Lunch_Turnover, Dinner_Turnover from hamimelon.daily where teppanyaki_date LIKE '%"
							+ time + "%'");
			// select teppanyaki_date, Lunch_Turnover, Dinner_Turnover
			// from hamimelon.daily
			// where teppanyaki_date LIKE '%2017-02%'

			while (mResultSet.next()) {

				String[] ResultString = new String[3];
				ResultString[0] = mResultSet.getString("teppanyaki_date");
				ResultString[1] = Integer.toString(mResultSet.getInt("Lunch_Turnover"));
				ResultString[2] = Integer.toString(mResultSet.getInt("Dinner_Turnover"));

				mResultArray.add(ResultString);
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
		return mResultArray;
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

	public List<String> selectMeatClass() {
		List<String> meatClassList = new ArrayList<>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("select * from hamimelon.Meat_class");
			while (mResultSet.next()) {
				meatClassList.add(mResultSet.getString("name"));
				System.out.println(mResultSet.getString("name"));
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
		return meatClassList;
	}

	public int getReserveById(String id) {
		try {
			if (mSqlConnection == null)
				return 0;
			String sql = "select reserve_number from hamimelon.Stock WHERE id ='" + id + "'";
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			System.out.println("成功執行" + sql);
			while (mResultSet.next()) {
				return mResultSet.getInt("reserve_number");
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查無此諸");
			return 0;
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

	public List<StockBean> selectAllStock() {
		List<StockBean> stockList = new ArrayList<>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery(
					"select a.*, b.name from hamimelon.Stock as a, hamimelon.Meat_class as b where a.id = b.id");
			while (mResultSet.next()) {
				StockBean stock = new StockBean();
				stock.setId(mResultSet.getString("id"));
				stock.setName(mResultSet.getString("name"));
				stock.setReserve(mResultSet.getInt("reserve_number"));
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

	public boolean updateStockById(String id, int reserveNumber) {
		// UPDATE 資料表名稱
		// SET 欄位名稱1=欄位1的資料, 欄位名稱2=欄位2的資料,...
		// WHERE 條件式
		String sql = "UPDATE hamimelon.Stock SET reserve_number='" + reserveNumber + "' WHERE id='" + id + "'";
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

	public boolean deletePurchaseData(PurchaseBean purchase) {
		boolean deleteResult = deletePurchaseData(purchase.getId());
		System.out.println("刪除一筆" + (deleteResult ? "成功" : "失敗"));
		return deleteResult;
	}

	public boolean deletePurchaseData(int id) {
		String sql = new String("DELETE FROM hamimelon.Purchase WHERE id='" + id + "'");
		return executeSql(sql);
	}

	public boolean deletePurchaseData(int id, String facturer, String name, int amount) {
		String sql = new String("DELETE FROM hamimelon.Purchase WHERE id='" + id + "' and name='" + name
				+ "' and manufacturer='" + facturer + "' and amount='" + amount + "'");
		return executeSql(sql);
	}

	public boolean insertPurchaseData(PurchaseBean purchase) {
		boolean addResult = insertPurchaseData(purchase.getName(), purchase.getQuantity(), purchase.getUnit(),
				purchase.getAmount(), purchase.getManufacturer());
		System.out.println("新增一筆" + (addResult ? "成功" : "失敗"));
		return addResult;
	}

	public boolean insertPurchaseData(String name, int quantity, String unit, int amount, String facturer) {
		String sql = new String("INSERT into hamimelon.Purchase(name, quantity, unit, amount, manufacturer)values('"
				+ name + "','" + +quantity + "','" + unit + "','" + amount + "','" + facturer + "')");
		return executeSql(sql);
	}

	public List<PurchaseBean> selectAllPurchase() {
		List<PurchaseBean> purchaseList = new ArrayList<>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("select * from hamimelon.Purchase");
			while (mResultSet.next()) {
				PurchaseBean purchase = new PurchaseBean();
				purchase.setId(mResultSet.getInt("id"));
				purchase.setName(mResultSet.getString("name"));
				purchase.setQuantity(mResultSet.getInt("quantity"));
				purchase.setUnit(mResultSet.getString("unit"));
				purchase.setAmount(mResultSet.getInt("amount"));
				purchase.setManufacturer(mResultSet.getString("manufacturer"));
				purchase.setDate(mResultSet.getString("date"));
				purchaseList.add(purchase);
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
		return purchaseList;
	}
}