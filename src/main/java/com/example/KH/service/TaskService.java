package com.example.KH.service;

import com.example.KH.controller.form.TaskForm;
import com.example.KH.repository.TaskRepository;
import com.example.KH.repository.entity.Task;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /*
     * レコード絞り込み取得処理
     */
    public List<TaskForm> findTaskByOrder(LocalDate start, LocalDate end, Short status, String content) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;

        if (start != null) {
            startDateTime = start.atStartOfDay();
        } else {
            startDateTime = LocalDate.of(2020,1,1).atStartOfDay();
        }

        if (end != null) {
            endDateTime = end.plusDays(1).atStartOfDay().minusSeconds(1);
        } else {
            endDateTime = LocalDate.of(2101,1,1).atStartOfDay().minusSeconds(1);;
        }

        if (status != null && !StringUtils.isBlank(content)) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndStatusAndContentOrderByLimitDateAsc(startDateTime, endDateTime, status, content);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else if (status != null) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndStatusOrderByLimitDateAsc(startDateTime, endDateTime, status);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else if (!StringUtils.isBlank(content)) {
            List<Task> results = taskRepository.findByLimitDateBetweenAndContentOrderByLimitDateAsc(startDateTime, endDateTime, content);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        } else {
            List<Task> results = taskRepository.findByLimitDateBetweenOrderByLimitDateAsc(startDateTime, endDateTime);
            List<TaskForm> tasks = setTaskForm(results);
            return tasks;
        }
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
            task.setLimitDate(result.getLimitDate().toLocalDate());
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

    /*
     * レコード追加
     */
    public void saveTask(TaskForm taskForm) {
        Task saveTask = setTaskEntity(taskForm);
        if(saveTask.getStatus() == 0){
            saveTask.setStatus((short)1);
        }
        taskRepository.save(saveTask);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Task setTaskEntity(TaskForm reqTask) {
        Task task = new Task();
        task.setLimitDate(reqTask.getLimitDate().atTime(0, 0, 0));
        task.setId(reqTask.getId());
        task.setContent(reqTask.getContent());
        task.setStatus(reqTask.getStatus());

        return task;
    }

    public TaskForm selectTask(Integer id) {
        List<Task> results = new ArrayList<>();
        results.add((Task)taskRepository.findById(id).orElse(null));
        List<TaskForm> tasks = setTaskForm(results);
        return tasks.get(0);
    }
}
