package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.GenerateDailyTask;
import application.order.MenuBean;
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

	public boolean insertOrderList(String type, int people_num, int cost, String meals) {
		String list_sql = new String("INSERT into hamimelon.Order_list(type,people,cost,meals)values('" + type + "','"
				+ people_num + "','" + cost + "','" + meals + "')");
		return executeSql(list_sql);
	}

	public boolean insertQCList(String type, int people_num, int cost, String meals, String time) {
		String list_sql = new String("INSERT into hamimelon.Order_list(type,people,cost,meals,time)values('" + type
				+ "','" + people_num + "','" + cost + "','" + meals + "','" + time + "')");
		return executeSql(list_sql);
	}

	public Map<String, String> getMonthlyReport() {
		Map<String, String> month = new HashMap<String, String>();
		String systemtime = GenerateDailyTask.getDateTime();
		String[] timeArray = systemtime.split(" ");
		String[] timeArray2 = timeArray[0].split("-");
		String time = timeArray2[1];
		System.out.println("current month=" + time);
		try {
			mStatement = mSqlConnection.createStatement();
			/*
			 * SELECT sum(Turnover), sum(Lunch_Turnover),
			 * sum(L_Number_of_visitors), avg(L_Average_consumption),
			 * sum(L_Outsourcing), sum(L_delivery), sum(Dinner_Turnover),
			 * sum(D_Number_of_visitors), avg(D_Average_consumption),
			 * sum(D_Outsourcing), sum(D_delivery), sum(Double_package),
			 * sum(Special_meals), sum(wind_and_rain), sum(total_visitors),
			 * sum(total_AVG_Turnover) FROM hamimelon.Daily
			 *
			 * 
			 */
			mResultSet = mStatement
					.executeQuery("SELECT sum(Turnover), sum(Lunch_Turnover), sum(L_Number_of_visitors), "
							+ "avg(L_Average_consumption), sum(L_Outsourcing), sum(L_delivery), sum(Dinner_Turnover), "
							+ "sum(D_Number_of_visitors), avg(D_Average_consumption), sum(D_Outsourcing), sum(D_delivery), "
							+ "sum(Double_package_Turnover), sum(Special_meals_Turnover), sum(windrain_Turnover), sum(total_visitors), "
							+ "sum(total_AVG_Turnover) FROM hamimelon.Daily WHERE date_format(teppanyaki_date,'%m')= '"
							+ time + "' ");
			while (mResultSet.next()) {

				// day.setDailySales(mResultSet.getInt("Turnover"));
				month.put("Turnover",
						(mResultSet.getString("sum(Turnover)") != null) ? mResultSet.getString("sum(Turnover)") : "0");
				month.put("Lunch_Turnover", (mResultSet.getString("sum(Lunch_Turnover)") != null)
						? mResultSet.getString("sum(Lunch_Turnover)") : "0");
				month.put("L_Number_of_visitors", (mResultSet.getString("sum(L_Number_of_visitors)") != null)
						? mResultSet.getString("sum(L_Number_of_visitors)") : "0");
				month.put("L_Average_consumption", (mResultSet.getString("avg(L_Average_consumption)") != null)
						? mResultSet.getString("avg(L_Average_consumption)") : "0");
				month.put("L_Outsourcing", (mResultSet.getString("sum(L_Outsourcing)") != null)
						? mResultSet.getString("sum(L_Outsourcing)") : "0");
				month.put("L_delivery", (mResultSet.getString("sum(L_delivery)") != null)
						? mResultSet.getString("sum(L_delivery)") : "0");
				month.put("Dinner_Turnover", (mResultSet.getString("sum(Dinner_Turnover)") != null)
						? mResultSet.getString("sum(Dinner_Turnover)") : "0");
				month.put("D_Number_of_visitors", (mResultSet.getString("sum(D_Number_of_visitors)") != null)
						? mResultSet.getString("sum(D_Number_of_visitors)") : "0");
				month.put("D_Average_consumption", (mResultSet.getString("avg(D_Average_consumption)") != null)
						? mResultSet.getString("avg(D_Average_consumption)") : "0");
				month.put("D_Outsourcing", (mResultSet.getString("sum(D_Outsourcing)") != null)
						? mResultSet.getString("sum(D_Outsourcing)") : "0");
				month.put("D_delivery", (mResultSet.getString("sum(D_delivery)") != null)
						? mResultSet.getString("sum(D_delivery)") : "0");
				month.put("Double_package", (mResultSet.getString("sum(Double_package_Turnover)") != null)
						? mResultSet.getString("sum(Double_package_Turnover)") : "0");
				month.put("Special_meals", (mResultSet.getString("sum(Special_meals_Turnover)") != null)
						? mResultSet.getString("sum(Special_meals_Turnover)") : "0");
				month.put("wind_and_rain", (mResultSet.getString("sum(windrain_Turnover)") != null)
						? mResultSet.getString("sum(windrain_Turnover)") : "0");
				month.put("total_visitors", (mResultSet.getString("sum(total_visitors)") != null)
						? mResultSet.getString("sum(total_visitors)") : "0");

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
		return month;
	}

	public String getDailyMeals(String time) {
		String getDailyMeals = new String(
				"SELECT group_concat(meals) todays_meal FROM hamimelon.Order_list where date_format(time,'%Y-%m-%d')='"
						+ time + "'");
		String mealString = "";
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery(getDailyMeals);
			while (mResultSet.next()) {
				mealString = mResultSet.getString("todays_meal");
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
		return mealString;
	}

	public DailyReportBean getDailyReport() {
		String systemtime = GenerateDailyTask.getDateTime();
		String[] timeArray = systemtime.split(" ");
		String time = timeArray[0];
		System.out.println("time=" + time);
		return selectDailyReportFromOrderList(time);
	}

	public DailyReportBean selectDailyReport(String time) {
		DailyReportBean day = new DailyReportBean();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("SELECT * FROM hamimelon.Daily where teppanyaki_date='" + time + "'");
			while (mResultSet.next()) {

				day.setDailyTurnover(mResultSet.getInt("Turnover"));
				int dailyOutsideSales = mResultSet.getInt("L_Outsourcing") + mResultSet.getInt("D_Outsourcing");
				int dailyDeliverSales = mResultSet.getInt("L_delivery") + mResultSet.getInt("D_delivery");
				day.setOutsideSales(dailyOutsideSales);
				day.setDeliverSales(dailyDeliverSales);
				int dailyLunchSales = mResultSet.getInt("Lunch_Turnover");
				int dailyDinnerSales = mResultSet.getInt("Dinner_Turnover");
				day.setLunchTurnover(dailyLunchSales);
				day.setDinnerTurnover(dailyDinnerSales);
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

	public DailyReportBean selectDailyReportFromOrderList(String time) {
		DailyReportBean day = new DailyReportBean();
		PreparedStatement prestmt;
		try {
			prestmt = getSqlConnection().prepareStatement(
					"SELECT(SELECT sum(cost) FROM hamimelon.Order_list WHERE date_format(time,'%Y-%m-%d')=?) AS Turnover"
							+ ",(SELECT sum(cost) FROM hamimelon.Order_list WHERE date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')=?) AS Lunch_Turnover"
							+ ",(SELECT sum(cost) FROM hamimelon.Order_list WHERE date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')=?) AS Dinner_Turnover"
							+ ",(SELECT sum(people) FROM hamimelon.Order_list WHERE date_format(time,'%Y-%m-%d')=?) AS Total_Visitors"
							+ ",(SELECT sum(cost) FROM hamimelon.Order_list WHERE date_format(time,'%Y-%m-%d')=? and type='內用') AS Inside_Turnover"
							+ ",(SELECT sum(cost) FROM hamimelon.Order_list WHERE date_format(time,'%Y-%m-%d')=? and type='外帶') AS Outside_Turnover"
							+ ",(SELECT sum(cost) FROM hamimelon.Order_list WHERE date_format(time,'%Y-%m-%d')=? and type='外送') AS Deliver_Turnover"
							+ ",(SELECT sum(cost)/sum(people) FROM hamimelon.Order_list WHERE date_format(time,'%Y-%m-%d')=? and type='內用') AS Avg_Turnover");

			prestmt.setString(1, time);
			prestmt.setString(2, time);
			prestmt.setString(3, time);
			prestmt.setString(4, time);
			prestmt.setString(5, time);
			prestmt.setString(6, time);
			prestmt.setString(7, time);
			prestmt.setString(8, time);

			ResultSet result = prestmt.executeQuery();
			while (result.next()) {
				day.setDailyTurnover(result.getInt("Turnover"));
				day.setLunchTurnover(result.getInt("Lunch_Turnover"));
				day.setDinnerTurnover(result.getInt("Dinner_Turnover"));
				day.setTotalNum(result.getInt("Total_Visitors"));
				day.setInsideSales(result.getInt("Inside_Turnover"));
				day.setOutsideSales(result.getInt("Outside_Turnover"));
				day.setDeliverSales(result.getInt("Deliver_Turnover"));
				day.setAvgSales(result.getInt("Avg_Turnover"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return day;
	}

	public ArrayList<String[]> getMonthlyTurnOver(String time) {

		ArrayList<String[]> mResultArray = new ArrayList<String[]>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery(
					"select teppanyaki_date, Lunch_Turnover, Dinner_Turnover from hamimelon.Daily where teppanyaki_date LIKE '%"
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
			System.out.println("成功執行: " + sql);
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
			mResultSet = mStatement.executeQuery("select * from hamimelon.Manufacturer");
			while (mResultSet.next()) {
				manufacturerList.add(mResultSet.getString("name"));
				System.out.println(mResultSet.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("SQLException" + e.toString());
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

	public List<MenuBean> selectMenu() {
		List<MenuBean> menuBeanList = new ArrayList<>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("select * from hamimelon.Menu");
			while (mResultSet.next()) {
				String id = mResultSet.getString("id");
				MenuBean menu = new MenuBean(id);
				menu.setSet(mResultSet.getString("dish_set"));
				menu.setName(mResultSet.getString("name"));
				menu.setPrice(mResultSet.getInt("price"));
				menu.setMeatClass(mResultSet.getString("meat_class"));
				menuBeanList.add(menu);
			}
		} catch (SQLException e) {
			System.out.println("SQLException" + e.toString());
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
		return menuBeanList;
	}

	public Map<String, String> getMeatClassHashMap() {
		Map<String, String> meatClassHashMap = new HashMap<>();
		try {
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery("select * from hamimelon.Meat_class");
			while (mResultSet.next()) {
				meatClassHashMap.put(mResultSet.getString("id"), mResultSet.getString("name"));
				System.out.println(mResultSet.getString("id") + "," + mResultSet.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("SQLException" + e.toString());
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
		return meatClassHashMap;
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
			System.out.println("SQLException" + e.toString());
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

	public int getStockReserveById(String id) {
		try {
			if (mSqlConnection == null)
				return 0;
			String sql = "SELECT reserve_number from hamimelon.Stock WHERE id ='" + id + "'";
			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			System.out.println("成功執行: " + sql);
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

	public boolean updateStockReserveById(String id, int reserveNumber) {
		// UPDATE 資料表名稱
		// SET 欄位名稱1=欄位1的資料, 欄位名稱2=欄位2的資料,...
		// WHERE 條件式
		String sql = "UPDATE hamimelon.Stock SET reserve_number='" + reserveNumber + "' WHERE id='" + id + "'";
		try {
			if (mSqlConnection == null)
				return false;
			mStatement = mSqlConnection.createStatement();
			mStatement.execute(sql);
			System.out.println("成功執行: " + sql);
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

	// unused
	public int getPurchaseQuanitySumByName(String name) {
		try {
			String sql = "SELECT sum(t.quantity) total_quantity FROM("
					+ "SELECT purchase.name, purchase.quantity FROM hamimelon.Purchase purchase" + ") t where t.name='"
					+ name + "' group by t.name";

			mStatement = mSqlConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while (mResultSet.next()) {
				return mResultSet.getInt("total_quantity");
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
		return 0;
	}

	public void updateDailyWeather(String weather) {
		String systemtime = GenerateDailyTask.getDateTime();
		String[] timeArray = systemtime.split(" ");
		String time = timeArray[0];
		String sql = "UPDATE hamimelon.Daily SET weather='" + weather + "' WHERE teppanyaki_date='" + time + "'";

		try {
			mStatement = mSqlConnection.createStatement();
			mStatement.execute(sql);
			System.out.println("update weather success");
		} catch (SQLException e) {

			e.printStackTrace();
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

	public Map<String, String> getPreviousMonthlyReport() {
		Map<String, String> month = new HashMap<String, String>();
		String systemtime = GenerateDailyTask.getDateTime();
		String[] timeArray = systemtime.split(" ");
		String[] timeArray2 = timeArray[0].split("-");
		String time = timeArray2[1];
		int previous_month = Integer.parseInt(time) - 1;
		String previous_month_trans = (previous_month > 10) ? String.valueOf(previous_month)
				: "0" + String.valueOf(previous_month);
		System.out.println("previous month=" + previous_month_trans);
		try {
			mStatement = mSqlConnection.createStatement();
			/*
			 * SELECT sum(Turnover), sum(Lunch_Turnover),
			 * sum(L_Number_of_visitors), avg(L_Average_consumption),
			 * sum(L_Outsourcing), sum(L_delivery), sum(Dinner_Turnover),
			 * sum(D_Number_of_visitors), avg(D_Average_consumption),
			 * sum(D_Outsourcing), sum(D_delivery), sum(Double_package),
			 * sum(Special_meals), sum(wind_and_rain), sum(total_visitors),
			 * sum(total_AVG_Turnover) FROM hamimelon.Daily
			 *
			 * 
			 */
			mResultSet = mStatement
					.executeQuery("SELECT sum(Turnover), sum(Lunch_Turnover), sum(L_Number_of_visitors), "
							+ "avg(L_Average_consumption), sum(L_Outsourcing), sum(L_delivery), sum(Dinner_Turnover), "
							+ "sum(D_Number_of_visitors), avg(D_Average_consumption), sum(D_Outsourcing), sum(D_delivery), "
							+ "sum(Double_package), sum(Special_meals), sum(wind_and_rain), sum(total_visitors), "
							+ "sum(total_AVG_Turnover) FROM hamimelon.Daily WHERE date_format(teppanyaki_date,'%m')= '"
							+ previous_month_trans + "'");
			while (mResultSet.next()) {

				// day.setDailySales(mResultSet.getInt("Turnover"));
				month.put("Turnover",
						(mResultSet.getString("sum(Turnover)") != null) ? mResultSet.getString("sum(Turnover)") : "0");
				month.put("Lunch_Turnover", (mResultSet.getString("sum(Lunch_Turnover)") != null)
						? mResultSet.getString("sum(Lunch_Turnover)") : "0");
				month.put("L_Number_of_visitors", (mResultSet.getString("sum(L_Number_of_visitors)") != null)
						? mResultSet.getString("sum(L_Number_of_visitors)") : "0");
				month.put("L_Average_consumption", (mResultSet.getString("avg(L_Average_consumption)") != null)
						? mResultSet.getString("avg(L_Average_consumption)") : "0");
				month.put("L_Outsourcing", (mResultSet.getString("sum(L_Outsourcing)") != null)
						? mResultSet.getString("sum(L_Outsourcing)") : "0");
				month.put("L_delivery", (mResultSet.getString("sum(L_delivery)") != null)
						? mResultSet.getString("sum(L_delivery)") : "0");
				month.put("Dinner_Turnover", (mResultSet.getString("sum(Dinner_Turnover)") != null)
						? mResultSet.getString("sum(Dinner_Turnover)") : "0");
				month.put("D_Number_of_visitors", (mResultSet.getString("sum(D_Number_of_visitors)") != null)
						? mResultSet.getString("sum(D_Number_of_visitors)") : "0");
				month.put("D_Average_consumption", (mResultSet.getString("avg(D_Average_consumption)") != null)
						? mResultSet.getString("avg(D_Average_consumption)") : "0");
				month.put("D_Outsourcing", (mResultSet.getString("sum(D_Outsourcing)") != null)
						? mResultSet.getString("sum(D_Outsourcing)") : "0");
				month.put("D_delivery", (mResultSet.getString("sum(D_delivery)") != null)
						? mResultSet.getString("sum(D_delivery)") : "0");
				month.put("Double_package", (mResultSet.getString("sum(Double_package)") != null)
						? mResultSet.getString("sum(Double_package)") : "0");
				month.put("Special_meals", (mResultSet.getString("sum(Special_meals)") != null)
						? mResultSet.getString("sum(Special_meals)") : "0");
				month.put("wind_and_rain", (mResultSet.getString("sum(wind_and_rain)") != null)
						? mResultSet.getString("sum(wind_and_rain)") : "0");
				month.put("total_visitors", (mResultSet.getString("sum(total_visitors)") != null)
						? mResultSet.getString("sum(total_visitors)") : "0");

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
		return month;
	}
}