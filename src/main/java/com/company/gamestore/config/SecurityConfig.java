package com.company.gamestore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    // This method configures Spring Security to use the default JDBC schema that we have setup to
    // hold our user accounts and their associated authorities (aka roles).
    //
    // The DataSource is configured in the application.properties file and injected into this class. The
    // AuthenticationManagerBuilder is also injected into this class by Spring. It is the tool we use to
    // configure Spring Security to use our schema.
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);

    }

    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers("store/loggedin").authenticated()
                // Only adminUser can delete Consoles
                .mvcMatchers(HttpMethod.DELETE, "/store/consoles/{consoleId}").hasAuthority("ROLE_ADMIN")
                // staffUser can update Consoles
                .mvcMatchers(HttpMethod.PUT, "/store/consoles").hasAuthority("ROLE_STAFF")
                // mangerUser can create Consoles
                .mvcMatchers(HttpMethod.POST, "/store/consoles").hasAuthority("ROLE_MANAGER")


                // Only adminUser can delete Games
                .mvcMatchers(HttpMethod.DELETE, "/store/games/{gameId}").hasAuthority("ROLE_ADMIN")
                // staffUser can update Games
                .mvcMatchers(HttpMethod.PUT, "/store/games").hasAuthority("ROLE_STAFF")
                // mangerUser can create Games
                .mvcMatchers(HttpMethod.POST, "/store/games").hasAuthority("ROLE_MANAGER")


                // Only adminUser can delete T-shirts
                .mvcMatchers(HttpMethod.DELETE, "/store/tshirts/{tshirtId}").hasAuthority("ROLE_ADMIN")
                // staffUser can update T-shirts
                .mvcMatchers(HttpMethod.PUT, "/store/tshirts").hasAuthority("ROLE_ADMIN")
                // mangerUser can create t-shirts
                .mvcMatchers(HttpMethod.POST, "/store/tshirts").hasAuthority("ROLE_MANAGER")


                // plainUser(authenticated) can create purchase
                .mvcMatchers(HttpMethod.POST, "/store/purchases").hasAuthority("ROLE_USER")

                // remaining get and list of all products is available to all users.
                .anyRequest().permitAll();

        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/allDone").deleteCookies("JSESSIONID").deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }

}
