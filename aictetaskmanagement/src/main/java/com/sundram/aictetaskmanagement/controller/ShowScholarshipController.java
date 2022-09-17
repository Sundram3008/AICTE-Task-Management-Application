package com.sundram.aictetaskmanagement.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.repository.ScholarShipFormRepository;

@RestController
public class ShowScholarshipController {
    
    Logger logger = Logger.getLogger("ShowScholarshipController logger");

    @Autowired
    ScholarShipFormRepository scholarShipFormRepository;

    @GetMapping("/showScholarshipApplications/{collegeName}")
    public Object showScholarshipForms(@PathVariable(name = "collegeName") String collegeName){
        try{
            return scholarShipFormRepository.getScholarshipFormByCollegeName(collegeName);

        }catch(RuntimeException e){
            return "No Such data found";
        }

    }
}
