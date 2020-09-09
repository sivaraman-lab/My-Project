package com.creditcardapproval.service;

import com.creditcardapproval.bean.ApprovalResponse;
import com.creditcardapproval.bean.CreditCardApprovalBean;
import com.creditcardapproval.bean.CreditCardDecisionBean;

public interface CreditCardApprovalService {
	
	public String updateCreditCardRequest(CreditCardDecisionBean decisionBean);
	
	public ApprovalResponse insertForApproval(CreditCardApprovalBean request);

}
