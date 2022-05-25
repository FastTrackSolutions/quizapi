package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;
  private final QuestionMapper questionMapper;

  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    return quizMapper.entitiesToDtos(quizRepository.findAll());
  }

  @Override
  public ResponseEntity<QuizResponseDto> createQuiz(QuizRequestDto quizRequestDto) {
    Quiz incomingQuiz = quizMapper.requestDtoToEntity(quizRequestDto);

    for (Question question : incomingQuiz.getQuestions()) {
      question.setQuiz(incomingQuiz);

      for (Answer answer: question.getAnswers()) {
        answer.setQuestion(question);
      }
    }
    Quiz savedQuiz = quizRepository.saveAndFlush(incomingQuiz);
    return new ResponseEntity<>(quizMapper.entityToDto(savedQuiz), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<QuizResponseDto> deleteQuiz(Long id) {
    Optional<Quiz> quizToDelete = quizRepository.findById(id);

    if (quizToDelete.isPresent()) {
      Quiz quizWeAreDeleting = quizToDelete.get();
      quizRepository.delete(quizWeAreDeleting);
      return new ResponseEntity<>(quizMapper.entityToDto(quizWeAreDeleting), HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("There is no existing quiz with this id");
    }
  }

  @Override
  public ResponseEntity<QuestionResponseDto> getRandomQuestion(Long id) {
    Optional<Quiz> quiz = quizRepository.findById(id);

    if (quiz.isPresent()) {
      Quiz chosenQuiz = quiz.get();
      List<Question> questions = chosenQuiz.getQuestions();

      Random randomQuestionShuffle = new Random();
      int randomIndexInList = randomQuestionShuffle.nextInt(questions.size());
      Question randomQuestionChosen = questions.get(randomIndexInList);
      return new ResponseEntity<>(questionMapper.entityToDto(randomQuestionChosen), HttpStatus.OK);
    } else {
      throw new IllegalArgumentException("Quiz question did not work, try again later");
    }
  }

  @Override
  public ResponseEntity<QuestionResponseDto> renameQuiz(Long id, String newName) {
    // make an optional type
    // get the quiz from the db
    // set the quiz name
//    chosenQuiz.setName(newName);
    // save and flush quiz
    // return the new named quiz


    return null;
  }


}
