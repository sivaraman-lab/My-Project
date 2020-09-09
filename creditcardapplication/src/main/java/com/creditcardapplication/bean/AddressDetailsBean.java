package com.creditcardapplication.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDetailsBean {
	
	private String type;
	private String streetdetails;
	private String city;
	private String state;
	private Long pincode;

}
