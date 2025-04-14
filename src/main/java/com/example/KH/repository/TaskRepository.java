package com.example.KH.repository;

import com.example.KH.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer> {
    public List<Task> findAllByOrderByLimitDateAsc();
    @Modifying
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    void saveStatus(@Param("status")short status, @Param("id")int id);
}
