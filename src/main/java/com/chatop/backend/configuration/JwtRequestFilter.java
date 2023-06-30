package com.chatop.backend.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatop.backend.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatop.backend.service.JwtUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		return path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// Récupération du jeton JWT à partir de l'en-tête de la requête
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			// Supprime le préfixe "Bearer" pour extraire le jeton JWT
			jwtToken = requestTokenHeader.substring(7);
			try {
				// Le jeton JWT est ensuite vérifié
				DecodedJWT jwt = jwtTokenUtil.verifyToken(jwtToken);
				// Si la validation réussit, le nom d'utilisateur est extrait
				username = jwtTokenUtil.getUserEmailFromToken(jwtToken);
			} catch (Exception e) {
				System.out.println("Unable to get JWT Token or JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Chargement des détails de l'utilisateur
			CustomUserDetails userDetails = this.jwtUserDetailsService.loadUserByUserEmail(username);

			// Validation finale du jeton et configuration de l'authentification
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				// une instance UsernamePasswordAuthenticationToken est créée avec les détails de l'utilisateur, ses rôles et ses autorisations.
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				// Ajout des détails de l'authentification
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// l'authentification est définie dans le contexte de sécurité : signifie que l'utilisateur actuel est considéré comme authentifié par le système de sécurité
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		// On continue la chaine de filtre et le traitement de la requête par d'autres composants
		chain.doFilter(request, response);
	}
}
