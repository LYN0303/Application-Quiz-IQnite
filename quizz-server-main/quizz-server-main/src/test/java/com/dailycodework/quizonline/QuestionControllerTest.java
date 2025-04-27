package com.dailycodework.quizonline.controller;

import com.dailycodework.quizonline.model.Question;
import com.dailycodework.quizonline.service.IQuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Question sampleQuestion;

    @BeforeEach
    void setUp() {
        sampleQuestion = new Question();
        sampleQuestion.setId(1L);
        sampleQuestion.setSubject("Java");
        sampleQuestion.setTitle("What is polymorphism?");
        sampleQuestion.setOptionA("Overloading");
        sampleQuestion.setOptionB("Inheritance");
        sampleQuestion.setOptionC("Abstraction");
        sampleQuestion.setOptionD("All of the above");
        sampleQuestion.setRightAnswer("All of the above");
    }

    @Test
    void shouldCreateQuestion() throws Exception {
        when(questionService.createQuestion(any(Question.class))).thenReturn(sampleQuestion);

        mockMvc.perform(post("/api/quizzes/create-new-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleQuestion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(sampleQuestion.getTitle())));
    }

    @Test
    void shouldReturnAllQuestions() throws Exception {
        List<Question> questions = List.of(sampleQuestion);
        when(questionService.getAllQuestions()).thenReturn(questions);

        mockMvc.perform(get("/api/quizzes/all-questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].subject", is("Java")));
    }

    @Test
    void shouldReturnQuestionById() throws Exception {
        when(questionService.getQuestionById(1L)).thenReturn(Optional.of(sampleQuestion));

        mockMvc.perform(get("/api/quizzes/question/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject", is("Java")));
    }

    @Test
    void shouldUpdateQuestion() throws Exception {
        when(questionService.updateQuestion(Mockito.eq(1L), any(Question.class)))
                .thenReturn(sampleQuestion);

        mockMvc.perform(put("/api/quizzes/question/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleQuestion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void shouldDeleteQuestion() throws Exception {
        mockMvc.perform(delete("/api/quizzes/question/1/delete"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnAllSubjects() throws Exception {
        List<String> subjects = Arrays.asList("Java", "Spring");
        when(questionService.getAllSubjects()).thenReturn(subjects);

        mockMvc.perform(get("/api/quizzes/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0]", is("Java")));
    }

    @Test
    void shouldReturnShuffledQuestionsForUser() throws Exception {
        List<Question> questions = Arrays.asList(sampleQuestion, sampleQuestion, sampleQuestion);
        when(questionService.getQuestionsForUser(3, "Java")).thenReturn(questions);

        mockMvc.perform(get("/api/quizzes/quiz/fetch-questions-for-user")
                .param("numOfQuestions", "3")
                .param("subject", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", lessThanOrEqualTo(3)));
    }
}
