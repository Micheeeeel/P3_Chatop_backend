package com.chatop.backend.configuration;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import com.chatop.backend.service.CustomUserDetails;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	//retrieve username from jwt token
	public String getUserEmailFromToken(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getSubject();
	}

	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getExpiresAt();
	}

	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	//generate token for user
	public String generateToken(CustomUserDetails userDetails) {
		Algorithm algorithm = Algorithm.HMAC512(secret);
		JWTCreator.Builder builder = JWT.create()
				.withSubject(userDetails.getEmail())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));

		return builder.sign(algorithm);
	}

	// vérifie la validité du token avec les détails de l'utilisateur et l'expiration du token
	public Boolean validateToken(String token, CustomUserDetails userDetails) {
		final String userEmailFromToken = getUserEmailFromToken(token);
		return (userEmailFromToken.equals(userDetails.getEmail()) && !isTokenExpired(token));
	}

	//  Vérifie la conformité d'un jeton JWT en utilisant l'algorithme HMAC512 et la clé secrète.
	public DecodedJWT verifyToken(String token) {
		Algorithm algorithm = Algorithm.HMAC512(secret);
		JWTVerifier verifier = JWT.require(algorithm).build();	   // Création d'un vérificateur JWT en utilisant l'algorithme défini.

		// Si la vérification réussit, un objet DecodedJWT contenant les informations décodées du jeton est retourné.
		return verifier.verify(token);
	}
}
