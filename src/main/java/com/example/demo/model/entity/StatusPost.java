package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "status")
public class StatusPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator="seq_status")
//    @SequenceGenerator(name="seq_status", sequenceName="SEQ_STATUS", allocationSize=1)
    private Long id;

    private String name;

    public StatusPost(String name) {
        this.name = name;
    }
}
