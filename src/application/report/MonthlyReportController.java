package application.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.MainScene;
import application.Task;
import db.MySqlConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class MonthlyReportController implements Initializable {

	@FXML
	private Label daily_sales, daily_lunch_sales, daily_dinner_sales;
	@FXML
	private Label daily_inside_sales, daily_outside_sales, daily_deliver_sales, daily_total_num, daily_avg_sales;
	@FXML
	private Label daily_double_num, daily_special_num, daily_wind_rain_num, daily_luxury_num;

	@FXML
	private Label label_back_door;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getData();
	}

	private void getData() {

		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		//DailyReportBean day = mySqlConnection.getDailyReport();
		mySqlConnection.disconnectSql();

		// L_Average_consumption int(11)
		// D_Average_consumption int(11)

		
	}

	@FXML
	protected void BackButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));
		
		MainScene.changeScene(mainstage);
	}
	
	@FXML
	protected void CountTurnoverAction(ActionEvent event) throws IOException {
		//Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook(); 
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("TurnOver Data");
          
        //This data needs to be written (Object[])
        Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
        Calendar T_Calendar = Calendar.getInstance();
        
        String systemtime = Task.getDateTime();
		String[] timeArray = systemtime.split("-");
		String time = timeArray[0]+"-"+timeArray[1];
		
		data.put(1, new Object[] {time, "","午餐", "晚餐","合計"});
		
        for(int m = 1; m <= 31 ; m++){
        	
        	T_Calendar.set(Integer.parseInt(timeArray[0]),Integer.parseInt(timeArray[1])-1,m);
        	int day = T_Calendar.getTime().getDay()==0?7:T_Calendar.getTime().getDay();
        	int lunch_total = 66;
        	int dinner_total = 66;
        	int day_total = lunch_total + dinner_total;
        	data.put(m+1, new Object[] {m, day, lunch_total,dinner_total,day_total });
        	System.out.print(T_Calendar.getTime().getDay()+" ");
        	
        }
        
        
        //String systemtime = Task.getDateTime();
        System.out.print(T_Calendar.getTime().getDay());
          
        //Iterate over data and write to sheet
        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key = 1 ; key <= data.keySet().size() ; key++)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(time+" 營業額.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("輸出成功");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	}

}
