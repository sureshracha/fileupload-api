package com.padma.spring.files.upload.db.service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.padma.spring.files.upload.db.model.FileDB;
import com.padma.spring.files.upload.db.repository.FileDBRepository;
import com.padma.spring.files.upload.db.utils.GetFileCheckSum;

@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;

  public FileDB store(MultipartFile file,String authorName) throws IOException, NoSuchAlgorithmException {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    GetFileCheckSum gfcs = new GetFileCheckSum();

    String fileHashCode = gfcs.checksum( new File(  file.getOriginalFilename()));

    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes(),fileHashCode,authorName);

    return fileDBRepository.save(FileDB);
  }

  public FileDB getFile(String id) {
    return fileDBRepository.findById(id).get();
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
}
