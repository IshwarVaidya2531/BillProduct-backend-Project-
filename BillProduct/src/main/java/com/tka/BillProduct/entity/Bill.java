package com.tka.BillProduct.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class Bill {
	
	private int productId;
	private String name;
	private String productType;
	private String category;
	private double baseprice;
	private double discount;
	private double  finalPrice;
	
	@Autowired
	Charges charges;
	
	
	
	
	public Bill() {
		super();
		this.finalPrice = 0;
		
	}

	public Bill(int productId, String name, String productType, String category, double baseprice, double discount,
			double finalPrice, Charges charges) {
		super();
		this.productId = productId;
		this.name = name;
		this.productType = productType;
		this.category = category;
		this.baseprice = baseprice;
		this.discount = discount;
		this.finalPrice = finalPrice;
		this.charges = charges;
	}

	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getBaseprice() {
		return baseprice;
	}

	public void setBaseprice(double baseprice) {
		this.baseprice = baseprice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Charges getCharges() {
		return charges;
	}

	public void setCharges(Charges charges) {
		this.charges = charges;
	}

	public double getFinalPrice() {
		return finalPrice;
	}
	
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
		return "Bill [ProductId=" + productId + ", name=" + name + ", ProductType=" + productType + ", category="
				+ category + ", baseprice=" + baseprice + ", discount=" + discount + ", finalPrice=" + finalPrice
				+ ", charges=" + charges + "]";
	}



}
