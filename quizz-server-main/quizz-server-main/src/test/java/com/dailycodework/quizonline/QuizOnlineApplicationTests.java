/*
 * -------------------------------------------------------------------------------
 * Spécifications des tests pour la classe QuizServiceTest
 * -------------------------------------------------------------------------------
 * Cette classe contient un test unitaire pour la classe `QuizService`.
 * Le test vérifie le comportement de la méthode `getAllQuizzes()` qui récupère 
 * tous les quiz disponibles dans le repository.
 *
 * -------------------------------------------------------------------------------
 * Test spécifique :
 * -------------------------------------------------------------------------------
 * 1. **`shouldReturnListOfQuizzes()`** - Teste la récupération de tous les quiz.
 *    - Le test crée deux objets `Quiz` (quiz1 et quiz2) et les ajoute à la liste retournée
 *      par le mock du repository `quizRepository`.
 *    - Le test vérifie ensuite que la méthode `getAllQuizzes()` du service retourne 
 *      bien la liste des quiz attendue avec une taille correcte (2 quiz).
 *    - Enfin, le test vérifie que le titre du premier quiz dans la liste est bien "Java Basics".
 *
 * -------------------------------------------------------------------------------
 * Dépendances et Mocks :
 * -------------------------------------------------------------------------------
 * - `QuizRepository` : Utilisé pour simuler l'accès aux données des quiz.
 * - `QuizService` : Service à tester qui utilise le repository pour accéder aux quiz.
 * - `Mockito` : Utilisé pour créer des mocks des objets dépendants et définir leurs comportements.
 *
 * -------------------------------------------------------------------------------
 * Assertions dans le test :
 * -------------------------------------------------------------------------------
 * - `assertEquals(...)` : Vérifie que la valeur retournée par la méthode testée est égale à 
 *   celle attendue.
 *
 * -------------------------------------------------------------------------------
 * Méthodes Mockito utilisées :
 * -------------------------------------------------------------------------------
 * - `when(...).thenReturn(...)` : Cette méthode est utilisée pour spécifier ce que le mock 
 *   `quizRepository` doit retourner lorsqu'une méthode spécifique est appelée.
 *
 * -------------------------------------------------------------------------------
 * Structure de la classe `Quiz` :
 * -------------------------------------------------------------------------------
 * - `id` : L'identifiant unique du quiz (long).
 * - `title` : Le titre du quiz (String).
 *
 * -------------------------------------------------------------------------------
 * Résumé du comportement testé :
 * -------------------------------------------------------------------------------
 * - Récupération de la liste de tous les quiz disponibles dans le repository.
 */



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
