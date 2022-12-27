package com.irinalyamina.InsuranceAgency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/").permitAll()

                .antMatchers("/car/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/employee/**").hasAnyRole("ADMIN")
                .antMatchers("/personAllowedToDrive/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/policyholder/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/report/**").hasAnyRole("ADMIN", "OPERATOR")

                .antMatchers("/policy/list").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/policy/listUser").hasAnyRole("USER")
                .antMatchers("/policy/details/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/policy/create/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/policy/edit/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/policy/editPersonsAllowedToDrive/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/policy/delete/**").hasAnyRole("ADMIN")

                .antMatchers("/insuranceEvent/list").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/insuranceEvent/details/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/insuranceEvent/create/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/insuranceEvent/delete/**").hasAnyRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }

//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select username,password,enabled from UsersTable where username=?")
//                .authoritiesByUsernameQuery("select username,authority from authorities where username=?");
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("agencyaadm@gmail.com")
                        .password("agencyaadm")
                        .roles("ADMIN")
                        .build();
        UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("mishina@mail.ru")
                        .password("mishina")
                        .roles("OPERATOR")
                        .build();
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("novikov@gmail.com")
                        .password("novikov")
                        .roles("OPERATOR")
                        .build();
        UserDetails user3 =
                User.withDefaultPasswordEncoder()
                        .username("aleksandrov@mail.ru")
                        .password("aleksandrov")
                        .roles("OPERATOR")
                        .build();
        UserDetails user4 =
                User.withDefaultPasswordEncoder()
                        .username("morozova@gmail.com")
                        .password("morozova")
                        .roles("OPERATOR")
                        .build();
        UserDetails user5 =
                User.withDefaultPasswordEncoder()
                        .username("gavrilov@gmail.com")
                        .password("gavrilov")
                        .roles("USER")
                        .build();
        UserDetails user6 =
                User.withDefaultPasswordEncoder()
                        .username("shestakov@gmail.com")
                        .password("shestakov")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user, user1, user2, user3, user4, user5, user6);
    }
}