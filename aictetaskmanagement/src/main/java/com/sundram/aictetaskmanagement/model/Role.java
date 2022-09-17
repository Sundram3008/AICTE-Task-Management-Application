package com.sundram.aictetaskmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
@Entity(name = "Roles")
public class Role {
    
    private String name;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int roleId;

}
