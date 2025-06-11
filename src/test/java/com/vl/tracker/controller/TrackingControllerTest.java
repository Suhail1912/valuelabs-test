package com.vl.tracker.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.vl.tracker.model.BaseResponse;
import com.vl.tracker.model.Parcel;
import com.vl.tracker.service.TrackingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

class TrackingControllerTest {

    @Mock
    private TrackingService trackingService;

    @InjectMocks
    private TrackingController trackingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNextTracking_Success() {
        // Arrange
        String origin = "IN";
        String destination = "NY";
        Double weight = 1.123;
        Date createdAt = new Date();
        String customerId = UUID.randomUUID().toString();
        String customerName = "Redbox Logistics";
        String customerSlug = "redbox-logistics";

        Map<String, Object> mockMap = new HashMap<>();
        mockMap.put("tracking_number", "ABC123");
        mockMap.put("created_at", new Date());
        
		Parcel parcel = new Parcel(origin, destination,
				weight, createdAt,
				customerId, customerName,
				customerSlug);
		mockMap.put("parcel_info", parcel);

        when(trackingService.generateAndSaveTrackingNumber(any())).thenReturn(mockMap);

        // Act
        ResponseEntity<BaseResponse> response = trackingController.nextTracking(
                origin, destination, weight, createdAt, customerId, customerName, customerSlug);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals("Success", response.getBody().getMessage());
        assertEquals(mockMap, response.getBody().getData());
    }

    @Test
    void testNextTracking_InvalidWeight() {
        // Act
        ResponseEntity<BaseResponse> response = trackingController.nextTracking(
                "IN", "US", -1.0, new Date(), UUID.randomUUID().toString(),
                "Customer", "slug");

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Weight must be greater than 0.", response.getBody().getMessage());
    }

    @Test
    void testNextTracking_InternalError() {
        // Arrange
        when(trackingService.generateAndSaveTrackingNumber(any()))
                .thenThrow(new RuntimeException("Simulated"));

        // Act
        ResponseEntity<BaseResponse> response = trackingController.nextTracking(
                "IN", "US", 1.0, new Date(), UUID.randomUUID().toString(),
                "Customer", "slug");

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Sorry! Internal error occured, please try again later!", response.getBody().getMessage());
    }
}
