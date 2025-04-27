/*
 * -------------------------------------------------------------------------------
 * Spécifications de la classe `Question`
 * -------------------------------------------------------------------------------
 * Cette classe représente une question de quiz dans le système. Elle est utilisée pour 
 * modéliser les questions avec leurs choix de réponses, réponses correctes et autres 
 * métadonnées nécessaires pour interagir avec les utilisateurs.
 *
 * -------------------------------------------------------------------------------
 * Points clés :
 * -------------------------------------------------------------------------------
 * - La classe est annotée avec `@Entity` pour la mapper à une table de base de données via JPA (Jakarta Persistence API).
 * - Les attributs de la classe sont persistés sous forme de colonnes dans la table correspondante.
 * - Utilisation de `@Getter` et `@Setter` de Lombok pour générer automatiquement les méthodes d'accès (getter) et de modification (setter) pour les champs.
 *
 * -------------------------------------------------------------------------------
 * Attributs :
 * -------------------------------------------------------------------------------
 * 1. **`id`** :
 *    - `@Id` : Indique que cet attribut est la clé primaire de la table.
 *    - `@GeneratedValue(strategy = GenerationType.IDENTITY)` : La valeur de `id` est générée automatiquement par la base de données.
 *    - Type : `Long` (Identifiant unique pour chaque question).
 *
 * 2. **`question`** :
 *    - `@NotBlank` : Cette annotation garantit que le champ `question` ne peut pas être vide ou nul.
 *    - Type : `String` (Le texte de la question).
 *
 * 3. **`subject`** :
 *    - `@NotBlank` : Cette annotation garantit que le champ `subject` ne peut pas être vide ou nul.
 *    - Type : `String` (Le sujet auquel appartient la question, par exemple "Java" ou "Spring").
 *
 * 4. **`questionType`** :
 *    - `@NotBlank` : Cette annotation garantit que le champ `questionType` ne peut pas être vide ou nul.
 *    - Type : `String` (Type de question, par exemple "choix multiples", "vrai/faux").
 *
 * 5. **`choices`** :
 *    - `@ElementCollection` : Cette annotation permet de mapper une collection d'éléments simples comme une liste dans une table secondaire.
 *    - Type : `List<String>` (Liste des choix possibles pour la réponse à la question).
 *
 * 6. **`correctAnswers`** :
 *    - `@ElementCollection` : Cette annotation permet de mapper une collection d'éléments simples comme une liste dans une table secondaire.
 *    - Type : `List<String>` (Liste des réponses correctes pour la question).
 *
 * -------------------------------------------------------------------------------
 * Validation :
 * -------------------------------------------------------------------------------
 * - `@NotBlank` est utilisé pour garantir que certains champs, comme la question, le sujet et le type de question, ne soient jamais vides ou nulls.
 * 
 * -------------------------------------------------------------------------------
 * La classe `Question` est essentielle pour la gestion des questions de quiz et permet de stocker toutes 
 * les informations nécessaires dans la base de données. Elle est utilisée dans les différents services et contrôleurs 
 * pour manipuler et répondre aux besoins des utilisateurs, tels que la création, la mise à jour et la récupération 
 * des questions pour le quiz.
 */



package com.dailycodework.quizonline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Simpson Alfred
 */
@Getter
@Setter
@Entity
public class Question {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String question;
    @NotBlank
    private String subject;
    @NotBlank
    private String questionType;


    @ElementCollection
    private List<String> choices;


    @ElementCollection
    private List<String> correctAnswers;
}
