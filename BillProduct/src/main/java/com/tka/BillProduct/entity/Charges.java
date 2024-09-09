package com.tka.BillProduct.entity;

public class Charges {
 private double gst;
 private double deliveryCherges;
 
 
 
public double getGST() {
	return gst;
}
public void setGST(double gST) {
	this.gst = gST;
}
public double getDeliveryCherges() {
	return deliveryCherges;
}
public void setDeliveryCherges(double deliveryCherges) {
	this.deliveryCherges = deliveryCherges;
}


@Override
public String toString() {
	return "Charges [GST=" + gst + ", DeliveryCherges=" + deliveryCherges + "]";
}
 

}
