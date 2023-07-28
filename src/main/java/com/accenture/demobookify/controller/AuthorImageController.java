package com.accenture.demobookify.controller;

import com.accenture.demobookify.dto.AuthorResponse;
import com.accenture.demobookify.dto.DatosAuthor;
import com.accenture.demobookify.exception.DataNotFoundException;
import com.accenture.demobookify.exception.IOImageAuthorException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.FileData;
import com.accenture.demobookify.service.AuthorService;
import com.accenture.demobookify.service.StorageFileDataService;
import com.accenture.demobookify.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class AuthorImageController {

    private StorageFileDataService storageFileDataService;
    private AuthorService authorService;

    @Autowired
    public AuthorImageController(StorageFileDataService storageFileDataService, AuthorService authorService){
        this.storageFileDataService = storageFileDataService;
        this.authorService = authorService;
    }

    @PostMapping("/uploadAuthorImage/{id}")
    @Transactional //Para que haga commit o rollback a la modificacion de Author
    public ResponseEntity<?> uploadFileToFileSystem(@PathVariable Long id, @RequestParam("image") MultipartFile file){
        try{
            BufferedImage image = ImageIO.read(file.getInputStream());
            int width = 1980;
            int height = 1000;

            if(image.getWidth() > width || image.getHeight() > height){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The image dimensions must be smaller than 800 x 600");
            }
            FileData fileDb = storageFileDataService.uploadImageToFileSystem(file);
            Author author = authorService.updateImageAuthor(fileDb,id);
            //return ResponseEntity.ok("The image was load correctly.\nAuthor Data: \n" +  author.getId() +
            //        "\n" + author.getFirstname() + " " + author.getLastname());
                return ResponseEntity.status(HttpStatus.CREATED).body(new AuthorResponse(author.getId(), author.getFirstname(),
                        author.getLastname(), author.getFileData()));
        }catch (IOImageAuthorException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), e.getStackTrace()));
        }
    }

    @GetMapping("/authorImage/{id}")
    public ResponseEntity<?> getAuthorImage(@PathVariable Long id) throws DataNotFoundException {

        try{
            byte[] imageData = storageFileDataService.downloadImageFromFileSystem(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        }catch (IOImageAuthorException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
