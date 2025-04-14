package com.example.KH.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String content;

    @Column
    private short status;

    @Column
    private Date limitDate;

    @Column(name = "updated_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "created_date", insertable = false)
    private Date updatedDate;
}
