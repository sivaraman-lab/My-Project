package com.creditcardapplication.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditcardapplication.bean.RequestBean;
import com.creditcardapplication.bean.UserDetailsBean;
import com.creditcardapplication.service.CreditCardApplicationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/creditcard")
@Slf4j
public class CreditCardApplicationController {

	
	CreditCardApplicationService servicemethod;
	
	

	public CreditCardApplicationController(CreditCardApplicationService servicemethod) {
		super();
		this.servicemethod = servicemethod;
	}

	/*
	 * this URl mapping for application for credit card 
	 */
	@PostMapping(value = "/applyCreditCard")
	public String applyCreditCard(@RequestBody RequestBean request) {

		String response = servicemethod.applyCreditCard(request);
		log.info("response is ", response);
		return response;

	}
	
	/*
	 * this URl mapping for entering user details
	 */
	@PostMapping(value = "/registerUserDetails")
	public String registerUserDetails(@RequestBody UserDetailsBean request) {
		return servicemethod.enterUserDetails(request);
	}
	
	
	/*
	 * this URl mapping for fetching user details
	 */
	@GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RequestBean getUserDetails(@PathVariable(name = "customerId", required = true) String customerId) {
		return servicemethod.fetchUserDetails(customerId);

	}

}
