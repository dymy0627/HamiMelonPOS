package application.order;

public class ListItem {
	private MealBean meal;
	private int number = 0;

	public ListItem(MealBean meal) {
		this.meal = meal;
		this.number = 1;
	}

	public String getId() {
		return meal.getId();
	}

	public MealBean getMeal() {
		return meal;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
