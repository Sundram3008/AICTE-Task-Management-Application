package com.sundram.aictetaskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.ScholarShip;
import com.sundram.aictetaskmanagement.repository.ScholarShipRepository;

import java.util.Date;
import java.util.logging.Logger;

@RestController
public class ScholarshipController {
    
    Logger logger = Logger.getLogger("ScholarshipController logger");

    @Autowired
    ScholarShipRepository scholarShipRepository;

    /* TO create new scholarship will be used by employee to create new scholarship */
    @PostMapping("/scholarship")
    public ScholarShip saveScholarShip(@RequestBody ScholarShip scholarship){
        logger.info("Initiating of crete scholarship");
        scholarship.setCreationDate(new Date());
        ScholarShip scholarShip = scholarShipRepository.save(scholarship);
        return scholarShip; 
    }

    /* To get scholarship details (will be used when user clicks on details button present in row of this scholarship) */
    @GetMapping("/scholarship/{id}")
    public ScholarShip getScholarShip(@PathVariable(name = "id") String scholarshipId){
        logger.info("Starting Getting scholarship with scholarship id"+ scholarshipId.toString());
        ScholarShip scholarShip= scholarShipRepository.getScholarShipByScholarShipId(Integer.parseInt(scholarshipId));
        return scholarShip;
    }

    /* To delete Scholarship while seeing details (Will be used by employee) */
    @DeleteMapping("/scholarship/{id}")
    public String deleteScholarship(@PathVariable(name = "id") String scholarshipId){
        logger.info("Starting Deleting user with scholarship id "+ scholarshipId.toString());
        ScholarShip scholarShip= scholarShipRepository.getScholarShipByScholarShipId(Integer.parseInt(scholarshipId));
        try{
            scholarShipRepository.delete(scholarShip);
            return "Successfully Deleted scholarship with scholarship id "+ scholarshipId;
        }catch(Exception e){
            return "Scholarship Not Found with scholarship id "+ scholarshipId;
        }
    }

    /* returns list of all present scholarship will be used by employee and college*/
    @GetMapping("/scholarship")
    public Object allScholarships(){
        try{
            return scholarShipRepository.getAllScholarships();
        }catch(Exception e){
            return "No Scholarships Found";
        }
    }
    /* will be used to show all application on a particular scholarship when clicked on details button of a scholarship */
    @GetMapping("/scholarship/{id}/details")
    public Object getApplicationOnaScholarship(@PathVariable(name ="id") String id){
        try{
            return scholarShipRepository.getAllApplicationsWithScholarshipId(Integer.parseInt(id));
        }catch(Exception e){
            return "No application found";
        }
    }
}
