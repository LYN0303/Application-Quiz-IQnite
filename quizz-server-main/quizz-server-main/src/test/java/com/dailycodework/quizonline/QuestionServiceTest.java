package com.dailycodework.quizonline.service;

import com.dailycodework.quizonline.model.Question;
import com.dailycodework.quizonline.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    private QuestionRepository questionRepository;
    private QuestionService questionService;

    private Question sampleQuestion;

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        questionService = new QuestionService(questionRepository);

        sampleQuestion = new Question();
        sampleQuestion.setId(1L);
        sampleQuestion.setSubject("Java");
        sampleQuestion.setQuestion("What is polymorphism?");
        sampleQuestion.setChoices(List.of("Inheritance", "Abstraction", "Encapsulation"));
        sampleQuestion.setCorrectAnswers(List.of("Inheritance", "Abstraction"));
    }

    @Test
    void shouldCreateQuestion() {
        when(questionRepository.save(sampleQuestion)).thenReturn(sampleQuestion);

        Question result = questionService.createQuestion(sampleQuestion);
        assertEquals("Java", result.getSubject());
        verify(questionRepository, times(1)).save(sampleQuestion);
    }

    @Test
    void shouldReturnAllQuestions() {
        when(questionRepository.findAll()).thenReturn(List.of(sampleQuestion));

        List<Question> result = questionService.getAllQuestions();
        assertEquals(1, result.size());
        verify(questionRepository).findAll();
    }

    @Test
    void shouldReturnQuestionById() {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(sampleQuestion));

        Optional<Question> result = questionService.getQuestionById(1L);
        assertTrue(result.isPresent());
        assertEquals("Java", result.get().getSubject());
    }

    @Test
    void shouldReturnAllSubjects() {
        List<String> subjects = List.of("Java", "Spring");
        when(questionRepository.findDistinctSubject()).thenReturn(subjects);

        List<String> result = questionService.getAllSubjects();
        assertEquals(2, result.size());
    }

    @Test
    void shouldUpdateQuestion() throws ChangeSetPersister.NotFoundException {
        when(questionRepository.findById(1L)).thenReturn(Optional.of(sampleQuestion));
        when(questionRepository.save(any(Question.class))).thenReturn(sampleQuestion);

        Question updatedData = new Question();
        updatedData.setQuestion("Updated?");
        updatedData.setChoices(List.of("A", "B"));
        updatedData.setCorrectAnswers(List.of("A"));

        Question result = questionService.updateQuestion(1L, updatedData);

        assertEquals("Updated?", result.getQuestion());
        assertEquals(List.of("A"), result.getCorrectAnswers());
        verify(questionRepository).save(any(Question.class));
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentQuestion() {
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        Question dummy = new Question();
        assertThrows(ChangeSetPersister.NotFoundException.class, () -> {
            questionService.updateQuestion(1L, dummy);
        });
    }

    @Test
    void shouldDeleteQuestion() {
        doNothing().when(questionRepository).deleteById(1L);

        questionService.deleteQuestion(1L);
        verify(questionRepository).deleteById(1L);
    }

    @Test
    void shouldReturnQuestionsForUser() {
        Pageable pageable = PageRequest.of(0, 2);
        when(questionRepository.findBySubject(eq("Java"), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(sampleQuestion, sampleQuestion)));

        List<Question> result = questionService.getQuestionsForUser(2, "Java");

        assertEquals(2, result.size());
        verify(questionRepository).findBySubject("Java", pageable);
    }
}
