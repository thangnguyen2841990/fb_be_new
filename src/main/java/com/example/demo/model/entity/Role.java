package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator="seq_role")
//    @SequenceGenerator(name="seq_role", sequenceName="SEQ_ROLE", allocationSize=1)
    private Long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
