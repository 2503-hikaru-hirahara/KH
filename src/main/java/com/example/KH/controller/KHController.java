package com.example.KH.controller;

import com.example.KH.controller.form.TaskForm;
import com.example.KH.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        // タスクを全件取得
        List<TaskForm> taskData = taskService.findAllTask();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // タスクデータオブジェクトを保管
        mav.addObject("tasks", taskData);
        return mav;
    }

    /*
     * ステータス変更処理
     */
    @PutMapping("/update-Status/{id}")
    public ModelAndView updateStatus(@PathVariable Integer id,
                                       @ModelAttribute("status") short status){
        taskService.saveStatus(id, status);
        return new ModelAndView("redirect:/");
    }
}
