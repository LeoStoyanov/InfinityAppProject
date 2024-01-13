package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import com.example.demo.ConfirmedResponse;
import com.example.demo.draw.DrawGuild;
import com.example.demo.guild.Guild;
import com.example.demo.guild.RoleTypes;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.user.User;

@RunWith(Parameterized.class)
public class GuildLinkTest {

	private long id;
	private User user;
	private Guild guild;
	private RoleTypes.RoleType role;
	private boolean expectApproved;
	private String expectedMessage;
	private int testCase;

	public GuildLinkTest(long id, User user, Guild guild, RoleTypes.RoleType role, int testCase, boolean expectApproved,
			String expectedMessage) {
		this.id = id;
		this.user = user;
		this.guild = guild;
		this.role = role;
		this.expectApproved = expectApproved;
		this.expectedMessage = expectedMessage;
		this.testCase = testCase;

	}

	@Parameters
	public static Collection<Object[]> getParameters() {
		Collection<Object[]> ParameterList = new ArrayList<Object[]>();

		int i = 0;
		try {
			Scanner in = new Scanner(new File("TestCases\\GuildLinkFile.txt"));
			// Read as many lines as there are in the file
			while (in.hasNextLine()) {
				String l = in.nextLine();
				// split the line using delimiter and then create the test-case object
				String dataArray[] = l.split(",");
				Object[] d = new Object[7];
//				Object[] n = new Object[7];
				d[0] = Long.parseLong(dataArray[0]); // id
				d[1] = new User(Long.parseLong(dataArray[1]), dataArray[2], dataArray[3], dataArray[4], dataArray[5],
						dataArray[6]);
				d[2] = new Guild(Long.parseLong(dataArray[7]), dataArray[8], dataArray[9], dataArray[10]);
				RoleTypes R = new RoleTypes(dataArray[11]);
				d[3] = R.getType();
				d[4] = Integer.parseInt(dataArray[12]); // testCase
				d[5] = Boolean.parseBoolean(dataArray[13]); // expectApproved
				d[6] = dataArray[14]; // expectedMessage

				// add the test data into the arraylist

				ParameterList.add(d);
				i++;
			} // end of while
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// return all the test-cases
		return ParameterList;
	}

	@Test
	public void testGuildLink() throws Throwable {
		// Given
		GuildLink underTest = new GuildLink(id, guild, user, role);
		// When
		ConfirmedResponse result = underTest.ConfirmedGuildLink(testCase, id, guild, user, role);
		assertNotNull(result);
		assertEquals(expectApproved, result.isApproved());
		assertEquals(expectedMessage, result.getMessage());
	}
}
