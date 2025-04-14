package com.example.KH.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskForm {

    private int id;
    private String content;
    private short status;
    private Date limitDate;
    private Date createdDate;
    private Date updatedDate;
}
