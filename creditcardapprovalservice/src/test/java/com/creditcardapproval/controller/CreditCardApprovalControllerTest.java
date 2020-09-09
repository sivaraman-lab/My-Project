package com.creditcardapproval.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.mail.SendFailedException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.creditcardapproval.bean.ApprovalResponse;
import com.creditcardapproval.bean.CreditCardApprovalBean;
import com.creditcardapproval.bean.CreditCardDecisionBean;
import com.creditcardapproval.entity.CreditCardApprovalEntity;
import com.creditcardapproval.repository.CreditCardApprovalRepository;
import com.creditcardapproval.service.CreditCardApprovalService;
import com.creditcardapproval.service.MailSenderService;
import com.creditcardapproval.serviceimpl.CreditCardApprovalServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@Slf4j
public class CreditCardApprovalControllerTest {

	private ObjectMapper objectMapper;
	
	private MockMvc mockMvc;
	
	@Mock
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
		objectMapper=new ObjectMapper();
		mockMvc=MockMvcBuilders.standaloneSetup(new CreditCardApprovalController(serviceMethod)).build();
	}
	
	public CreditCardDecisionBean setDecision() {
		
		CreditCardDecisionBean decision= CreditCardDecisionBean.builder().approvalRequestId("qwerty1234")
				.customerId("Strung1234").status("APPROVED").build();
		
		return decision;
	}
	
	public CreditCardApprovalBean setRequest() {
		
		CreditCardApprovalBean request = CreditCardApprovalBean.builder().approvalRequestId("wer123")
				.approverName("anand").userEmailId("gmail@gmail.com").customerId("strung1234").status("Registered").build();
		
		return request;
	}
	
	public Optional<CreditCardApprovalEntity> setEntity() {
		
		CreditCardApprovalEntity entity = CreditCardApprovalEntity.builder().customerId("strung1234")
				.approvalRequestId("wer123").status("Registered").userEmailId("gmail@gmail.com").build();
		
		Optional<CreditCardApprovalEntity> optionalEntity = Optional.of(entity);
		return optionalEntity;
	}
	
	
	@Test
	public void approvalDecisionTest() throws JsonProcessingException, Exception {
		
		when(creditCardApprovalRepository.findByCustomerIdAndApprovalRequestId(Mockito.anyString(), Mockito.anyString())).thenReturn(setEntity());
		when(creditCardApprovalRepository.save(new CreditCardApprovalEntity())).thenReturn(setEntity().get());
		doNothing().when(mailSender).sendSimpleMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		when(serviceMethod.updateCreditCardRequest(setDecision())).thenReturn("Yours Registered");
		mockMvc.perform(put("/approvalservice/approvaldecision").contentType("application/json").
				content(objectMapper.writeValueAsString(setDecision()))).andExpect(status().isOk());
	}
	
	
	@Test
	public void registerForApprovalTest() throws JsonProcessingException, Exception {
	
		ApprovalResponse response = ApprovalResponse.builder().customerId("strung1234").status("registered").build();
		when(creditCardApprovalRepository.save(new CreditCardApprovalEntity())).thenReturn(setEntity().get());
		when(serviceMethod.insertForApproval(setRequest())).thenReturn(response);
		mockMvc.perform(post("/approvalservice/registerforapproval").contentType("application/json").
				content(objectMapper.writeValueAsString(setRequest()))).andExpect(status().isOk());
	}
	
}
