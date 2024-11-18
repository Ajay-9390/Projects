package com.anr.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/shop/worker/add", "/shop/worker/edit/**", "/shop/worker/delete/**").hasRole("ADMIN")
				.requestMatchers("/shop", "/shop/workers").hasAnyRole("ADMIN", "USER").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
						.defaultSuccessUrl("/shop/workers", true).permitAll())
				.logout(logout -> logout.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("1")).roles("ADMIN")
				.build();
		UserDetails worker = User.builder().username("worker").password(passwordEncoder().encode("2")).roles("USER")
				.build();
		return new InMemoryUserDetailsManager(admin, worker);
	}
}
