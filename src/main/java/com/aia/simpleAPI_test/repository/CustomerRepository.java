package com.aia.simpleAPI_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aia.simpleAPI_test.model.Customer;


/**
 * The interface Customer repository.
 *
 * @author Anson
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>  {

}
