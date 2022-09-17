package com.sundram.aictetaskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sundram.aictetaskmanagement.model.ScholarShip;



@Repository
public interface ScholarShipRepository extends JpaRepository<ScholarShip,Integer>{
    

   ScholarShip getScholarShipByScholarShipId(int id);

   // To get all scholarships
   @Query(value = "select * from scholarship", nativeQuery = true)
   List<ScholarShip> getAllScholarships();

   // To get all applications on a particular scholarship
   @Query(value = "select * from scholarship_forms where scholarship_id like %id% ORDER BY date_of_registration DESC", nativeQuery = true)
   List<ScholarShip> getAllApplicationsWithScholarshipId(int id);

   void deleteScholarShipByScholarShipId(int id);

}
