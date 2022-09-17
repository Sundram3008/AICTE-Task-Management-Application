package com.sundram.aictetaskmanagement.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.sundram.aictetaskmanagement.model.ScholarshipForm;
import com.sundram.aictetaskmanagement.repository.ScholarShipFormRepository;
import com.sundram.aictetaskmanagement.repository.ScholarShipRepository;

@Configuration
@EnableScheduling
public class OldApplicationsController {

    @Autowired
    ScholarShipFormRepository scholarShipFormRepository;

    @Autowired
    ScholarShipRepository scholarShipRepository;
    @Scheduled(cron="0 15/15 * ? * *")
    public void scheduleFixedDelayTask() {
        List<ScholarshipForm> getAllScholarships= scholarShipFormRepository.getAllScholarshipForms();
        for(int i=0; i<getAllScholarships.size(); i++){
            int schId=getAllScholarships.get(i).getScholarshipId();
            Date date=scholarShipRepository.getScholarShipByScholarShipId(schId).getLastDate();
            if(date.after(new Date())) getAllScholarships.remove(i);

        }
    }
}
