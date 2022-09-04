package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = "seq_image")
//    @SequenceGenerator(name = "seq_image", sequenceName = "SEQ_IMAGE", allocationSize = 1)
    private Long id;

    private String image;

    @ManyToOne
    private Post post;

    public Images(String image, Post post) {
        this.image = image;
        this.post = post;
    }
}
