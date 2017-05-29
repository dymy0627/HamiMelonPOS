package application;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimerTask;

import application.order.MenuBean;
import application.order.MenuBuilder;
import application.report.DailyReportBean;
import db.MySqlConnection;

public class GenerateDailyTask extends TimerTask {

	private int rain_special, pair_special, deluxe_special, chef_special;
	private int rain_special_turnover, pair_special_turnover, chef_special_turnover;

	public void run() {
		System.out.println("殺神降臨");
		String systemtime = getDateTime();
		System.out.println("現在時間" + getDateTime()); // 輸出 現在時間
		String[] timeArray = systemtime.split(" ");
		String time = timeArray[0];

		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		DailyReportBean dailyBean = mySqlConnection.selectDailyReportFromOrderList(time);

		String insertdate = new String("INSERT INTO hamimelon.Daily(teppanyaki_date)values('" + time + "')");

		String updateTurnoverSQL = new String("UPDATE hamimelon.Daily SET Turnover='" + dailyBean.getDailyTurnover()
				+ "' where teppanyaki_date='" + time + "'");

		String updateLunchSQL = new String("UPDATE hamimelon.Daily SET "//
				+ "Lunch_Turnover='" + dailyBean.getLunchTurnover() + "', "
				+ "L_Number_of_visitors=(select sum(people) from hamimelon.Order_list  where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
				+ time + "'), "
				+ "L_Average_consumption=(select TRUNCATE(sum(cost)/sum(people),0) from hamimelon.Order_list where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
				+ time + "'), "
				+ "L_Outsourcing=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
				+ time + "' and type='外帶'), "
				+ " L_delivery=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
				+ time + "' and type='外送') where teppanyaki_date='" + time + "'");

		String updateDinnerSQL = new String("UPDATE hamimelon.Daily SET Dinner_Turnover='" + dailyBean.getDinnerTurnover()
				+ "', D_Number_of_visitors=(select sum(people) from hamimelon.Order_list  where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time
				+ "'), D_Average_consumption=(select TRUNCATE(sum(cost)/sum(people),0) from hamimelon.Order_list where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time
				+ "'), D_Outsourcing=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time
				+ "' and type='外帶'), D_delivery=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time + "' and type='外送') where teppanyaki_date='" + time + "'");

		String updateTotalVisitorsSQL = new String("UPDATE hamimelon.Daily SET total_visitors='"
				+ dailyBean.getTotalNum() + "' WHERE teppanyaki_date='" + time + "'");

		String updateAvgTurnoverSQL = new String("UPDATE hamimelon.Daily SET total_AVG_Turnover='"
				+ dailyBean.getAvgSales() + "' WHERE teppanyaki_date='" + time + "'");

		calAllSetNumber(mySqlConnection.getDailyMeals(time));

		String updateSetsSQL = new String(
				"UPDATE hamimelon.Daily SET Double_package='" + pair_special + "',Special_meals='" + chef_special
						+ "',wind_and_rain='" + rain_special + "',Double_Package_Turnover='" + pair_special_turnover
						+ "',Special_meals_Turnover='" + chef_special_turnover + "',windrain_Turnover='"
						+ rain_special_turnover + "'where teppanyaki_date='" + time + "'");

		Statement st = null;
		try {
			st = mySqlConnection.getSqlConnection().createStatement();
			st.execute(insertdate);
			st.execute(updateTurnoverSQL);
			st.execute(updateLunchSQL);
			st.execute(updateDinnerSQL);
			st.execute(updateTotalVisitorsSQL);
			st.execute(updateAvgTurnoverSQL);
			st.execute(updateSetsSQL);
		} catch (SQLException e) {
			System.out.println("DropDB Exception :" + e.toString());
		} finally {
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (SQLException e) {
				System.out.println("Close Exception :" + e.toString());
			}
		}
		mySqlConnection.disconnectSql();
	}

	private void calAllSetNumber(String mealString) {
		String[] meals = mealString.split(",");
		for (String meal : meals) {
			System.out.println("meal=" + meal);
			if (!meal.isEmpty()) {
				checkSpecialMeal(MenuBuilder.getMealById(meal));
			}

		}
		System.out.println(
				"風雨:" + rain_special + " 雙人:" + pair_special + " 豪華:" + deluxe_special + " 特餐:" + chef_special);
	}

	private void checkSpecialMeal(MenuBean checkMeal) {

		String setName = checkMeal.getSet();
		System.out.println("setName = " + setName);

		if (setName.contains("風雨")) {
			rain_special++;
			rain_special_turnover += checkMeal.getPrice();
		} else if (setName.contains("雙")) {
			pair_special++;
			pair_special_turnover += checkMeal.getPrice();
		} else if (setName.contains("套餐")) {
			chef_special++;
			chef_special_turnover += checkMeal.getPrice();
		} else if (setName.contains("豪華"))
			deluxe_special++;

		System.out.println(rain_special_turnover);
		System.out.println(pair_special_turnover);
		System.out.println(chef_special_turnover);
	}

	public static String getDateTime() { // 無參數=傳回現在時間
		Calendar c = Calendar.getInstance();
		return getYMDHMS(c);
	}

	public static String getDateTime(int Y, int M, int D, int H, int m, int S) { // 指定時間
		Calendar c = Calendar.getInstance();
		c.set(Y, --M, D, H, m, S); // 傳進來的實際月份要減 1
		return getYMDHMS(c);
	}

	public static String getYMDHMS(Calendar c) { // 輸出格式製作
		int[] a = { c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
				c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND) };
		StringBuffer sb = new StringBuffer();
		sb.append(a[0]);
		if (a[1] < 9) {
			sb.append("-0" + (a[1] + 1));
		} // 加 1 才會得到實際月份
		else {
			sb.append("-" + (a[1] + 1));
		}
		if (a[2] < 10) {
			sb.append("-0" + (a[2]));
		} else {
			sb.append("-" + (a[2]));
		}
		if (a[3] < 10) {
			sb.append(" 0" + (a[3]));
		} else {
			sb.append(" " + (a[3]));
		}
		if (a[4] < 10) {
			sb.append(":0" + a[4]);
		} else {
			sb.append(":" + a[4]);
		}
		if (a[5] < 10) {
			sb.append(":0" + a[5]);
		} else {
			sb.append(":" + a[5]);
		}
		return sb.toString();
	}

}