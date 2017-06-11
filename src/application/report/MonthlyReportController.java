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
import db.MySqlConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MonthlyReportController implements Initializable {

	@FXML
	private Label monthly_sales, monthly_lunch_sales, monthly_dinner_sales;
	@FXML
	private Label monthly_inside_sales, monthly_outside_sales, monthly_deliver_sales, monthly_total_num,
			monthly_avg_sales;
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
	private Map<String, String> p_month;
	private ArrayList<XYChart.Data<Integer, String>> T_MonthData = new ArrayList<XYChart.Data<Integer, String>>();
	private ArrayList<XYChart.Data<Integer, String>> P_MonthData = new ArrayList<XYChart.Data<Integer, String>>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getData();
	}

	private void getData() {
		myProgressIndicator.setVisible(false);
		new Thread(new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);
				label_back_door.setDisable(true);

				if (needUpdate)
					new GenerateDailyTask().run();

				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				// month = mySqlConnection.getMonthlyReport();
				int preMonth = Integer.parseInt(GenerateDailyTask.getTodayDateMonth()) - 1;
				String preMonthString = (preMonth > 10) ? String.valueOf(preMonth) : "0" + String.valueOf(preMonth);
				p_month = mySqlConnection.getMonthlyReport(preMonthString);
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

				int all_turnover = Integer.parseInt(month.get("Turnover"))
						+ Integer.parseInt(month.get("L_Outsourcing")) + Integer.parseInt(month.get("D_Outsourcing"))
						+ Integer.parseInt(month.get("L_delivery")) + Integer.parseInt(month.get("D_delivery"));
				monthly_sales.setText(month.get("Turnover"));

				int month_inside = Integer.parseInt(month.get("Lunch_Turnover"))
						+ Integer.parseInt(month.get("Dinner_Turnover"));
				monthly_inside_sales.setText(Integer.toString(month_inside));

				monthly_lunch_sales.setText(month.get("Lunch_Turnover"));
				monthly_dinner_sales.setText(month.get("Dinner_Turnover"));

				int month_togo = Integer.parseInt(month.get("L_Outsourcing"))
						+ Integer.parseInt(month.get("D_Outsourcing"));
				monthly_outside_sales.setText(Integer.toString(month_togo));

				int month_delivery = Integer.parseInt(month.get("L_delivery"))
						+ Integer.parseInt(month.get("D_delivery"));
				monthly_deliver_sales.setText(Integer.toString(month_delivery));

				int month_total_visit = Integer.parseInt(month.get("L_Number_of_visitors"))
						+ Integer.parseInt(month.get("D_Number_of_visitors"));
				monthly_total_num.setText(Integer.toString(month_total_visit));

				double month_avg = (Double.parseDouble(month.get("L_Average_consumption"))
						+ Double.parseDouble(month.get("D_Average_consumption"))) / 2;
				// System.out.println(month_avg);
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

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	private void BarChartCreat() {

		String R_First = "本月";
		String R_Second = "前月";
		String R_Third = "總營業額";

		setThisMonthData();
		setPreviousMonthData();

		final String GP_total = "總營業額";//
		final NumberAxis xAxis = new NumberAxis();
		final CategoryAxis yAxis = new CategoryAxis();
		final BarChart<Number, String> bc = new BarChart<Number, String>(xAxis, yAxis);
		bc.setTitle("月圖報表");
		xAxis.setTickLabelRotation(90);
		xAxis.setUpperBound(90);
		yAxis.setTickLabelGap(10);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName(R_First);
		for (XYChart.Data<Integer, String> i : T_MonthData) {
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

		XYChart.Series series2 = new XYChart.Series();
		series2.setName(R_Second);
		for (XYChart.Data<Integer, String> i : P_MonthData) {
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

		XYChart.Series series3 = new XYChart.Series();
		series3.setName(R_Third);
		series3.getData().add(new XYChart.Data(100, GP_total));

		bc.setBarGap(3);
		bc.setCategoryGap(3);
		bc.getData().addAll(series1, series2, series3);

		BarChartView1.setVgrow(bc, Priority.ALWAYS);
		BarChartView1.getChildren().add(bc);

	}

	private void setPreviousMonthData() {
		// yAxis Content Data
		int all_togo = Integer.parseInt(month.get("L_Outsourcing")) + Integer.parseInt(month.get("D_Outsourcing"));
		int all_delivery = Integer.parseInt(month.get("L_delivery")) + Integer.parseInt(month.get("D_delivery"));

		int total_turnover = Integer.parseInt(month.get("Turnover")) + all_togo + all_delivery;

		int lunch_persent = (Integer.parseInt(month.get("Lunch_Turnover")) * 100 / total_turnover);
		int dinner_persent = Integer.parseInt(month.get("Dinner_Turnover")) * 100 / total_turnover;

		int togo_persent = (all_togo * 100) / total_turnover;
		int delivery_persent = (all_delivery * 100) / total_turnover;

		int paircombo_percent = Integer.parseInt(month.get("Double_package")) * 100 / total_turnover;
		int specialcombo_percent = Integer.parseInt(month.get("Special_meals")) * 100 / total_turnover;
		int windfurcombo_percent = Integer.parseInt(month.get("wind_and_rain")) * 100 / total_turnover;

		System.out.println("本月總營業額 " + total_turnover + " 午餐占比 " + lunch_persent + " 晚餐占比 " + dinner_persent + " 外帶占比 "
				+ togo_persent + " 外送占比 " + delivery_persent);

		P_MonthData.add(new XYChart.Data<Integer, String>(paircombo_percent, "雙人"));
		P_MonthData.add(new XYChart.Data<Integer, String>(specialcombo_percent, "特餐"));
		P_MonthData.add(new XYChart.Data<Integer, String>(windfurcombo_percent, "楓雨"));
		P_MonthData.add(new XYChart.Data<Integer, String>(togo_persent, "外帶"));
		P_MonthData.add(new XYChart.Data<Integer, String>(delivery_persent, "外送"));
		P_MonthData.add(new XYChart.Data<Integer, String>(dinner_persent, "晚餐"));
		P_MonthData.add(new XYChart.Data<Integer, String>(lunch_persent, "午餐"));

	}

	private void setThisMonthData() {// yAxis Content Data
		int all_togo = Integer.parseInt(month.get("L_Outsourcing")) + Integer.parseInt(month.get("D_Outsourcing"));
		int all_delivery = Integer.parseInt(month.get("L_delivery")) + Integer.parseInt(month.get("D_delivery"));

		int total_turnover = Integer.parseInt(month.get("Turnover"));

		int lunch_persent = (Integer.parseInt(month.get("Lunch_Turnover")) * 100 / total_turnover);
		int dinner_persent = Integer.parseInt(month.get("Dinner_Turnover")) * 100 / total_turnover;

		int togo_persent = (all_togo * 100) / total_turnover;
		int delivery_persent = (all_delivery * 100) / total_turnover;

		int paircombo_percent = Integer.parseInt(month.get("Double_package")) * 100 / total_turnover;
		int specialcombo_percent = Integer.parseInt(month.get("Special_meals")) * 100 / total_turnover;
		int windfurcombo_percent = Integer.parseInt(month.get("wind_and_rain")) * 100 / total_turnover;

		System.out.println("本月總營業額 " + total_turnover + " 午餐占比 " + lunch_persent + " 晚餐占比 " + dinner_persent + " 外帶占比 "
				+ togo_persent + " 外送占比 " + delivery_persent);

		T_MonthData.add(new XYChart.Data<Integer, String>(paircombo_percent, "雙人"));
		T_MonthData.add(new XYChart.Data<Integer, String>(specialcombo_percent, "特餐"));
		T_MonthData.add(new XYChart.Data<Integer, String>(windfurcombo_percent, "楓雨"));
		T_MonthData.add(new XYChart.Data<Integer, String>(togo_persent, "外帶"));
		T_MonthData.add(new XYChart.Data<Integer, String>(delivery_persent, "外送"));
		T_MonthData.add(new XYChart.Data<Integer, String>(dinner_persent, "晚餐"));
		T_MonthData.add(new XYChart.Data<Integer, String>(lunch_persent, "午餐"));

	}

	private void displayLabelForData(XYChart.Data<Integer, String> data) {
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

	@FXML
	protected void BackButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		MainScene.changeScene(mainstage);
	}

	@SuppressWarnings("deprecation")
	@FXML
	protected void CountTurnoverAction(ActionEvent event) throws IOException {
		// Blank workbook
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("TurnOver Data");
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);

		// This data needs to be written (Object[])
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		Calendar T_Calendar = Calendar.getInstance();

		String systemtime = GenerateDailyTask.getDateTime();
		String[] timeArray = systemtime.split("-");
		String time = timeArray[0] + "-" + timeArray[1];

		data.put(1, new Object[] { "", "", "本月營業額", "", "" });
		workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
		workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0, 0, 5, 6));

		data.put(2, new Object[] { time, "", "午餐", "晚餐", "合計" });
		for (int m = 1; m <= 31; m++) {

			T_Calendar.set(Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]) - 1, m);
			String day = "";
			switch (T_Calendar.getTime().getDay() == 0 ? 7 : T_Calendar.getTime().getDay()) {
			case 1:
				day = "一";
				break;
			case 2:
				day = "二";
				break;
			case 3:
				day = "三";
				break;
			case 4:
				day = "四";
				break;
			case 5:
				day = "五";
				break;
			case 6:
				day = "六";
				break;
			case 7:
				day = "日";
				break;
			}

			data.put(m + 2, new Object[] { m, day, 0, 0, 0 });
		}

		int rownum = 0;
		for (Integer key = 1; key <= data.keySet().size(); key++) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				cell.setCellStyle(style);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);

			}
		}

		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		ArrayList<String[]> turnover_array = new ArrayList<String[]>();
		turnover_array = mySqlConnection.getMonthlyTurnOver(time);
		mySqlConnection.disconnectSql();
		for (String[] Read_Value_A : turnover_array) {

			// System.out.println(Read_Value_A[0] + " " + Read_Value_A[1] + " "
			// + Read_Value_A[2] + " ");
			String[] Date_A = Read_Value_A[0].split("-");
			int lunch_total = Integer.parseInt(Read_Value_A[1]);
			int dinner_total = Integer.parseInt(Read_Value_A[2]);
			int day_total = lunch_total + dinner_total;
			// System.out.println("data " + Date_A[2] + " " + lunch_total + " "
			// + dinner_total + " ");
			// data.replace(,new Object[] {lunch_total,dinner_total,day_total }
			// );

			Row header = sheet.getRow(Integer.parseInt(Date_A[2]) + 1);
			header.getCell(2).setCellValue(lunch_total);
			header.getCell(3).setCellValue(dinner_total);
			header.getCell(4).setCellValue(day_total);

		}
		// String systemtime = Task.getDateTime();
		System.out.println(Calendar.MONTH - 1);

		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(time + " 營業額.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("輸出成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
