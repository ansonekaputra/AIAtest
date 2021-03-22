package com.aia.simpleAPI_test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.aia.simpleAPI_test.model.Customer;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleApiTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleApiTestApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllCustomers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/customers",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetCustomerById() {
		Customer customer = restTemplate.getForObject(getRootUrl() + "/customers/1", Customer.class);
		System.out.println(customer.getFirstName());
		Assert.assertNotNull(customer);
	}

	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setEmail("admin@gmail.com");
		customer.setFirstName("admin");
		customer.setLastName("admin");
		customer.setCreatedBy("admin");
		customer.setUpdatedBy("admin");

		ResponseEntity<Customer> postResponse = restTemplate.postForEntity(getRootUrl() + "/customers", customer, Customer.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePost() {
		int id = 1;
		Customer customer = restTemplate.getForObject(getRootUrl() + "/customers/" + id, Customer.class);
		customer.setFirstName("admin1");
		customer.setLastName("admin2");

		restTemplate.put(getRootUrl() + "/customers/" + id, customer);

		Customer updatedCustomer = restTemplate.getForObject(getRootUrl() + "/customers/" + id, Customer.class);
		Assert.assertNotNull(updatedCustomer);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		Customer customer = restTemplate.getForObject(getRootUrl() + "/customers/" + id, Customer.class);
		Assert.assertNotNull(customer);

		restTemplate.delete(getRootUrl() + "/customers/" + id);

		try {
			customer = restTemplate.getForObject(getRootUrl() + "/customers/" + id, Customer.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
