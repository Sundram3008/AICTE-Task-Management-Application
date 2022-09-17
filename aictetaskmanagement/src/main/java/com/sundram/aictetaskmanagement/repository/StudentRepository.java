package com.sundram.aictetaskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sundram.aictetaskmanagement.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    

   Student getStudentBystudentId(int id);

   String getScholarShipsOptedByStudentId(int id);

}
