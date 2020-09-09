package com.creditcardapplication.service.serviceimpl;


import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.creditcardapplication.entity.AddressEntity;
import com.creditcardapplication.entity.CreditCardRequesEntity;
import com.creditcardapplication.entity.UserDetailsEntity;
import com.creditcardapplication.repository.CreditCardRequestRepository;
import com.creditcardapplication.repository.UserAddressRepository;
import com.creditcardapplication.repository.UserDetailsRepository;
import com.creditcardapplication.service.CreditCardApplicationService;
import com.creditcardapplication.service.MailSenderService;

import lombok.extern.slf4j.Slf4j;

import com.creditcardapplication.AttributeEncryptor;
import com.creditcardapplication.bean.AddressDetailsBean;
import com.creditcardapplication.bean.ApprovalResponse;
import com.creditcardapplication.bean.CreditCardApprovalBean;
import com.creditcardapplication.bean.RequestBean;
import com.creditcardapplication.bean.UserDetailsBean;

@Service
@Slf4j
public class CreditCardApplicationServiceImpl implements CreditCardApplicationService {

	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	UserAddressRepository userAddressRepository;
	
	@Autowired
	CreditCardRequestRepository creditCardRequestRepository;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
    MailSenderService mailSender;
	
	@Autowired
	AttributeEncryptor encryptor;
	
	@Value("${message}")
	String message;
	

	/*
	 * this method is used to apply for credit card 
	 */
	@Override
	public String applyCreditCard(RequestBean request) {
		
		log.info("request{}",request.getName());
		CreditCardRequesEntity creditEntity= CreditCardRequesEntity.builder().
		emailId(encryptor.convertToDatabaseColumn(request.getEmailId())).
		mobileNumber(encryptor.convertToDatabaseColumn(request.getMobileNumber()))	.
		dateOfBirth(request.getDateOfBirth()).name(request.getName()).
		panCard(encryptor.convertToDatabaseColumn(request.getPanCard())).
		annualIncome(request.getAnnualIncome())
		.userDetials(new UserDetailsEntity()).address(new HashSet()).build();
		Set<AddressDetailsBean> add=request.getAddress();
		add.stream().forEach(e ->{
			AddressEntity addEntity= AddressEntity.builder().type(e.getType()).streetdetails(e.getStreetdetails()).
			city(e.getCity()).state(e.getState()).pincode(e.getPincode()).build();
			creditEntity.addAddress(addEntity);
		});
		creditCardRequestRepository.save(creditEntity);
		CreditCardApprovalBean approvalRequest= CreditCardApprovalBean.builder().approvalRequestId(createRandomPin()).customerId(creditEntity.getCustomerId()).status("Registered")
				.userEmailId(creditEntity.getEmailId()).approverName("Anand").build();
		ApprovalResponse response=restTemplate.postForObject("http://localhost:9090/credit-card-approval-service/approvalservice/registerforapproval",approvalRequest,ApprovalResponse.class);
		log.info("response{} ",response);
		String customerId=message +creditEntity.getCustomerId();
		log.info("message{} ",message);
		
		try {
			mailSender.sendSimpleMessage(request.getEmailId(),
					"Credit Card Application", message +customerId);
		} catch (SendFailedException e1) {
			log.error("error in sending mail",e1);
		}
		
		return customerId;
	}

	/*
	 * this method is used to enter user details for credit card 
	 */
	@Override
	public String enterUserDetails(UserDetailsBean userDetailsBean) {
		
		String response= "";
		Optional<CreditCardRequesEntity> creditEntity=creditCardRequestRepository.findByCustomerId(userDetailsBean.getCustomerId());
		if(creditEntity.isPresent()) {
			UserDetailsEntity userDetails=UserDetailsEntity.builder().customer(creditEntity.get()).fatherName(userDetailsBean.getFatherName())
			.maritalStatus(userDetailsBean.getMaritalStatus())
			.citizenship(userDetailsBean.getCitizenship()).fullName(userDetailsBean.getFullName())
			.profession(userDetailsBean.getProfession()).companyName(userDetailsBean.getCompanyName())
			.designation(userDetailsBean.getDesignation()).build();
			
			userDetailsRepository.save(userDetails);
			response = "User Details registered"; 
			
		}else {
			response="Customer Id not found";
		}
		return response;
	}

	/*
	 * this method is used to fetch application details
	 */
	@Override
	public RequestBean fetchUserDetails(String customerId) {
		
		RequestBean response= new RequestBean();
		Optional<CreditCardRequesEntity> creditEntity=creditCardRequestRepository.findByCustomerId(customerId);
		if(creditEntity.isPresent()) {
			response=RequestBean.builder().emailId(encryptor.convertToEntityAttribute(creditEntity.get().getEmailId())).
					mobileNumber(encryptor.convertToEntityAttribute(creditEntity.get().getMobileNumber()))
			.dateOfBirth(creditEntity.get().getDateOfBirth()).name(creditEntity.get().getName()).
			panCard(encryptor.convertToEntityAttribute(creditEntity.get().getPanCard())).
			annualIncome(creditEntity.get().getAnnualIncome()).address(new HashSet<>())
			.build();
			Set<AddressDetailsBean> addressDets = new HashSet<>();
			creditEntity.get().getAddress().stream().forEach(e -> {
				AddressDetailsBean address= AddressDetailsBean.builder().type(e.getType()).streetdetails(e.getStreetdetails())
						.city(e.getCity()).state(e.getState()).pincode(e.getPincode()).build();
				addressDets.add(address);
			});
			response.setAddress(addressDets);
		}
		return response;
		
	}
	
	/*
	 * this method is used to random pin generator
	 */
	public String createRandomPin() {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(100000);
		return String.format("%05d", num); 
	}


	
}
