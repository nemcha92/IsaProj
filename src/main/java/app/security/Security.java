package app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http	.authorizeRequests().antMatchers("/").permitAll().and()
				.authorizeRequests().antMatchers("/#/").permitAll().and()
				.authorizeRequests().antMatchers("/console/**").permitAll();
		
		http
			.formLogin().disable()
			.csrf().disable();
		
		//enable for console to work
		http	.headers().frameOptions().disable();
	}
}
