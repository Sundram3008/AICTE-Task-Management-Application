package com.sundram.aictetaskmanagement.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity(name = "ScholarshipForms")
public class ScholarshipForm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int applicationId;

    private String studentName;

    private String govtId;

    private double seniorSecondaryPercentage;

    private double secondaryPercentage;
    
    private int collegeId; // have to be same as collegeId present in college.java file
    
    private int scholarshipId; //Get from dropdown Menu

    private String collegeName;

    private String studentAddress;

    private Date dateOfRegistration;

    private String multipartFile; //this will contain path of file

    private String studentImage; //this will contain path of file

    private boolean isScholared = false;

    private boolean isPending = true; // to check application is in pending

    private boolean isRejected = false;

    private String emailId;

    private String studentContactNumber;

    private String studentCity;

    private String studentState;

    private int studentId;
}
