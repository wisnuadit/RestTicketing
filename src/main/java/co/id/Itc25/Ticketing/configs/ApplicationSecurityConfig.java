package co.id.Itc25.Ticketing.configs;

import co.id.Itc25.Ticketing.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public ApplicationSecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    //Authentication -> username, password
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("itc25")
//                .password(passwordEncoder().encode("indocyber")).roles("ADMIN");

        auth.userDetailsService(userDetailsService);
    }

    //Autorization -> role / authority
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user").authenticated()
                //
                .antMatchers("/employee").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/employee/employee-available").hasRole("ADMIN")
                .antMatchers("/employee/inser-employee").hasRole("ADMIN")
                .antMatchers("/employee/register").permitAll()
                .antMatchers("/employee/update-employee").hasRole("ADMIN")
                .antMatchers("/employee/delete-employee").hasRole("ADMIN")
                //
                .antMatchers("/ticket").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/ticket/ticket-by-year").hasRole("ADMIN")
                .antMatchers("/ticket/ticket-by-status").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/ticket/ticket-orderby-urgency").hasRole("ADMIN")
                .antMatchers("/ticket/ticket-request").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/ticket/insert-ticket").hasRole("ADMIN")
                .antMatchers("/ticket/cancel-ticket").hasRole("ADMIN")
                .antMatchers("/ticket-history").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/ticket-history/insert-ticket-history").hasRole("TECH_SUPPORT")
                .antMatchers("/ticket-history/update-tocompleted-ticket").hasRole("MANAGER")
                .and()
                .httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //encode -> decode
    //plaintext -> encryption -> hash
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
