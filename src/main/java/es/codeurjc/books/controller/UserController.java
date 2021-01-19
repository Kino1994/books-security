package es.codeurjc.books.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.books.model.Comment;
import es.codeurjc.books.model.User;
import es.codeurjc.books.service.CommentService;
import es.codeurjc.books.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService users;

	@Autowired
	private CommentService comments;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody User user) {

		try {

			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			users.save(user);

		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest()
					.body("User nick should be unique");
		}

		URI location = fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getNick()).toUri();

		return ResponseEntity.created(location).body(user);
	}

	@PutMapping("/users/{nick}")
	public User replaceUser(@RequestBody User newUser, @PathVariable String nick) {

		if (!users.findById(nick).isEmpty()) {
			users.replace(newUser);
			return newUser;
		}
		else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found");
		}
	}

	@GetMapping("/users/")
	public List<User> getUsers() {
		return users.findAll();
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable String id) {
		return users.findById(id).orElseThrow();
	}

	@DeleteMapping("/users/{nick}")
	public ResponseEntity<User> deleteUser(@PathVariable String nick) {

		User user = users.findById(nick).orElseThrow();

		List<Comment> comment = comments.findAllCommentsByUserNick(nick);
		if (comment.size() == 0) {
			users.deleteById(nick);
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		}
	}
}