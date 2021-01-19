package es.codeurjc.books.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.books.model.User;
import es.codeurjc.books.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository users;

	public void save(User user) {
		users.save(user);
	}

	public void replace(User updatedUser) {
		users.findByNick(updatedUser.getNick()).orElseThrow();
		users.save(updatedUser);
	}

	public List<User> findAll() {
		return users.findAll();
	}

	public Optional<User> findById(String nick) {
		return users.findById(nick);
	}
	
	public boolean existsById(String nick) {
		return users.existsById(nick);
	}

	public void deleteById(String nick) {
		users.deleteById(nick);
	}

	public Optional<User> findByNick(String nick) {
		return users.findByNick(nick);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = this.users.findByNick(username);
        if (user.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getNick(), user.get().getPassword(), Collections.emptyList());
	}
}
