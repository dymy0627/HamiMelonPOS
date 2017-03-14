package application.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.ResourceBundle;

import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import application.MainScene;
import application.GenerateDailyTask;
import db.MySqlConnection;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MonthlyReportController implements Initializable {

	@FXML
	private Label monthly_sales, monthly_lunch_sales, monthly_dinner_sales;
	@FXML
	private Label monthly_inside_sales, monthly_outside_sales, monthly_deliver_sales, monthly_total_num, monthly_avg_sales;
	@FXML
	private Label monthly_double_num, monthly_special_num, monthly_wind_rain_num, monthly_luxury_num;

	@FXML
	private Label label_back_door;
	
	@FXML
	private VBox BarChartView1;
	
	@FXML
	private ProgressIndicator myProgressIndicator;
	
	private static boolean needUpdate = false;
	
	private Map<String, String> month;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		getData();
		
	}

	private void getData() {
		myProgressIndicator.setVisible(false);
		new Thread(new Task<Boolean>() {

			@SuppressWarnings("unchecked")
			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);
				label_back_door.setDisable(true);

				if (needUpdate)
					new GenerateDailyTask().run();

				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				month = mySqlConnection.getMonthlyReport();
				mySqlConnection.disconnectSql();
				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				myProgressIndicator.setVisible(false);
				label_back_door.setDisable(false);
				needUpdate = false;

				System.out.println("Load from DB Done!");

				// L_Average_consumption int(11)
				// D_Average_consumption int(11)
				
				monthly_sales.setText(month.get("Turnover"));
				
				int month_inside = Integer.parseInt(month.get("Lunch_Turnover")) + Integer.parseInt(month.get("Dinner_Turnover"));
				monthly_inside_sales.setText(Integer.toString(month_inside));
				
				monthly_lunch_sales.setText(month.get("Lunch_Turnover"));
				monthly_dinner_sales.setText(month.get("Dinner_Turnover"));
				
				int month_togo = Integer.parseInt(month.get("L_Outsourcing")) + Integer.parseInt(month.get("D_Outsourcing"));
				monthly_outside_sales.setText(Integer.toString(month_togo));
				
				int month_delivery = Integer.parseInt(month.get("L_delivery"))+Integer.parseInt(month.get("D_delivery"));
				monthly_deliver_sales.setText(Integer.toString(month_delivery));
					
				int month_total_visit = Integer.parseInt(month.get("L_Number_of_visitors")) + Integer.parseInt(month.get("D_Number_of_visitors"));
				monthly_total_num.setText(Integer.toString(month_total_visit));
				
				double month_avg = ( Double.parseDouble(month.get("L_Average_consumption")) + Double.parseDouble(month.get("D_Average_consumption")))/2;
				//System.out.println(month_avg);
				monthly_avg_sales.setText(Double.toString(month_avg));
				
				monthly_double_num.setText(month.get("Double_package"));
				monthly_special_num.setText(month.get("Special_meals"));
				monthly_wind_rain_num.setText(month.get("wind_and_rain"));
				monthly_luxury_num.setText("0");	
				
				BarChartCreat();
			}

			@Override
			protected void failed() {
				super.failed();
				myProgressIndicator.setVisible(false);
				label_back_door.setDisable(false);
				needUpdate = false;

				System.out.println("Load from DB Failed!");
			}
		}).start();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void BarChartCreat() {
		
		//Result
		String R_First = "本月";
		String R_Second = "前月";
		String R_Third = "總營業額";
		
		//yAxis Content
		final String GP_lunch = "午餐";//午餐
		final String GP_dinner = "晚餐";//晚餐
		final String GP_togo = "外帶";//外帶
		final String GP_delivery = "外送";//外送
		final String GP_pair = "雙人";//雙人
		final String GP_special = "特餐";//特餐
		final String GP_windfur = "楓雨";//風雨
		final String GP_total = "總營業額";//風雨
		
		int total_turnover = Integer.parseInt(month.get("Turnover"));
		double lunch_persent = Double.parseDouble(month.get("Lunch_Turnover")) / total_turnover;
		double dinner_persent = Double.parseDouble(month.get("Dinner_Turnover"))/ total_turnover;
		double togo_persent = (Double.parseDouble(month.get("L_Outsourcing")) + Double.parseDouble(month.get("D_Outsourcing")))/ total_turnover;		
		double delivery_persent = (Double.parseDouble(month.get("L_delivery"))+Double.parseDouble(month.get("D_delivery"))) / total_turnover;
		
		System.out.println("總營業額 " + total_turnover + "午餐占比 " + lunch_persent + "晚餐占比 " + dinner_persent + "外帶占比 " + togo_persent + "外送占比 " + delivery_persent);

	    final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number,String> bc = 
            new BarChart<Number,String>(xAxis,yAxis);
        bc.setTitle("月圖報表");
        xAxis.setLabel("比例");  
        xAxis.setTickLabelRotation(90);
        xAxis.setUpperBound(90);

        //yAxis.tickLabelRotationProperty().set(90);
        yAxis.setLabel("內容");        
        yAxis.setTickLabelGap(10);
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(R_First);    
        series1.getData().add(new XYChart.Data(0.5, GP_pair));
        series1.getData().add(new XYChart.Data(0.8, GP_special));   
        series1.getData().add(new XYChart.Data(0.95, GP_windfur));   
        series1.getData().add(new XYChart.Data(togo_persent, GP_togo));
        series1.getData().add(new XYChart.Data(delivery_persent, GP_delivery));
        series1.getData().add(new XYChart.Data(dinner_persent, GP_dinner));
        series1.getData().add(new XYChart.Data(lunch_persent, GP_lunch));
        
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(R_Second); 
        series2.getData().add(new XYChart.Data(.066, GP_pair));
        series2.getData().add(new XYChart.Data(0.85, GP_special));   
        series2.getData().add(new XYChart.Data(0.06, GP_windfur));   
        series2.getData().add(new XYChart.Data(0.68, GP_togo));
        series2.getData().add(new XYChart.Data(0.36, GP_delivery));
        series2.getData().add(new XYChart.Data(0.2, GP_dinner));
        series2.getData().add(new XYChart.Data(0.3, GP_lunch));
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName(R_Third); 
        series3.getData().add(new XYChart.Data(1.0, GP_total));
        
        bc.setBarGap(3);
        bc.setCategoryGap(3);
        bc.getData().addAll(series1, series2, series3); 
       
        BarChartView1.setVgrow(bc, Priority.ALWAYS);
        BarChartView1.getChildren().add(bc);
        
        
        //BarChartView1.getStylesheets().add("../css/background.css");
		
	}

	@FXML
	protected void BackButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));
		
		MainScene.changeScene(mainstage);
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	protected void CountTurnoverAction(ActionEvent event) throws IOException {
		//Blank workbook
        @SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(); 
         
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("TurnOver Data");
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
