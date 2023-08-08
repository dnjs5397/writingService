package com.writingService.writingService;

import com.writingService.writingService.Question.Question;
import com.writingService.writingService.Question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class WritingServiceApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testJPA() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		Question q3 = new Question();
		q3.setSubject("게시글 작성법.");
		q3.setContent("알아냈습니다.");
		q3.setCreateDate(LocalDateTime.now());
		questionRepository.save(q3);  // 두번째 질문 저장

	}

}
