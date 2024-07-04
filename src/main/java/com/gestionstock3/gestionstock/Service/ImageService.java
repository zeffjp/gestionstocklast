package com.gestionstock3.gestionstock.Service;

import java.io.IOException;

import com.gestionstock3.gestionstock.Modele.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
public interface ImageService {
    Image uplaodImage(MultipartFile file) throws IOException;
    Image getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id) ;
}
