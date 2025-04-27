/*
 * -------------------------------------------------------------------------------
 * Spécifications de la classe `QuestionService`
 * -------------------------------------------------------------------------------
 * Cette classe implémente l'interface `IQuestionService` et fournit des méthodes 
 * pour la gestion des questions dans le système de quiz. Elle interagit directement 
 * avec la base de données via le `QuestionRepository` pour effectuer des opérations 
 * telles que la création, la récupération, la mise à jour et la suppression des 
 * questions, ainsi que pour récupérer des questions spécifiques pour les utilisateurs 
 * en fonction du sujet et du nombre de questions demandées.
 *
 * -------------------------------------------------------------------------------
 * Points clés :
 * -------------------------------------------------------------------------------
 * - La classe est annotée avec `@Service`, indiquant qu'elle est un composant 
 *   de service dans l'architecture Spring.
 * - L'annotation `@RequiredArgsConstructor` génère automatiquement un constructeur 
 *   avec un paramètre pour chaque champ `final` de la classe (dans ce cas, `questionRepository`).
 * - La classe est responsable de la logique métier, incluant les interactions avec 
 *   la couche de persistance (via `QuestionRepository`).
 *
 * -------------------------------------------------------------------------------
 * Méthodes :
 * -------------------------------------------------------------------------------
 * 1. **`createQuestion(Question question)`** :
 *    - Description : Crée une nouvelle question dans la base de données.
 *    - Retourne : La question qui a été enregistrée.
 *
 * 2. **`getAllQuestions()`** :
 *    - Description : Récupère toutes les questions de la base de données.
 *    - Retourne : Une liste de toutes les questions présentes dans la base de données.
 *
 * 3. **`getQuestionById(Long id)`** :
 *    - Description : Récupère une question en fonction de son identifiant unique.
 *    - Retourne : Un `Optional<Question>` qui contient la question si elle existe, sinon est vide.
 *
 * 4. **`getAllSubjects()`** :
 *    - Description : Récupère une liste de tous les sujets distincts pour lesquels des 
 *      questions sont disponibles dans le système.
 *    - Retourne : Une liste de chaînes représentant les différents sujets.
 *
 * 5. **`updateQuestion(Long id, Question question)`** :
 *    - Description : Met à jour une question existante avec de nouvelles informations. 
 *      Si la question n'existe pas, lève une exception.
 *    - Paramètres : 
 *      - `id` : L'identifiant de la question à mettre à jour.
 *      - `question` : La nouvelle question avec les informations mises à jour.
 *    - Retourne : La question mise à jour.
 *    - Lève : `ChangeSetPersister.NotFoundException` si la question avec l'id donné 
 *      n'existe pas dans la base de données.
 *
 * 6. **`deleteQuestion(Long id)`** :
 *    - Description : Supprime une question de la base de données en fonction de son identifiant.
 *    - Paramètres : 
 *      - `id` : L'identifiant de la question à supprimer.
 *
 * 7. **`getQuestionsForUser(Integer numOfQuestions, String subject)`** :
 *    - Description : Récupère un certain nombre de questions (aléatoires) pour un utilisateur 
 *      selon un sujet donné. Utilise la pagination pour limiter le nombre de questions récupérées.
 *    - Paramètres : 
 *      - `numOfQuestions` : Le nombre de questions à récupérer.
 *      - `subject` : Le sujet des questions à récupérer.
 *    - Retourne : Une liste de questions pour l'utilisateur, filtrées par sujet et 
 *      limitées à `numOfQuestions` questions.
 *
 * -------------------------------------------------------------------------------
 * La classe `QuestionService` est une partie essentielle du service de gestion de quiz. 
 * Elle assure la communication avec la base de données pour la gestion des questions 
 * et offre des méthodes clés pour manipuler les données de manière cohérente et sécurisée.
 */


package com.dailycodework.quizonline.service;

import com.dailycodework.quizonline.model.Question;
import com.dailycodework.quizonline.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/**
 * @author IQniteQuiz Groupe
 */

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{
    private final QuestionRepository questionRepository;
    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if (theQuestion.isPresent()){
            Question updatedQuestion = theQuestion.get();
            updatedQuestion.setQuestion(question.getQuestion());
            updatedQuestion.setChoices(question.getChoices());
            updatedQuestion.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(updatedQuestion);
        }else {
            throw new ChangeSetPersister.NotFoundException();
        }

    }
    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
    @Override
    public List<Question> getQuestionsForUser(Integer numOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0, numOfQuestions);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}
