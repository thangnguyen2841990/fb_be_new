package com.example.demo.service.image;

import com.example.demo.model.entity.Images;

import java.util.List;
import java.util.Optional;

public interface IImageService  {
    List<Images> findAll();

    Optional<Images> findById(Long id);

    void remove(Long id);

    Images save(Images image);

    List<Images> findAllPostOfPost(Long postId);
}
