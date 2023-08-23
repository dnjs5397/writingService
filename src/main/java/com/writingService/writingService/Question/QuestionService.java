package com.writingService.writingService.Question;

import com.writingService.writingService.Answer.Answer;
import com.writingService.writingService.DataNotFoundException;
import com.writingService.writingService.User.UserInfo;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            Question question1 = question.get();
            question1.setView(question1.getView()+1);
            questionRepository.save(question1);
            return question.get();
        } else {
            throw new DataNotFoundException("question not found.");
        }
    }

    public void createQuestion(String subject, String content, UserInfo author) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(author);
        this.questionRepository.save(q);
    }

    public void modifyQuestion(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void deleteQuestion(Question question) {
        this.questionRepository.delete(question);
    }


    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(orders));
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public void vote(Question question, UserInfo userInfo) {
        question.getVoter().add(userInfo);
        this.questionRepository.save(question);
    }

//    private Specification<Question> search(String kw) {
//        return new Specification<Question>() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                query.distinct(true);
//                Join<Question, UserInfo> u1 = root.join("author", JoinType.LEFT);
//                Join<Question, Answer> a = root.join("answerList", JoinType.LEFT);
//                Join<Answer, UserInfo> u2 = root.join("author", JoinType.LEFT);
//                return criteriaBuilder.or(criteriaBuilder.like(root.get("subject"), "%" + kw + "%"), //제목
//                        criteriaBuilder.like(root.get("content"), "%" + kw + "%"),      // 내용
//                        criteriaBuilder.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
//                        criteriaBuilder.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
//                        criteriaBuilder.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
//            }
//        };
//    }
}

