package com.edu.service;

import com.edu.core.service.impl.BaseServiceImpl;
import com.edu.dao.UserRepository;
import com.edu.dto.UserDto;
import com.edu.dto.create.UserCreate;
import com.edu.dto.criteria.UserCriteria;
import com.edu.dto.update.UserUpdate;
import com.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseServiceImpl<UserRepository, UserCriteria, UserCreate, UserUpdate, UserDto, User, Long> {

  @Autowired
  private BCryptPasswordEncoder BCryptPasswordEncoder;

  @Override
  public User fromCreateToEntity(UserCreate create) {
    User entity = super.fromCreateToEntity(create);
    entity.setPassword(BCryptPasswordEncoder.encode(entity.getPassword()));
    return entity;

  }
}
