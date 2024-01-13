package com.example.demo.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ascase jroot
 */

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * GET
	 *
	 * Finds all the users in the data base with their information.
	 *
	 * @return - list of all users and their information.
	 */
	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	/**
	 * GET
	 *
	 * Finds a specific user and checks if it exists. Then displays all information
	 * on the specific user.
	 *
	 * @param id - The Id of the specific user in the database.
	 * @return - Displays all information of the specific user called
	 */
	@GetMapping(path = "/users/{id}")
	public User getUserById(@PathVariable("id") long id) {
		return userService.getUserById(id);
	}
	
	@PostMapping(path = "/addTestUser")
	public long postTestUser() {
		User user = new User("x","x","x","x","x");
		return userService.saveUser(user).getId();
	}

	/**
	 * POST
	 *
	 * Creates an instance of a user and adds them to the database with a new unique
	 * Id.
	 *
	 * @param user - The information of the new user.
	 * @return - The information of the user with a new Id.
	 */
//	@PostMapping(path = "/addUser")
//	public ResponseEntity<User> saveUser(@RequestBody User user) {
//		
//		return userService.saveUser(user);
//	}
	@PostMapping(path = "/addUser")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		User u = userService.saveUser(user);
		return new ResponseEntity<String>("User made successfull! At id: " + Long.toString(u.getId()), HttpStatus.CREATED);
	}
	
	/**
	 * PUT
	 *
	 * Updates an existing user by taking the new information and storing it to the
	 * specified user.
	 *
	 * @param id   - The Id of the specific user we are updating.
	 * @param user - The new information we are storing to the specific user.
	 * @return - The existing user at the specified Id but with the new and updated
	 *         information.
	 */
	@PutMapping(path = "/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		return new ResponseEntity<User>(userService.updateUser(id, user), HttpStatus.OK);
	}

	/**
	 * DELETE
	 *
	 * Deletes the user from the database.
	 *
	 * @param id - The Id of the specific user being deleted.
	 * @return - A string that states if the deletion of the user was successful or
	 *         not.
	 */
	@DeleteMapping(path = "/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
		return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.OK);
	}

	@GetMapping(path = "/UserCount")
	public ResponseEntity<Integer> CountOfUser() {
		return new ResponseEntity<Integer>(userService.CountOfUser(), HttpStatus.OK);
	}

}
