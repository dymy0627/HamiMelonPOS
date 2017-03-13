package application;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimerTask;

import application.order.MenuBuilder;
import db.MySqlConnection;

public class GenerateDailyTask extends TimerTask {

	private int rain_special, pair_special, deluxe_special, chef_special;

	public void run() {
		System.out.println("殺神降臨");
		String systemtime = getDateTime();
		System.out.println("現在時間" + getDateTime()); // 輸出 現在時間
		String[] timeArray = systemtime.split(" ");

		String time = timeArray[0];

		String insertdate = new String("insert into hamimelon.Daily(teppanyaki_date)values('" + time + "')");

		String Turnover = new String(
				"update hamimelon.Daily set Turnover=(select sum(cost) from hamimelon.Order_list where date_format(time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String updateLunchSQL = new String(
				"UPDATE hamimelon.Daily SET Lunch_Turnover=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
						+ time
						+ "'), L_Number_of_visitors=(select sum(people) from hamimelon.Order_list  where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
						+ time
						+ "'), L_Average_consumption=(select TRUNCATE(sum(cost)/sum(people),0) from hamimelon.Order_list where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
						+ time
						+ "'), L_Outsourcing=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
						+ time
						+ "' and type='外帶'),  L_delivery=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
						+ time + "' and type='外送') " + "where teppanyaki_date='" + time + "'");

		// String L_Number_of_visitors = new String(
		// "update hamimelon.Daily set L_Number_of_visitors=(select sum(people)
		// from hamimelon.Order_list where date_format(time,'%H')<'16' and
		// date_format(time,'%Y-%m-%d')='"
		// + time + "') where teppanyaki_date='" + time + "'");

		// String L_Average_consumption = new String(
		// "update hamimelon.Daily set L_Average_consumption=(select
		// TRUNCATE(sum(cost)/sum(people),0) from hamimelon.Order_list where
		// date_format(time,'%H')<'16' and date_format(time,'%Y-%m-%d')='"
		// + time + "')where teppanyaki_date='" + time + "'");

		// String L_Outsourcing = new String(
		// "update hamimelon.Daily set L_Outsourcing=(select count(type) from
		// hamimelon.Order_list where date_format(time,'%H')<'16' and
		// date_format(time,'%Y-%m-%d')='"
		// + time + "' and type='外帶')where teppanyaki_date='" + time + "'");

		// String L_delivery = new String(
		// "update hamimelon.Daily set L_delivery=(select count(type) from
		// hamimelon.Order_list where date_format(time,'%H')<'16' and
		// date_format(time,'%Y-%m-%d')='"
		// + time + "' and type='外送')where teppanyaki_date='" + time + "'");

		String updateDinnerSQL = new String("UPDATE hamimelon.Daily SET"
				+ " Dinner_Turnover=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time
				+ "'), D_Number_of_visitors=(select sum(people) from hamimelon.Order_list  where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time
				+ "'), D_Average_consumption=(select TRUNCATE(sum(cost)/sum(people),0) from hamimelon.Order_list where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time
				+ "'), D_Outsourcing=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time
				+ "' and type='外帶'), D_delivery=(select sum(cost) from hamimelon.Order_list where date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
				+ time + "' and type='外送') where teppanyaki_date='" + time + "'");

		// String D_Number_of_visitors = new String(
		// "update hamimelon.Daily set D_Number_of_visitors=(select sum(people)
		// from hamimelon.Order_list where date_format(time,'%H')>='16' and
		// date_format(time,'%Y-%m-%d')='"
		// + time + "') where teppanyaki_date='" + time + "'");

		// String D_Average_consumption = new String(
		// "update hamimelon.Daily set D_Average_consumption=(select
		// TRUNCATE(sum(cost)/sum(people),0) from hamimelon.Order_list where
		// date_format(time,'%H')>='16' and date_format(time,'%Y-%m-%d')='"
		// + time + "')where teppanyaki_date='" + time + "'");

		// String D_Outsourcing = new String(
		// "update hamimelon.Daily set D_Outsourcing=(select count(type) from
		// hamimelon.Order_list where date_format(time,'%H')>='16' and
		// date_format(time,'%Y-%m-%d')='"
		// + time + "' and type='外帶')where teppanyaki_date='" + time + "'");

		// String D_delivery = new String(
		// "update hamimelon.Daily set D_delivery=(select count(type) from
		// hamimelon.Order_list where date_format(time,'%H')>='16' and
		// date_format(time,'%Y-%m-%d')='"
		// + time + "' and type='外送')where teppanyaki_date='" + time + "'");

		String total_visitors = new String(
				"update hamimelon.Daily set total_visitors=(select sum(people) from hamimelon.Order_list where date_format(time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String total_AVG_Turnover = new String(
				"update hamimelon.Daily set total_AVG_Turnover=(select sum(cost)/sum(people) from hamimelon.Order_list where date_format(time,'%Y-%m-%d')='"
						+ time + "' and type='內用')where teppanyaki_date='" + time + "'");

		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();

		calAllSetNumber(mySqlConnection.getDailyMeals(time));

		String updateSetsSQL = new String(
				"UPDATE hamimelon.Daily set Double_package='" + pair_special + "',Special_meals='" + chef_special
						+ "',wind_and_rain='" + rain_special + "' where teppanyaki_date='" + time + "'");

		Statement st = null;
		try {
			st = mySqlConnection.getSqlConnection().createStatement();
			// 撈出剛剛新增的資料
			st.execute(insertdate);
			st.execute(Turnover);
			st.execute(updateLunchSQL);
			// st.execute(L_Number_of_visitors);
			// st.execute(L_Average_consumption);
			// st.execute(L_Outsourcing);
			// st.execute(L_delivery);
			st.execute(updateDinnerSQL);
			// st.execute(D_Number_of_visitors);
			// st.execute(D_Average_consumption);
			// st.execute(D_Outsourcing);
			// st.execute(D_delivery);
			st.execute(updateSetsSQL);
			// st.execute(Double_package);
			// st.execute(Special_meals);
			// st.execute(wind_and_rain);
			st.execute(total_visitors);
			st.execute(total_AVG_Turnover);

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
			if (!meal.isEmpty())
				checkSpecialMeal(MenuBuilder.getMealById(meal).getSet());
		}
		System.out.println(
				"風雨:" + rain_special + " 雙人:" + pair_special + " 豪華:" + deluxe_special + " 特餐:" + chef_special);
	}

	private void checkSpecialMeal(String setName) {
		System.out.println("setName = " + setName);
		if (setName.contains("風雨"))
			rain_special++;
		else if (setName.contains("雙"))
			pair_special++;
		else if (setName.contains("套餐"))
			chef_special++;
		else if (setName.contains("豪華"))
			deluxe_special++;
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