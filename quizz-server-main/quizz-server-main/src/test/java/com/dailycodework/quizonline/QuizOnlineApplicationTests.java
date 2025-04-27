package com.dailycodework.quizonline.service;

import com.dailycodework.quizonline.model.Quiz;
import com.dailycodework.quizonline.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Test
    void shouldReturnListOfQuizzes() {
        // Arrange
        QuizRepository quizRepository = mock(QuizRepository.class);
        QuizService quizService = new QuizService(quizRepository);

        Quiz quiz1 = new Quiz(1L, "Java Basics");
        Quiz quiz2 = new Quiz(2L, "Spring Boot");

        when(quizRepository.findAll()).thenReturn(Arrays.asList(quiz1, quiz2));

        // Act
        List<Quiz> quizzes = quizService.getAllQuizzes();

        // Assert
        assertEquals(2, quizzes.size());
        assertEquals("Java Basics", quizzes.get(0).getTitle());
    }
}
