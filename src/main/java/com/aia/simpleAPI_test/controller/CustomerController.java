package com.aia.simpleAPI_test.controller;


import org.springframework.web.bind.annotation.RestController;

import com.aia.simpleAPI_test.exception.ResourceNotFoundException;
import com.aia.simpleAPI_test.model.Customer;
import com.aia.simpleAPI_test.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The type Customer controller.
 *
 * @author anson
 */
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Get all customers list.
	 *
	 * @return the list
	 */
	@GetMapping("/customers")
	public List<Customer> getAllUsers() {
		return customerRepository.findAll();
	}

	/**
	 * Gets customer by id.
	 *
	 * @param customerId the user id
	 * @return the customer by id
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getUsersById(@PathVariable(value = "id") Long customerId) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found on :: " + customerId));
		return ResponseEntity.ok().body(customer);
	}

	/**
	 * Create customer customer.
	 *
	 * @param customer the customer
	 * @return the customer
	 */
	@PostMapping("/customer")
	public Customer createCustomer(@Valid @RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	/**
	 * Update customer response entity.
	 *
	 * @param customerId      the customer id
	 * @param customerDetails the customer details
	 * @return the response entity
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateUser(@PathVariable(value = "id") Long customerId,
			@Valid @RequestBody Customer userDetails) throws ResourceNotFoundException {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found on :: " + customerId));

		customer.setEmail(userDetails.getEmail());
		customer.setLastName(userDetails.getLastName());
		customer.setFirstName(userDetails.getFirstName());
		customer.setUpdatedAt(new Date());
		final Customer updatedUser = customerRepository.save(customer);
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * Delete user map.
	 *
	 * @param userId the user id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/customer/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId) throws Exception {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found on :: " + customerId));

		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
