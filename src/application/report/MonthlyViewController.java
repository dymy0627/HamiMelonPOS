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
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MonthlyViewController implements Initializable {

	// FXML Object Def.
	@FXML
	private Label monthly_sales, monthly_lunch_sales, monthly_dinner_sales, monthly_inside_sales, monthly_outside_sales,
			monthly_deliver_sales, monthly_total_num, monthly_avg_sales, monthly_double_num, monthly_special_num,
			monthly_wind_rain_num, monthly_luxury_num;
	@FXML
	private VBox BarChartView1;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Connect Sql
		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		Map<String, String> curMonthReport = mySqlConnection.getMonthlyReport(GenerateDailyTask.getTodayDateMonth());
		int preMonth = Integer.parseInt(GenerateDailyTask.getTodayDateMonth()) - 1;
		String preMonthString = (preMonth > 10) ? String.valueOf(preMonth) : "0" + String.valueOf(preMonth);
		Map<String, String> preMonthReport = mySqlConnection.getMonthlyReport(preMonthString);
		mySqlConnection.disconnectSql();

		// DailyReportContent Controll
		DailyBusinessTable(curMonthReport);
		DailyBusinessGraphic(curMonthReport, preMonthReport);
	}

	private void DailyBusinessTable(Map<String, String> month) {
		int allTurnover = Integer.parseInt(month.get("Turnover")) + Integer.parseInt(month.get("L_Outsourcing"))
				+ Integer.parseInt(month.get("D_Outsourcing")) + Integer.parseInt(month.get("L_delivery"))
				+ Integer.parseInt(month.get("D_delivery"));
		monthly_sales.setText(String.valueOf(allTurnover));

		int monthInside = Integer.parseInt(month.get("Lunch_Turnover"))
				+ Integer.parseInt(month.get("Dinner_Turnover"));
		monthly_inside_sales.setText(Integer.toString(monthInside));

		monthly_lunch_sales.setText(month.get("Lunch_Turnover"));
		monthly_dinner_sales.setText(month.get("Dinner_Turnover"));

		int monthTogo = Integer.parseInt(month.get("L_Outsourcing")) + Integer.parseInt(month.get("D_Outsourcing"));
		monthly_outside_sales.setText(Integer.toString(monthTogo));

		int monthDelivery = Integer.parseInt(month.get("L_delivery")) + Integer.parseInt(month.get("D_delivery"));
		monthly_deliver_sales.setText(Integer.toString(monthDelivery));

		int monthTotalVisitor = Integer.parseInt(month.get("L_Number_of_visitors"))
				+ Integer.parseInt(month.get("D_Number_of_visitors"));
		monthly_total_num.setText(Integer.toString(monthTotalVisitor));

		double monthAvg = (Double.parseDouble(month.get("L_Average_consumption"))
				+ Double.parseDouble(month.get("D_Average_consumption")));
		monthly_avg_sales.setText(Double.toString(monthAvg));

		monthly_double_num.setText(month.get("Double_package"));
		monthly_special_num.setText(month.get("Special_meals"));
		monthly_wind_rain_num.setText(month.get("wind_and_rain"));
		monthly_luxury_num.setText("0");
	}

	private void DailyBusinessGraphic(Map<String, String> curMonthReport, Map<String, String> preMonthReport) {

		final String R_First = "本月";
		final String R_Second = "前月";
		final String GP_total = "總營業額";//
		final NumberAxis xAxis = new NumberAxis();
		final CategoryAxis yAxis = new CategoryAxis();
		final BarChart<Number, String> bc = new BarChart<Number, String>(xAxis, yAxis);
		bc.setTitle("月圖報表");
		xAxis.setTickLabelRotation(90);
		xAxis.setUpperBound(90);
		yAxis.setTickLabelGap(10);

		XYChart.Series<Number, String> series1 = new XYChart.Series<Number, String>();
		series1.setName(R_First);
		for (XYChart.Data<Number, String> i : setThisMonthData(curMonthReport)) {
			i.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					if (node != null) {
						displayLabelForData(i);
					}
				}
			});
			series1.getData().add(i);
		}

		XYChart.Series<Number, String> series2 = new XYChart.Series<Number, String>();
		series2.setName(R_Second);
		for (XYChart.Data<Number, String> i : setPreviousMonthData(preMonthReport)) {
			i.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					if (node != null) {
						displayLabelForData(i);
					}
				}

			});
			series2.getData().add(i);
		}

		XYChart.Series<Number, String> series3 = new XYChart.Series<Number, String>();
		series3.setName(GP_total);
		series3.getData().add(new XYChart.Data<Number, String>(100, GP_total));

		bc.setBarGap(3);
		bc.setCategoryGap(3);
		bc.getData().add(series1);
		bc.getData().add(series2);
		bc.getData().add(series3);

		VBox.setVgrow(bc, Priority.ALWAYS);
		BarChartView1.getChildren().add(bc);
	}

	/**
	 * yAxis Content Data
	 */
	private ArrayList<XYChart.Data<Number, String>> setThisMonthData(Map<String, String> curMonthReport) {
		int curAllTogo = Integer.parseInt(curMonthReport.get("L_Outsourcing"))
				+ Integer.parseInt(curMonthReport.get("D_Outsourcing"));
		int curAllDelivery = Integer.parseInt(curMonthReport.get("L_delivery"))
				+ Integer.parseInt(curMonthReport.get("D_delivery"));

		int curTotalTurnover = Integer.parseInt(curMonthReport.get("Turnover")) + curAllTogo + curAllDelivery;

		int curLunchTurnoverPersent = 0;
		int curDinnerTurnoverPersent = 0;

		int curTogoPersent = 0;
		int curDeliveryPersent = 0;

		int curPaircomboTurnoverPercent = 0;
		int curSpecialcomboTurnoverPercent = 0;
		int curWindfurcomboTurnoverPercent = 0;

		if (curTotalTurnover != 0) {
			curLunchTurnoverPersent = (Integer.parseInt(curMonthReport.get("Lunch_Turnover")) * 100 / curTotalTurnover);
			curDinnerTurnoverPersent = Integer.parseInt(curMonthReport.get("Dinner_Turnover")) * 100 / curTotalTurnover;

			curTogoPersent = (curAllTogo * 100) / curTotalTurnover;
			curDeliveryPersent = (curAllDelivery * 100) / curTotalTurnover;

			curPaircomboTurnoverPercent = Integer.parseInt(curMonthReport.get("Double_package")) * 100
					/ curTotalTurnover;
			curSpecialcomboTurnoverPercent = Integer.parseInt(curMonthReport.get("Special_meals")) * 100
					/ curTotalTurnover;
			curWindfurcomboTurnoverPercent = Integer.parseInt(curMonthReport.get("wind_and_rain")) * 100
					/ curTotalTurnover;
		}

		System.out.println("本月總營業額 " + curTotalTurnover + " 午餐占比 " + curLunchTurnoverPersent + " 晚餐占比 "
				+ curDinnerTurnoverPersent + " 外帶占比 " + curTogoPersent + " 外送占比 " + curDeliveryPersent);

		ArrayList<XYChart.Data<Number, String>> curMonthData = new ArrayList<XYChart.Data<Number, String>>();
		curMonthData.add(new XYChart.Data<Number, String>(curPaircomboTurnoverPercent, "雙人"));
		curMonthData.add(new XYChart.Data<Number, String>(curSpecialcomboTurnoverPercent, "特餐"));
		curMonthData.add(new XYChart.Data<Number, String>(curWindfurcomboTurnoverPercent, "楓雨"));
		curMonthData.add(new XYChart.Data<Number, String>(curTogoPersent, "外帶"));
		curMonthData.add(new XYChart.Data<Number, String>(curDeliveryPersent, "外送"));
		curMonthData.add(new XYChart.Data<Number, String>(curDinnerTurnoverPersent, "晚餐"));
		curMonthData.add(new XYChart.Data<Number, String>(curLunchTurnoverPersent, "午餐"));
		return curMonthData;
	}

	private ArrayList<XYChart.Data<Number, String>> setPreviousMonthData(Map<String, String> preMonthReport) {
		// yAxis Content Data
		int preAllTogo = Integer.parseInt(preMonthReport.get("L_Outsourcing"))
				+ Integer.parseInt(preMonthReport.get("D_Outsourcing"));
		int preAllDelivery = Integer.parseInt(preMonthReport.get("L_delivery"))
				+ Integer.parseInt(preMonthReport.get("D_delivery"));

		int preTotalTurnover = Integer.parseInt(preMonthReport.get("Turnover")) + preAllTogo + preAllDelivery;

		int preLunchPersent = 0;
		int preDinnerPersent = 0;

		int preTogoPersent = 0;
		int preDeliveryPersent = 0;

		int prePaircomboPercent = 0;
		int preSpecialcomboPercent = 0;
		int preWindfurcomboPercent = 0;

		if (preTotalTurnover != 0) {
			preLunchPersent = (Integer.parseInt(preMonthReport.get("Lunch_Turnover")) * 100 / preTotalTurnover);
			preDinnerPersent = Integer.parseInt(preMonthReport.get("Dinner_Turnover")) * 100 / preTotalTurnover;

			preTogoPersent = (preAllTogo * 100) / preTotalTurnover;
			preDeliveryPersent = (preAllDelivery * 100) / preTotalTurnover;

			prePaircomboPercent = Integer.parseInt(preMonthReport.get("Double_package")) * 100 / preTotalTurnover;
			preSpecialcomboPercent = Integer.parseInt(preMonthReport.get("Special_meals")) * 100 / preTotalTurnover;
			preWindfurcomboPercent = Integer.parseInt(preMonthReport.get("wind_and_rain")) * 100 / preTotalTurnover;
		}

		System.out.println("前月總營業額 " + preTotalTurnover + " 午餐占比 " + preLunchPersent + " 晚餐占比 " + preDinnerPersent
				+ " 外帶占比 " + preTogoPersent + " 外送占比 " + preDeliveryPersent);

		ArrayList<XYChart.Data<Number, String>> preMonthData = new ArrayList<XYChart.Data<Number, String>>();
		preMonthData.add(new XYChart.Data<Number, String>(prePaircomboPercent, "雙人"));
		preMonthData.add(new XYChart.Data<Number, String>(preSpecialcomboPercent, "特餐"));
		preMonthData.add(new XYChart.Data<Number, String>(preWindfurcomboPercent, "楓雨"));
		preMonthData.add(new XYChart.Data<Number, String>(preTogoPersent, "外帶"));
		preMonthData.add(new XYChart.Data<Number, String>(preDeliveryPersent, "外送"));
		preMonthData.add(new XYChart.Data<Number, String>(preDinnerPersent, "晚餐"));
		preMonthData.add(new XYChart.Data<Number, String>(preLunchPersent, "午餐"));
		return preMonthData;
	}

	private void displayLabelForData(XYChart.Data<Number, String> data) {
		final Node node = data.getNode();
		final Text dataText = new Text(data.getXValue() + "");
		node.parentProperty().addListener(new ChangeListener<Parent>() {
			public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
				Group parentGroup = (Group) parent;
				parentGroup.getChildren().add(dataText);
			}
		});

		node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
				dataText.setLayoutX(Math.round(bounds.getMinX() + bounds.getWidth() + dataText.prefWidth(-1) * 0.3));
				dataText.setLayoutY(
						Math.round(bounds.getMinY() - dataText.prefHeight(-1) * 0.5 + dataText.prefHeight(-1)));
			}
		});
	}
}
