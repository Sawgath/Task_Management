package com.Jahan.Task_Management.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.Jahan.Task_Management.repo.UserRepository;
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	 @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable();
	    
//	    http
//        .authorizeRequests()
//            .antMatchers("/", "/home").permitAll()
//            .anyRequest().authenticated();
//	    
//	    http.antMatcher("/ListofUser")
//	      .authorizeRequests()
//	        .antMatchers("/ListofUser").hasAnyRole(Role.ADMIN))
//	        .anyRequest().authenticated();
	  }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
    }	
	@Override
	public void configure(WebSecurity web) throws Exception {
	   
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
