package com.sundram.aictetaskmanagement.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.Role;
import com.sundram.aictetaskmanagement.repository.RoleRepository;
import com.sundram.aictetaskmanagement.service.RoleService;

@RestController
public class RoleController {
    

    Logger logger = Logger.getLogger("Role controller logger");

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("/role/{id}")
    Role saveRole(@PathVariable(name="id") String id,@RequestBody Role role){
        logger.info("Start creating role");
        return roleService.saveRole(role, id);
    }


    @GetMapping("/role/{id}")
    Role getRoleById(@PathVariable int id){
        logger.info("start getting user");
        return roleService.getRoleById(id);
    }
    
}
