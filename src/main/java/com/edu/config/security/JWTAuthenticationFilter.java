package com.edu.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.edu.dao.UserRepository;
import com.edu.dto.UserLoginDto;
import com.edu.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  private String jwtSecretKey;

  private long jwtExpirationMs;

  private UserRepository userRepository;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {
    try {
      User creds = new ObjectMapper()
          .readValue(req.getInputStream(), User.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              creds.getUsername(),
              creds.getPassword(),
              new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException {

    String userName = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
    String token = JWT.create()
        .withSubject(userName)
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .sign(Algorithm.HMAC512(jwtSecretKey.getBytes()));

    res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    res.setContentType("application/json");

    UserLoginDto userLoginDto = userRepository.findByUsername(userName)
        .map(user -> {
          UserLoginDto dto = new UserLoginDto();
          dto.setId(user.getId());
          dto.setUsername(user.getUsername());
          dto.setEmail(user.getEmail());
          dto.setAccessToken(token);
          return dto;
        })
        .orElseThrow(() -> new RuntimeException("Can not get user form userName: " + userName));

    ObjectMapper mapper = new ObjectMapper();
    //Converting the Object to JSONString
    String jsonString = mapper.writeValueAsString(userLoginDto);

    res.getWriter().write(jsonString);
    res.getWriter().flush();
  }

  public String getJwtSecretKey() {
    return jwtSecretKey;
  }

  public void setJwtSecretKey(String jwtSecretKey) {
    this.jwtSecretKey = jwtSecretKey;
  }

  public long getJwtExpirationMs() {
    return jwtExpirationMs;
  }

  public void setJwtExpirationMs(long jwtExpirationMs) {
    this.jwtExpirationMs = jwtExpirationMs;
  }

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
