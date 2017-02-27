package application;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimerTask;

import db.MySqlConnection;

public class GenerateDailyTask extends TimerTask { 

	public void run() {
		System.out.println("殺神降臨");
		String systemtime = getDateTime();
		System.out.println("現在時間" + getDateTime()); // 輸出 現在時間
		String[] timeArray = systemtime.split(" ");

		String time = timeArray[0];

		String insertdate = new String("insert into hamimelon.daily(teppanyaki_date)values('" + time + "')");

		String Turnover = new String(
				"update hamimelon.daily set Turnover=(select sum(cost) from hamimelon.detail_list_meals where date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String Lunch_Turnover = new String(
				"update hamimelon.daily set Lunch_Turnover=(select sum(cost) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "') where teppanyaki_date='" + time + "'");

		String L_Number_of_visitors = new String(
				"update hamimelon.daily set L_Number_of_visitors=(select sum(number_of_meals) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "') where teppanyaki_date='" + time + "'");

		String L_Average_consumption = new String(
				"update hamimelon.daily set L_Average_consumption=(select TRUNCATE(sum(cost)/sum(number_of_meals),0) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String L_Outsourcing = new String(
				"update hamimelon.daily set L_Outsourcing=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "' and consumption_type='外帶')where teppanyaki_date='" + time + "'");

		String L_delivery = new String(
				"update hamimelon.daily set L_delivery=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "' and consumption_type='外送')where teppanyaki_date='" + time + "'");

		String Dinner_Turnover = new String(
				"update hamimelon.daily set Dinner_Turnover=(select sum(cost) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "') where teppanyaki_date='" + time + "'");

		String D_Number_of_visitors = new String(
				"update hamimelon.daily set D_Number_of_visitors=(select sum(number_of_meals) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "') where teppanyaki_date='" + time + "'");

		String D_Average_consumption = new String(
				"update hamimelon.daily set D_Average_consumption=(select TRUNCATE(sum(cost)/sum(number_of_meals),0) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String D_Outsourcing = new String(
				"update hamimelon.daily set D_Outsourcing=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "' and consumption_type='外帶')where teppanyaki_date='" + time + "'");

		String D_delivery = new String(
				"update hamimelon.daily set D_delivery=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "' and consumption_type='外送')where teppanyaki_date='" + time + "'");

		String Double_package = new String(
				"update hamimelon.daily set Double_package=(select sum(Double_package) from hamimelon.special_meals where date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "') where teppanyaki_date='" + time + "'");

		String Special_meals = new String(
				"update hamimelon.daily set Special_meals=(select sum(Special_meals) from hamimelon.special_meals where date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String wind_and_rain = new String(
				"update hamimelon.daily set wind_and_rain=(select sum(wind_and_rain) from hamimelon.special_meals where date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String total_visitors = new String(
				"update hamimelon.daily set total_visitors=(select sum(number_of_meals) from hamimelon.detail_list_meals where date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "')where teppanyaki_date='" + time + "'");

		String total_AVG_Turnover = new String(
				"update hamimelon.daily set total_AVG_Turnover=(select sum(cost)/sum(number_of_meals) from hamimelon.detail_list_meals where date_format(Ordering_time,'%Y-%m-%d')='"
						+ time + "' and Consumption_type='內用')where teppanyaki_date='" + time + "'");

		MySqlConnection mySqlConnection = new MySqlConnection();

		try {
			Statement st = mySqlConnection.getSqlConnection().createStatement();
			// 撈出剛剛新增的資料
			st.execute(insertdate);
			st.execute(Turnover);
			st.execute(Lunch_Turnover);
			st.execute(L_Number_of_visitors);
			st.execute(L_Average_consumption);
			st.execute(L_Outsourcing);
			st.execute(L_delivery);
			st.execute(Dinner_Turnover);
			st.execute(D_Number_of_visitors);
			st.execute(D_Average_consumption);
			st.execute(D_Outsourcing);
			st.execute(D_delivery);
			st.execute(Double_package);
			st.execute(Special_meals);
			st.execute(wind_and_rain);
			st.execute(total_visitors);
			st.execute(total_AVG_Turnover);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mySqlConnection.disconnectSql();

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