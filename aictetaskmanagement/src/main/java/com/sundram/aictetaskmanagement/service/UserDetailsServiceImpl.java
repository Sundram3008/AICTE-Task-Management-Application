package com.sundram.aictetaskmanagement.service;

import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sundram.aictetaskmanagement.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    Logger logger = Logger.getLogger("New logger");

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        logger.info("email id is"+arg0);
        UserDetails user = userRepository.findByEmailId(arg0);
        if (user == null) {
            logger.info("user not found with this email id");
            throw new UsernameNotFoundException("the given email is not found");
        }
        logger.info("The username is "+user.getUsername());
        return user;
    }

}
