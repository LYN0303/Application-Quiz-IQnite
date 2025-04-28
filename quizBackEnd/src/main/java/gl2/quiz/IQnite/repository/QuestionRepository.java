package gl2.quiz.IQnite.repository;

import gl2.quiz.IQnite.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.net.ContentHandler;
import java.util.List;
/*
 * -------------------------------------------------------------------------------
 * Spécifications de la classe `QuestionRepository`
 * -------------------------------------------------------------------------------
 * Cette interface est un repository pour l'entité `Question`. Elle étend `JpaRepository`, 
 * ce qui permet à Spring Data JPA de gérer les opérations CRUD de manière automatique 
 * pour cette entité.
 *
 * -------------------------------------------------------------------------------
 * Points clés :
 * -------------------------------------------------------------------------------
 * - L'interface étend `JpaRepository`, permettant une gestion simplifiée des entités 
 *   et des opérations de base de données sans avoir besoin d'implémenter des méthodes 
 *   spécifiques pour les opérations standards.
 * - Elle contient des méthodes personnalisées permettant de récupérer des informations 
 *   spécifiques, telles que les sujets distincts ou les questions filtrées par sujet.
 *
 * -------------------------------------------------------------------------------
 * Méthodes :
 * -------------------------------------------------------------------------------
 * 1. **`findDistinctSubject()`** :
 *    - Description : Cette méthode permet de récupérer tous les sujets distincts 
 *      disponibles dans la base de données. Elle utilise une requête personnalisée 
 *      avec l'annotation `@Query`.
 *    - Retourne : Une liste de chaînes de caractères représentant les sujets uniques.
 * 
 * 2. **`findBySubject(String subject, Pageable pageable)`** :
 *    - Description : Cette méthode récupère une page de questions associées à un sujet 
 *      spécifique, avec prise en charge de la pagination. Elle permet d'afficher 
 *      un nombre défini de résultats par page.
 *    - Paramètres :
 *      - `subject` : Le sujet des questions à récupérer.
 *      - `pageable` : Un objet `Pageable` permettant de spécifier la pagination des résultats.
 *    - Retourne : Une page de questions filtrées par sujet.
 *
 * -------------------------------------------------------------------------------
 * Héritage :
 * -------------------------------------------------------------------------------
 * Cette interface hérite de `JpaRepository`, ce qui permet d'utiliser automatiquement 
 * des fonctionnalités comme `save()`, `findAll()`, `delete()`, et plus, sans avoir 
 * besoin de les définir manuellement. Le repository simplifie ainsi l'interaction avec 
 * la base de données pour les entités `Question`.
 *
 * -------------------------------------------------------------------------------
 * Utilisation :
 * -------------------------------------------------------------------------------
 * - **`findDistinctSubject()`** : Utilisée pour obtenir la liste des sujets distincts 
 *   des questions. Cela peut être utilisé dans les cas où l'on veut afficher les 
 *   sujets de manière dynamique dans une interface utilisateur, sans doublons.
 * - **`findBySubject(String subject, Pageable pageable)`** : Utilisée lorsque vous 
 *   souhaitez récupérer une liste de questions correspondant à un sujet spécifique, 
 *   tout en paginant les résultats. Cela est très utile dans les applications où 
 *   les questions peuvent être nombreuses et doivent être consultées par petites 
 *   portions à la fois.
 *
 * -------------------------------------------------------------------------------
 * Remarque :
 * -------------------------------------------------------------------------------
 * Ces méthodes sont directement utilisées dans les services ou contrôleurs qui 
 * interagissent avec les questions de quiz dans l'application.
 * 
 * L'interface repose sur les fonctionnalités puissantes de Spring Data JPA pour 
 * fournir une gestion efficace et simplifiée des entités `Question` dans la base de données.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Récupère une liste distincte de tous les sujets disponibles dans la base de données.
     *
     * @return une liste de chaînes représentant les sujets distincts des questions.
     */
    @Query("SELECT DISTINCT q.subject FROM Question q")
    List<String> findDistinctSubject();

    /**
     * Récupère une page de questions correspondant à un sujet spécifique.
     *
     * @param subject  Le sujet des questions à récupérer.
     * @param pageable L'objet Pageable pour la pagination.
     * @return un objet Page contenant les questions correspondant au sujet donné.
     */
    Page<Question> findBySubject(String subject, Pageable pageable);
}
