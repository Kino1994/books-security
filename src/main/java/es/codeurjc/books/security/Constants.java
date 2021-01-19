package es.codeurjc.books.security;

public class Constants {

	// Spring Security

	public static final String LOGIN_URL = "/login";
	public static final String BOOKS_URL = "/books/";
	public static final String USERS_URL = "/users";
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";

	// JWT

	public static final String ISSUER_INFO = "whatever";
	public static final String SUPER_SECRET_KEY = "1234";
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

}
