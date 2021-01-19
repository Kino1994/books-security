package es.codeurjc.books.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class User {
	
	public interface Basic {}

	@Id
	@JsonView(Basic.class)
	private String nick;

	@JsonView(Basic.class)
	private String email;
	
	private String password;


	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
