package com.sundram.aictetaskmanagement.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.FogotPassword;
import com.sundram.aictetaskmanagement.model.Users;
import com.sundram.aictetaskmanagement.repository.UserRepository;


@RestController
public class ForgotPasswordController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/forgotpassword/{emailId}")
    private boolean getUserByEmailId(@PathVariable(name = "emailId") String emailId){
        if(userRepository.getUsersByEmailId(emailId)!=null){
            return true;
        }else{
            return false;
        }
    }
    @PostMapping("/forgotPassword/{emailId}/{dob}")
    private boolean checkSecurityQuestion(@PathVariable(name = "emailId") String emailId,@PathVariable(name ="dob") Date dob){
        if(userRepository.getUsersByEmailId(emailId).getDob()==dob ) return true;
        else return false;
    }

    @PatchMapping("/updatepassword/{emailId}")
    private Object updatePassword(@RequestBody FogotPassword fogotPassword, @PathVariable(name = "emailId") String emailId){
        Users user= userRepository.getUsersByEmailId(emailId);
        user.setUsername(user.getEmailId());
        user.setPassword(bCryptPasswordEncoder.encode(fogotPassword.getNewPassword()));
        userRepository.save(user);
        return "Password Updated Successfully";
    }
}
