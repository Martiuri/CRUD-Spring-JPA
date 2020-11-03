package com.crud.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.crud.app.service.JpaUserDetailService;



@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	
		@Autowired
		private JpaUserDetailService userDetailService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/css/**","/js/**","/images/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin()
			//.loginPage("/login") //personalaizar pagina de login
			.permitAll()
			.and()
			.logout().permitAll();
		}



		@Autowired
		public void configureGlobal (AuthenticationManagerBuilder builder) throws Exception{
			
			builder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
		/*
		 * PasswordEncoder encoder = passwordEncoder(); UserBuilder users =
		 * User.builder().passwordEncoder(encoder::encode);
		 * builder.inMemoryAuthentication()
		 * .withUser(users.username("admin").password("admin").roles("ADMIN", "USER"));
		 */
		}

}
