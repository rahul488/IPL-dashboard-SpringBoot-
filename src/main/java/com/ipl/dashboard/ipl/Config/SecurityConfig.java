package com.ipl.dashboard.ipl.Config;

import com.ipl.dashboard.ipl.Oauth2.CustomOauth2UserService;
import com.ipl.dashboard.ipl.Oauth2.Oauth2LoginSuccessHandler;
import com.ipl.dashboard.ipl.Service.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private CustomOauth2UserService customOauth2UserService;

    @Autowired
    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable();
            http.cors().and().
                    httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/teams/{teamName}","/generateToken","/register","/getAllTeams","/load").permitAll()
                    .and()
                    .authorizeRequests()
                   // .antMatchers("/load").hasRole("ADMIN")
                    .antMatchers("/teams/{teamName}/matches").hasAnyRole("ADMIN","USER")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .and()
                    .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                    .userInfoEndpoint()
                    .userService(customOauth2UserService)
                    .and()
                    .successHandler(oauth2LoginSuccessHandler)
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    ;

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
