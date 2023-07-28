package com.accenture.demobookify.service;

import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.exception.IOImageAuthorException;
import com.accenture.demobookify.model.FileData;
import org.springframework.web.multipart.MultipartFile;

public interface StorageFileDataService {
    //String FOLDER_PATH = "C:\\Users\\j.a.juarez\\Desktop\\Bookify\\src\\main\\resources\\AuthorImages\\";
    //String FOLDER_PATH = "/Users/jljm/Documents/springProjects/demo-bookifysrc/main/resources/AuthorImages/";
    FileData uploadImageToFileSystem(MultipartFile file) throws IOImageAuthorException;
    byte[] downloadImageFromFileSystem(Long id) throws IOImageAuthorException, DataNotFoundException;

}
