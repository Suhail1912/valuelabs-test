package com.vl.tracker.model;

import java.util.Date;


public class TrackingRequestModel {

	private String originCountry;

    private String destinationCountry;

    private Double weight;

    private Date createdDateTime;
    
    private String customerId;

    private String customerName;

    private String customerSlug;
    
    

	public TrackingRequestModel(String originCountry, String destinationCountry, Double weight, Date createdDateTime,
			String customerId, String customerName, String customerSlug) {
		super();
		this.originCountry = originCountry;
		this.destinationCountry = destinationCountry;
		this.weight = weight;
		this.createdDateTime = createdDateTime;
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerSlug = customerSlug;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSlug() {
		return customerSlug;
	}

	public void setCustomerSlug(String customerSlug) {
		this.customerSlug = customerSlug;
	}
 
    
    
}
