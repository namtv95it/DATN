package nem.com.config;

import nem.com.security.jwt.JwtEntryPoint;
import nem.com.security.jwt.JwtTokenFilter;
import nem.com.security.userprincipal.staff.EmployeeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@Order(1)
public class WebSecurityAdminConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EmployeeUserDetailsService employeeUserDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean("authenticationManagerAdmin")
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/v1/category/**", "/api/v1/product/**", "/api/v1/productDetail/**", "/api/v1/productImage/**", "/api/v1/color/**", "/api/v1/size/**").permitAll()
//                .antMatchers("/api/v1/customer/**", "/api/v1/address/**", "/api/v1/cart/**", "/api/v1/order-online", "/api/v1/order-detail-online").hasAnyRole("CUSTOMER", "ADMIN")
//                .antMatchers("/api/v1/employee/**", "/api/v1/customer/**", "/api/v1/category/**", "/api/v1/product/**",
//                        "/api/v1/productDetail/**", "/api/v1/color/**", "/api/v1/size/**").hasRole("ADMIN")
//                .anyRequest().hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
