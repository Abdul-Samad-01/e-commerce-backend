package com.sellerbuyer.sellerbuyer.service;


import com.sellerbuyer.sellerbuyer.payloads.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    UserDto getUserById(long id);

    Page<UserDto> getUsers(Pageable pageable);

    UserDto addUser(UserDto userDto);

    List<UserDto> getUserByRole(String role);

    void approveUser(long id);

    void deactivateUser(long id);

    void activateUser(long id);
}
