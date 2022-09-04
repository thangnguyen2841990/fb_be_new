package com.example.demo.controller;

import com.example.demo.model.entity.Images;
import com.example.demo.service.image.IImageService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/images")
public class ImagesRestController {
    @Autowired
    private IImageService imageService;

    @GetMapping("/image/{imageId}")
    public ResponseEntity<Images> findImageById(@PathVariable Long imageId) {
        Optional<Images> imagesOptional = this.imageService.findById(imageId);
        if (!imagesOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(imagesOptional.get(), HttpStatus.OK);
    }
    @DeleteMapping("/deleteImage/image/{imageId}")
    public ResponseEntity<Images> deleteImage(@PathVariable Long imageId) {
        Optional<Images> imagesOptional = this.imageService.findById(imageId);
        if (!imagesOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.imageService.remove(imageId);
        return new ResponseEntity<>(imagesOptional.get(), HttpStatus.OK);
    }
}
