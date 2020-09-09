package com.creditcardapproval.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.creditcardapproval.entity.CreditCardApprovalEntity;

@Repository
public interface CreditCardApprovalRepository extends CrudRepository<CreditCardApprovalEntity, String> {
	
	Optional<CreditCardApprovalEntity> findByCustomerIdAndApprovalRequestId(String customerId,String approvalRequestId);

}
