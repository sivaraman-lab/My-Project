package com.creditcardapproval.serviceimpl;

import java.util.Optional;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.creditcardapproval.bean.ApprovalResponse;
import com.creditcardapproval.bean.CreditCardApprovalBean;
import com.creditcardapproval.bean.CreditCardDecisionBean;
import com.creditcardapproval.entity.CreditCardApprovalEntity;
import com.creditcardapproval.repository.CreditCardApprovalRepository;
import com.creditcardapproval.service.CreditCardApprovalService;
import com.creditcardapproval.service.MailSenderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreditCardApprovalServiceImpl implements CreditCardApprovalService {

	@Autowired
	CreditCardApprovalRepository creditCardApprovalRepository;
	
	@Autowired
    MailSenderService mailSender;
	
	@Value("${message}")
	String message;

	public CreditCardApprovalServiceImpl() {
	}

	/*
	 * this method for approve/reject of application
	 */
	@Override
	public String updateCreditCardRequest(CreditCardDecisionBean decisionBean) {

		String response = "";
		Optional<CreditCardApprovalEntity> approvalRequestEntity = creditCardApprovalRepository
				.findByCustomerIdAndApprovalRequestId(decisionBean.getCustomerId(),
						decisionBean.getApprovalRequestId());
		if (approvalRequestEntity.isPresent()) {
			CreditCardApprovalEntity approvalEntity = approvalRequestEntity.get();
			if ("APPROVED".equalsIgnoreCase(decisionBean.getStatus())) {
				approvalEntity.setStatus("APPROVED");
				response = message+" approved";
			} else {
				approvalEntity.setStatus("REJECTED");
				response = message+" rejected";
			}
			creditCardApprovalRepository.save(approvalEntity);
			try {
				mailSender.sendSimpleMessage(approvalEntity.getUserEmailId(),
						"Credit Card Application", response);
			} catch (SendFailedException e1) {
				log.error("error in sending mail",e1);
			}
			return response;

		} else {
			response = "No records found";
		}
		return response;

	}

	/*
	 * this method for submission of application for approval
	 */
	@Override
	public ApprovalResponse insertForApproval(CreditCardApprovalBean request) {
		ApprovalResponse response = new ApprovalResponse();
		CreditCardApprovalEntity entity = CreditCardApprovalEntity.builder().customerId(request.getCustomerId())
				.approvalRequestId(request.getApprovalRequestId()).status(request.getStatus())
				.userEmailId(request.getUserEmailId()).approverName(request.getApproverName()).build();

		creditCardApprovalRepository.save(entity);
		response.setCustomerId(request.getCustomerId());
		response.setStatus("Registered");
		return response;
	}

}