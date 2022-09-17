
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
@Entity(name = "Affiliation")
public class AffiliationForm {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int applicationFormId;

    private String collegeName;

    private String collegeAddress;

    private String director;

    private Date dateOfRegistration;

    private String relatedDocsLinks; //path of file

    private Boolean isAffiliated= false;

    private Boolean isPending = true;
    
    private Boolean isRejected = false;
    
    private String collegeEmailId;

    private String collegeContactNumber;

    private String collegeCity;

    private String collegeState;

    private int collegeId; //value have to be same as collegeId of college.java file
}
