package com.tka.BillProduct.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductCharges {

	@Id
	private String productCatgory;
	private int discount;
	private int gST;
	private int deliveryCharge;

	public ProductCharges(int discount, int gST, int deliveryCharge) {
		super();
		this.discount = discount;
		this.gST = gST;
		this.deliveryCharge = deliveryCharge;
	}



	public ProductCharges() {
		super();
		
	}

	public String getProductCatgory() {
		return productCatgory;
	}

	public void setProductCatgory(String productCatgory) {
		this.productCatgory = productCatgory;
	}



	public int getDiscount() {
		return discount;
	}



	public void setDiscount(int discount) {
		this.discount = discount;
	}



	public int getgST() {
		return gST;
	}



	public void setgST(int gST) {
		this.gST = gST;
	}



	public int getDeliveryCharge() {
		return deliveryCharge;
	}



	public void setDeliveryCharge(int deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}



	@Override
	public String toString() {
		return "ProductCharges [productCatgory=" + productCatgory + ", discount=" + discount + ", gST=" + gST
				+ ", deliveryCharge=" + deliveryCharge + "]";
	}




	
	
	
	
	
}
