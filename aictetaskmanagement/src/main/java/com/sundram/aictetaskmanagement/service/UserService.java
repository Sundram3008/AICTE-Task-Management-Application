package com.sundram.aictetaskmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sundram.aictetaskmanagement.model.Users;
import com.sundram.aictetaskmanagement.repository.UserRepository;

@Service
public class UserService {

    
    @Autowired(required = false)
    UserRepository userRepository;


    @Transactional(rollbackFor = Exception.class)
    public Users saveUser(Users user){

        //this method is responsible to save user

        Users userObj = new Users();
        userObj =  userRepository.save(user);
        if(userObj == null){
            throw new RuntimeException("Unable to createUser");
        }
        return userObj;
    }


    public Users getUserById(int id){
        Users user = userRepository.getUsersByUserId(id);
        if(user == null){
            throw new RuntimeException("Unable to get User");
        }
        return user;
    }

    public String getUserRoleById(int id){
        String role = userRepository.getRoleByUserId(id);
        if(role == null){
            throw new RuntimeException("Unable to get User");
        }
        return role;
    }

}

