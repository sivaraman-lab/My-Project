package com.creditcardapproval.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDecisionBean {

	private String customerId;
	private String approvalRequestId;
	private String status;
}
