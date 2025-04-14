package com.example.KH.service;

import com.example.KH.controller.form.TaskForm;
import com.example.KH.repository.TaskRepository;
import com.example.KH.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /*
     * レコード全件取得処理
     */
    public List<TaskForm> findAllTask() {
        List<Task> results = taskRepository.findAllByOrderByLimitDateAsc();
        List<TaskForm> tasks = setTaskForm(results);
        return tasks;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<TaskForm> setTaskForm(List<Task> results) {
        List<TaskForm> tasks = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            TaskForm task = new TaskForm();
            Task result = results.get(i);
            task.setId(result.getId());
            task.setContent(result.getContent());
            task.setStatus(result.getStatus());
            task.setLimitDate(result.getLimitDate());
            tasks.add(task);
        }
        return tasks;
    }

    @Transactional
    public void saveStatus(Integer id, short status) {
        taskRepository.saveStatus(status, id);
    }

    /*
     * レコード削除
     */
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
