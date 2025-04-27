# **Cahier des Charges : Application-Quiz-IQnite**

## **1. Contexte du projet**

L'application **IQnite** est une plateforme de quiz en ligne permettant aux utilisateurs de tester et d'améliorer leurs connaissances dans différents domaines. Cette application est destinée à la fois aux étudiants, qui peuvent l'utiliser pour se préparer à des évaluations, et aux enseignants, qui peuvent l'utiliser pour créer des quiz sur des sujets variés. Le projet a été développé dans le cadre du module de Génie Logiciel pour les étudiants de L3 MIAGE.

## **2. Objectifs du projet**

### Objectif principal :
Développer une application web permettant de créer, participer et évaluer des quiz dans un environnement interactif et sécurisé.

### Objectifs secondaires :
- Offrir aux utilisateurs (étudiants et enseignants) une interface simple et intuitive.
- Implémenter un système de gestion des quiz, des questions et des résultats.
- Assurer la possibilité de personnaliser les quiz (création, modification, suppression).
- Utiliser une base de données pour la gestion des informations utilisateurs et des quiz.

## **3. Exigences fonctionnelles**

### 3.1 **Fonctionnalités pour les étudiants :**
- **Inscription et connexion :** Les étudiants doivent pouvoir créer un compte et se connecter à l'application.
- **Participation aux quiz :** Les étudiants peuvent choisir un quiz, répondre aux questions, et obtenir un score final.
- **Suivi des résultats :** Les étudiants peuvent consulter leur historique de quiz, avec les résultats détaillés.
  
### 3.2 **Fonctionnalités pour les enseignants :**
- **Création de quiz :** Les enseignants peuvent créer de nouveaux quiz en choisissant des questions dans une base de données.
- **Gestion des quiz :** Les enseignants peuvent modifier, supprimer ou ajouter des questions à un quiz.
- **Analyse des résultats :** Les enseignants peuvent consulter les résultats des étudiants pour chaque quiz.

### 3.3 **Fonctionnalités administratives :**.
- **Gestion de la base de données :** Assurer la mise à jour et la sauvegarde des quiz, questions et résultats dans la base de données.

## **4. Exigences techniques**

### 4.0 **Aspect projet**

- Utilisation obligatoire d'un git pour le code.
- Expression du besoin via un document "User Story".
- Réaliser une documentation complète.


### 4.1 **Technologies utilisées :**
- **Frontend :** React.js, HTML5, CSS3
- **Backend :** Java, Spring Boot
- **Base de données :** MySQL, PHPMyAdmin pour la gestion de la base de données.
- **Gestion de version :** Git, GitHub pour le contrôle de version et la collaboration.
- **Outils de développement :** Visual Studio Code, IntelliJ IDEA pour le développement.

### 4.2 **Architecture :**
L'application suivra une architecture **MVC (Modèle-Vue-Contrôleur)** pour assurer une séparation claire des responsabilités et une meilleure gestion du code :
- **Modèle (Model)** : Représente les données et la logique métier (gestion des quiz, des résultats, etc.).
- **Vue (View)** : L'interface utilisateur développée en React.
- **Contrôleur (Controller)** : La logique de contrôle qui gère les interactions entre le modèle et la vue.

### 4.3 **Sécurité :**
- **Authentification et autorisation :** Implémentation de la gestion des utilisateurs avec des niveaux d'accès différenciés (étudiants, enseignants, administrateurs).
- **Protection contre les attaques :** Mise en place de mesures pour protéger contre les injections SQL, les attaques CSRF, etc.

## **5. Planning du projet**

Le projet sera réalisé en plusieurs phases :

1. **Phase 1 : Analyse et conception (2 semaines)**  
   - Analyse des besoins  
   - Conception de l'architecture (modèle de données, diagrammes de cas d'utilisation, etc.)

2. **Phase 2 : Développement (5 semaines)**  
   - Développement du frontend (React)  
   - Développement du backend (Spring Boot, API RESTful)  
   - Mise en place de la base de données

3. **Phase 3 : Tests et déploiement (2 semaines)**  
   - Tests unitaires et tests d'intégration  
   - Déploiement sur un serveur de test  
   - Correction des erreurs et mise en production

## **6. Gestion du projet**

- **Jira** : Suivi de l'avancement du projet et gestion des tâches.
- **Slack** : Communication en équipe.
- **GitHub** : Hébergement du code source et suivi des versions.



## **7. Suivi et évaluation**

Le projet sera suivi via les outils de gestion (Jira et GitHub), avec des réunions hebdomadaires pour assurer la bonne avancée du projet. Des tests seront effectués sur chaque fonctionnalité au fur et à mesure de leur développement pour garantir la qualité et la robustesse de l'application.

## **8. Conclusion**

Le projet **IQnite** vise à répondre à un besoin réel en matière d'évaluation et de révision pour les étudiants tout en offrant aux enseignants une solution flexible pour créer et gérer des quiz. Grâce à une architecture solide et l'utilisation de technologies modernes, ce projet permettra d'améliorer l'apprentissage en ligne de manière interactive et efficace.
