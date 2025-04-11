package com.example.KH.service;

import com.example.KH.controller.form.TaskForm;
import com.example.KH.repository.TaskRepository;
import com.example.KH.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public void saveStatus(int id, short status) {
        taskRepository.saveStatus(status, id);
    }
}
