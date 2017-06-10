package db.bean;

public class CartListBean {
	private MenuBean menuBean;
	private int number = 0;

	public CartListBean(MenuBean menuBean) {
		this.menuBean = menuBean;
		this.number = 1;
	}

	public String getId() {
		return menuBean.getId();
	}

	public MenuBean getMenuBean() {
		return menuBean;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
