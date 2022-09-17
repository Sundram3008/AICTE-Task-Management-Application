package com.sundram.aictetaskmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class College {
    
    private String collegeName;

    private String applierName;

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private int collegeId;

    private String contactNumber;

    private String address;

    private String capacity;

    private String branchesAvailable;
}

