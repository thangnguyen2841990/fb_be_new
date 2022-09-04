package com.example.demo.service.image;

import com.example.demo.model.entity.Images;
import com.example.demo.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService{
    @Autowired
    private IImageRepository imageRepository;

    @Override
    public List<Images> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Images> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Images save(Images image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Images> findAllPostOfPost(Long postId) {
        return this.imageRepository.findAllImagesOfPost(postId);
    }
}
