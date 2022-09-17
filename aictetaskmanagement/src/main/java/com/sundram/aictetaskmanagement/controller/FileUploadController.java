package com.sundram.aictetaskmanagement.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sundram.aictetaskmanagement.model.AffiliationForm;
import com.sundram.aictetaskmanagement.model.ScholarshipForm;
import com.sundram.aictetaskmanagement.repository.AffiliationFormRepository;
import com.sundram.aictetaskmanagement.repository.ScholarShipFormRepository;
import com.sundram.aictetaskmanagement.upload.FileUploadResponse;
import com.sundram.aictetaskmanagement.upload.FileUploadUtil;
 
@RestController
public class FileUploadController {
     
    @Autowired 
    ScholarShipFormRepository scholarShipFormRepository;

    @Autowired
    AffiliationFormRepository affiliationFormRepository;
    /* Requirements are govtid of user and scholarhip id of that particular scholarship which he/she is applying for */
    @PatchMapping("{govId}/{schId}/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable(name ="govId") String govId, @PathVariable(name = "schId") String schId) throws IOException {
        
        List<ScholarshipForm> scholarshipForm = scholarShipFormRepository.getScholarshipFormByGovtId(govId, Integer.parseInt(schId));
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
        scholarshipForm.get(0).setMultipartFile(response.getDownloadUri());
        scholarShipFormRepository.save(scholarshipForm.get(0));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("{collegeId}/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFileForCollege(@RequestParam("file") MultipartFile multipartFile, @PathVariable(name ="collegeId") String collegeId) throws IOException {
        
        List<AffiliationForm> affiliationForm = affiliationFormRepository.getAffiliationFormByCollegeId(Integer.parseInt(collegeId));
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
        affiliationForm.get(0).setRelatedDocsLinks(response.getDownloadUri());
        affiliationFormRepository.save(affiliationForm.get(0));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}