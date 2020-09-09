package com.creditcardapplication.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardApprovalBean {
	
	private String approvalRequestId;
	private String customerId;
	private String status;
	private String userEmailId;
	private String approverName;

}
