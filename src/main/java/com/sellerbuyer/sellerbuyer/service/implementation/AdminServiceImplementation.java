package com.sellerbuyer.sellerbuyer.service.implementation;

import com.sellerbuyer.sellerbuyer.entity.Role;
import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.exception.CustomException;
import com.sellerbuyer.sellerbuyer.payloads.dto.UserDto;
import com.sellerbuyer.sellerbuyer.repository.UserRepository;
import com.sellerbuyer.sellerbuyer.service.AdminService;
import com.sellerbuyer.sellerbuyer.util.DtoConverter;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sellerbuyer.sellerbuyer.util.Constants.ERROR_WHILE_SAVING_USER;
import static com.sellerbuyer.sellerbuyer.util.Constants.USERS_NOT_FOUND;


@Service
public class AdminServiceImplementation implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DtoConverter dtoConverter;
    private final Logger logger;

    @Autowired
    public AdminServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                      DtoConverter dtoConverter, Logger logger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dtoConverter = dtoConverter;
        this.logger = logger;
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "user Not found"));
        return dtoConverter.convertToDto(user, UserDto.class);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        if (userPage.isEmpty()) {
            logger.warn(USERS_NOT_FOUND);
            throw new CustomException(HttpStatus.NOT_FOUND, USERS_NOT_FOUND);
        } else {
            return userPage.map((user) -> dtoConverter.convertToDto(user, UserDto.class));
        }
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = dtoConverter.convertToEntity(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_USER + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return dtoConverter.convertToDto(user, UserDto.class);
    }

    @Override
    public List<UserDto> getUserByRole(String role) {
        role = "ROLE_" + role.toUpperCase();
        List<User> userList = userRepository.findByRole_Role(role);
        if (userList.isEmpty()) {
            logger.warn(USERS_NOT_FOUND);
            throw new CustomException(HttpStatus.NOT_FOUND, USERS_NOT_FOUND);
        } else {
            return dtoConverter.convertToDtoList(userList, UserDto.class);
        }
    }

    @Override
    public void approveUser(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, USERS_NOT_FOUND));
        existingUser.setRole(new Role(2, ""));
        try {
            userRepository.save(existingUser);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_USER + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_USER);
        }
    }

    @Override
    public void deactivateUser(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, USERS_NOT_FOUND));
        existingUser.setActive(false);
        try {
            userRepository.save(existingUser);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_USER + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_USER);
        }
    }

    @Override
    public void activateUser(long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, USERS_NOT_FOUND));
        existingUser.setActive(true);
        try {
            userRepository.save(existingUser);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_USER + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_USER);
        }
    }
}
