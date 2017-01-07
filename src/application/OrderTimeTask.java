package application;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Label;

public class OrderTimeTask extends TimerTask
{
	Date now_date = new Date();
	static String time = "66";
	
	@Override
	public void run() 
	{
		//tmp_label.setText(now_date.toString());
		time = now_date.getMonth()+1 + "/" + now_date.getDate() + " " + now_date.getHours() + ":" + now_date.getMinutes();
		System.out.println(time);
		
		if(MenuController.pause)
			this.cancel();
		
		

	}

}
