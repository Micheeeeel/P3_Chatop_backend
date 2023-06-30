package com.chatop.backend.configuration;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// personnalise le comportement lorsque l'authentification échoue ou n'est pas fournie (cas où un utilisateur tente d'accéder à une ressource protégée sans être authentifié)
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		// envoie une réponse d'erreur avec le code HTTP 401 (Unauthorized) et le message "Unauthorized"
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}