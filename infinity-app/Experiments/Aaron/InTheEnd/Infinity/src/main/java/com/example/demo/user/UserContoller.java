package com.example.demo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;

@RestController
//@RequestMapping("/api/users")
public class UserContoller {

	@Autowired
	private UserRepository userRepository;

	// GET
	/*
	 * Gets all guilds
	 */
//	@GetMapping
	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/*
	 * Gets a guild by that Id
	 */
//	@GetMapping("{id}")
	@GetMapping(path = "/users/{id}")
	public User getUserById(@PathVariable("id") long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new ResourceNotFoundException("User", "ID", id);
		}
	}

	/*
	 * Saves the user
	 */
	// POST
//	@PostMapping
	@PostMapping(path = "/users")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
	}

	/*
	 * Updates the user by what is inputed
	 */
	// PUT
	@PutMapping(path = "/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setDisplayname(user.getDisplayname());
		userRepository.save(existingUser);
		return new ResponseEntity<User>(existingUser, HttpStatus.OK);
	}

//	@DeleteMapping("{id}")
	@DeleteMapping(path = "/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
		userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
		userRepository.deleteById(id);
		return new ResponseEntity<String>("User termination successfull!", HttpStatus.OK);
	}

}
