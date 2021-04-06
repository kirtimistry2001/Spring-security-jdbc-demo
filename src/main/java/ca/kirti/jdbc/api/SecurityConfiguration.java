package ca.kirti.jdbc.api;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
	@Autowired
	DataSource dataSource; // used with jdbcAuthentication
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//jdbc Authentication
		auth.jdbcAuthentication()
			.dataSource(dataSource) //H2 Database
			.withDefaultSchema() 	// default database schema just ot test
			.withUser(	//adding some dummy users to default database
					User.withUsername("user")
					.password("pass")
					.roles("USER")
					)
			.withUser(
					User.withUsername("admin")
					.password("pass2")
					.roles("ADMIN")
					);
	}
	
	//setip authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()  //use when need to authorize all endspoint
		.antMatchers("/admin").hasRole("ADMIN") ///admin endpoint for loggedin user with role  'ADMIN'
		.antMatchers("/user").hasAnyRole("USER","ADMIN") ///users endpoing for logged in user with 'User" role, User can have ADMIN role as well. so its any Role
		.antMatchers("/*").permitAll() // for all end points it needs to access login form. Note:never put this first
		.and().formLogin();
	}
	
	@Bean
	public PasswordEncoder getEncoder() {
		return NoOpPasswordEncoder.getInstance(); //this one do nothig it sends the password in same text format . you have to encode password in production environment
	}
}
