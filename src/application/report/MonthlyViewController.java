package application.report;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import db.MySqlConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MonthlyViewController implements Initializable{
	
	//FXML Object Def.
	@FXML
	private Label monthly_sales, monthly_lunch_sales, monthly_dinner_sales;
	@FXML
	private Label monthly_inside_sales, monthly_outside_sales, monthly_deliver_sales, monthly_total_num, monthly_avg_sales;
	@FXML
	private Label monthly_double_num, monthly_special_num, monthly_wind_rain_num, monthly_luxury_num;
	@FXML
	private VBox BarChartView1;
	
	//Controll Object Def.
	private MySqlConnection mySqlConnection;
	private Map<String, String> month;
	private Map<String, String> p_month;
	private ArrayList<XYChart.Data<Integer,String>> T_MonthData = new ArrayList<XYChart.Data<Integer,String>>();
	private ArrayList<XYChart.Data<Integer,String>> P_MonthData = new ArrayList<XYChart.Data<Integer,String>>();
	private int lunch_persent,dinner_persent,togo_persent,delivery_persent,paircombo_percent,specialcombo_percent,windfurcombo_percent = 0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Connect Sql
		mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		
		//DailyReportContent Controll
		DailyBusinessTable();
		
		DailyBusinessGraphic();
		
	}
	
	private void DailyBusinessTable() {
		
		month = mySqlConnection.getMonthlyReport();
		p_month = mySqlConnection.getPreviousMonthlyReport();
		
		int all_turnover = Integer.parseInt(month.get("Turnover"))+Integer.parseInt(month.get("L_Outsourcing")) + Integer.parseInt(month.get("D_Outsourcing"))
		+Integer.parseInt(month.get("L_delivery"))+Integer.parseInt(month.get("D_delivery"));
		//monthly_sales.setText(month.get("Turnover"));
		monthly_sales.setText(String.valueOf(all_turnover));
		
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
	}

	private void DailyBusinessGraphic() {
		
		String R_First = "本月";
		String R_Second = "前月";
		String R_Third = "總營業額";
		
		setThisMonthData();
		setPreviousMonthData();
	
		final String GP_total = "總營業額";//
		final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number,String> bc = 
            new BarChart<Number,String>(xAxis,yAxis);
        bc.setTitle("月圖報表");
        xAxis.setTickLabelRotation(90);
        xAxis.setUpperBound(90);
        yAxis.setTickLabelGap(10);
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(R_First);
        for(XYChart.Data<Integer,String> i : T_MonthData){
        	i.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                  if (node != null) {
                    displayLabelForData(i);
                  } 
                }
              });
        	series1.getData().add(i);
        }
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(R_Second); 
        for(XYChart.Data<Integer,String> i : P_MonthData){
        	i.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                  if (node != null) {
                    displayLabelForData(i);
                  } 
                }

              });
        	series2.getData().add(i);
        }
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName(R_Third); 
        series3.getData().add(new XYChart.Data(100, GP_total));

        bc.setBarGap(3);
        bc.setCategoryGap(3);
        bc.getData().addAll(series1, series2, series3); 
       
        BarChartView1.setVgrow(bc, Priority.ALWAYS);
        BarChartView1.getChildren().add(bc);
		
	
	}
	
	private void setThisMonthData() {//yAxis Content Data
		int all_togo = Integer.parseInt(month.get("L_Outsourcing"))+Integer.parseInt(month.get("D_Outsourcing"));
		int all_delivery = Integer.parseInt(month.get("L_delivery"))+Integer.parseInt(month.get("D_delivery"));
		
		int total_turnover = Integer.parseInt(month.get("Turnover")) + all_togo + all_delivery;
		
		int lunch_persent = (Integer.parseInt(month.get("Lunch_Turnover"))*100 / total_turnover);
		int dinner_persent = Integer.parseInt(month.get("Dinner_Turnover"))*100/ total_turnover;
		
		int togo_persent = (all_togo*100)/ total_turnover;		
		int delivery_persent = (all_delivery*100) / total_turnover;
		
		int paircombo_percent = Integer.parseInt(month.get("Double_package"))*100 / total_turnover;
		int specialcombo_percent = Integer.parseInt(month.get("Special_meals"))*100 / total_turnover;
		int windfurcombo_percent = Integer.parseInt(month.get("wind_and_rain"))*100 / total_turnover;
		
		System.out.println("本月總營業額 " + total_turnover + " 午餐占比 " + lunch_persent + " 晚餐占比 " + dinner_persent + " 外帶占比 " + togo_persent + " 外送占比 " + delivery_persent);

		T_MonthData.add(new XYChart.Data<Integer, String>(paircombo_percent, "雙人"));
		T_MonthData.add(new XYChart.Data<Integer, String>(specialcombo_percent, "特餐"));
		T_MonthData.add(new XYChart.Data<Integer, String>(windfurcombo_percent, "楓雨"));
		T_MonthData.add(new XYChart.Data<Integer, String>(togo_persent, "外帶"));
		T_MonthData.add(new XYChart.Data<Integer, String>(delivery_persent, "外送"));
		T_MonthData.add(new XYChart.Data<Integer, String>(dinner_persent, "晚餐"));
		T_MonthData.add(new XYChart.Data<Integer, String>(lunch_persent, "午餐"));
		
	}
	
	private void setPreviousMonthData() {
		//yAxis Content Data
		int all_togo = Integer.parseInt(p_month.get("L_Outsourcing"))+Integer.parseInt(p_month.get("D_Outsourcing"));
		int all_delivery = Integer.parseInt(p_month.get("L_delivery"))+Integer.parseInt(p_month.get("D_delivery"));
		
		int total_turnover = Integer.parseInt(p_month.get("Turnover")) + all_togo + all_delivery;
		
		if(total_turnover == 0){
			lunch_persent = 0;
			dinner_persent = 0;
			
			togo_persent = 0;		
			delivery_persent = 0;
			
			paircombo_percent = 0;
			specialcombo_percent = 0;
			windfurcombo_percent = 0;
		}else{
			lunch_persent = (Integer.parseInt(p_month.get("Lunch_Turnover"))*100 / total_turnover);
			dinner_persent = Integer.parseInt(p_month.get("Dinner_Turnover"))*100/ total_turnover;
			
			togo_persent = (all_togo*100)/ total_turnover;		
			delivery_persent = (all_delivery*100) / total_turnover;
			
			paircombo_percent = Integer.parseInt(p_month.get("Double_package"))*100 / total_turnover;
			specialcombo_percent = Integer.parseInt(p_month.get("Special_meals"))*100 / total_turnover;
			windfurcombo_percent = Integer.parseInt(p_month.get("wind_and_rain"))*100 / total_turnover;
		}
			
		
		
		System.out.println("前月總營業額 " + total_turnover + " 午餐占比 " + lunch_persent + " 晚餐占比 " + dinner_persent + " 外帶占比 " + togo_persent + " 外送占比 " + delivery_persent);

		P_MonthData.add(new XYChart.Data<Integer, String>(paircombo_percent, "雙人"));
		P_MonthData.add(new XYChart.Data<Integer, String>(specialcombo_percent, "特餐"));
		P_MonthData.add(new XYChart.Data<Integer, String>(windfurcombo_percent, "楓雨"));
		P_MonthData.add(new XYChart.Data<Integer, String>(togo_persent, "外帶"));
		P_MonthData.add(new XYChart.Data<Integer, String>(delivery_persent, "外送"));
		P_MonthData.add(new XYChart.Data<Integer, String>(dinner_persent, "晚餐"));
		P_MonthData.add(new XYChart.Data<Integer, String>(lunch_persent, "午餐"));
		
	}
	
	private void displayLabelForData(XYChart.Data<Integer,String> data) {
		  final Node node = data.getNode();
		  final Text dataText = new Text(data.getXValue() + "");
		  node.parentProperty().addListener(new ChangeListener<Parent>() {
		    public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
		      Group parentGroup = (Group) parent;
		      parentGroup.getChildren().add(dataText);
		    }
		  });

		  node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
		    @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
		      dataText.setLayoutX(
		        Math.round(
		          bounds.getMinX() + bounds.getWidth() + dataText.prefWidth(-1)*0.3
		        )
		      );
		      dataText.setLayoutY(
		        Math.round(
		          bounds.getMinY() - dataText.prefHeight(-1) * 0.5 + dataText.prefHeight(-1)
		        )
		      );
		    }
		  });
		}
}
