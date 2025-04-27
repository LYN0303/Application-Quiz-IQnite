/*
 * -------------------------------------------------------------------------------
 * Spécifications de la classe `QuestionController`
 * -------------------------------------------------------------------------------
 * Cette classe est un contrôleur REST pour gérer les questions du quiz via des 
 * endpoints HTTP. Elle permet de créer, lire, mettre à jour, supprimer des 
 * questions, ainsi que d'obtenir la liste des sujets et des questions pour un quiz.
 *
 * -------------------------------------------------------------------------------
 * Points clés :
 * -------------------------------------------------------------------------------
 * - Le contrôleur est annoté avec `@RestController` et `@RequestMapping("/api/quizzes")`,
 *   ce qui signifie qu'il gère les requêtes HTTP sur les routes liées aux quiz.
 * - `@CrossOrigin("http://localhost:5173")` : Permet à l'application front-end de communiquer 
 *   avec le back-end en autorisant les requêtes provenant de `http://localhost:5173`.
 * 
 * -------------------------------------------------------------------------------
 * Méthodes :
 * -------------------------------------------------------------------------------
 * 1. **`createQuestion()`** (POST) : 
 *    - Crée une nouvelle question. 
 *    - Attente de données de type `Question` dans le corps de la requête.
 *    - Retourne un statut HTTP 201 (Created) et la question créée.
 *
 * 2. **`getAllQuestions()`** (GET) :
 *    - Récupère toutes les questions.
 *    - Retourne un statut HTTP 200 (OK) et la liste des questions.
 *
 * 3. **`getQuestionById()`** (GET) :
 *    - Récupère une question en fonction de son ID.
 *    - Si la question est trouvée, un statut HTTP 200 (OK) et la question sont retournés.
 *    - Si la question n'est pas trouvée, une exception `ChangeSetPersister.NotFoundException` est levée.
 *
 * 4. **`updateQuestion()`** (PUT) :
 *    - Met à jour une question existante.
 *    - Les nouvelles données de la question sont fournies dans le corps de la requête.
 *    - Retourne un statut HTTP 200 (OK) et la question mise à jour.
 *
 * 5. **`deleteQuestion()`** (DELETE) :
 *    - Supprime une question en fonction de son ID.
 *    - Retourne un statut HTTP 204 (No Content) après la suppression.
 *
 * 6. **`getAllSubjects()`** (GET) :
 *    - Récupère la liste des sujets disponibles pour les questions.
 *    - Retourne un statut HTTP 200 (OK) et la liste des sujets.
 *
 * 7. **`getQuestionsForUser()`** (GET) :
 *    - Récupère une liste aléatoire de questions pour un utilisateur, en fonction du nombre demandé (`numOfQuestions`) 
 *      et du sujet spécifique (`subject`).
 *    - Les questions sont mélangées avant d'être retournées.
 *    - Retourne un statut HTTP 200 (OK) et la liste des questions aléatoires pour l'utilisateur.
 *
 * -------------------------------------------------------------------------------
 * Gestion des erreurs et exceptions :
 * -------------------------------------------------------------------------------
 * - Si une question demandée n'est pas trouvée, la méthode `getQuestionById()` lance une exception 
 *   `ChangeSetPersister.NotFoundException`, ce qui provoque un code de réponse HTTP 404.
 *
 * -------------------------------------------------------------------------------
 * Classe `Question` :
 * -------------------------------------------------------------------------------
 * - La classe `Question` est utilisée dans le corps des requêtes et des réponses HTTP pour représenter une question.
 * - Elle contient des informations comme le sujet, la question, les choix de réponse, et les réponses correctes.
 *
 * -------------------------------------------------------------------------------
 * Dépendances :
 * -------------------------------------------------------------------------------
 * - `IQuestionService` : Service utilisé pour gérer la logique métier associée aux questions.
 * 
 * -------------------------------------------------------------------------------
 * Résumé du comportement de chaque méthode :
 * -------------------------------------------------------------------------------
 * - **POST /api/quizzes/create-new-question** : Crée une question.
 * - **GET /api/quizzes/all-questions** : Récupère toutes les questions.
 * - **GET /api/quizzes/question/{id}** : Récupère une question par son ID.
 * - **PUT /api/quizzes/question/{id}/update** : Met à jour une question par son ID.
 * - **DELETE /api/quizzes/question/{id}/delete** : Supprime une question par son ID.
 * - **GET /api/quizzes/subjects** : Récupère tous les sujets disponibles.
 * - **GET /api/quizzes/quiz/fetch-questions-for-user** : Récupère un nombre spécifique de questions aléatoires pour un utilisateur.
 *
 * -------------------------------------------------------------------------------
 * Les méthodes utilisent `ResponseEntity` pour envoyer les réponses HTTP appropriées, 
 * ainsi que des statuts comme `CREATED`, `OK`, `NO_CONTENT` pour indiquer les résultats des actions.
 */



package com.dailycodework.quizonline.controller;

import com.dailycodework.quizonline.model.Question;
import com.dailycodework.quizonline.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * @author IQniteQuiz Groupe
 */

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuestionController {
    private final IQuestionService questionService;

    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question){
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(CREATED).body(createdQuestion);
    }

    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = questionService.getQuestionById(id);
        if (theQuestion.isPresent()){
            return ResponseEntity.ok(theQuestion.get());
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @PutMapping("/question/{id}/update")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Question updatedQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/question/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects(){
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/quiz/fetch-questions-for-user")
    public ResponseEntity<List<Question>> getQuestionsForUser(
            @RequestParam Integer numOfQuestions, @RequestParam String subject){
        List<Question> allQuestions = questionService.getQuestionsForUser(numOfQuestions, subject);

        List<Question> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);

        int availableQuestions = Math.min(numOfQuestions, mutableQuestions.size());
        List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);
        return ResponseEntity.ok(randomQuestions);
    }

}
