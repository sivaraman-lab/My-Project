package com.creditcardapproval.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.mail.SendFailedException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.creditcardapproval.bean.ApprovalResponse;
import com.creditcardapproval.bean.CreditCardApprovalBean;
import com.creditcardapproval.bean.CreditCardDecisionBean;
import com.creditcardapproval.controller.CreditCardApprovalController;
import com.creditcardapproval.entity.CreditCardApprovalEntity;
import com.creditcardapproval.repository.CreditCardApprovalRepository;
import com.creditcardapproval.serviceimpl.CreditCardApprovalServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@Slf4j
public class CreditCardApprovalServiceTest {

	private ObjectMapper objectMapper;

	@InjectMocks
	CreditCardApprovalServiceImpl serviceMethod;

	@InjectMocks
	CreditCardApprovalController controller;

	@Mock
	MailSenderService mailSender;

	@Mock
	CreditCardApprovalRepository creditCardApprovalRepository;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		objectMapper = new ObjectMapper();
		MockMvcBuilders.standaloneSetup(new CreditCardApprovalServiceImpl()).build();
	}

	public CreditCardDecisionBean setDecision() {

		CreditCardDecisionBean decision = CreditCardDecisionBean.builder().approvalRequestId("qwerty1234")
				.customerId("Strung1234").status("APPROVED").build();

		return decision;
	}

	public CreditCardApprovalBean setRequest() {

		CreditCardApprovalBean request = CreditCardApprovalBean.builder().approvalRequestId("wer123")
				.approverName("anand").userEmailId("gmail@gmail.com").customerId("strung1234").status("Registered")
				.build();

		return request;
	}

	public Optional<CreditCardApprovalEntity> setEntity() {

		CreditCardApprovalEntity entity = CreditCardApprovalEntity.builder().customerId("strung1234")
				.approvalRequestId("wer123").status("Registered").userEmailId("gmail@gmail.com").build();

		Optional<CreditCardApprovalEntity> optionalEntity = Optional.of(entity);
		return optionalEntity;
	}

	@Test
	public void updateCreditCardRequestTest() throws SendFailedException {

		when(creditCardApprovalRepository.findByCustomerIdAndApprovalRequestId(Mockito.anyString(), Mockito.anyString())).thenReturn(setEntity());
		when(creditCardApprovalRepository.save(new CreditCardApprovalEntity())).thenReturn(setEntity().get());
		doNothing().when(mailSender).sendSimpleMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		String response=serviceMethod.updateCreditCardRequest(setDecision());
		assertNotNull(response);
	}
	
	@Test
	public void insertForApprovalTest() {
		
		ApprovalResponse ogResponse = ApprovalResponse.builder().customerId("strung1234").status("registered").build();
		when(creditCardApprovalRepository.save(new CreditCardApprovalEntity())).thenReturn(setEntity().get());
		ApprovalResponse response =serviceMethod.insertForApproval(setRequest());
		
		assertEquals(ogResponse,response);
	}

}
