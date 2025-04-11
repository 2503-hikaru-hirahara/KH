package com.example.KH.controller;

import com.example.KH.controller.form.TaskForm;
import com.example.KH.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

public class KHController {
    @Autowired
    TaskService taskService;

    /*
     * ステータス変更処理
     */
    @PutMapping("/update-Status/{id}")
    public ModelAttribute updateStatus(@PathVariable Integer id,
                                       @ModelAttribute("status") short status){
        taskService.saveStatus(id, status);
        return new ModelAndView("redirect:/");
    }
}
