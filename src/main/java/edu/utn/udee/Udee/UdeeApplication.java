package edu.utn.udee.Udee;

import edu.utn.udee.Udee.filter.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
public class UdeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdeeApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
						.antMatchers(HttpMethod.POST, "/login").permitAll()
						.antMatchers("/h2-console/**").permitAll()
						.antMatchers("/api/backoffice/**").hasRole("BACKOFFICE")
<<<<<<< HEAD
						.antMatchers("/api/receiver").hasRole("RECEIVER")
=======
						.antMatchers("/api/client/**").hasRole("CLIENT")
>>>>>>> d7416f719c45d155feb5b98a6750829e5f6ab75a
						.anyRequest().authenticated()
						.and().httpBasic();

			http.headers().frameOptions().disable();
		}
	}
}

