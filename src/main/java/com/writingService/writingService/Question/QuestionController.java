package com.writingService.writingService.Question;

import com.writingService.writingService.Answer.AnswerForm;
import com.writingService.writingService.User.UserInfo;
import com.writingService.writingService.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String question_list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String question_detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String question_create(QuestionForm questionForm) {
        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String question_create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        else {
            UserInfo userInfo = this.userService.getUser(principal.getName());
            this.questionService.createQuestion(questionForm.getSubject(), questionForm.getContent(), userInfo);
            return "redirect:/question/list";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String question_modify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String question_modify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                  @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        this.questionService.modifyQuestion(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String question_delete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        this.questionService.deleteQuestion(question);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String question_vote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        UserInfo userInfo = this.userService.getUser(principal.getName());
        this.questionService.vote(question, userInfo);
        return String.format("redirect:/question/detail/%s", id);

    }
}
