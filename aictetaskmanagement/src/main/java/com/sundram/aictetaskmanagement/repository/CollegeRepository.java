package com.sundram.aictetaskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sundram.aictetaskmanagement.model.College;


@Repository
public interface CollegeRepository extends JpaRepository<College,Integer>{
    
    

    @Query(value = "select * from College where college_name like %:name%", nativeQuery = true)
    List<College> getCollegeLikecollegeName(String name);

    College getCollegeBycollegeId(int id);

}
