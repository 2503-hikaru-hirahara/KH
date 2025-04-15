package com.example.KH.repository;

import com.example.KH.repository.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.limitDate BETWEEN :start AND :end AND t.status = :status AND t.content = :content ORDER BY t.limitDate ASC")
    public List<Task> findByLimitDateBetweenAndStatusAndContentOrderByLimitDateAsc(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") Short status,
            @Param("content") String content
    );
    @Query("SELECT t FROM Task t WHERE t.limitDate BETWEEN :start AND :end AND t.status = :status ORDER BY t.limitDate ASC")
    public List<Task> findByLimitDateBetweenAndStatusOrderByLimitDateAsc(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") Short status
    );
    @Query("SELECT t FROM Task t WHERE t.limitDate BETWEEN :start AND :end AND t.content = :content ORDER BY t.limitDate ASC")
    public List<Task> findByLimitDateBetweenAndContentOrderByLimitDateAsc(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("content") String content
    );
    @Query("SELECT t FROM Task t WHERE t.limitDate BETWEEN :start AND :end ORDER BY t.limitDate ASC")
    public List<Task> findByLimitDateBetweenOrderByLimitDateAsc(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
    @Modifying
    @Query("UPDATE Task t SET t.status = :status, t.updatedDate = CURRENT_TIMESTAMP WHERE t.id = :id")
    void saveStatus(@Param("status")short status, @Param("id")int id);
}
