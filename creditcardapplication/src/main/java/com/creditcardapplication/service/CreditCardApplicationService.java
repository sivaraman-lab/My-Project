package com.creditcardapplication.service;

import com.creditcardapplication.bean.RequestBean;
import com.creditcardapplication.bean.UserDetailsBean;

public interface CreditCardApplicationService {

	public String applyCreditCard(RequestBean request);
	
	public String enterUserDetails(UserDetailsBean userDetailsBean);
	
	public RequestBean fetchUserDetails(String customerId);
	
	
}
