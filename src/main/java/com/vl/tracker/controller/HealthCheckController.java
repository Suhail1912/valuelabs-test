package com.vl.tracker.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vl.tracker.model.BaseResponse;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

	
	@GetMapping
	public ResponseEntity<?> healthCheck() {
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(HttpStatus.OK);
		baseResponse.setMessage("Service is UP and Healthy");
		baseResponse.setData(new Date());
		
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
}
