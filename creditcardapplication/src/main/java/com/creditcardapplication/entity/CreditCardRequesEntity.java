package com.creditcardapplication.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="CREDIT_CARD_REQUEST",schema="CRDBO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardRequesEntity {
	@Id
	@Column(name="customer_id")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String customerId;
	
	private String emailId;
	private String mobileNumber;
	private String dateOfBirth;
	private String panCard;
	private String name;
	private Double annualIncome;
	
	@OneToOne(mappedBy = "customer",cascade = CascadeType.ALL,optional = true)
	private UserDetailsEntity userDetials;
	
	
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Set<AddressEntity> address;
	
	
	public void addUserdetails(UserDetailsEntity details) {
		userDetials = details;
		details.setCustomer(this);
	}
	
	public void addAddress(AddressEntity addressEntity) {
		address.add(addressEntity);
		addressEntity.setCustomer(this);
	}

	
}
