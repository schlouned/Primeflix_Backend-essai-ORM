package com.BackPrimeflix.config;

import com.BackPrimeflix.filter.JwtRequestFilter;
import com.BackPrimeflix.security.CustomUserDetailService;
import com.BackPrimeflix.util.service.BlackListTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

//*************************************************************
//SecurityConfiguration is a class use to configue Spring security Framework
//*************************************************************
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource datasource;

	@Autowired
	CustomUserDetailService userDetailsService; //for the token

	@Autowired
	JwtRequestFilter jwtRequestFilter;//to check authentication on request

	//here we configure the autentication by token using the userDetailClass
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	//here we configure the security chain with the different authorisated path for request
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/registration/registrationByEmail/**").permitAll()
				.antMatchers("/authentication/loginByEmail/**").permitAll()
				.antMatchers("/authentication/logout/*").permitAll()
				.antMatchers("/oAuth/loginByFacebook/**").permitAll()
				.antMatchers("/oAuth/loginByGoogle/**").permitAll()
				.antMatchers("/registration/accountVerificationByEmail/**").permitAll()
				.antMatchers("/account/getUserInformations/**").authenticated()
				.antMatchers("/account/updateUserInformations/**").authenticated()
				.antMatchers("/movies/getAllMovies/**").authenticated()
				.antMatchers("/movies/getMoviesFilter/**").authenticated()
				.antMatchers("/movies/getMoviesByCategory/**").authenticated()
				.antMatchers("/cart/addToCart**").authenticated()
				.antMatchers("/category/getAllCategories**").authenticated()
				.antMatchers("/discount/saveDiscount**").authenticated()
				.antMatchers("/cart/getCart**").authenticated()
				.antMatchers("/account/getCompanyAddress**").authenticated()
				.antMatchers("/order/saveOrder**").authenticated()
				.antMatchers("/order/getOrderById**").authenticated()
				.antMatchers("/order/getOrdersByUserId**").authenticated()
				.antMatchers("/order/getAllOrders**").authenticated()
				.antMatchers("/order/orderNextStatus**").authenticated()
				.antMatchers("/order/deleteOrder**").authenticated()
				.antMatchers("/order/changeOrderStatusToPayed**").authenticated()
				.antMatchers("/stripePayment/charge").authenticated()
				.antMatchers("paypalPayment/make/payment**").authenticated()
				.antMatchers("paypalPayment/complete/payment**").authenticated()
				.antMatchers("/inventory/saveInventoryItem**").authenticated()
				.antMatchers("/files/uploadMovieFile**").authenticated()
				.antMatchers("/user/users-list**").authenticated()
				.antMatchers("/user/save-user**").authenticated()
				.antMatchers("/user/delete-user**").authenticated()
				.antMatchers("/discount/discount-list**").authenticated()
				.antMatchers("/discount/delete-discount**").authenticated()
				.antMatchers("/inventory/inventory-list**").authenticated()
				.anyRequest().authenticated();
		//check authentication by token
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		//logout
		http.logout();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
