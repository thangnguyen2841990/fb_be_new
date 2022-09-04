package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "like_comments")
public class LikeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator="seq_like_comments")
//    @SequenceGenerator(name="seq_like_comments", sequenceName="SEQ_LIKE_COMMENTS", allocationSize=1)
    private Long id;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Comment comment;
}
