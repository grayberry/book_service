package am.dreamteam.bookservice.configs;

import am.dreamteam.bookservice.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private DataSource dataSource;

    public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder, DataSource dataSource) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().ignoringAntMatchers("/dialogs/**", "/transfers/**","/admin/**", "/user/mypage/**", "/resend")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()
                .passwordParameter("password")
                .usernameParameter("username")
                .and()
                .logout()
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/registration","/admin").permitAll()
                .antMatchers("/admin").hasRole("ADMIN");
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, active from users where lower(username) = lower(?)")
                .authoritiesByUsernameQuery("select u.username, r.roles from users u inner join roles r on u.id = r.user_id where u.username = ?");

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, active from users where lower(email) = lower(?)")
                .authoritiesByUsernameQuery("select u.username, r.roles from users u inner join roles r on u.id = r.user_id where u.username = ?");


    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
}