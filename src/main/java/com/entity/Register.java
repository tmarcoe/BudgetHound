package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Register implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int entry_id;
	private int household_id;
	private Date trans_date;
	private String recipient;
	private String description;
	private double withdrawal;
	private double deposit;
	private String category;
	
	public int getEntry_id() {
		return entry_id;
	}
	public void setEntry_id(int entry_id) {
		this.entry_id = entry_id;
	}
	public int getHousehold_id() {
		return household_id;
	}
	public void setHousehold_id(int household_id) {
		this.household_id = household_id;
	}
	public Date getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getWithdrawal() {
		return withdrawal;
	}
	public void setWithdrawal(double withdrawal) {
		this.withdrawal = withdrawal;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
