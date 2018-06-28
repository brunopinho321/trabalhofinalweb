package com.ufc.br.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ufc.br.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImplementacao userDetailsImplementacao;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/sonic/adicionar/instrumento").hasRole("ADMIN") 
		.antMatchers("/sonic/listar/instrumento").hasRole("ADMIN")  
		.antMatchers("/sonic/excluir/instrumento/{id}").hasRole("ADMIN") 
		.antMatchers("/sonic/exibir/instrumento/{id}").permitAll()
		.antMatchers("/pessoa/formulario").permitAll()
		.antMatchers("/sonic/listar/instrumento2").permitAll() 
		.antMatchers("/pessoa/salvar").permitAll()
		.anyRequest().authenticated() 
		.and()
		.formLogin()
		.loginPage("/pessoa/logar")
		.permitAll() 
		
		
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/pessoa/logar?logout") 
		.permitAll();
	}
	
	//th:if="!(${#authorization.expression(('hasRole(''ADMIN'')'))})"
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsImplementacao).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/js/**","/images/**");
	}

}
