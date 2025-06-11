package com.vl.tracker.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vl.tracker.model.TrackingRequestModel;

public class TrackingServiceTest {

    private TrackingService trackingService;

    @BeforeEach
    public void setUp() {
        trackingService = new TrackingService();
    }

    @Test
    public void testGenerateAndSaveTrackingNumber() {
        TrackingRequestModel req = new TrackingRequestModel(
            "IN", "US", 2.5,
            new Date(), "123e4567-e89b-12d3-a456-426614174000",
            "RedBox Logistics", "redbox-logistics"
        );

        Map<String, Object> result = trackingService.generateAndSaveTrackingNumber(req);

        assertNotNull(result.get("tracking_number"));
        assertEquals(16, result.get("tracking_number").toString().length());
        assertTrue(result.containsKey("created_at"));
        assertTrue(result.containsKey("parcel_info"));
    }
}