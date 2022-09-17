package com.sundram.aictetaskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sundram.aictetaskmanagement.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {


   public Role getRoleByRoleId(int id);

   public void deleteRoleByRoleId(int id);

   
   public Role getRoleByName(String name);
}
