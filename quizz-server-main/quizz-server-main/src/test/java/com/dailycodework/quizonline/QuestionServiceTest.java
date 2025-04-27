/*
 * -------------------------------------------------------------------------------
 * Spécifications des tests pour la classe QuestionServiceTest
 * -------------------------------------------------------------------------------
 * Cette classe contient des tests unitaires pour la classe `QuestionService`.
 * Les tests vérifient le comportement des méthodes de service liées aux opérations CRUD
 * (création, lecture, mise à jour et suppression) sur les questions, ainsi que certaines
 * fonctionnalités supplémentaires comme la récupération des questions pour un utilisateur.
 *
 * Les tests couvrent les scénarios suivants :
 *
 * 1. **`shouldCreateQuestion()`** - Teste la création d'une nouvelle question.
 *    - Vérifie que la méthode `createQuestion()` appelle la méthode `save()` du repository 
 *      et que la question retournée correspond à celle passée en paramètre.
 * 
 * 2. **`shouldReturnAllQuestions()`** - Teste la récupération de toutes les questions.
 *    - Vérifie que la méthode `getAllQuestions()` renvoie bien une liste de questions
 *      et appelle correctement la méthode `findAll()` du repository.
 *
 * 3. **`shouldReturnQuestionById()`** - Teste la récupération d'une question par son ID.
 *    - Vérifie que la méthode `getQuestionById()` renvoie une question avec le bon sujet 
 *      lorsque la question existe dans la base de données.
 *    - Vérifie également qu'un `Optional` est retourné.
 *
 * 4. **`shouldReturnAllSubjects()`** - Teste la récupération de tous les sujets distincts.
 *    - Vérifie que la méthode `getAllSubjects()` renvoie bien une liste de sujets distincts.
 * 
 * 5. **`shouldUpdateQuestion()`** - Teste la mise à jour d'une question.
 *    - Vérifie que la méthode `updateQuestion()` met bien à jour les données d'une question
 *      et sauvegarde les modifications via le repository.
 *
 * 6. **`shouldThrowWhenUpdatingNonExistentQuestion()`** - Teste le cas où l'on tente
 *    de mettre à jour une question qui n'existe pas.
 *    - Vérifie que la méthode `updateQuestion()` lève une exception 
 *      `ChangeSetPersister.NotFoundException` si la question n'existe pas dans le repository.
 * 
 * 7. **`shouldDeleteQuestion()`** - Teste la suppression d'une question par son ID.
 *    - Vérifie que la méthode `deleteQuestion()` appelle bien la méthode `deleteById()`
 *      du repository et ne lance aucune exception.
 *
 * 8. **`shouldReturnQuestionsForUser()`** - Teste la récupération de questions pour un utilisateur.
 *    - Vérifie que la méthode `getQuestionsForUser()` renvoie une liste de questions paginée
 *      basée sur un sujet spécifique et un nombre de questions donné.
 * 
 * -------------------------------------------------------------------------------
 * Dépendances et Mocks :
 * -------------------------------------------------------------------------------
 * - `@Mock` : Utilisé pour simuler le repository `QuestionRepository` dans les tests.
 * - `QuestionService` : Service à tester qui utilise le repository pour accéder aux données.
 * - `Mockito` : Utilisé pour créer des mocks des objets dépendants et définir leurs comportements.
 * 
 * -------------------------------------------------------------------------------
 * Assertions dans les tests :
 * -------------------------------------------------------------------------------
 * - `assertEquals(...)` : Vérifie que la valeur retournée par la méthode testée est égale à 
 *   celle attendue.
 * - `assertTrue(...)` : Vérifie que l'expression passée en paramètre est vraie.
 * - `assertThrows(...)` : Vérifie qu'une exception spécifique est levée dans un cas donné.
 * - `verify(...)` : Vérifie qu'une méthode a été appelée sur un mock un certain nombre de fois.
 *
 * -------------------------------------------------------------------------------
 * Méthodes Mockito utilisées :
 * -------------------------------------------------------------------------------
 * - `when(...).thenReturn(...)` : Cette méthode est utilisée pour spécifier ce que le mock 
 *   `questionRepository` doit retourner lorsqu'une méthode spécifique est appelée.
 * - `doNothing().when(...)` : Utilisé pour spécifier qu'aucune action ne doit être effectuée 
 *   lors de l'appel à la méthode `deleteById()` du repository.
 * - `ArgumentMatchers.eq(...)` : Permet de spécifier les arguments exacts pour la méthode mockée.
 *
 * -------------------------------------------------------------------------------
 * Structure de la classe `Question` :
 * -------------------------------------------------------------------------------
 * - `id` : L'identifiant unique de la question (long).
 * - `subject` : Le sujet de la question (String).
 * - `question` : La question (String).
 * - `choices` : Liste des options de réponse possibles (List<String>).
 * - `correctAnswers` : Liste des bonnes réponses (List<String>).
 *
 * -------------------------------------------------------------------------------
 * Résumé des comportements testés :
 * -------------------------------------------------------------------------------
 * - Création d'une question (`save()`).
 * - Récupération de toutes les questions (`findAll()`).
 * - Récupération d'une question par son ID (`findById()`).
 * - Récupération de tous les sujets distincts (`findDistinctSubject()`).
 * - Mise à jour d'une question (`save()`).
 * - Suppression d'une question (`deleteById()`).
 * - Récupération de questions pour un utilisateur spécifique (`findBySubject()`).
 */


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
