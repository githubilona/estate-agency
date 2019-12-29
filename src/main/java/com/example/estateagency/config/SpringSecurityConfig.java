package com.example.estateagency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = passwordEncoder();
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("user1").password(encoder.encode("user")).roles("USER");
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("admin1").password(encoder.encode("admin")).roles("ADMIN");
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("useradmin1").password(encoder.encode("useradmin")).roles("USER","ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/statics/**", "/webjars/**", "/", "/propertyList.html")
                .permitAll()//do powyższych zasobów ma mieć każdy
                .antMatchers( "/propertyForm.html").hasRole("ADMIN")//do tych zasobów ma dostęp tylko ADMIN
                .anyRequest().authenticated();//pozostałe żądania mają być uwierzytelnione

        http
                .formLogin()//pozwól użytkownikom uwierzytelniać się poprzez formularz logowania
                .loginPage("/login")//formularz dostępny jest pod URL
                .permitAll();//pozwól każdemu się zalogować.

        http.logout().permitAll();//pozwól każdemu się wylogować
    }



}

