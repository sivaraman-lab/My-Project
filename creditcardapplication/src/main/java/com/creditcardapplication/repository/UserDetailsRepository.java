package com.creditcardapplication.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.creditcardapplication.entity.UserDetailsEntity;


@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetailsEntity, String> {

	Optional<UserDetailsEntity> findByCustomerCustomerId(String customerId);
}
