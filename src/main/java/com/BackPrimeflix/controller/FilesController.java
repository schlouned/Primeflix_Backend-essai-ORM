package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.response.UploadResponseMessage;
import com.BackPrimeflix.util.service.CSVLoader;
import com.BackPrimeflix.util.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/files")
public class FilesController {

    private final FileService fileService;

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadMovieFile")
    public ResponseEntity<UploadResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.save(file);

        //save the file in DB
        //**********************
        Connection c = null;
        try {
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","root");
            props.setProperty("stringtype", "unspecified");

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/primeflix",
                            props);

            System.out.println("Opened database successfully");

            //next code
            //------------
            CSVLoader loader = new CSVLoader(c);
            loader.loadCSV("C:/Users/Schloune Denis/Documents/primeflix_movies/movies.csv", "movie_entity", false);
            //------------
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        //**********************

        return ResponseEntity.status(HttpStatus.OK)
                             .body(new UploadResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
    }
}
