package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator="seq_post")
//    @SequenceGenerator(name="seq_post", sequenceName="SEQ_POST", allocationSize=1)
    private Long id;

    private String content;

    private Date dateCreated;

    @ManyToOne
    private StatusPost statusPost;

    @ManyToOne
    private User user;

    public Post(String content, Date dateCreated, StatusPost statusPost, User user) {
        this.content = content;
        this.dateCreated = dateCreated;

        this.statusPost = statusPost;
        this.user = user;
    }
}
