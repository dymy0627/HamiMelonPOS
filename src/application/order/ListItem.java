package application.order;

public class ListItem {
	private MenuBean menuBean;
	private int number = 0;

	public ListItem(MenuBean menuBean) {
		this.menuBean = menuBean;
		this.number = 1;
	}

	public String getId() {
		return menuBean.getId();
	}

	public MenuBean getMeal() {
		return menuBean;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
