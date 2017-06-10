package application.stock;

import java.io.IOException;

import db.bean.StockListBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class StockListViewCell extends ListCell<StockListBean> {

	@FXML
	private HBox stockItem;

	@FXML
	private Label nameLabel;
	@FXML
	private Label purchaseLabel;
	@FXML
	private Label shippingLabel;
	@FXML
	private Label reserveLabel;

	private FXMLLoader mFXMLLoader;

	@Override
	protected void updateItem(StockListBean stock, boolean empty) {
		super.updateItem(stock, empty);
		if (empty || stock == null) {
			setText(null);
			setGraphic(null);

		} else { // all the other code goes in the else block
			if (mFXMLLoader == null) {
				mFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/StockListCell.fxml"));
				mFXMLLoader.setController(this);
				try {
					mFXMLLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			nameLabel.setText(stock.getName());
			purchaseLabel.setText(String.valueOf(stock.getPurchaseNum()));
			shippingLabel.setText(String.valueOf(stock.getShippingNum()));
			reserveLabel.setText(String.valueOf(stock.getReserveNum()));

			setText(null);
			setGraphic(stockItem);
		}
	}
}
