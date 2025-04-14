package com.example.KH.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class TaskForm {

    private int id;
    private String content;
    private short status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date limitDate;
    private Date createdDate;
    private Date updatedDate;
}
