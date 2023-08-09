package com.writingService.writingService;

import com.writingService.writingService.Question.Question;
import com.writingService.writingService.Question.QuestionRepository;
import com.writingService.writingService.Question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class WritingServiceApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void testJPA() {

	}

}
