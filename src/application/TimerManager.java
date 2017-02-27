package application;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerManager {

	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	public TimerManager() {
		System.out.println("開始排程");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 22);
		calendar.set(Calendar.MINUTE, 20);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();

		if (date.before(new Date())) {
			date = this.addDay(date, 1);
		}
		Timer timer = new Timer();
		GenerateDailyTask task = new GenerateDailyTask();
		timer.schedule(task, date, PERIOD_DAY);
	}

	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}
}
