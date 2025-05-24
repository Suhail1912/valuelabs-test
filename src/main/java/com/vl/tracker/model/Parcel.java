package com.vl.tracker.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Parcel {

	@JsonProperty("origin_country")
	private String originCountry;

	@JsonProperty("destination_country")
    private String destinationCountry;

	@JsonProperty("weight")
    private Double weight;

	@JsonProperty("created_at")
    private Date createdDateTime;
    
	@JsonProperty("customer_id")
    private String customerId;

	@JsonProperty("customer_name")
    private String customerName;

	@JsonProperty("customer_slug")
    private String customerSlug;
	
    public Parcel() {}

    public Parcel(String originCountry, String destinationCountry, Double weight,
                          Date createdAt, String customerId, String customerName, String customerSlug) {
        this.originCountry = originCountry;
        this.destinationCountry = destinationCountry;
        this.weight = weight;
        this.createdDateTime = createdAt;
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
