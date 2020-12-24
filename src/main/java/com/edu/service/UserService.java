package com.edu.service;

import com.edu.core.service.impl.BaseServiceImpl;
import com.edu.dao.UserRepository;
import com.edu.dto.UserDto;
import com.edu.dto.create.UserCreate;
import com.edu.dto.criteria.UserCriteria;
import com.edu.dto.update.UserUpdate;
import com.edu.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseServiceImpl<UserRepository, UserCriteria, UserCreate, UserUpdate, UserDto, User, Long> {

}
