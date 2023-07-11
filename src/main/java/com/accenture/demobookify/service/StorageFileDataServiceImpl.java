package com.accenture.demobookify.service;

import com.accenture.demobookify.exception.IOImageAuthorException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.FileData;
import com.accenture.demobookify.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
@Service
public class StorageFileDataServiceImpl implements StorageFileDataService{

    @Autowired
    FileDataRepository fileDataRepository;

    @Autowired
    AuthorService authorService;

    @Override
    public FileData uploadImageToFileSystem(MultipartFile file) throws IOImageAuthorException {
        String filePath = FOLDER_PATH+file.getOriginalFilename();
        FileData fileData;

        try{
            //Construimos el objeto de la clase FileData (info de la imagen) y con ayuda del repo lo guardamos en BD
            fileData = fileDataRepository.save(FileData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath)
                    .build());
            //Guardar en la ruta del filesystem
            file.transferTo(new File(filePath));
        }catch (IOException e){
            throw new IOImageAuthorException("There was a problem when the image was being uploaded.");
        }

        return fileData;
    }

    @Override
    public byte[] downloadImageFromFileSystem(Long id) throws IOImageAuthorException {

        try{
            Author authordb = authorService.getById(id);
            if(authordb.getFileData() != null){
                Optional<FileData> fileData = fileDataRepository.findById(authordb.getFileData().getId());
                String filePath;
                byte[] imageBytes;
                filePath = fileData.get().getFilePath();
                imageBytes = Files.readAllBytes(new File(filePath).toPath());
                return imageBytes;
            }else{
                throw new IOImageAuthorException("There is no image upload for Author: " + authordb.getFirstname()
                        + " " + authordb.getLastname() + " or the image was removed from file system");
            }

        }catch (IOException e) {
            throw new IOImageAuthorException("An error occurred when the image was being downloaded.");
        }
    }
}
