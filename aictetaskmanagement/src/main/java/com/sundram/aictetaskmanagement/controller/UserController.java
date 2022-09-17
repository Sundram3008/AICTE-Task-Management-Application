package com.sundram.aictetaskmanagement.controller;

import java.util.logging.Logger;

import com.sundram.aictetaskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.Users;


@RestController
public class UserController {
    

    Logger logger = Logger.getLogger("Usercontroller logger");


    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public Object saveUser(@RequestBody Users users){
        logger.info("initiating of create user");
        if(userRepository.getUsersByEmailId(users.getEmailId())!=null){
            return "User already exists";
        }
        else{
            users.setUsername(users.getEmailId());
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            Users user = userRepository.save(users);
            return user;
        }
    }

    @PostMapping("/user/{id}/register")
    public Object saveEmployeeAndAffliator(@RequestBody Users users, @PathVariable(name ="id") String id){
        logger.info("initiating of create user");
        if(userRepository.getUsersByEmailId(users.getEmailId())!=null){
            return "User already exists";
        }
        else{
            users.setUsername(users.getEmailId());
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
            Users user = userRepository.save(users);
            return user;
        }
    }
    @GetMapping("/user/{id}")
    public Users getUser(@PathVariable(name = "id") String emailId ){
        logger.info("Starting getting user with user id = " + emailId.toString());
        Users users = userRepository.getUsersByEmailId(emailId.toString());
        return users;
    }
    /* Changes Required */
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable(name = "id") String UserId){
        logger.info("Starting Deleting user with user id = " + UserId.toString());
        Users users= userRepository.getUsersByUserId(Integer.parseInt(UserId));
        try{
            userRepository.delete(users);
            return  "Successfully Deleted user " + UserId;
        }catch(Exception e){
            return "User Not Found with user id "+ UserId;
        }
    }

    @GetMapping("/user/{id}/employee")
    public Object getAllEmployeesPresent(){
        try{ 
            return userRepository.getAllEmployees();
        }catch(Exception e){
            return "No Employees Found";
        }
    }

    @GetMapping("/user/{id}/affiliator")
    public Object getAllAffiliatorsPresent(){
        try{ 
            return userRepository.getAllAffiliators();
        }catch(Exception e){
            return "No Affiliators Found";
        }
    }

    public int GetId(String emailId){
        return userRepository.getUsersByEmailId(emailId).getUserId();
    }
}
