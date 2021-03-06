package com.appsdeveloperblog.photoapp.api.users.security;

import com.appsdeveloperblog.photoapp.api.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {


    Environment environment;
    private UsersService usersService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(Environment environment,UsersService usersService,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.environment=environment;
        this.usersService=usersService;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                //.antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
                .antMatchers(HttpMethod.POST,"/users").hasIpAddress(environment.getProperty("gateway.ip"))
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
        .and()
        .addFilter(getAuthenticationFilter())
        .addFilter(new AuthorizationFilter(authenticationManager(),environment));
        http.headers().frameOptions().disable();  //to enable h2 console
    }
    private AuthenticationFilter getAuthenticationFilter() throws Exception
    {
        AuthenticationFilter authenticationFilter=new AuthenticationFilter(usersService,environment,authenticationManager());
        //authenticationFilter.setAuthenticationManager(authenticationManager());
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));  //to change the default login url provided by spring boot(/login)
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);   //calls the loadUserByUsername method in UserServiceImpl (need to extend the UserDetailService class to override the method)
    }
}
