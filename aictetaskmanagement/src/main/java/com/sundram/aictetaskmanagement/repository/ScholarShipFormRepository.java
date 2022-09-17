package com.sundram.aictetaskmanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sundram.aictetaskmanagement.model.ScholarshipForm;


@Repository
public interface ScholarShipFormRepository extends JpaRepository<ScholarshipForm,Integer>{

   /* Will be used to check if there doesn't exist a student with same govtId  */
   @Query(value = "select * from scholarship_forms where govt_id = :govid and scholarship_id = :schid", nativeQuery = true)
   List<ScholarshipForm> getScholarshipFormByGovtId(String govid, int schid);

   @Query(value = "SELECT * FROM scholarship_forms where college_name like :collegename ORDER BY date_of_registration DESC",  nativeQuery = true)
   List<ScholarshipForm> getScholarshipFormByCollegeName(String collegename);


   @Query(value = "select * from scholarship_forms where date_of_registration like %:dateOfReg% ORDER BY date_of_registration DESC", nativeQuery = true)
   List<ScholarshipForm> getScholarShipFormByDateOfRegistration(Date dateOfReg);

   @Query(value = "select * from scholarship_forms where email_id like :emailId ORDER BY date_of_registration DESC", nativeQuery = true)
   List<ScholarshipForm> getScholarShipFormByEmailId(String emailId);

   @Query(value= "select * from scholarship_forms where application_id =  :id", nativeQuery = true)
   ScholarshipForm getScholarshipFormByApplicationId(int id);

   @Query(value = "select * from scholarship_forms where student_id = ?1 ORDER BY date_of_registration DESC", nativeQuery = true)
   List<ScholarshipForm> getScholarShipFormByStudentIdOrderByDateOfRegistrationDesc(int studentId);

   @Query(value = "select * from scholarship_forms where is_scholared = 0 and is_pending = 1", nativeQuery = true)
   List<ScholarshipForm> getAllNonApprovedScholarshipForms();

   //Will be used in College Controller to view scholarhip Forms related to that college
   @Query(value = "select * from scholarship_forms where college_id like %collegeId%", nativeQuery = true)
   List<ScholarshipForm> getScholarshipFormsOfCollege(int collegeId);

   @Query(value = "select * from scholarship_forms where is_scholared = 1", nativeQuery = true)
   List<ScholarshipForm> getALLApprovedScholarshipForms();

   @Query(value = "select * from scholarship_forms where scholarship_id = :sch_id", nativeQuery = true)
   List<ScholarshipForm> getAllScholarshipFromsOfSchId(int sch_id);

   @Query(value = "select * from scholarship_forms", nativeQuery = true)
   List<ScholarshipForm> getAllScholarshipForms();
}