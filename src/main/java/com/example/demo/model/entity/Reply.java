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
@Table(name = "replies")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator="seq_reply")
//    @SequenceGenerator(name="seq_reply", sequenceName="SEQ_REPLY", allocationSize=1)
    private Long id;

    private String content;

    private Date dateCreated;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private Post post;
}
