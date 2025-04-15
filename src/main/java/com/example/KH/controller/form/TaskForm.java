package com.example.KH.controller.form;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskForm {

    private int id;
    @NotBlank(message = "タスクを入力してください")
    @Length(max= 140, message = "タスクは140文字以内で入力してください")
    private String content;
    private short status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "期限を設定してください")
    @FutureOrPresent(message = "無効な日付です")
    private LocalDate limitDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
