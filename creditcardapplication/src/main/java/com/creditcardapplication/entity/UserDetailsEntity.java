package com.creditcardapplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER_DETAILS",schema="CRDBO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsEntity {
	
	@Id	
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String userDetailId;
	
	@OneToOne
	@JoinColumn(name="customer_id",referencedColumnName = "customer_id")
	private CreditCardRequesEntity customer;
	
	private String fatherName;
	private String maritalStatus;
	private String citizenship;
	private String fullName;
	private String profession;
	private String companyName;
	private String designation;
	
	
}
