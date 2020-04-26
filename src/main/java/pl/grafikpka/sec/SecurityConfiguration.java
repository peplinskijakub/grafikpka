package pl.grafikpka.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserPrincipalDetailService userPrincipalDetailService;

    public SecurityConfiguration(UserPrincipalDetailService userPrincipalDetailService) {
        this.userPrincipalDetailService = userPrincipalDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());

    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/index.html")
                .permitAll() //do startowej wszyscy
                .antMatchers("/users/**").authenticated() //dla użytkownika
                .antMatchers("/api/public/users", "/admin/**").hasRole("ADMIN")
                .antMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .and()
                .formLogin()
                .loginProcessingUrl("/signin")
                .loginPage("/login").permitAll()
                .usernameParameter("txtUsername")
                .passwordParameter("txtPassword")
                .and()
                .logout()
                //.invalidateHttpSession(true)
               // .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .and()
                .rememberMe().tokenValiditySeconds(2592000).key("mySecret!")
                .rememberMeParameter("checkRememberMe");
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
