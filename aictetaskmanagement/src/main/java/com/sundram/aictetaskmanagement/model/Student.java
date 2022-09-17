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
public class Student {
    
    //this will be populated with student's userID
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private int studentId;

    private int userId;
    private String userName;

    private String studentCollegeId;

    private String branch;

    private String scholarShipsOpted;

}
