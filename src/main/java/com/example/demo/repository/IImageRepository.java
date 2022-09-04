package com.example.demo.repository;

import com.example.demo.model.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Images, Long> {
    @Query(value = "select * from images where post_id = ?1", nativeQuery = true)
    List<Images> findAllImagesOfPost(Long postId);
}
