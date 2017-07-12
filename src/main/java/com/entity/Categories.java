package com.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Categories implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String category;
	private int household_id;
	private String parent;
	private double ceiling;
	private int level;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getHousehold_id() {
		return household_id;
	}
	public void setHousehold_id(int household_id) {
		this.household_id = household_id;
	}	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public double getCeiling() {
		return ceiling;
	}
	public void setCeiling(double ceiling) {
		this.ceiling = ceiling;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
