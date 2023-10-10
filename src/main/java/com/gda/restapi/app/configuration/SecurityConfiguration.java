package com.gda.restapi.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import com.gda.restapi.app.service.JwtService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired private AuthenticationProvider authenticationProvider;
	@Autowired private CorsConfigurationSource corsConfigurationSource;
			   private static final String BASE_PATH = "/api/v1";

	@Value("${frontend.domain}")
	private String frontendDomain;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		var csrfTokenHandler = new CsrfTokenRequestAttributeHandler();
		csrfTokenHandler.setCsrfRequestAttributeName(null);
		
		var csrfTokenRepository = new CookieCsrfTokenRepository();
		csrfTokenRepository.setCookieCustomizer(cookie -> cookie
				.httpOnly(false)
				.domain(frontendDomain.replaceFirst("https://|http://", "")));

		http.cors(cors -> cors.configurationSource(corsConfigurationSource))
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers(mvc.pattern(BASE_PATH + "/login")).permitAll()
				.requestMatchers(mvc.pattern(BASE_PATH + "/refresh-xsrf")).permitAll()
				.requestMatchers(mvc.pattern(BASE_PATH + "/admin/**")).hasAuthority("ADMIN")
				.requestMatchers(mvc.pattern(BASE_PATH + "/user/**")).hasAuthority("USER")
				.requestMatchers(mvc.pattern(BASE_PATH + "/users/sessions")).hasAuthority("ADMIN")
				.requestMatchers(mvc.pattern(BASE_PATH + "/users/{id}/sessions/**")).hasAuthority("USER")
				.anyRequest().authenticated()
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) 
			.csrf((csrf) -> csrf
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.csrfTokenRequestHandler(csrfTokenHandler::handle) 
			)
			.addFilterAfter(new CsrfCookieFilter(), UsernamePasswordAuthenticationFilter.class)
			.headers(header -> header.frameOptions(frame -> frame.sameOrigin()))
			.logout(logout -> logout
					.logoutUrl("/api/v1/logout")
					.deleteCookies(JwtService.COOKIE_NAME)
					.logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext()));


		return http.build();
	}
	
}
