package application.order;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class MenuListViewCell extends ListCell<ListItem> {

	@FXML
	private HBox menuItem;

	@FXML
	private Label setLabel;
	@FXML
	private Label nameLabel;
	@FXML
	private Label priceLabel;
	@FXML
	private Label countLabel;
	@FXML
	private Label totalLabel;

	private FXMLLoader mFXMLLoader;

	@Override
	protected void updateItem(ListItem listItem, boolean empty) {
		super.updateItem(listItem, empty);
		if (empty || listItem == null) {
			setText(null);
			setGraphic(null);

		} else { // all the other code goes in the else block
			if (mFXMLLoader == null) {
				mFXMLLoader = new FXMLLoader(getClass().getResource("/fxml/MenuListCell.fxml"));
				mFXMLLoader.setController(this);
				try {
					mFXMLLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			MenuBean menu = listItem.getMenuBean();
			setLabel.setText(menu.getSet());
			nameLabel.setText(menu.getName());
			priceLabel.setText(String.valueOf(menu.getPrice()));
			countLabel.setText("*" + String.valueOf(listItem.getNumber()));
			totalLabel.setText(String.valueOf(menu.getPrice() * listItem.getNumber()));

			setText(null);
			setGraphic(menuItem);
		}
	}

}
