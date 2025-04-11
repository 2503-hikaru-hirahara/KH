package com.example.KH.repository;

import com.example.KH.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer> {
}
