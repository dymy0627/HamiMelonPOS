package application.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.MainScene;
import application.GenerateDailyTask;
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
        DataFormat format = workbook.createDataFormat();
        CellStyle  style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        
        
        //This data needs to be written (Object[])
        Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
        Calendar T_Calendar = Calendar.getInstance();
        
        String systemtime = GenerateDailyTask.getDateTime();
		String[] timeArray = systemtime.split("-");
		String time = timeArray[0]+"-"+timeArray[1];
		
		data.put(1, new Object[] {"","","上月營業額","","","同期營業額"});
		workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
		workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
		
		data.put(2, new Object[] {time, "","午餐", "晚餐","合計","總計","差額"});
        for(int m = 1; m <= 31 ; m++){
        	
        	T_Calendar.set(Integer.parseInt(timeArray[0]),Integer.parseInt(timeArray[1])-1,m);
        	String day = "";
        	switch(T_Calendar.getTime().getDay()==0?7:T_Calendar.getTime().getDay()){
        		case 1:
        			day = "一";	break;
        		case 2:
        			day = "二";	break;
        		case 3:
        			day = "三";	break;
        		case 4:
        			day = "四";	break;
        		case 5:
        			day = "五";	break;
        		case 6:
        			day = "六";	break;
        		case 7:
        			day = "日";	break;
        	}
        	
        	data.put(m+2, new Object[] {m, day, 0,0,0 });
        }
        
      //Iterate over data and write to sheet
        Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key = 1 ; key <= data.keySet().size() ; key++){
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr){
               Cell cell = row.createCell(cellnum++);
               cell.setCellStyle(style);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);

            }
        }
        
        MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		ArrayList<String[]> turnover_array = new ArrayList<String[]>();
		turnover_array = mySqlConnection.getMonthlyTurnOver(time);
    	mySqlConnection.disconnectSql();
        for(String[] Read_Value_A : turnover_array){
        	
        	//System.out.println(Read_Value_A[0] + " " + Read_Value_A[1] + " " + Read_Value_A[2] + " ");
        	String[] Date_A = Read_Value_A[0].split("-");
        	int lunch_total = Integer.parseInt(Read_Value_A[1]);
        	int dinner_total = Integer.parseInt(Read_Value_A[2]);
        	int day_total = lunch_total + dinner_total;
        	//System.out.println("data " + Date_A[2] + " " + lunch_total + " " + dinner_total + " ");
        	//data.replace(,new Object[] {lunch_total,dinner_total,day_total } );

        	Row header = sheet.getRow(Integer.parseInt(Date_A[2])+1);
        	header.getCell(2).setCellValue(lunch_total);
        	header.getCell(3).setCellValue(dinner_total);
        	header.getCell(4).setCellValue(day_total);
        	
        }
        //String systemtime = Task.getDateTime();
        System.out.println(Calendar.MONTH-1);
          
        
        try{
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(time+" 營業額.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("輸出成功");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}

}
