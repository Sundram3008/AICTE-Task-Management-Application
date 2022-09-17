package com.sundram.aictetaskmanagement.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.AffiliationForm;
import com.sundram.aictetaskmanagement.model.Users;
import com.sundram.aictetaskmanagement.repository.AffiliationFormRepository;
import com.sundram.aictetaskmanagement.repository.UserRepository;

@RestController
public class AffiliationFormController {
    Logger logger = Logger.getLogger("AffiliationFormController logger");

    @Autowired
    AffiliationFormRepository affiliationFormRepository;

    @Autowired
    UserRepository userRepository;


    /* Will be used for apply for application by college connected to a button named (apply for affiliation) */
    @PostMapping("/affiliationform")
    public AffiliationForm saveAffiliationForm(@RequestBody AffiliationForm affiliationForm){
        logger.info("Initiating of createing Affiliation form");
        affiliationForm.setDateOfRegistration(new Date());
        AffiliationForm affiliationForm1= affiliationFormRepository.save(affiliationForm);
        return affiliationForm1;
    }

    /* To get affiliation form will be used in affiliator side while they click on details of a particular application  */
    @GetMapping("/affiliationform/{id}")
    public AffiliationForm getAffiliationForm(@PathVariable(name = "id") String affiliationFormApplicationID){
        logger.info("Starting getting affliation form with application id = "+ affiliationFormApplicationID.toString());
        AffiliationForm affiliationForm1= affiliationFormRepository.getAffiliationFormByApplicationFormId(Integer.parseInt(affiliationFormApplicationID));
        return affiliationForm1;
    }

    /* To delete affiliation form will be used at college side */
    @DeleteMapping("/affiliationform/{id}")
    public String deleteAffiliationForm(@PathVariable(name = "id") String affiliationFormApplicationId){
        logger.info("Starting Deleting Affiliation form with application id" + affiliationFormApplicationId.toString());
        AffiliationForm affiliationForm = affiliationFormRepository.getAffiliationFormByApplicationFormId(Integer.parseInt(affiliationFormApplicationId));
        try{
            affiliationFormRepository.delete(affiliationForm);
            return "Successfully Deleted Affiliation Form with Application id "+ affiliationFormApplicationId;
        }catch(Exception e){
            return "Affiliation form with application id "+affiliationFormApplicationId+" Not found"; 
        }
    }


    //to get all colleges with non affiliated form
    @GetMapping("/showNonAffiliatedColleges")
    public Object showNonAffiliatedColleges(){
        try{
            return affiliationFormRepository.getAllNonAffiliationForms();
        }catch(Exception e){
            return "No Non Affiliated Colleges";
        }
    }


    //to mark college affiliated
    @PatchMapping("/affiliationform/{userId}/{collegeId}")
    public Object markCollegeAffiliated(@PathVariable(name = "userId") String userId, 
    @PathVariable(name = "collegeId") String collegeId){
        try{
            List<AffiliationForm> affiliationForm = affiliationFormRepository.getAffiliationFormByCollegeId(Integer.parseInt(collegeId));
            AffiliationForm affiliationForm2 = affiliationForm.get(0);
            Users user = userRepository.getUsersByUserId(Integer.parseInt(userId));
            if(user.getRole().equalsIgnoreCase("affiliator")){
                affiliationForm2.setIsAffiliated(true);
                affiliationForm2.setIsPending(false);
                affiliationForm2.setIsRejected(false);
                affiliationFormRepository.save(affiliationForm2);
                return "Affiliated Success";
            }else{
                return "Current user is not allowed to perform this action";
            }
        }catch(RuntimeException e){
            return "Something went wrong";
        }

    }

    // to reject scholarship application
    @PatchMapping("/rejectaffiliationform/{userId}/{collegeId}")
    public Object rejectCollegeAffiliation(@PathVariable(name = "userId") String userId, @PathVariable(name = "collegeId") String collegeId){
        try{
            List<AffiliationForm> affiliationForm = affiliationFormRepository.getAffiliationFormByCollegeId(Integer.parseInt(collegeId));
            AffiliationForm affiliationForm2 = affiliationForm.get(0);
            Users user = userRepository.getUsersByUserId(Integer.parseInt(userId));
            if(user.getRole().equalsIgnoreCase("affiliator")){
                affiliationForm2.setIsRejected(true);
                affiliationForm2.setIsPending(false);
                affiliationForm2.setIsAffiliated(false);
                affiliationFormRepository.save(affiliationForm2);
                return "Affiliation Form Rejected";
            }else{
                return "Current user is not allowed to perform this action";
            }
        }catch(RuntimeException e){
            return "Something went wrong";
        }
    }

    /* to show all affiliated Colleges */
    @GetMapping("/showAffiliatedColleges")
    public Object showAffiliatedColleges(){
        try{
            return affiliationFormRepository.getAllAffiliatedForms();
        }catch(Exception e){
            return "No Affiliated Colleges";
        }
    }

    /* TO get affiliation form by college (might use in searhing by affiliator) */
    @GetMapping("/affiliationform/{userId}/{collegeName}")
    public Object showAffiliationFormByCollegeName(@PathVariable(name ="userId") String userId, @PathVariable(name = "collegeName") String collegeName){
        try{
            List<AffiliationForm> affiliationForm = affiliationFormRepository.getAffiliationFormLikeCollegeName(collegeName);
            Users user = userRepository.getUsersByUserId(Integer.parseInt(userId));
            if(user.getRole().equalsIgnoreCase("affiliator")){
                return affiliationForm;
            }else{
                return "Current user is not allowed to perform this action";
            }
        }catch(RuntimeException e){
            return "No Data";
        }
    }

    /* To check if college is affiliated or not will help in toggling button for apply for scholarship by college */
    @GetMapping("/affiliationform/{id}/isaffiliated")
    public Object isCollegeAffiliated(@PathVariable(name = "id") String id){
        try{
            List<AffiliationForm> affiliationForm = affiliationFormRepository.getAffiliationFormByCollegeId(Integer.parseInt(id));
            return affiliationForm.get(0).getIsAffiliated();
        }catch(Exception e){
            return "No data found";
        }
    }

    @GetMapping("/affiliationform/{emailId}")
    public Object getAffiliationFormByEmailId(@PathVariable(name= "emailId") String emailId){
        try{
            return affiliationFormRepository.getAffiliationFormByCollegeEmailId(emailId);
        }catch(Exception e){
            return "No data found";
        }
    }
}
