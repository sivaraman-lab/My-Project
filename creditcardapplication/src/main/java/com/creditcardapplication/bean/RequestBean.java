package com.creditcardapplication.bean;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestBean {
	
	private String emailId;
	private String mobileNumber;
	private String dateOfBirth;
	private String panCard;
	private String name;
	private Double annualIncome;
	private Set<AddressDetailsBean> address;
	
	
	
	 
}
