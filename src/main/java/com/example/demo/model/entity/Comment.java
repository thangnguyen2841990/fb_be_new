package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name="seq_comment", sequenceName="SEQ_COMMENT", allocationSize=1)
    private Long id;

    private String content;

    private Date dateCreated;

    @ManyToOne
    private User formUser;

    @ManyToOne
    private Post post;
}
