package com.creditcardapplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.creditcardapplication.entity.AddressEntity;


@Repository
public interface UserAddressRepository extends CrudRepository<AddressEntity, String>{

}
