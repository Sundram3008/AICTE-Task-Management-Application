
package com.sundram.aictetaskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sundram.aictetaskmanagement.model.AffiliationForm;


@Repository
public interface AffiliationFormRepository extends JpaRepository<AffiliationForm,Integer>{
    

    AffiliationForm getAffiliationFormByApplicationFormId(int id);

    @Query(value = "select * from Affiliation where college_id = ?1 ", nativeQuery = true)
   List<AffiliationForm> getAffiliationFormByCollegeId(int id);

   @Query(value = "select * from Affiliation where college_name = ?1", nativeQuery = true)
   List<AffiliationForm> getAffiliationFormLikeCollegeName(String collegeName);

   @Query(value = "select * from Affiliation where college_email_id = :email", nativeQuery = true)
   List<AffiliationForm> getAffiliationFormByCollegeEmailId(String email);

   @Query(value = "select * from Affiliation where is_affiliated = 0 and is_pending = 0", nativeQuery = true)
   List<AffiliationForm> getAllNonAffiliationForms();

   @Query(value = "select * from Affiliation where is_affiliated = 1", nativeQuery = true)
   List<AffiliationForm> getAllAffiliatedForms();

}
