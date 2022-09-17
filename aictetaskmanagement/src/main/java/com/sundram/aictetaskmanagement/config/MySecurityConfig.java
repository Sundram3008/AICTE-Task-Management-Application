package com.sundram.aictetaskmanagement.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sundram.aictetaskmanagement.service.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    /* strategy of password encoder */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * Authentication manager builder is the one where we decide what kind of
     * authentication we have to do, like database authentication or which service
     * is going to be responsible for doing the auth things
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf() // CsrfConfigurer<HttpSecurity>
                .disable().cors().and().authorizeRequests()/*.antMatchers("/distributor/{distributorId}/myretailers").access("hasRole('distributor')").antMatchers("/users/toggle-distributor-status/{distributorid}/{status}","/users/toggle-retailer-status/{retailerId}/{status}","/distributor/getalldisableddistributor","/distributor/getallenableddistributor","/retailer/getallenabledretailers","/retailer/getalldisabledretailers").access("hasRole('admin')")*/
                .antMatchers("/register","/downloadFile/**","/users/admin-creation", "/users/retailer-register", "/users/distributor-register", "/login","/users/user-email/{id}/{role}","/distributor/getAllCitiesWithDistributor")
                .permitAll().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

/**
 * 
                // antMatchers("/users/","/users/admin-creation","/users/toggle-distributor-status/**","/users/toggle-retailer-status/**").access("hasRole('ROLE_ADMIN')")
 * 
 */

 /*chnaged while making call from angular if in case something went wrong paste below to get back to normal state
 http.csrf() // CsrfConfigurer<HttpSecurity>
                .disable().cors().disable().authorizeRequests().antMatchers("/distributor/{distributorId}/myretailers").access("hasRole('distributor')")
                .antMatchers("/users/admin-creation", "/users/retailer-register", "/users/distributor-register", "/login")
                .permitAll().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
*/