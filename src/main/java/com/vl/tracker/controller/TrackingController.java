package com.vl.tracker.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vl.tracker.model.BaseResponse;
import com.vl.tracker.model.TrackingRequestModel;
import com.vl.tracker.service.TrackingService;

@RestController
@RequestMapping("/tracker")
public class TrackingController {

	private static Logger LOGGER = LoggerFactory.getLogger(TrackingController.class);
	
	@Autowired
	private TrackingService trackingService;
	
	

	/**
	 * API for adding the tracking number for the request
	 * @param originCountryId
	 * @param destinationCountryId
	 * @param weight
	 * @param createdAt
	 * @param customerId
	 * @param customerName
	 * @param customerSlug
	 * @return
	 */
	@GetMapping("/tag-tracking-id")
	public ResponseEntity<BaseResponse> nextTracking(@RequestParam("origin_country_id") String originCountryId,
			@RequestParam("destination_country_id") String destinationCountryId, @RequestParam("weight") Double weight,
			@RequestParam("created_at") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date createdAt,
			@RequestParam("customer_id") String customerId, @RequestParam("customer_name") String customerName,
			@RequestParam("customer_slug") String customerSlug) {
		BaseResponse baseResponse = new BaseResponse();
		try {
			LOGGER.info("nextTracking: API Started");
			TrackingRequestModel trackingRequestModel = new TrackingRequestModel(originCountryId, destinationCountryId,
					weight, createdAt, customerId, customerName, customerSlug);

			// Call the validateData method to validate the fields
			String errorMessage = validateData(trackingRequestModel);
			if(errorMessage != null) {
				baseResponse.setMessage(errorMessage);
				baseResponse.setStatus(HttpStatus.BAD_REQUEST);
				return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.BAD_REQUEST);
			}

			Map<String, Object> map = trackingService.generateAndSaveTrackingNumber(trackingRequestModel);

			baseResponse.setData(map);
			baseResponse.setMessage("Success");

			return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("nextTracking: Error is ",e);
			baseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			baseResponse.setMessage("Sorry! Internal error occured, please try again later!");
			return new ResponseEntity<BaseResponse>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Method that validates all the fields
	private String validateData(TrackingRequestModel trackingRequestModel) {
		LOGGER.info("validateData: Validaing the request");
		if (!isStringNotNullAndBlank(trackingRequestModel.getOriginCountry()) || !isStringHasOnlyAlphbets(trackingRequestModel.getOriginCountry())
				|| trackingRequestModel.getOriginCountry().trim().length() != 2) {
			return "Origin country ID must be 2 alphabetical characters only.";
		}

		if (!isStringNotNullAndBlank(trackingRequestModel.getDestinationCountry()) || !isStringHasOnlyAlphbets(trackingRequestModel.getDestinationCountry())
				|| trackingRequestModel.getDestinationCountry().trim().length() != 2) {
			return "Destination country ID must be 2 alphabetical characters only.";
		}

		if (trackingRequestModel.getWeight() == null || trackingRequestModel.getWeight() <= 0) {
			return "Weight must be greater than 0.";
		}
		String weightStr = String.valueOf(trackingRequestModel.getWeight());
		if (!weightStr.matches("^\\d+(\\.\\d{1,3})?$")) {
			return "Weight must have up to three decimal places.";
		}

		if (!isStringNotNullAndBlank(trackingRequestModel.getCustomerId()) 
				|| trackingRequestModel.getCustomerId().trim().length() != 36) {
			return "Customer ID must be of 36 characters.";
		}

		if (!isStringNotNullAndBlank(trackingRequestModel.getCustomerName()) 
				|| trackingRequestModel.getCustomerName().trim().length() > 32) {
			return "Customer name is required.";
		}

		if (!isStringNotNullAndBlank(trackingRequestModel.getCustomerSlug()) 
				|| trackingRequestModel.getCustomerSlug().trim().length() > 32) {
			return "Customer slug is required.";
		}

		if (trackingRequestModel.getCreatedDateTime() == null) {
			return "Created at date is required.";
		}

		// All validations passed
		return null;
	}

	// Helper method for checking the string types
	private boolean isStringNotNullAndBlank(String input) {
		if(input == null || input.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean isStringHasOnlyAlphbets(String input) {
		if(input == null || input.trim().isEmpty()) {
			return false;
		}
		if(!input.matches("^[A-Za-z]+$")) {
			return false;
		}
		return true;
	}

}
