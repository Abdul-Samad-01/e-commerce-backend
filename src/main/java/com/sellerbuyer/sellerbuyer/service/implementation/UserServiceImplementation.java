package com.sellerbuyer.sellerbuyer.service.implementation;

import com.sellerbuyer.sellerbuyer.entity.Role;
import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.exception.CustomException;
import com.sellerbuyer.sellerbuyer.payloads.dto.UserDto;
import com.sellerbuyer.sellerbuyer.repository.UserRepository;
import com.sellerbuyer.sellerbuyer.service.UserService;
import com.sellerbuyer.sellerbuyer.util.DtoConverter;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.sellerbuyer.sellerbuyer.util.Constants.ERROR_WHILE_SAVING_USER;
import static com.sellerbuyer.sellerbuyer.util.Constants.NOT_AUTHORISED_TO_CHOOSE_ROLE;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DtoConverter dtoConverter;
    private final Logger logger;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                     DtoConverter dtoConverter, Logger logger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dtoConverter = dtoConverter;
        this.logger = logger;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = dtoConverter.convertToEntity(userDto,
                User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole().getId() == 2) {
            user.setRole(new Role(4, ""));
        } else if (user.getRole().getId() == 3) {
            throw new CustomException(HttpStatus.BAD_REQUEST, NOT_AUTHORISED_TO_CHOOSE_ROLE);
        }
        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_USER + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_USER);
        }
        return dtoConverter.convertToDto(user, UserDto.class);
    }
}
