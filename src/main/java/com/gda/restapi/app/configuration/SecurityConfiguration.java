package com.gda.restapi.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired private AuthenticationProvider authenticationProvider;
	@Autowired private CorsConfigurationSource corsConfigurationSource;
			   private static final String BASE_PATH = "/api/v1";

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		
		http.csrf((csrf) -> csrf.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource))
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(mvc.pattern(BASE_PATH + "/login")).permitAll()
				.requestMatchers(mvc.pattern(BASE_PATH + "/admin/**")).hasAnyAuthority("ADMIN", "SUPER_ADMIN")
				.requestMatchers(mvc.pattern(BASE_PATH + "/user/**")).hasAuthority("USER")
				.anyRequest().authenticated()
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) 
			.headers(header -> header.frameOptions(frame -> frame.disable()));


		return http.build();
	}
	
}
