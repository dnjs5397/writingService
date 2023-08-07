package com.writingService.writingService.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface QuestionRepository extends JpaRepository<Question, Integer> {

//    @Modifying
//    @Query("update Question q set q.view = q.view + 1 where q.id = :id")
//    void updateView(@Param("id") Integer id);

}
