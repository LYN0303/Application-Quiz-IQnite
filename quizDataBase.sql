-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : jeu. 10 avr. 2025 à 20:47
-- Version du serveur : 8.0.31
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `quizz`
--

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

CREATE TABLE `question` (
  `id` bigint NOT NULL,
  `question` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `subject` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `question_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `question`
--

INSERT INTO `question` (`id`, `question`, `subject`, `question_type`) VALUES
(5, 'Quelle est la capitale de l\'Algérie', 'Géographie', 'single'),
(7, 'Quelle est la capitale de la France', 'Géographie', 'multiple'),
(8, 'quelle est ?', 'New', 'multiple');

-- --------------------------------------------------------

--
-- Structure de la table `question_choices`
--

CREATE TABLE `question_choices` (
  `question_id` bigint NOT NULL,
  `choices` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `question_choices`
--

INSERT INTO `question_choices` (`question_id`, `choices`) VALUES
(5, 'Alger'),
(5, 'Oran'),
(5, 'Tizi Ouzou'),
(5, 'Boumerdes'),
(7, 'Paris'),
(7, 'Marseille'),
(7, 'Lyon'),
(7, 'Paname'),

-- --------------------------------------------------------

--
-- Structure de la table `question_correct_answers`
--

CREATE TABLE `question_correct_answers` (
  `question_id` bigint NOT NULL,
  `correct_answers` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `question_correct_answers`
--

INSERT INTO `question_correct_answers` (`question_id`, `correct_answers`) VALUES
(5, 'Alger'),
(7, 'Paris');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `question_choices`
--
ALTER TABLE `question_choices`
  ADD KEY `FKifc0cyjdk3ijjhtju0fual7a6` (`question_id`);

--
-- Index pour la table `question_correct_answers`
--
ALTER TABLE `question_correct_answers`
  ADD KEY `FK3nr4qylvsx1obopacubbv012h` (`question_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `question`
--
ALTER TABLE `question`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `question_choices`
--
ALTER TABLE `question_choices`
  ADD CONSTRAINT `FKifc0cyjdk3ijjhtju0fual7a6` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

--
-- Contraintes pour la table `question_correct_answers`
--
ALTER TABLE `question_correct_answers`
  ADD CONSTRAINT `FK3nr4qylvsx1obopacubbv012h` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
