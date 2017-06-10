package db.bean;

public class StockListBean {
	private String name;
	private int purchaseNum;
	private int shippingNum;
	private int reserveNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(int purchaseNum) {
		this.purchaseNum = purchaseNum;
	}

	public int getShippingNum() {
		return shippingNum;
	}

	public void setShippingNum(int shippingNum) {
		this.shippingNum = shippingNum;
	}

	public int getReserveNum() {
		return reserveNum;
	}

	public void setReserveNum(int reserveNum) {
		this.reserveNum = reserveNum;
	}
}
