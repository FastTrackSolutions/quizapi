package com.cooksys.quiz_api.controllers;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.services.QuizService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }
  
  // TODO: Implement the remaining 6 endpoints from the documentation.

  @PostMapping
  public ResponseEntity<QuizResponseDto> createQuiz(@RequestBody QuizRequestDto quizRequestDto) {
    return quizService.createQuiz(quizRequestDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<QuizResponseDto> deleteQuiz(@PathVariable Long id) {
    return quizService.deleteQuiz(id);
  }

  @GetMapping("/{id}/random")
  public ResponseEntity<QuestionResponseDto> getRandomQuestion(@PathVariable Long id) {
    return quizService.getRandomQuestion(id);
  }

  @PatchMapping("/{id}/rename/{newName}")
  public ResponseEntity<QuestionResponseDto> renameQuiz(@PathVariable Long id, @PathVariable String newName) {
    return quizService.renameQuiz(id, newName);
  }


}
