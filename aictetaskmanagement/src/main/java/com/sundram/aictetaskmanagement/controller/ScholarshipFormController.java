package com.sundram.aictetaskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.ScholarShip;
import com.sundram.aictetaskmanagement.model.ScholarshipForm;
import com.sundram.aictetaskmanagement.model.Users;
import com.sundram.aictetaskmanagement.repository.ScholarShipFormRepository;
import com.sundram.aictetaskmanagement.repository.ScholarShipRepository;
import com.sundram.aictetaskmanagement.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ScholarshipFormController {
    
    Logger logger = Logger.getLogger("ScholarshipFormController logger");

    @Autowired
    ScholarShipFormRepository scholarShipFormRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScholarShipRepository scholarShipRepository;

    //Save Schoarship Forms
    @PostMapping("/scholarshipform")
    public Object saveScholarShipForm(@RequestBody ScholarshipForm scholarShip){
        logger.info("Initiating save scholarship form");
        //checks if someone applied with same govt. id in same sch
        if(scholarShipFormRepository.getScholarshipFormByGovtId(scholarShip.getGovtId(), scholarShip.getScholarshipId()).size()==0){
            scholarShip.setDateOfRegistration(new Date());
            if(AutomateCheckScholarshipForm(scholarShip)==false){
                scholarShip.setRejected(true);
                scholarShip.setScholared(false);
                scholarShip.setPending(false);
            }
            else{
                scholarShip.setRejected(false);
                scholarShip.setScholared(true);
                scholarShip.setPending(false);
            }
            ScholarshipForm scholarShip1= scholarShipFormRepository.save(scholarShip);
            return scholarShip1;
        }else{
            return "Already applied with given Government Id";
        }
    }
    /*@PatchMapping("/criteriaform")
    public Object setCriteriaFormId(@RequestBody ScholarshipForm scholarshipForm){
        ScholarShip scholarShip = scholarShipRepository.getById(scholarshipForm.getScholarshipId());
        if(scholarShip.getHaveCriteriaForm()==false){
            scholarShip.setCriteriaFormid(scholarshipForm.getApplicationId());
            scholarShip.setHaveCriteriaForm(true);
            scholarShipFormRepository.save(scholarshipForm);
            return "Criteria form id filled ";
        }
        else return "Criteria Form already Exists";
    }
    */
    //Get scholarship Forms on the basis of email id
    @GetMapping("/scholarshipform/{emailId}")
    public Object getApplicationsByStudentEmail(@PathVariable(name = "emailId") String emailId){
        try{
            System.out.println(emailId.toString());
            List<ScholarshipForm> scholarshipForms= scholarShipFormRepository.getScholarShipFormByEmailId(emailId.toString());
            if(scholarshipForms.size()==0) System.out.println("0 data found");
            return scholarshipForms;
        }catch(Exception e){
            return "No applications Found";
        }
    }

    //Delete Scholarship Forms
    @DeleteMapping("scholarshipform/{id}")
    public String deleteScholarshipForm(@PathVariable(name = "id") String ApplicationId){
        logger.info("Starting Deleting scholarship form with application id = "+ ApplicationId.toString());
        ScholarshipForm scholarshipForm= scholarShipFormRepository.getScholarshipFormByApplicationId(Integer.parseInt(ApplicationId));
        try{
            scholarShipFormRepository.delete(scholarshipForm);
            return "Successfully Deleted Scholarship Form with given applicaton id" + ApplicationId; 
        }catch(Exception e){
            return "Scholarship Form with given Application id not found";
        }
    }

    //to show all non approved scholarship forms
    @GetMapping("/showNonApprovedScholarshipForms")
    public Object showNonApprovedScholarshipForms(){
        try{
            return scholarShipFormRepository.getAllNonApprovedScholarshipForms();
        }catch(Exception e){
            return "No non approved Scholarship forms";
        }
    }

    //to mark student scholared
    @PatchMapping("/scholarshipform/{userId}/{applicationId}")
    public Object markScholared(@PathVariable(name = "userId") String userId, @PathVariable(name = "applicationId") String applicationId){
        try{
            ScholarshipForm scholarshipForm = scholarShipFormRepository.getScholarshipFormByApplicationId(Integer.parseInt(applicationId));
            Users user = userRepository.getUsersByUserId(Integer.parseInt(userId));
            if(user.getRole().equalsIgnoreCase("employee")){
                scholarshipForm.setScholared(true);
                scholarshipForm.setRejected(false);
                scholarshipForm.setPending(false);
                scholarShipFormRepository.save(scholarshipForm);
                return "Scholarship Granted";
            }
            else{
                return "Current user is not allowed to perform this action";
            }
        }catch(RuntimeException e){
            return "Something went wrong";
        }
    }

    // TO reject Scholarship Form
    @PatchMapping("/rejectscholarshipform/{userId}/{applicationId}")
    public Object rejectScholarshiForm(@PathVariable(name = "userId") String userId, @PathVariable(name = "applicationId") String applicationId){
        try{
            ScholarshipForm scholarshipForm = scholarShipFormRepository.getScholarshipFormByApplicationId(Integer.parseInt(applicationId));
            Users user = userRepository.getUsersByUserId(Integer.parseInt(userId));
            if(user.getRole().equalsIgnoreCase("employee")){
                scholarshipForm.setRejected(true);
                scholarshipForm.setScholared(false);
                scholarshipForm.setPending(false);
                scholarShipFormRepository.save(scholarshipForm);
                return "Scholarship Rejected";
            }
            else{
                return "Current user is not allowed to perform this action";
            }
        }catch(RuntimeException e){
            return "Something went wrong";
        }
    }
    //to show all approved scholarship forms
    @GetMapping("/showApprovedScholarshipForms")
    public Object showApprovedScholarshipForms(){
        try{
            return scholarShipFormRepository.getALLApprovedScholarshipForms();
        }catch(Exception e){
            return "No approved Scholarship forms";
        }
    }

    //to show Scholarship forms by collegeName for employee
    @GetMapping("/scholarshipform/{userId}/{collegeName}")
    public Object getScholarshipFormsByCollegeName(@PathVariable(name = "userId") String userId, @PathVariable(name = "collegeName") String collegeName){
        try{
            Users users= userRepository.getUsersByUserId(Integer.parseInt(userId));
            List<ScholarshipForm> scholarshipForm= scholarShipFormRepository.getScholarshipFormByCollegeName(collegeName.toString());
            if(users.getRole().equalsIgnoreCase("employee")){
                return scholarshipForm;
            }
            else{
                return "Current user is not allowed to perform this action";
            }
        }catch(RuntimeException e){
            return "No data Found";
        }
    }

    private Boolean AutomateCheckScholarshipForm(ScholarshipForm scholarshipForm){
        int schId= scholarshipForm.getScholarshipId();
        ScholarShip scholarShip = scholarShipRepository.getById(schId);
        if(scholarShip.getMarks10th()<=scholarshipForm.getSecondaryPercentage() && scholarShip.getMarks12th()<=scholarshipForm.getSeniorSecondaryPercentage()){
            return true;
        }
        else return false;
    }

}
