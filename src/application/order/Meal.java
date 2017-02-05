package application.order;

public class Meal {
	private String id;
	private String set;
	private String name;
	private int price;

	public Meal(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}