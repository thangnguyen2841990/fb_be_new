package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "like_posts")
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator="seq_like_post")
//    @SequenceGenerator(name="seq_like_post", sequenceName="SEQ_LIKE_POST", allocationSize=1)
    private Long id;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private Post post;

    public LikePost(User fromUser, Post post) {
        this.fromUser = fromUser;
        this.post = post;
    }
}
