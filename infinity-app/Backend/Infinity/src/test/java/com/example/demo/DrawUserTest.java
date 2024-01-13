package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.example.demo.draw.DrawUser;
import com.example.demo.user.User;


@RunWith(Parameterized.class)
public class DrawUserTest {

	private long id;
	private String drawname;
	private String drawing;
	private User drawHostUser;
	private boolean expectApproved;
	private String expectedMessage;
	private int testCase;

	public DrawUserTest(long id, User drawHostUser, String drawname, String drawing, int testCase,
			boolean expectApproved, String expectedMessage) {
		this.id = id;
		this.drawname = drawname;
		this.drawing = drawing;
		this.drawHostUser = drawHostUser;
		this.expectApproved = expectApproved;
		this.expectedMessage = expectedMessage;
		this.testCase = testCase;
	}

	@Parameters
	public static Collection<Object[]> getParameters() {
		Collection<Object[]> ParameterList = new ArrayList<Object[]>();
		try {
			Scanner in = new Scanner(new File("TestCases\\DrawUserFile.txt"));
			// Read as many lines as there are in the file
			while (in.hasNextLine()) {
				String l = in.nextLine();
				// split the line using delimiter and then create the test-case object
				String dataArray[] = l.split(",");
				Object[] d = new Object[7];
//					Object[] n = new Object[7];
				d[0] = Long.parseLong(dataArray[0]); // id
				d[1] = new User(Long.parseLong(dataArray[1]), dataArray[2], dataArray[3], dataArray[4], dataArray[5],
						dataArray[6]); // drawHostUser
				d[2] = dataArray[7]; // drawname
				d[3] = dataArray[8]; // drawing
				d[4] = Integer.parseInt(dataArray[9]); // testCase
				d[5] = Boolean.parseBoolean(dataArray[10]); // expectApproved
				d[6] = dataArray[11]; // expectedMessage
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
	public void testDrawUser() throws Throwable {
		DrawUser underTest = new DrawUser(id, drawname, drawing, drawHostUser);
		// When
		ConfirmedResponse result = underTest.ConfirmedDrawUser(testCase, id, drawname, drawing, drawHostUser);
		assertNotNull(result);
		assertEquals(expectApproved, result.isApproved());
		assertEquals(expectedMessage, result.getMessage());

	}
}
