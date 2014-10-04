package com.company.issuetracker.provider.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.company.issuetracker.core.domain.User;
import com.company.issuetracker.core.domain.UserPK;
//import com.company.issuetracker.client.util.RestUtils;
import com.company.issuetracker.core.dto.UserDTO;
import com.company.issuetracker.core.validation.ErrorMessage;
import com.company.issuetracker.provider.cfg.AppConfig;
import com.company.issuetracker.provider.cfg.JpaTestConfig;
import com.company.issuetracker.provider.domain.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class,JpaTestConfig.class})
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class
})
//@Transactional
//@TransactionConfiguration(defaultRollback = false)
public class UserRestControllerIT {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestControllerIT.class);

	private static final String REST_PATH="http://localhost:8080/issuetracker/provider/users/";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserEntity user;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MessageSource messages;

	private Locale locale=Locale.getDefault();


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LOGGER.info("Start of integration testing for the UserRestController.java");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LOGGER.info("Integration testing for the UserRestController.java ended");
	}

	private static HttpHeaders getAuthHeader() {		
		final String CREDENTIALS="user1:password";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		byte[] encodedAuthorization = Base64.encode(CREDENTIALS.getBytes());
		headers.add("Authorization", "Basic " + new String(encodedAuthorization));

		return headers;
	}

	@Before
	public void setUp() throws Exception {
		ResourceDatabasePopulator r=new ResourceDatabasePopulator();
		r.addScript(new ClassPathResource("testData.sql"));		
		r.populate(dataSource.getConnection());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegisterUser() {

		//		Returns validation errors

		UserDTO userDTO=new UserDTO();
		User user=new User(new UserPK("user1"));		
		user.setName("");		
		user.setSurname("");
		user.setPassword("12e45");		
		userDTO.setUser(user);

		HttpEntity<Object> request = new HttpEntity<Object>(userDTO,null);
		ResponseEntity<UserDTO> response = restTemplate.exchange(REST_PATH, HttpMethod.POST, 
				request, UserDTO.class);		
		UserDTO dtoResponse = response.getBody();

		List<ErrorMessage> expectedErrors=Arrays.asList(
				new ErrorMessage("user.id.userId",messages.getMessage("error.userExists", null, locale)	),
				new ErrorMessage("user.name",messages.getMessage("Size.userDto.user.name", null, locale)),
				new ErrorMessage("user.name",messages.getMessage("NotBlank.userDto.user.name", null, locale)),
				new ErrorMessage("user.surname",messages.getMessage("Size.userDto.user.surname", null, locale)),
				new ErrorMessage("user.surname",messages.getMessage("NotBlank.userDto.user.surname", null, locale)),
				new ErrorMessage("user.password",messages.getMessage("Size.userDto.user.password", null, locale))
				);

		assertTrue(CollectionUtils.isEqualCollection(
				expectedErrors, dtoResponse.getErrors()));
		//


		//		Attempt succeeded

		user=new User(new UserPK("new-user-name"));		
		user.setName("name");		
		user.setSurname("surname");
		user.setPassword("password");		
		userDTO.setUser(user);

		request = new HttpEntity<Object>(userDTO,null);
		response = restTemplate.exchange(REST_PATH, HttpMethod.POST, 
				request, UserDTO.class);		

		dtoResponse = response.getBody();

		assertNull(dtoResponse.getErrors());		
		//		
	}

	@Test
	public void testGetUser() {

		HashMap<String, String> parameters=new HashMap<String, String>();
		parameters.put("userId", "user1");

		HttpEntity<Object> request = new HttpEntity<Object>(getAuthHeader());
		ResponseEntity<UserDTO> response = restTemplate.exchange(REST_PATH+"{userId}", 
				HttpMethod.GET, 
				request, UserDTO.class, parameters);

		UserDTO dtoResponse = response.getBody();

		assertNotNull(dtoResponse.getUser().getId().getUserId());		
	}

	@Test
	public void testUpdateUser() {

		//		Validation errors

		UserDTO userDTO=new UserDTO();
		User user=new User(new UserPK("user1"));		
		user.setName("n");		
		user.setSurname("new surname");
		user.setPassword("12e479h");		
		userDTO.setUser(user);		

		HttpEntity<Object> request = new HttpEntity<Object>(userDTO,getAuthHeader());
		ResponseEntity<UserDTO> response = restTemplate.exchange(REST_PATH, HttpMethod.PUT, 
				request, UserDTO.class);		
		UserDTO dtoResponse = response.getBody();		

		List<ErrorMessage> expectedErrors=Arrays.asList(
				new ErrorMessage("user.name",messages.getMessage("Size.userDto.user.name", null, locale)));

		assertTrue(CollectionUtils.isEqualCollection(expectedErrors, dtoResponse.getErrors()));		
		//		

		//		Update succeeded

		userDTO=new UserDTO();	
		user.setName("new name");				
		userDTO.setUser(user);

		request = new HttpEntity<Object>(userDTO,getAuthHeader());
		response = restTemplate.exchange(REST_PATH, HttpMethod.PUT, 
				request, UserDTO.class);		
		dtoResponse = response.getBody();

		assertNull(dtoResponse.getErrors());		
		//				
	}
}
