package it.polito.tdp.food.model;

public class FoodCalories implements Comparable<FoodCalories>{
	Food f;
	Double calorie;
	public FoodCalories(Food f, Double calorie) {
		super();
		this.f = f;
		this.calorie = calorie;
	}
	public Food getF() {
		return f;
	}
	public void setF(Food f) {
		this.f = f;
	}
	public Double getCalorie() {
		return calorie;
	}
	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}
	@Override
	public int compareTo(FoodCalories o) {
		return (int) -(this.calorie-o.calorie);
	}
	
	

}
