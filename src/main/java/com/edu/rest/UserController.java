package com.edu.rest;

import com.edu.core.rest.impl.BaseControllerImpl;
import com.edu.dto.UserDto;
import com.edu.dto.create.UserCreate;
import com.edu.dto.criteria.UserCriteria;
import com.edu.dto.update.UserUpdate;
import com.edu.entity.User;
import com.edu.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseControllerImpl<UserService, UserCriteria, UserCreate, UserUpdate, UserDto, User, Long> {

}
