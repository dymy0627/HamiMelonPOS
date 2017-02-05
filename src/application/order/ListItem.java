package application.order;

public class ListItem {
	private Meal meal;
	private int number = 0;

	public ListItem(Meal meal) {
		this.meal = meal;
		this.number = 1;
	}

	public String getId() {
		return meal.getId();
	}

	public Meal getMeal() {
		return meal;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
