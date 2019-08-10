package com.huunghia.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//lay thong tin authorization tu header
		String header = request.getHeader("Authorization");
		
		//kiem tra neu khong ton tai hoac sai cu phap
		if(header == null || !header.startsWith("Bearer ")) {
			response.setStatus(401);
			response.getWriter().write("sai token");
			response.getWriter().close();
			return;
		}
		
		//lay thong tin dang nhap tu phuong thuc getAuthenticationToken
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
		
		//set thong tin dang nhap vao SecurityContext
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if(token == null) return null;
		
		//lay ra username da gan vao token luc dang nhap
		String username = Jwts.parser().setSigningKey("secretKey")
				//thay the "Bearer " bang ""
				.parseClaimsJws(token.replace("Bearer ", ""))
				.getBody()
				.getSubject();
		
		//lay ra thong tin dang nhap va quyen user
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		return username != null ? 
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
		
	}

	
	
	

}
