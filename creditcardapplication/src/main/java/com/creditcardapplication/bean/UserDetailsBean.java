package com.creditcardapplication.bean;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsBean {
	
	private String customerId;
	private String fatherName;
	private String maritalStatus;
	private String citizenship;
	private String residentalStatus;
	private String fullName;
	private String profession;
	private String companyName;
	private String designation;

}
