package com.money2020.easypzy;

public class Product {
	String barcodeId;
	String company;
	String productName;
	float price;
	String storeName;
	
	public String toString(){
		return "Product informations is: " + barcodeId +"," + company + "," + productName + "," + price + "," + storeName;
	}
	
	public String getBarcodeId() {
		return barcodeId;
	}
	public void setBarcodeId(String barcodeId) {
		this.barcodeId = barcodeId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}
