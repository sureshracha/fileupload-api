package com.padma.spring.files.upload.db.controller;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import com.padma.spring.files.upload.db.message.ResponceFileForDupe;
import com.padma.spring.files.upload.db.message.ResponseMessage;
import com.padma.spring.files.upload.db.service.FileDupeCheck;
import com.padma.spring.files.upload.db.service.FileStorageService;
import com.padma.spring.files.upload.db.utils.GetFileCheckSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.padma.spring.files.upload.db.message.ResponseFile;
import com.padma.spring.files.upload.db.model.FileDB;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {

  @Autowired
  private FileStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("author") String authorName) {
    String message = "";
    try {

      GetFileCheckSum gfcs = new GetFileCheckSum();
      String fileHashCode = gfcs.checksum( new File(  file.getOriginalFilename()));
      List<ResponceFileForDupe> files = storageService.getAllFiles().map(dbFile -> new ResponceFileForDupe(
              dbFile.getName(),
              dbFile.getHashCode(),
              dbFile.getAuthorName())).collect(Collectors.toList());

      List< ResponceFileForDupe > dbList =  files.stream().filter(p-> p.getHasCode().equalsIgnoreCase(fileHashCode) && p.getAuthorName().equalsIgnoreCase(authorName))
              .collect(Collectors.toList());
      System.out.println(dbList.stream().toArray().toString());

      if(dbList.size()==0) {

        storageService.store(file, authorName,fileHashCode);
        message = "Uploaded the file  successfully : " + file.getOriginalFilename() + " -  Author : " + authorName ;
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
     }
     message = "Uploaded the file : " + file.getOriginalFilename() + " -  Author : " + authorName + " duplicated ";
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      
    } catch (Exception e) {
      message =    "Could not upload the file: " + file.getOriginalFilename() + " -  Author " + authorName + "!";

      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length,
          dbFile.getHashCode(),
              dbFile.getAuthorName());
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
}
