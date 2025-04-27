/*
 * -------------------------------------------------------------------------------
 * Spécifications de l'interface `IQuestionService`
 * -------------------------------------------------------------------------------
 * Cette interface définit les méthodes nécessaires pour gérer les opérations liées 
 * aux questions dans le système de quiz. Elle est utilisée pour l'implémentation des 
 * services qui gèrent la création, la récupération, la mise à jour, la suppression 
 * et la gestion des questions dans la base de données, ainsi que d'autres fonctionnalités 
 * comme la récupération des sujets distincts et la sélection aléatoire de questions 
 * pour les utilisateurs.
 *
 * -------------------------------------------------------------------------------
 * Méthodes :
 * -------------------------------------------------------------------------------
 * 1. **`createQuestion(Question question)`** :
 *    - Description : Crée une nouvelle question dans le système.
 *    - Retourne : La question créée.
 * 
 * 2. **`getAllQuestions()`** :
 *    - Description : Récupère toutes les questions présentes dans la base de données.
 *    - Retourne : Une liste de toutes les questions.
 *
 * 3. **`getQuestionById(Long id)`** :
 *    - Description : Récupère une question spécifique à partir de son identifiant unique.
 *    - Retourne : Un objet `Optional<Question>` contenant la question si elle est trouvée, sinon vide.
 *
 * 4. **`getAllSubjects()`** :
 *    - Description : Récupère une liste de tous les sujets distincts pour lesquels des questions 
 *      sont disponibles dans le système.
 *    - Retourne : Une liste de chaînes de caractères représentant les sujets distincts.
 *
 * 5. **`updateQuestion(Long id, Question question)`** :
 *    - Description : Met à jour une question existante avec de nouvelles informations. 
 *      Lève une exception si la question n'existe pas.
 *    - Paramètres : 
 *      - `id` : L'identifiant de la question à mettre à jour.
 *      - `question` : Les nouvelles données pour la question.
 *    - Retourne : La question mise à jour.
 *    - Lève : `ChangeSetPersister.NotFoundException` si la question n'existe pas.
 *
 * 6. **`deleteQuestion(Long id)`** :
 *    - Description : Supprime une question spécifique de la base de données.
 *    - Paramètres : 
 *      - `id` : L'identifiant de la question à supprimer.
 *
 * 7. **`getQuestionsForUser(Integer numOfQuestions, String subject)`** :
 *    - Description : Récupère un certain nombre de questions aléatoires pour un utilisateur 
 *      en fonction du sujet spécifié.
 *    - Paramètres : 
 *      - `numOfQuestions` : Le nombre de questions à récupérer.
 *      - `subject` : Le sujet des questions à récupérer.
 *    - Retourne : Une liste de questions pour l'utilisateur, filtrées par sujet et 
 *      aléatoirement sélectionnées.
 *
 * -------------------------------------------------------------------------------
 * Remarque :
 * -------------------------------------------------------------------------------
 * L'interface `IQuestionService` définit les fonctionnalités essentielles pour gérer 
 * les questions d'un quiz. Elle est utilisée par les services qui doivent interagir 
 * avec la couche de persistance pour manipuler les questions, leurs sujets, et fournir 
 * des ensembles de questions aux utilisateurs. Cette interface est cruciale pour 
 * l'architecture du service de quiz.
 */


package com.dailycodework.quizonline.service;

import com.dailycodework.quizonline.model.Question;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

/**
 * @author IQniteQuiz Groupe
 */

public interface IQuestionService {

    Question createQuestion(Question question);

    List<Question> getAllQuestions();

    Optional<Question> getQuestionById(Long id);

    List<String> getAllSubjects();

    Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException;

    void  deleteQuestion(Long id);

    List<Question> getQuestionsForUser(Integer numOfQuestions, String subject);


}
