package com.edu.config.security;

import com.edu.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(entity -> {
          SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(entity.getRole().name());
          List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
          simpleGrantedAuthorityList.add(simpleGrantedAuthority);

          return new User(entity.getUsername(), entity.getPassword(), simpleGrantedAuthorityList);
        })
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}