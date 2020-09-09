package com.creditcardapplication.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.creditcardapplication.AttributeEncryptor;
import com.creditcardapplication.bean.AddressDetailsBean;
import com.creditcardapplication.bean.ApprovalResponse;
import com.creditcardapplication.bean.RequestBean;
import com.creditcardapplication.bean.UserDetailsBean;
import com.creditcardapplication.entity.AddressEntity;
import com.creditcardapplication.entity.CreditCardRequesEntity;
import com.creditcardapplication.repository.CreditCardRequestRepository;
import com.creditcardapplication.repository.UserAddressRepository;
import com.creditcardapplication.repository.UserDetailsRepository;
import com.creditcardapplication.service.MailSenderService;
import com.creditcardapplication.service.serviceimpl.CreditCardApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@Slf4j
public class CreditCardApplicationControllerTest {
	
	
	private MockMvc mockMvc;
	
	@InjectMocks
	CreditCardApplicationController controller;
	
	@Mock
	CreditCardApplicationServiceImpl servicemethod;
	
	@Mock
	UserDetailsRepository userDetailsRepository;
	
	@Mock
	UserAddressRepository userAddressRepository;
	
	@Mock
	CreditCardRequestRepository creditCardRequestRepository;

	@Mock
	RestTemplate restTemplate;
	
	@Mock
    MailSenderService mailSender; 
	
	private ObjectMapper objectMapper; 
	
	@Mock
	AttributeEncryptor encryptor;
	
	@Value("${message}")
	String message;
	
	public RequestBean setRequest() {
		Set<AddressDetailsBean> address= new HashSet<>();
		AddressDetailsBean bean= AddressDetailsBean.builder().type("type").streetdetails("street").city("city").state("state")
				.pincode((long) 123456).build();
		
		address.add(bean);
		RequestBean request= RequestBean.builder().emailId("gmail@gmail.com").mobileNumber("1234567890")
				.dateOfBirth("00/00/0000").name("zero").panCard("1234567").annualIncome(123456.00).address(address).build();
		return request;
	}
	
	public UserDetailsBean setDetails() {
		
		UserDetailsBean details= UserDetailsBean.builder().customerId("qw123456")
				.fatherName("zerofather").maritalStatus("single").citizenship("indian").residentalStatus("Active")
				.fullName("zerohero").profession("nthg").companyName("kekuranmekuran").designation("summaba").build();
		
		return details;
		
	}
	
	public Optional<CreditCardRequesEntity> entity(){
		
		AddressEntity address = AddressEntity.builder().type("type").streetdetails("street").city("city").state("state")
				.pincode((long) 123456).build();
		Set<AddressEntity> set = new HashSet<>();
		set.add(address);
		CreditCardRequesEntity data = CreditCardRequesEntity.builder().emailId("gmail@gmail.com").mobileNumber("1234567890")
				.dateOfBirth("00/00/0000").name("zero").panCard("1234567").annualIncome(123456.00).address(set).build();
		 Optional<CreditCardRequesEntity> entity = Optional.of(data);
		
		return entity;
	}
	
	@Before
	public void dataSetup (){
		MockitoAnnotations.initMocks(this);
		objectMapper=new ObjectMapper();
		mockMvc=MockMvcBuilders.standaloneSetup(new CreditCardApplicationController(servicemethod)).build();
		
	}
	
	@Test
	public void applyCreditCardTest() throws Exception {
		
		when(encryptor.convertToDatabaseColumn(Mockito.anyString())).thenReturn("hello");
		when(creditCardRequestRepository.save(new CreditCardRequesEntity())).thenReturn(new CreditCardRequesEntity());
		when(restTemplate.postForObject(Mockito.anyString(),Mockito.any(),Mockito.any())).thenReturn(new ApprovalResponse());
		doNothing().when(mailSender).sendSimpleMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		when(servicemethod.applyCreditCard(setRequest())).thenReturn(message);
		mockMvc.perform(post("/creditcard/applyCreditCard").contentType("application/json").content(objectMapper.writeValueAsString(setRequest()))).andExpect(status().isOk());
		
	}
	
	@Test
	public void registerUserDetailsTest() throws Exception {
		
		when(creditCardRequestRepository.findByCustomerId(Mockito.anyString())).thenReturn(entity());
		when(userDetailsRepository.findByCustomerCustomerId(Mockito.anyString())).thenReturn(Optional.empty());
		when(servicemethod.enterUserDetails(setDetails())).thenReturn(message);
		mockMvc.perform(post("/creditcard/registerUserDetails").contentType("application/json").
				content(objectMapper.writeValueAsString(setDetails()))).andExpect(status().isOk());
	}
	
	@Test
	public void getUserDetailsTest() throws Exception {
		
		when(creditCardRequestRepository.findByCustomerId(Mockito.anyString())).thenReturn(entity());
		when(servicemethod.fetchUserDetails(Mockito.anyString())).thenReturn(setRequest());
		mockMvc.perform(get("/creditcard/strung1234").contentType("application/json")).andExpect(status().isOk());
	}
	

}
