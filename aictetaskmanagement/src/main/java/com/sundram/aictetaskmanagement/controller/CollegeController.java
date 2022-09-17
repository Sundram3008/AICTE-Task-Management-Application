package com.sundram.aictetaskmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.College;
import com.sundram.aictetaskmanagement.model.ScholarshipForm;
import com.sundram.aictetaskmanagement.repository.CollegeRepository;
import com.sundram.aictetaskmanagement.repository.ScholarShipFormRepository;

@RestController
public class CollegeController {
    Logger logger = Logger.getLogger("CollegeController logger");

    @Autowired
    CollegeRepository collegeRepository;

    @Autowired 
    ScholarShipFormRepository scholarShipFormRepository;

    // create college 
    @PostMapping("/college")
    public College saveCollege(@RequestBody College college){
        logger.info("Initiating of create college");
        College college1= collegeRepository.save(college);
        return college1;
    }

    // to get college by id 
    @GetMapping("/college/{id}")
    public College getCollege(@PathVariable(name= "id") String collegeId){
        logger.info("Starting getting college with college id = "+ collegeId.toString());
        College college = collegeRepository.getCollegeBycollegeId(Integer.parseInt(collegeId));
        return college;
    }

    // To delete account of College
    @DeleteMapping("/college/{id}")
    public String deleteCollege(@PathVariable(name = "id") String CollegeId){
        logger.info("Starting Deleting college with college id = " + CollegeId.toString());
        College college= collegeRepository.getCollegeBycollegeId(Integer.parseInt(CollegeId));
        try{
            collegeRepository.delete(college);
            return "College with college id "+ CollegeId +" deleted Successfully";
        }catch(Exception e){
            return "College Not Found with college id "+ CollegeId;
        }
    }

    // to get all approved scholarship of a particular college (initially will be shown in scholarship forms section of college)
    @GetMapping("/college/{id}/scholarshipform")
    public Object getAllScholarshipFormsOfCollege(@PathVariable(name="id") String CollegeId){
        try{
            List<ScholarshipForm> scholarshipFormsOfGivenCollegeId= scholarShipFormRepository.getScholarshipFormsOfCollege(Integer.parseInt(CollegeId));
            return scholarshipFormsOfGivenCollegeId;
        }catch(RuntimeException e){
            return "No Data Found";
        }
    }

    // To get Approved or non approved scholarships of a particular college (will be used  to apply as Filter by college)
    @GetMapping("/college/{id}/scholarshipform/{isApproved}")
    public Object getApprovedScholarshipFormsOfCollege(@PathVariable(name ="id") String CollegeId, @PathVariable(name = "isApproved") String isApproved){
        try{
            List<ScholarshipForm> scholarshipFormsOfGivenCollegeId = scholarShipFormRepository.getScholarshipFormsOfCollege(Integer.parseInt(CollegeId));
            List<ScholarshipForm> approvedScholarshipForms = new ArrayList<>(), nonapproveScholarshipForms= new ArrayList<>();
            for (ScholarshipForm scholarshipForm : scholarshipFormsOfGivenCollegeId) {
                if(scholarshipForm.isScholared()==true){
                    approvedScholarshipForms.add(scholarshipForm);
                }
                else{
                    nonapproveScholarshipForms.add(scholarshipForm);
                }
            }
            if(isApproved.equalsIgnoreCase("true")){
                return approvedScholarshipForms;
            }
            else{
                return nonapproveScholarshipForms;
            }

        }catch(Exception e){
            return "No Data Found";
        }
    }
}
