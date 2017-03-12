package application.stock;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import application.MainScene;
import db.MySqlConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.Callback;

public class PurchaseAndStockController implements Initializable, PurchaseListViewListener {

	@FXML
	private ComboBox<String> purchaseNameComboBox;
	@FXML
	private TextField purchaseNameTextField;
	@FXML
	private Button purchaseNameButton;
	@FXML
	private TextField purchaseQuantityTextField;
	@FXML
	private TextField purchaseAmountTextField;
	@FXML
	private TextField purchaseManufacturerTextField;
	@FXML
	private Button purchaseSaveButton;

	@FXML
	private ListView<PurchaseBean> purchaseListView;
	private ObservableList<PurchaseBean> mPurchaseObservableList = FXCollections.observableArrayList();
	private Map<Integer, PurchaseBean> mPurchaseAddMap = new HashMap<>();

	@FXML
	private TitledPane stockTitledPane;
	@FXML
	private ListView<StockListBean> stockListView;
	private ObservableList<StockListBean> mStockListBeanObservableList = FXCollections.observableArrayList();
	private List<StockBean> stockArrayList;
	private List<StockListBean> stockListArrayList = new ArrayList<StockListBean>();

	@FXML
	private ProgressIndicator myProgressIndicator;

	private int tempId = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		purchaseListView.setItems(mPurchaseObservableList);
		purchaseListView.setCellFactory(new Callback<ListView<PurchaseBean>, ListCell<PurchaseBean>>() {
			@Override
			public ListCell<PurchaseBean> call(ListView<PurchaseBean> parms) {
				return new PurchaseListViewCell(PurchaseAndStockController.this);
			}
		});
		purchaseListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PurchaseBean>() {
			@Override
			public void changed(ObservableValue<? extends PurchaseBean> observable, PurchaseBean oldValue,
					PurchaseBean newValue) {
				if (observable.getValue() != null) {
					updatePurchaseListView();
				}
			}
		});

		stockListView.setItems(mStockListBeanObservableList);
		stockListView.setCellFactory(new Callback<ListView<StockListBean>, ListCell<StockListBean>>() {
			@Override
			public ListCell<StockListBean> call(ListView<StockListBean> parms) {
				return new StockListViewCell();
			}
		});

		myProgressIndicator.setVisible(false);
		new Thread(new Task<Boolean>() {
			private List<PurchaseBean> purchaseList;

			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);
				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				purchaseList = mySqlConnection.selectAllPurchase();
				stockArrayList = mySqlConnection.selectAllStock();
				mySqlConnection.disconnectSql();

				mPurchaseObservableList.setAll(purchaseList);

				List<String> purchaseNameList = new ArrayList<String>();
				for (StockBean stock : stockArrayList) {
					String stockName = stock.getName();
					int purchaseNum = 0;
					for (PurchaseBean purchase : purchaseList) {
						if (purchase.getName().equals(stockName)) {
							purchaseNum += purchase.getQuantity();
						}
					}
					int reserveNum = stock.getReserve();
					StockListBean stockListBean = new StockListBean();
					stockListBean.setName(stockName);
					stockListBean.setPurchaseNum(purchaseNum);
					stockListBean.setShippingNum(purchaseNum - reserveNum);
					stockListBean.setReserveNum(reserveNum);
					purchaseNameList.add(stock.getName());
					stockListArrayList.add(stockListBean);
				}
				purchaseNameList.add("其他");
				purchaseNameComboBox.getItems().setAll(purchaseNameList);
				mStockListBeanObservableList.setAll(stockListArrayList);

				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				purchaseNameComboBox.getSelectionModel().selectFirst();
				myProgressIndicator.setVisible(false);
				System.out.println("Load DB Succeeded!");
			}

			@Override
			protected void failed() {
				super.failed();
				myProgressIndicator.setVisible(false);
				System.out.println("Load DB Failed!");
			}
		}).start();

		purchaseNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldSelected, String newSelected) {
				if (newSelected.equals("其他")) {
					purchaseNameComboBox.setVisible(false);
					purchaseNameTextField.setVisible(true);
					purchaseNameButton.setVisible(true);
				}
			}
		});

		stockTitledPane.setExpanded(true);
	}

	private void updatePurchaseListView() {
		purchaseListView.setItems(null);
		purchaseListView.setItems(mPurchaseObservableList);
	}

	private boolean validCheck(int value) {
		return value > 0;
	}

	private void cleanAllPurchaseTextField() {
		purchaseQuantityTextField.setText("");
		purchaseAmountTextField.setText("");
		purchaseManufacturerTextField.setText("");
	}

	@Override
	public void delete() {
		deletePurchaseFromList(purchaseListView.getSelectionModel().getSelectedItem());
		purchaseListView.getSelectionModel().clearSelection();
	}

	@FXML
	protected void AddPurchaseAction(ActionEvent event) {

		String purchaseName;
		if (purchaseNameComboBox.isVisible()) {
			purchaseName = purchaseNameComboBox.getSelectionModel().getSelectedItem();
		} else {
			purchaseName = purchaseNameTextField.getText();
		}

		int purchaseQuantity = 0;
		int purchaseAmount = 0;
		try {
			purchaseQuantity = Integer.parseInt(purchaseQuantityTextField.getText());
			purchaseAmount = Integer.parseInt(purchaseAmountTextField.getText());
		} catch (NumberFormatException e) {
			System.out.println("Quantity and Amount cannot be String.");
		}

		if (!purchaseName.isEmpty() && validCheck(purchaseAmount) && validCheck(purchaseQuantity)) {
			PurchaseBean purchase = new PurchaseBean();
			purchase.setId(tempId++);
			purchase.setName(purchaseName);
			purchase.setQuantity(purchaseQuantity);
			purchase.setUnit("份");
			purchase.setAmount(purchaseAmount);
			purchase.setManufacturer(purchaseManufacturerTextField.getText().toString());
			purchase.setDeletable(true);
			purchase.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			addPurchaseToList(purchase);
			cleanAllPurchaseTextField();
		}
	}

	private void deletePurchaseFromList(PurchaseBean purchase) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (mPurchaseAddMap.containsKey(purchase.getId())) {
					mPurchaseAddMap.remove(purchase.getId());
				}
				mPurchaseObservableList.remove(purchase);
				System.out.println("Remove from Purchase List =" + purchase.getId());
			}
		});
	}

	private void addPurchaseToList(PurchaseBean purchase) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mPurchaseAddMap.put(purchase.getId(), purchase);
				mPurchaseObservableList.add(purchase);
				System.out.println("Add to Purchase List =" + purchase.getId());
			}
		});
	}

	@FXML
	protected void ShowComboBoxAction(ActionEvent event) {
		purchaseNameComboBox.setVisible(true);
		purchaseNameComboBox.getSelectionModel().selectFirst();
		purchaseNameTextField.setVisible(false);
		purchaseNameTextField.setText("");
		purchaseNameButton.setVisible(false);
	}

	@FXML
	protected void PurchaseSaveAction(ActionEvent event) {
		if (mPurchaseAddMap.isEmpty())
			return;
		new Thread(new Task<Boolean>() {

			private List<PurchaseBean> purchaseList;

			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);
				purchaseSaveButton.setDisable(true);

				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();

				for (Map.Entry<Integer, PurchaseBean> entry : mPurchaseAddMap.entrySet()) {
					PurchaseBean purchase = entry.getValue();
					mySqlConnection.insertPurchaseData(purchase);

					String meatId = MainScene.getMeatClassIdByName(purchase.getName());
					int reserveNum = mySqlConnection.getStockReserveById(meatId);
					System.out.println("reserveNum=" + reserveNum);

					int newPurchaseNum = purchase.getQuantity();
					System.out.println("purchaseNum=" + newPurchaseNum);

					int currentReserve = reserveNum + newPurchaseNum;
					mySqlConnection.updateStockReserveById(meatId, currentReserve);

					stockListArrayList.replaceAll(new UnaryOperator<StockListBean>() {
						@Override
						public StockListBean apply(StockListBean stockListBean) {
							if (purchase.getName().equals(stockListBean.getName())) {
								stockListBean.setReserveNum(currentReserve);
								System.out.println("setReserveNum=" + currentReserve);

								int currentPurchaseNum = stockListBean.getPurchaseNum() + newPurchaseNum;
								stockListBean.setPurchaseNum(currentPurchaseNum);
								System.out.println("setPurchaseNum=" + currentPurchaseNum);

								int shippingNum = currentPurchaseNum - currentReserve;
								stockListBean.setShippingNum(shippingNum);
								System.out.println("setShippingNum=" + shippingNum);
							}
							return stockListBean;
						}
					});
				}

				purchaseList = mySqlConnection.selectAllPurchase();

				mySqlConnection.disconnectSql();
				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				myProgressIndicator.setVisible(false);
				purchaseSaveButton.setDisable(false);
				mPurchaseAddMap.clear();
				mPurchaseObservableList.setAll(purchaseList);
				mStockListBeanObservableList.setAll(stockListArrayList);
				System.out.println("DB Save Done!");
			}

			@Override
			protected void failed() {
				super.failed();
				myProgressIndicator.setVisible(false);
				purchaseSaveButton.setDisable(false);
				System.out.println("DB Save Failed!");
			}
		}).start();
	}

	@FXML
	protected void LeaveButtonAction(ActionEvent event) throws IOException {
		Parent mainStage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		MainScene.changeScene(mainStage);
	}
}
