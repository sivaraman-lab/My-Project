package com.creditcardapproval.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="APPROVAL_CARD",schema="ARDBO")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreditCardApprovalEntity {
	
	@Id
	private String customerId;
	
	private String approvalRequestId;
	private String status;
	private String userEmailId;
	private String approverName;
}
