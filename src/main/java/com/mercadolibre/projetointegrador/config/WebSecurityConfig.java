package com.mercadolibre.projetointegrador.config;

import com.mercadolibre.projetointegrador.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/sign-in").permitAll()
                .antMatchers(HttpMethod.GET, "/ping").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/fake").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/fresh-products/inboundorder").permitAll()//.hasAnyAuthority("ROLE_SUPERVISOR", "ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/fresh-products/inboundorder").permitAll()//.hasAnyAuthority("ROLE_SUPERVISOR", "ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/fresh-products/orders").hasAnyAuthority("ROLE_BUYER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/fresh-products/orders").hasAnyAuthority("ROLE_BUYER", "ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/section").hasAnyAuthority("ROLE_SUPERVISOR", "ROLE_ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/warehouse").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_SUPERVISOR", "ROLE_ADMIN")

                .anyRequest().authenticated();
    }
}
