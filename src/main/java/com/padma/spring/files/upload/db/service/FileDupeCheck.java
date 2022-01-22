package com.padma.spring.files.upload.db.service;

import com.padma.spring.files.upload.db.message.ResponceFileForDupe;
import com.padma.spring.files.upload.db.message.ResponseFile;
import com.padma.spring.files.upload.db.model.FileDB;
import org.springframework.beans.factory.annotation.Autowired;
import com.padma.spring.files.upload.db.service.FileStorageService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDupeCheck {


     FileStorageService storageService = new FileStorageService();
    public boolean dupCheck(String fileHashCode,String authorName) {

         List< ResponceFileForDupe > files = storageService.getAllFiles().map(dbFile -> new ResponceFileForDupe(
                 dbFile.getName(),
                 dbFile.getHashCode(),
                 dbFile.getAuthorName())).collect(Collectors.toList());

        List< ResponceFileForDupe > dbList =  files.stream().filter(p-> p.getHasCode().equalsIgnoreCase(fileHashCode) && p.getAuthorName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
        System.out.println(dbList.stream().toArray().toString());
        return dbList.size()==0 ? true: false;



    }
}
