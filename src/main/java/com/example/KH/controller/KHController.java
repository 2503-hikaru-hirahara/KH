package com.example.KH.controller;

import com.example.KH.controller.form.TaskForm;
import com.example.KH.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class KHController {
    @Autowired
    TaskService taskService;

    /*
     * タスク内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        // 現在日時を取得
        LocalDateTime now = LocalDateTime.now();
        // タスクを全件取得
        List<TaskForm> taskData = taskService.findAllTask();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 現在日時データオブジェクトを保管
        mav.addObject("now", now);
        // タスクデータオブジェクトを保管
        mav.addObject("tasks", taskData);
        return mav;
    }

    /*
     * ステータス変更処理
     */
    @PutMapping("/update-Status/{id}")
    public ModelAndView updateStatus(@PathVariable int id,
                                       @ModelAttribute("status") short status){
        taskService.saveStatus(id, status);
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteTask(@PathVariable Integer id) {
        // テーブルから投稿を削除
        taskService.deleteTask(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}
