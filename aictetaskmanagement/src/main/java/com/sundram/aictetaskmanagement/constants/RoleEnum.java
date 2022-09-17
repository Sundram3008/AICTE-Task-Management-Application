package com.sundram.aictetaskmanagement.constants;


public enum RoleEnum {
    
    ADMIN("admin"),
    STUDENT("student"),
    EMPLOYEE("employee"),
    AFFLIATOR("affliator");

    private String role;

    RoleEnum(String role){
        this.role = role;
    }

    String getRole(){
        return this.role;
    } 

}
