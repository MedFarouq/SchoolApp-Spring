package com.emsi.schoolmangmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder passwordEncoder = passwordEncoder();

        String encodedPWD = passwordEncoder.encode("1234");
        System.out.println(encodedPWD);
        auth.inMemoryAuthentication().withUser("User1").password(encodedPWD).roles("USER");
        auth.inMemoryAuthentication().withUser("User2").password(passwordEncoder.encode("1111")).roles("USER");
        auth.inMemoryAuthentication().withUser("Admin").password(passwordEncoder.encode("2345")).roles("USER","ADMIN");


//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
//                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
//                .rolePrefix("ROLE_")
//                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin();
//        http.authorizeRequests().antMatchers("/").permitAll();
//        http.authorizeRequests().antMatchers("/delete/**","/edit/**","/Delete/**","/editTeacher/**","/save/**","/formStudents/**","/formTeacher/**","/formStudents/**").hasRole("ADMIN");
//        http.authorizeRequests().antMatchers("/index/**","/home/**","/about/**","/contact/**").hasRole("USER");
//        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
//        http.authorizeRequests().antMatchers("/user/**").hasRole("USER");
//        http.authorizeRequests()C:\Users\Med\IdeaProjects\
//        http.authorizeRequests().anyRequest().authenticated();
//        http.exceptionHandling().accessDeniedPage("/403");

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
