package com.edu.service;

import com.edu.core.service.impl.BaseServiceImpl;
import com.edu.dao.UserRepository;
import com.edu.dto.UserDto;
import com.edu.dto.create.UserCreate;
import com.edu.dto.criteria.UserCriteria;
import com.edu.dto.update.UserUpdate;
import com.edu.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseServiceImpl<UserRepository, UserCriteria, UserCreate, UserUpdate, UserDto, User, Long> {

  @Override
  public User newEntity() {
    return new User();
  }

  @Override
  public UserDto newDto() {
    return new UserDto();
  }

  @Override
  public Specification<User> newSpecification(UserCriteria userCriteria) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
}
