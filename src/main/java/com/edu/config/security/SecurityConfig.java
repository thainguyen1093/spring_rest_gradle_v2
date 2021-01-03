package com.edu.config.security;

import com.edu.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${project.app.jwtSecretKey}")
  private String jwtSecretKey;

  @Value("${project.app.jwtExpirationMs}")
  private long jwtExpirationMs;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    String[] userAllowList = {"/user-details"};
    String[] adminAllowList = {"/admin"};
    String[] allowList = {"/user/**"};

    JWTAuthenticationFilter jWTAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager());
    jWTAuthenticationFilter.setJwtSecretKey(jwtSecretKey);
    jWTAuthenticationFilter.setJwtExpirationMs(jwtExpirationMs);
    jWTAuthenticationFilter.setUserRepository(userRepository);

    JWTAuthorizationFilter jWTAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager());
    jWTAuthorizationFilter.setJwtSecretKey(jwtSecretKey);
    jWTAuthorizationFilter.setUserDetailsService(userDetailsService);

    http.cors()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(userAllowList).hasAuthority("USER")
        .antMatchers(adminAllowList).hasAuthority("ADMIN")
        .antMatchers(allowList).permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(jWTAuthenticationFilter)
        .addFilter(jWTAuthorizationFilter)
        // this disables session creation on Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

//  public static void main(String[] args) {
//    SecurityConfig securityConfig = new SecurityConfig();
//    String passwordEncoded = securityConfig.bCryptPasswordEncoder().encode("root");
//    System.out.println("passwordEncoded: " + passwordEncoded);
//  }
}
