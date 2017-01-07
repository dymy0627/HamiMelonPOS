package application.order;

import java.util.Date;
import java.util.TimerTask;

public class OrderTimeTask extends TimerTask {
	Date now_date = new Date();
	static String time = "66";

	@Override
	public void run() {
		// tmp_label.setText(now_date.toString());
		time = now_date.getMonth() + 1 + "/" + now_date.getDate() + " " + now_date.getHours() + ":"
				+ now_date.getMinutes();
		System.out.println(time);

		if (MenuController.pause)
			this.cancel();

	}

}
