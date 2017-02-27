package application.stock;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class StockListViewCell extends ListCell<StockBean> {

	@FXML
	private HBox stockItem;

	@FXML
	private Label nameLabel;
	@FXML
	private Label quantityLabel;
	@FXML
	private Label amountLabel;
	@FXML
	private Label manufacturerLabel;

	@FXML
	private Button deleteButton;

	private FXMLLoader mFXMLLoader;

	private StockListViewListener mListener;

	public StockListViewCell(StockListViewListener listener) {
		this.mListener = listener;
	}

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
			nameLabel.setText(stock.getName());
			quantityLabel.setText(String.valueOf(stock.getQuantity()) + stock.getUnit());
			amountLabel.setText("$ " + String.valueOf(stock.getAmount()));
			manufacturerLabel.setText(stock.getManufacturer());

			deleteButton.setVisible(false);
			StockBean selectedItem = getListView().getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				ObservableList<StockBean> listItem = getListView().getItems();
				if (listItem.indexOf(stock) == listItem.indexOf(selectedItem)) {
					deleteButton.setVisible(true);
				}
			}

			setText(null);
			setGraphic(stockItem);
		}
	}

	@FXML
	protected void itemDeleteAction(ActionEvent event) {
		mListener.delete();
	}
}
