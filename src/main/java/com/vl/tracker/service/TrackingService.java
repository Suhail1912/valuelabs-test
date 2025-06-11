package com.vl.tracker.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vl.tracker.model.Parcel;
import com.vl.tracker.model.TrackingRequestModel;

@Service
public class TrackingService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TrackingService.class);


	public Map<String, Object> generateAndSaveTrackingNumber(TrackingRequestModel trackingReqModdel) {

		LOGGER.info("generateAndSaveTrackingNumber: In service layer");
		
		String baseString = buildBaseString(trackingReqModdel);
	    String trackingNumber = sha512ToTrackingNumber(baseString);
	    
		Parcel parcel = new Parcel(trackingReqModdel.getOriginCountry(), trackingReqModdel.getDestinationCountry(),
				trackingReqModdel.getWeight(), trackingReqModdel.getCreatedDateTime(),
				trackingReqModdel.getCustomerId(), trackingReqModdel.getCustomerName(),
				trackingReqModdel.getCustomerSlug());
		
		Date createdAtDate = new Date();
		String createdAt = ZonedDateTime.ofInstant(createdAtDate.toInstant(), ZoneOffset.UTC)
	            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("tracking_number", trackingNumber);
		responseMap.put("created_at", createdAt);
		responseMap.put("parcel_info", parcel);
		
		LOGGER.info("generateAndSaveTrackingNumber: Generated track id {}", trackingNumber);
		
		return responseMap;
	}
	
    // Compute SHA-512 and convert to uppercase alphanumeric tracking number (length=16)
    private String sha512ToTrackingNumber(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] byteData = md.digest(input.getBytes(StandardCharsets.UTF_8));
      
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < byteData.length; i++) {
    			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    		}

    		return sb.toString().substring(0, 16).toUpperCase(Locale.ROOT);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not available", e);
        }
    }
    
    private String buildBaseString(TrackingRequestModel req) {
        long currentMillis = System.currentTimeMillis();
        SecureRandom secureRandom = new SecureRandom();
        int randomNum = secureRandom.nextInt(900000) + 100000; // 6-digit random number
        return (req.getOriginCountry() + "|" +
                req.getDestinationCountry() + "|" +
                req.getWeight() + "|" +
                req.getCustomerId() + "|" +
                req.getCustomerName() + "|" +
                req.getCustomerSlug() + "|" +
                currentMillis + "|" + randomNum).toLowerCase(Locale.ROOT);
    }
}
