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
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.ConfirmedResponse;
import com.example.demo.draw.DrawGuild;
import com.example.demo.guild.Guild;
import com.example.demo.tableLinks.GuildLink;

@RunWith(Parameterized.class)
public class GuildTest {

	private long id;
	private String guildname;
	private String guildimage;
	private String invitelink;
	private boolean expectApproved;
	private String expectedMessage;
	private int testCase;
//	private List<Chatroom> Guildrooms; //TODO James
	private List<DrawGuild> Guilddraws;
	private List<GuildLink> linklist;

	public GuildTest(long id, String guildname, String guildimage, String invitelink, int testCase, boolean expectApproved,
			String expectedMessage) {
		this.id = id;
		this.guildname = guildname;
		this.guildimage = guildimage;
		this.invitelink = invitelink;
		this.expectApproved = expectApproved;
		this.expectedMessage = expectedMessage;
		this.testCase = testCase;
	}

	@Parameters
	public static Collection<Object[]> getParameters() {
		Collection<Object[]> ParameterList = new ArrayList<Object[]>();
		try {
			Scanner in = new Scanner(new File("TestCases\\GuildFile.txt"));
			// Read as many lines as there are in the file
			while (in.hasNextLine()) {
				String l = in.nextLine();
				// split the line using delimiter and then create the test-case object
				String dataArray[] = l.split(",");
				Object[] d = new Object[7];
				d[0] = Long.parseLong(dataArray[0]); // id
				d[1] = dataArray[1]; // guildname
				d[2] = dataArray[2]; // guildimage
				d[3] = dataArray[3]; // invitelink
				d[4] = Integer.parseInt(dataArray[4]); // testCase
				d[5] = Boolean.parseBoolean(dataArray[5]); // expectApproved
				d[6] = dataArray[6]; // expectedMessage
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
	public void testGuild() throws Throwable {
		// Given
		Guild underTest = new Guild(id, guildname, guildimage, invitelink);
		// When
		ConfirmedResponse result = underTest.ConfirmedGuild(testCase, id, guildname, guildimage, invitelink);
		assertNotNull(result);
		assertEquals(expectApproved, result.isApproved());
		assertEquals(expectedMessage, result.getMessage());
	}

}
