package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.ConfirmedResponse;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.user.User;

import com.example.demo.user.*;

@RunWith(Parameterized.class)
public class UserTest {

	private long id;
	private String email;
	private String username;
	private String password;
	private String displayname;
	private String imageid;
	private boolean expectApproved;
	private String expectedMessage;
	private int testCase;
	private List<GuildLink> guildLink;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserServiceImpl userServiceImpl;

	public UserTest(long id, String email, String username, String password, String displayname, String imageid,
			int testCase, boolean expectApproved, String expectedMessage) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.displayname = displayname;
		this.imageid = imageid;
		this.expectApproved = expectApproved;
		this.expectedMessage = expectedMessage;
		this.testCase = testCase;

	}
//	public UserTest() {
//	}
	
//	@Parameters
//	public static Collection <Object[]> NonFileParameters() {
//		Collection<Object[]> ParameterList = new ArrayList<Object[]>();
//		Object[] d = new Object[9];
//		d [0] = (long) 1; // id
//		d[1] = "email";
//		d[2] = "username";
//		d[3] = "password";
//		d[4] = "displayname";
//		d[5] = "imageid";
//		d[6] = (int) 0; // Test Case
//		d[7] = true; // expectApproved
//		d[8] = "null";// expectedMessage
//		ParameterList.add(d);
//		return ParameterList;
//	}
	
	//////////////////////

	@Parameters
	public static Collection<Object[]> getParameters() {
		Collection<Object[]> ParameterList = new ArrayList<Object[]>();
		try {
			Scanner in = new Scanner(new File("TestCases\\UserFile.txt"));
			// Read as many lines as there are in the file
			while (in.hasNextLine()) {
				String l = in.nextLine();
				// split the line using delimiter and then create the test-case object
				String dataArray[] = l.split(",");
				Object[] d = new Object[9];

				d[0] = Long.parseLong(dataArray[0]); // id
				d[1] = dataArray[1]; // email
				d[2] = dataArray[2]; // username
				d[3] = dataArray[3]; // password
				d[4] = dataArray[4]; // displayname
				d[5] = dataArray[5]; // imageid
				d[6] = Integer.parseInt(dataArray[6]); // testCase
				d[7] = Boolean.parseBoolean(dataArray[7]); // expectApproved
				d[8] = dataArray[8]; // expectedMessage
				// add the test data into the arraylist
				ParameterList.add(d);
			} // end of while
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// return all the test-cases
		return ParameterList;
	}
	
	

	@Test
	public void testUser() throws Throwable {
		// Given
		
		User underTest = new User(id, email, username, password, displayname, imageid);
		// When
		
		ConfirmedResponse result = underTest.ConfirmedUser(testCase, id, email, username, password, displayname,
				imageid);
		assertNotNull(result);
		assertEquals(expectApproved, result.isApproved());
		assertEquals(expectedMessage, result.getMessage());
	}
	
	/////////////////////////////////////
	
//	@Test
//	public void testUserNonFile() throws Throwable {
//		User underTest = new User(id, email, username, password, displayname, imageid);
//		// When
//		for(int i = 0; i<=8; i++) {
//			ConfirmedResponse result = underTest.ConfirmedUser(testCase, id, email, username, password, displayname,
//					imageid);
//			assertNotNull(result);
//			assertEquals(expectApproved, result.isApproved());
//			assertEquals(expectedMessage, result.getMessage());
//		}
//	}

				
		


}
