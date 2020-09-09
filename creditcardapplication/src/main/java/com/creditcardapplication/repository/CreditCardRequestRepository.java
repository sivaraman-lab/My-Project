package com.creditcardapplication.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.creditcardapplication.entity.CreditCardRequesEntity;

@Repository
public interface CreditCardRequestRepository extends CrudRepository<CreditCardRequesEntity, String> {

	Optional<CreditCardRequesEntity> findByCustomerId(String customerId);
}
