package com.sellerbuyer.sellerbuyer.service.implementation;


import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.repository.UserRepository;
import com.sellerbuyer.sellerbuyer.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        return new MyUserDetails(user);
    }
}
