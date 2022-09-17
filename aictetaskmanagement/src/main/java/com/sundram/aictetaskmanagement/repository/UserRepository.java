package com.sundram.aictetaskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sundram.aictetaskmanagement.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
    

    public Users getUsersByUserId(int id);

    public String getRoleByUserId(int id);

    // @Query(value = "Select * from Users where email_id = ?1", nativeQuery = true)
    public Users getUsersByEmailId(String email);
    

    /* To get list of all Employees */
   @Query(value = "select * from users where role like '%employee%'  ", nativeQuery = true)
   List<Users> getAllEmployees();

   /* To get list of all Affiliators*/
   @Query(value = "select * from users where role like '%affiliator%' ", nativeQuery = true)
   List<Users> getAllAffiliators();

    public Users findByEmailId(String email);
}
