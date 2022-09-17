package com.sundram.aictetaskmanagement.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sundram.aictetaskmanagement.model.Role;
import com.sundram.aictetaskmanagement.model.Users;
import com.sundram.aictetaskmanagement.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    Logger logger = Logger.getLogger("RoleService");


    public Role saveRole(Role role, String userId){
        Role role1 = new Role();
        String errorMessage;
        try{
            String roleName = role.getName();
            Users user= userService.getUserById(Integer.parseInt(userId));
            if(roleRepository.getRoleByName(roleName)!=null || (!roleName.equalsIgnoreCase("admin") && !roleName.equalsIgnoreCase("employee") && !roleName.equalsIgnoreCase("affiliator") && !roleName.equalsIgnoreCase("college"))){
                errorMessage = "role with same role name already exists";
                throw new RuntimeException(errorMessage);
            }
            if(user.getRole().equalsIgnoreCase("admin")){
                role1 = roleRepository.save(role);
            }
            errorMessage = "user is not authorized to create a new role";
        }catch(Exception e){
            errorMessage = "Exception occured while creating a role";
            logger.info("Exception occured while creating a role");
            throw new RuntimeException("Exception occurred while creating a role");
        }
        return role1;
    }

    public Role getRoleById(int roleId){
        try{
            return roleRepository.getRoleByRoleId(roleId);
        }catch(Exception e){
            throw new RuntimeException("Role with given role id not found");
        }
    }

}
