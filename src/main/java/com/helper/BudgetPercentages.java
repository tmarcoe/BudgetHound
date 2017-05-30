package com.helper;

public class BudgetPercentages {
	
	private String Category;
	private double total;
	private double percent;
	
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getPercent() {
		return percent;
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
	public BudgetPercentages(String category, double total, double percent) {
		Category = category;
		this.total = total;
		this.percent = percent;
	}
}
