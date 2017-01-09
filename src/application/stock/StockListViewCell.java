package application.stock;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class StockListViewCell extends ListCell<StockBean> {
	@FXML
	private Label manufacturerLabel;
	@FXML
	private Label nameLabel;
	@FXML
	private Label amountLabel;
	@FXML
	private HBox stockItem;

	private FXMLLoader mFXMLLoader;

	@Override
	protected void updateItem(StockBean stock, boolean empty) {
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
			manufacturerLabel.setText(stock.getManufacturer());
			nameLabel.setText(stock.getName());
			amountLabel.setText(String.valueOf(stock.getAmount()));
			setText(null);
			setGraphic(stockItem);
		}
	}

}
