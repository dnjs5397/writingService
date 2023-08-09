package com.writingService.writingService.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);

    //페이징 기능
    Page<Question> findAll(Pageable pageable);

//    @Modifying
//    @Query("update Question q set q.view = q.view + 1 where q.id = :id")
//    void updateView(@Param("id") Integer id);

}
