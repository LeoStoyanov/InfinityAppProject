package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.example.demo.draw.DrawGuild;
import com.example.demo.draw.DrawUser;
import com.example.demo.guild.Guild;
import com.example.demo.user.User;

@RunWith(Parameterized.class)
public class DrawGuildTest {
	private long id;
	private String drawname;
	private String drawing;
	private Guild drawHostGuild;
	private boolean expectApproved;
	private String expectedMessage;
	private int testCase;

	public DrawGuildTest(long id, Guild drawHostGuild, String drawname, String drawing, int testCase,
			boolean expectApproved, String expectedMessage) {
		this.id = id;
		this.drawname = drawname;
		this.drawing = drawing;
		this.drawHostGuild = drawHostGuild;
		this.expectApproved = expectApproved;
		this.expectedMessage = expectedMessage;
		this.testCase = testCase;
	}

	@Parameters
	public static Collection<Object[]> getParameters() {
		Collection<Object[]> ParameterList = new ArrayList<Object[]>();
		try {
			Scanner in = new Scanner(new File("TestCases\\DrawGuildFile.txt"));
			// Read as many lines as there are in the file
			while (in.hasNextLine()) {
				String l = in.nextLine();
				// split the line using delimiter and then create the test-case object
				String dataArray[] = l.split(",");
				Object[] d = new Object[7];
//						Object[] n = new Object[7];
				d[0] = Long.parseLong(dataArray[0]); // id
				d[1] = new Guild(Long.parseLong(dataArray[1]), dataArray[2], dataArray[3], dataArray[4]); // drawHostGuild
				d[2] = dataArray[5]; // drawname
				d[3] = dataArray[6]; // drawing
				d[4] = Integer.parseInt(dataArray[7]); // testCase
				d[5] = Boolean.parseBoolean(dataArray[8]); // expectApproved
				d[6] = dataArray[9]; // expectedMessage
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
	public void testDrawGuild() throws Throwable {
		// Given
		DrawGuild underTest = new DrawGuild(id, drawname, drawing, drawHostGuild);
		// When
		ConfirmedResponse result = underTest.ConfirmedDrawGuild(testCase, id, drawname, drawing, drawHostGuild);
		assertNotNull(result);
		assertEquals(expectApproved, result.isApproved());
		assertEquals(expectedMessage, result.getMessage());
	}
}
