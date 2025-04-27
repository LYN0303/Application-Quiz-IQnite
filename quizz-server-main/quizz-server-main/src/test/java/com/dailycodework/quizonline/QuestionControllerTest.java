/*
 * -------------------------------------------------------------------------------
 * Spécifications des tests pour la classe QuestionControllerTest
 * -------------------------------------------------------------------------------
 * Cette classe contient des tests unitaires pour le contrôleur `QuestionController`.
 * Les tests utilisent JUnit 5, Mockito et Spring Web MVC Test pour simuler les
 * appels HTTP et vérifier le comportement des actions du contrôleur qui gèrent les
 * questions du quiz.
 *
 * Les tests couvrent les scénarios suivants :
 *
 * 1. **`shouldCreateQuestion()`** - Teste la création d'une nouvelle question via l'API.
 *    - Vérifie que l'API renvoie un statut `201 Created` et que le titre de la question 
 *      correspond à celui fourni.
 *
 * 2. **`shouldReturnAllQuestions()`** - Teste la récupération de toutes les questions via l'API.
 *    - Vérifie que l'API renvoie un statut `200 OK`, que le sujet de la première question 
 *      est `"Java"` et que la taille de la liste des questions est correcte.
 *
 * 3. **`shouldReturnQuestionById()`** - Teste la récupération d'une question par son ID via l'API.
 *    - Vérifie que l'API renvoie un statut `200 OK` et que le sujet de la question est `"Java"`.
 *
 * 4. **`shouldUpdateQuestion()`** - Teste la mise à jour d'une question via l'API.
 *    - Vérifie que l'API renvoie un statut `200 OK` et que l'ID de la question dans la réponse 
 *      est bien `1`.
 *
 * 5. **`shouldDeleteQuestion()`** - Teste la suppression d'une question via l'API.
 *    - Vérifie que l'API renvoie un statut `204 No Content`, indiquant que la suppression a 
 *      été effectuée avec succès sans retour de contenu.
 *
 * 6. **`shouldReturnAllSubjects()`** - Teste la récupération de tous les sujets via l'API.
 *    - Vérifie que l'API renvoie un statut `200 OK` et que le premier sujet dans la liste est 
 *      `"Java"`.
 *
 * 7. **`shouldReturnShuffledQuestionsForUser()`** - Teste la récupération de questions 
 *    mélangées pour un utilisateur via l'API.
 *    - Vérifie que l'API renvoie un statut `200 OK` et que le nombre de questions retournées 
 *      est inférieur ou égal au nombre spécifié.
 *
 * -------------------------------------------------------------------------------
 * Dépendances et Mocks :
 * -------------------------------------------------------------------------------
 * - `@WebMvcTest(QuestionController.class)` : Cette annotation charge uniquement les 
 *   composants nécessaires pour tester le contrôleur `QuestionController`, excluant les autres 
 *   composants de l'application tels que les services et les bases de données.
 * - `@MockBean` : Utilisé pour simuler le service `IQuestionService`.
 * - `MockMvc` : Outil de test Spring MVC permettant de simuler des requêtes HTTP et de 
 *   vérifier les réponses.
 * - `ObjectMapper` : Utilisé pour sérialiser des objets Java en JSON et vice versa.
 *
 * -------------------------------------------------------------------------------
 * Méthodes Mockito utilisées :
 * -------------------------------------------------------------------------------
 * - `when(...).thenReturn(...)` : Cette méthode est utilisée pour spécifier ce que le mock 
 *   `questionService` doit retourner lorsqu'une méthode spécifique est appelée.
 * - `any(...)` : Permet de faire correspondre n'importe quel argument de type spécifié.
 * - `Mockito.eq(...)` : Permet de spécifier un argument exact pour la méthode mockée.
 *
 * -------------------------------------------------------------------------------
 * Assertions dans les tests :
 * -------------------------------------------------------------------------------
 * - `andExpect(status().isCreated())` : Vérifie que le statut HTTP est `201 Created`.
 * - `andExpect(status().isOk())` : Vérifie que le statut HTTP est `200 OK`.
 * - `andExpect(status().isNoContent())` : Vérifie que le statut HTTP est `204 No Content`.
 * - `andExpect(jsonPath(...))` : Vérifie le contenu JSON de la réponse.
 *
 * -------------------------------------------------------------------------------
 * Structure de la classe `Question` :
 * -------------------------------------------------------------------------------
 * - `id` : L'identifiant unique de la question (long).
 * - `subject` : Le sujet de la question (String).
 * - `title` : Le titre de la question (String).
 * - `optionA`, `optionB`, `optionC`, `optionD` : Les options de réponse (String).
 * - `rightAnswer` : La bonne réponse (String).
 *
 * -------------------------------------------------------------------------------
 * Résumé des comportements testés :
 * -------------------------------------------------------------------------------
 * - Création de questions (`POST`).
 * - Récupération de toutes les questions (`GET`).
 * - Récupération d'une question spécifique par ID (`GET`).
 * - Mise à jour d'une question (`PUT`).
 * - Suppression d'une question (`DELETE`).
 * - Récupération de tous les sujets disponibles (`GET`).
 * - Récupération de questions mélangées pour un utilisateur (`GET`).
 */

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
