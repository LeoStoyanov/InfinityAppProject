package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.chat.Chatroom;
import com.example.demo.draw.DrawGuild;
import com.example.demo.draw.DrawUser;
import com.example.demo.guild.Guild;
import com.example.demo.guild.RoleTypes.RoleType;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.user.User;
import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
//import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;

//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestingSystemTest {

	@LocalServerPort
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}

	private List<Long> IdUserList;
	private List<Long> IdGuildList;
	private List<Long> IdGuildLinkList;
	private List<Long> IdDrawGuildList;
	private List<Long> IdDrawUserList;
	private List<String> IdGuildJoinCode;

	@Test
	public void UserTestCases() {
		IdUserList = new ArrayList<>();
		newUserTest();
		getUsersIdTest();
		getAllUsersTest();
		countOfUserTest();
		updateUserTest();
		deleteUserTest();
	}

	@Test
	public void GuildTestCases() {
		IdUserList = new ArrayList<>();
		IdGuildList = new ArrayList<>();
		IdGuildJoinCode = new ArrayList<>();
		newUserTest();
		newGuildTest(IdUserList.get(0));
		getGuildByIdTest();
		getAllGuildATest();
		updateGuildTest(IdGuildList.get(0), IdUserList.get(0));
		newUserTest();
		updateGuildTest(IdGuildList.get(0), IdUserList.get(1));
		deleteUserTest();
		deleteGuildTest();
		deleteUserTest();
	}

	@Test
	public void GuildLinkTestCases() {
		IdUserList = new ArrayList<>();
		IdGuildList = new ArrayList<>();
		IdGuildLinkList = new ArrayList<>();
		IdGuildJoinCode = new ArrayList<>();
		newUserTest();
		newUserTest();
		newGuildTest(IdUserList.get(0));
		addUserToGuildTest(IdGuildList.get(0), IdUserList.get(1));
		getGuildLinkByIdTest(IdGuildLinkList.get(0));
		getAllGuildLinkTest();
		deleteUserFromGuildTest(IdGuildList.get(0), IdUserList.get(1), IdGuildLinkList.get(0));
		deleteGuildTest();
		deleteUserTest();
		deleteUserTest();
	}

	@Test
	public void DrawTestCases() {
		IdUserList = new ArrayList<>();
		IdGuildList = new ArrayList<>();
		IdDrawGuildList = new ArrayList<>();
		IdDrawUserList = new ArrayList<>();
		newUserTest();
		newDrawUserTest(IdUserList.get(0));
		getDrawUserIdTest(IdDrawUserList.get(0));
		getAllUserDrawingsTest();
		deleteDrawUserTest();
		deleteUserTest();
	}

	// <----------------------- Users Testing -----------------------> //
	public void getAllUsersTest() {
		System.out.println("getUsersTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/users");
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void getUsersIdTest() {
		System.out.println("getUsersIdTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/users/" + IdUserList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void newUserTest() {
		System.out.println("newUserTest");
		User u = new User("x", "x", "x", "x", "x");
		String body = "";
		try {
			body = mapToJson(u);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.body(body).when().post("/addUser");
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(201, statusCode);
		IdUserList.add(Long.parseLong(response.getBody().asString().replaceAll("[^0-9]", "")));
	}

	public void updateUserTest() {
		System.out.println("updateUserTest");
		User u = new User("y", "y", "y", "y", "y");
		String body = "";
		try {
			body = mapToJson(u);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.body(body).when().put("/updateUser/" + IdUserList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void deleteUserTest() {
		System.out.println("deleteUserTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().delete("/deleteUser/" + IdUserList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
		IdUserList.remove(0);
	}

	public void countOfUserTest() {
		System.out.println("countOfUserTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/UserCount");
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

//	// <----------------------- Guild Testing -----------------------> //

	public void getAllGuildATest() {
		System.out.println("getGuildTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/guilds");
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void getGuildByIdTest() {
		System.out.println("getGuildByIdTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/guild/" + IdGuildList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void newGuildTest(long userId) {
		System.out.println("newGuildTest");
		Guild g = new Guild("x", "x", "x");
		String body = "";
		try {
			body = mapToJson(g);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.body(body).when().post("/newGuild/" + userId);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(201, statusCode);
		IdGuildList.add(Long.parseLong(response.getBody().asString().replaceAll("[^0-9]", "")));

	}

	public void updateGuildTest(long guildID, long userId) {
		System.out.println("updateGuildTest");
		Guild g = new Guild("y", "y", "y");
		String body = "";
		try {
			body = mapToJson(g);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.body(body).when().put("/updateGuild/" + guildID + "/" + userId);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

////	//TODO JAMES ADD CHAT TO GUILD DELETE CHAT FROM GUILD

	public void deleteGuildTest() {
		System.out.println("deleteGuildTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().delete("/deleteGuild/" + IdGuildList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
		IdGuildList.remove(0);
	}

//	// <----------------------- GuildLink Testing -----------------------> //

	public void getAllGuildLinkTest() {
		System.out.println("getAllGuildLinkTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/guildlinks");
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void getGuildLinkByIdTest(long guildLinkId) {
		System.out.println("getGuildLinkByIdTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/guildlink/" + guildLinkId);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void updateUserinGuildTest(long guildID, long userId) {
		System.out.println("updateUserinGuildTest");
		GuildLink gl = new GuildLink(RoleType.MOD);

		String body = "";

		try {
			body = mapToJson(gl);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.body(body).when().put("/updateUserinGuild/" + IdUserList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void addUserToGuildTest(long guildId, long userId) {
		System.out.println("addUserToGuildTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().post("/addUserToGuild/" + guildId + "/" + userId);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
		IdGuildLinkList.add(Long.parseLong(response.getBody().asString().replaceAll("[^0-9]", "")));
	}

	public void guildAddUserVaLinkTest(String IdJoinCode, long userId) {
		System.out.println("guildAddUserVaLinkTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().post("/addUserToGuildVaLink/" + userId + "/" + IdJoinCode);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);

		IdGuildLinkList.add(Long.parseLong(response.getBody().asString().replaceAll("[^0-9]", "")));
	}

	public void deleteUserFromGuildTest(long guildId, long userId, long IdGuildLink) {
		System.out.println("deleteUserFromGuildTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().delete("/deleteUserFromGuild/" + guildId + "/" + userId);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
		IdGuildLinkList.remove(IdGuildLink);
	}

	public void deleteGuildlinkTest() {
		System.out.println("deleteGuildlinkTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().delete("/deleteGuildlink/" + IdGuildLinkList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
		IdGuildLinkList.remove(0);

	}

//	// <----------------------- Draw Testing -----------------------> //

	public void getAllGuildDrawingsTest() {
		System.out.println("getAllGuildDrawingsTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/guildDrawings");
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	/*
	 * public void getDrawGuildIdTest(long IdDrawGuild) {
	 * System.out.println("getDrawGuildIdTest"); Response response =
	 * RestAssured.given().header("Content-Type",
	 * "application/json").header("charset", "utf-8") .when().get("/guildDrawings/"
	 * + IdDrawGuild); System.out.println(response.getBody().asString()); int
	 * statusCode = response.getStatusCode(); System.out.println(statusCode);
	 * assertEquals(200, statusCode); }
	 * 
	 * public void newDrawGuildTest(long guildId) {
	 * System.out.println("newGuildTest"); DrawGuild dg = new DrawGuild("x", "x");
	 * String body = ""; try { body = mapToJson(dg); } catch (JSONException e) {
	 * e.printStackTrace(); } Response response =
	 * RestAssured.given().header("Content-Type",
	 * "application/json").header("charset", "utf-8")
	 * .body(body).when().post("/newGuildDrawing/" + guildId);
	 * System.out.println(response.getBody().asString()); int statusCode =
	 * response.getStatusCode(); System.out.println(statusCode); assertEquals(201,
	 * statusCode);
	 * IdDrawGuildList.add(Long.parseLong(response.getBody().asString().replaceAll(
	 * "[^0-9]", ""))); }
	 * 
	 * public void updateDrawGuildTest(long DrawGuildID) {
	 * System.out.println("updateDrawGuildTest"); Guild g = new Guild("y", "y",
	 * "y"); DrawGuild dg = new DrawGuild("y", "y", g); String body = ""; try { body
	 * = mapToJson(dg); } catch (JSONException e) { e.printStackTrace(); } Response
	 * response = RestAssured.given().header("Content-Type",
	 * "application/json").header("charset", "utf-8")
	 * .body(body).when().put("/updateGuildDrawing/" + DrawGuildID);
	 * System.out.println(response.getBody().asString()); int statusCode =
	 * response.getStatusCode(); System.out.println(statusCode); assertEquals(200,
	 * statusCode); }
	 * 
	 * public void deleteDrawGuildTest() { System.out.println("deleteDrawUserTest");
	 * Response response = RestAssured.given().header("Content-Type",
	 * "application/json").header("charset", "utf-8")
	 * .when().delete("/deleteUserDrawing/" + IdDrawGuildList.get(0));
	 * System.out.println(response.getBody().asString()); int statusCode =
	 * response.getStatusCode(); System.out.println(statusCode); assertEquals(200,
	 * statusCode); }
	 */
	public void getAllUserDrawingsTest() {
		System.out.println("getAllUserDrawingsTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/userDrawings");
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void getDrawUserIdTest(long IdDrawUser) {
		System.out.println("getDrawUsersIdTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().get("/userDrawings/" + IdDrawUser);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void newDrawUserTest(long userId) {
		System.out.println("newDrawUserTest");
		DrawUser du = new DrawUser("x", "x");
		String body = "";
		try {
			body = mapToJson(du);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.body(body).when().post("/newUserDrawing/" + userId);
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(201, statusCode);
		IdDrawUserList.add(Long.parseLong(response.getBody().asString().replaceAll("[^0-9]", "")));
	}

	public void updateDrawUserTest(long DrawUserId) {
		System.out.println("updateDrawUserTest");
		DrawUser du = new DrawUser("y", "y");
		String body = "";
		try {
			body = mapToJson(du);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.body(body).when().put("/updateUserDrawing/" + IdUserList.get(1));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
	}

	public void deleteDrawUserTest() {
		System.out.println("deleteDrawUserTest");
		Response response = RestAssured.given().header("Content-Type", "application/json").header("charset", "utf-8")
				.when().delete("/userDrawing/" + IdDrawUserList.get(0));
		System.out.println(response.getBody().asString());
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		assertEquals(200, statusCode);
		IdDrawUserList.remove(0);
	}

//	Potentally Helpful codes
	private String mapToJson(Object obj) throws JSONException {
		JSONObject jObject = new JSONObject();
		obj.getClass();
		if (obj.getClass().equals(User.class)) {
			User u = (User) obj;
			jObject.put("id", u.getId());
			jObject.put("email", u.getEmail());
			jObject.put("password", u.getPassword());
			jObject.put("username", u.getUsername());
			jObject.put("displayname", u.getDisplayname());
			jObject.put("imageid", u.getImageid());
			jObject.put("userdrawings", u.getUserDrawings());
			jObject.put("guildlinks", u.getGuildLink());

		} else if (obj.getClass().equals(Guild.class)) {
			Guild g = (Guild) obj;
			jObject.put("guildname", g.getGuildname());
			jObject.put("guildimage", g.getGuildimage());
			jObject.put("invitelink", g.getInvitelink());
			jObject.put("owner", g.getOwner());
			jObject.put("guilddrawings", g.getGuilddraws());
			jObject.put("linklist", g.getLinklist());
//			jObject.put("chatrooms", g.getChatrooms());
		} 
//		else if (obj.getClass().equals(Chatroom.class)) {
//			Chatroom c = (Chatroom) obj;
//
//		}
		else if (obj.getClass().equals(GuildLink.class)) {
			GuildLink gl = (GuildLink) obj;
			jObject.put("users", gl.getUsers());
			jObject.put("guilds", gl.getGuilds());
			jObject.put("role", gl.getRole());
		}
//		else if (obj.getClass().equals(DrawGuild.class)) {
//			DrawGuild dg = (DrawGuild) obj;
//			jObject.put("drawname", dg.getDrawname());
//			jObject.put("drawing", dg.getDrawing());
//			jObject.put("drawHostGuild", dg.getDrawHostGuild());
//			jObject.put("canvas", dg.getCanvas());
//		} 
		else if (obj.getClass().equals(DrawUser.class)) {
			DrawUser du = (DrawUser) obj;
			jObject.put("drawname", du.getDrawname());
			jObject.put("drawing", du.getDrawing());
//			jObject.put("drawHostUser", du.getDrawHostUser());
		}

		return jObject.toString();
	}

}
