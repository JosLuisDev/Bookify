package com.accenture.demobookify.service;

import com.accenture.demobookify.dto.DatosAuthor;
import com.accenture.demobookify.exception.AuthorDataAlreadyExistException;
import com.accenture.demobookify.exception.UrlNotAccesibleException;
import com.accenture.demobookify.model.Author;
import com.accenture.demobookify.model.FileData;
import com.accenture.demobookify.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository=authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    @Override
    public Long save(DatosAuthor datosAuthor) throws AuthorDataAlreadyExistException, UrlNotAccesibleException{
        boolean exist = validateAuthorData(datosAuthor);
        if (exist){
            throw new AuthorDataAlreadyExistException("The name or email of the Author already exist");
        }
        //Si en la peticion se manda la url, validamos que se accesible.
        if(!datosAuthor.url().isEmpty()){
            checkUrlAccessibility(datosAuthor.url());
        }

        Author author = new Author(datosAuthor);
        Author authorRes = authorRepository.save(author);
        return authorRes.getId();
    }

    @Override
    public Long update(Long id, DatosAuthor datosAuthor) {
        Author authorBD = getById(id);
        authorBD.setFirstname(datosAuthor.firstname());
        authorBD.setLastname(datosAuthor.lastname());
        authorBD.setEmail(datosAuthor.email());
        authorBD.setDateOfBirth(datosAuthor.dateOfBirth());
        authorBD.setNationality(datosAuthor.nationality());
        authorBD.setBiography(datosAuthor.biography());
        authorBD.setUrl(datosAuthor.url());
        authorBD.setFileData(datosAuthor.fileData());
        return authorBD.getId();
    }

    @Override
    public void delete(Long id) {
        Author authorDB = getById(id);
        authorDB.setActive(false);
    }
    @Override
    public Author updateImageAuthor(FileData fileData, Long idAuthor){
        Author authorDb = authorRepository.getReferenceById(idAuthor);
        authorDb.setFileData(fileData);
        return authorDb;
    }

    @Override
    public void physicalDelete(Long id) {
        authorRepository.deleteById(id);
    }

    private boolean validateAuthorData(DatosAuthor datosAuthor){
        Optional<Author> authorFullName = authorRepository.findByFirstnameAndLastnameIgnoreCase(datosAuthor.firstname(), datosAuthor.lastname());
        Optional<Author> authorEmail = authorRepository.findByEmailIgnoreCase(datosAuthor.email());

        return (authorFullName.isPresent() || authorEmail.isPresent());
    }

    private void checkUrlAccessibility(String url) throws UrlNotAccesibleException {
        //Creamos un cliente que enviara la peticion
        HttpClient client = HttpClient.newHttpClient();
        //Creamos la peticion
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try{
            //Se envia la peticion. HttpResponse.BodyHandlers.discarding() -> descarta el body del response sin procesarlo.
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            int statusCode = response.statusCode();
            System.out.println("statusCode = " + statusCode);
            if(statusCode < 200 || statusCode >= 300){ //Cualquier codigo diferente de los 200 nos dira que no es accesible
                throw new UrlNotAccesibleException("The URL of the author is not accesible " + url);
            }
        }catch(Exception e){//Si entra al catch es que la URL no es accesible
            throw new UrlNotAccesibleException("The URL of the author is not accesible " + url);
        }
    }


}
