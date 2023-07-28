package com.accenture.demobookify.service;

import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.exception.IOImageAuthorException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.FileData;
import com.accenture.demobookify.repository.FileDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
@Service
@Slf4j
public class StorageFileDataServiceImpl implements StorageFileDataService{

    @Autowired
    FileDataRepository fileDataRepository;

    @Autowired
    AuthorService authorService;

    private String FOLDER_PATH;
    private Path targetPath;

    {
        File file = new File("");
        FOLDER_PATH = file.getAbsolutePath() + "/main/resources/AuthorImages/";
        targetPath = Paths.get(FOLDER_PATH);
        log.info(String.valueOf(targetPath.toAbsolutePath()));
        try {
            Files.createDirectories(targetPath); // crea el directorio si no existe
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
            //Guardar el archivo en la ruta del especificada
            if (Files.isWritable(targetPath)) { // comprueba si se puede escribir
                file.transferTo(new File(filePath)); // guarda el archivo
            } else {
                log.error("No se puede escribir en el directorio");
            }
        }catch (IOException e){
            throw new IOImageAuthorException(e.getMessage());
        }

        return fileData;
    }

    @Override
    public byte[] downloadImageFromFileSystem(Long id) throws IOImageAuthorException, DataNotFoundException {

        try{
            Author authordb = authorService.getById(id);
            if(authordb.getFileData() != null){
                Optional<FileData> fileData = fileDataRepository.findById(authordb.getFileData().getId());
                String filePath = fileData.get().getFilePath();
                byte[] imageBytes;
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
