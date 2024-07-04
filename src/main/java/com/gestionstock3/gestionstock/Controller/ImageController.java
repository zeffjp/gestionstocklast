package com.gestionstock3.gestionstock.Controller;

import com.gestionstock3.gestionstock.Modele.Image;
import com.gestionstock3.gestionstock.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {
    @Autowired
    ImageService imageService ;


    @PostMapping("/create")
    public Image uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }
    @GetMapping("/read/{id}")
    public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
        return imageService.getImageDetails(id) ;
    }
    @GetMapping("/load/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException
    {
        return imageService.getImage(id);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable("id") Long id){
        imageService.deleteImage(id);
    }
    @PutMapping("/update/{id}")
    public Image UpdateImage(@RequestParam("image")MultipartFile file) throws IOException {
        return imageService.uplaodImage(file);
    }
}
