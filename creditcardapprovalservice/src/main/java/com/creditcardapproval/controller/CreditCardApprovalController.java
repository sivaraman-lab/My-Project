package com.creditcardapproval.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditcardapproval.bean.ApprovalResponse;
import com.creditcardapproval.bean.CreditCardApprovalBean;
import com.creditcardapproval.bean.CreditCardDecisionBean;
import com.creditcardapproval.service.CreditCardApprovalService;

@RestController
@RequestMapping("/approvalservice")
//@EnableHystrix
public class CreditCardApprovalController {

	CreditCardApprovalService serviceMethod;
	
	
	public CreditCardApprovalController(CreditCardApprovalService serviceMethod) {
		super();
		this.serviceMethod = serviceMethod;
	}

	/*
	 * this URl mapping for approve/reject user credit card application
	 */
	@PutMapping(value="/approvaldecision")
	public String approvalDecision (@RequestBody CreditCardDecisionBean decisionBean) {
		return serviceMethod.updateCreditCardRequest(decisionBean);
	}
	
	/*
	 * this URl mapping for registering for credit card approval
	 */
	@PostMapping(value="/registerforapproval")
	public ApprovalResponse registerForApproval(@RequestBody CreditCardApprovalBean request) {
		return serviceMethod.insertForApproval(request);
	}
}
