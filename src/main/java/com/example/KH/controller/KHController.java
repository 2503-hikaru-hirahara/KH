package com.example.KH.controller;

import com.example.KH.controller.form.TaskForm;
import com.example.KH.service.TaskService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KHController {
    @Autowired
    TaskService taskService;

    /*
     * タスク内容表示処理
     */
    @GetMapping
    public ModelAndView top(@RequestParam (name = "start", required = false) LocalDate start,
                            @RequestParam (name = "end", required = false) LocalDate end,
                            @RequestParam (name = "status", required = false) Short status,
                            @RequestParam (name = "content", required = false) String content) {
        ModelAndView mav = new ModelAndView();
        // 現在日時を取得
        LocalDate now = LocalDate.now();
        // タスクを絞り込み取得
        List<TaskForm> taskData = taskService.findTaskByOrder(start, end, status, content);
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
    public ModelAndView updateStatus(@PathVariable Integer id,
                                       @ModelAttribute("status") short status) {
        taskService.saveStatus(id, status);
        return new ModelAndView("redirect:/");
    }

    /*
     * タスク削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteTask(@PathVariable Integer id) {
        // テーブルから投稿を削除
        taskService.deleteTask(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * タスク追加画面表示処理
     */
    @GetMapping("/new")
    public ModelAndView newTask(){
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        TaskForm taskForm = new TaskForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("taskForm", taskForm);
        return mav;
    }

    /*
     * タスク追加処理
     */
    @PostMapping("/add")
    public ModelAndView addTask(@ModelAttribute("taskForm")@Validated TaskForm taskForm,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        //バリデーション処理
        List<String> errorMessages = new ArrayList<>();
        if(result.hasErrors()){
            for(FieldError error : result.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }

        }
        if(taskForm.getContent().equals("　")){
            errorMessages.add("タスクを入力してください");
        }
        if(!CollectionUtils.isEmpty(errorMessages)){
            redirectAttributes.addFlashAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/new");
        }

        // 投稿をテーブルに格納
        taskService.saveTask(taskForm);

        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * タスク編集画面表示処理
     */
    @GetMapping({"/edit/", "/edit/{id}"})
    public ModelAndView editTask(@PathVariable(required = false) String id,
                                 RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView();
        TaskForm task = null;

        if (!StringUtils.isBlank(id) && id.matches("^[0-9]*$")) {
            int intId = Integer.parseInt(id);

            task = taskService.selectTask(intId);
        }

        if (task == null) {
            redirectAttributes.addFlashAttribute("errorMessages","不正なパラメータです");
            return new ModelAndView("redirect:/");
        }

        mav.addObject("taskForm", task);
        mav.setViewName("/edit");
        return mav;
    }

    /*
     * タスク編集処理
     */
    @PutMapping("/update/{id}")
    public ModelAndView updateTask(@PathVariable Integer id,
                                   @ModelAttribute("taskForm") @Validated TaskForm taskForm,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        //バリデーション処理
        List<String> errorMessages = new ArrayList<>();
        if(result.hasErrors()){
            for(FieldError error : result.getFieldErrors()){
                errorMessages.add(error.getDefaultMessage());
            }

        }
        if(taskForm.getContent().equals("　")){
            errorMessages.add("タスクを入力してください");
        }
        if(!CollectionUtils.isEmpty(errorMessages)){
            redirectAttributes.addFlashAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/edit/{id}");
        }

        taskForm.setId(id);
        taskService.saveTask(taskForm);
        return new ModelAndView("redirect:/");
    }
}
