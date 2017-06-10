package application.stock;

import java.io.IOException;

import db.bean.PurchaseBean;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class PurchaseListViewCell extends ListCell<PurchaseBean> {

	@FXML
	private HBox purchaseItem;

	@FXML
	private Label nameLabel;
	@FXML
	private Label quantityLabel;
	@FXML
	private Label amountLabel;
	@FXML
	private Label manufacturerLabel;
	@FXML
	private Label dateLabel;

	@FXML
	private Button deleteButton;

	private FXMLLoader mFXMLLoader;

	private PurchaseListViewListener mListener;

	public PurchaseListViewCell(PurchaseListViewListener listener) {
		this.mListener = listener;
	}

	@Override
	protected void updateItem(PurchaseBean purchase, boolean empty) {
		super.updateItem(purchase, empty);
		if (empty || purchase == null) {
			setText(null);
			setGraphic(null);

		} else { // all the other code goes in the else block
			if (mFXMLLoader == null) {
				mFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/PurchaseListCell.fxml"));
				mFXMLLoader.setController(this);
				try {
					mFXMLLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			nameLabel.setText(purchase.getName());
			quantityLabel.setText(String.valueOf(purchase.getQuantity()) + " " + purchase.getUnit());
			amountLabel.setText("$ " + String.valueOf(purchase.getAmount()));
			manufacturerLabel.setText(purchase.getManufacturer());
			String date = purchase.getDate();
			dateLabel.setText(date.substring(0, date.indexOf(" ")));

			deleteButton.setVisible(false);
			PurchaseBean selectedItem = getListView().getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				ObservableList<PurchaseBean> listItem = getListView().getItems();
				if (purchase.isDeletable() && listItem.indexOf(purchase) == listItem.indexOf(selectedItem)) {
					deleteButton.setVisible(true);
				}
			}

			setText(null);
			setGraphic(purchaseItem);
		}
	}

	@FXML
	protected void itemDeleteAction(ActionEvent event) {
		mListener.delete();
	}
}
