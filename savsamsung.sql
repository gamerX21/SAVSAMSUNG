-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  jeu. 03 sep. 2020 à 01:45
-- Version du serveur :  10.1.36-MariaDB
-- Version de PHP :  7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `savsamsung`
--

-- --------------------------------------------------------

--
-- Structure de la table `articles`
--

CREATE TABLE `articles` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) COLLATE utf16_bin NOT NULL,
  `type` varchar(255) COLLATE utf16_bin NOT NULL,
  `description` varchar(255) COLLATE utf16_bin NOT NULL,
  `img` varchar(255) COLLATE utf16_bin NOT NULL,
  `titre` varchar(255) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `articles` (`id`, `nom`, `type`, `description`, `img`, `titre`) VALUES
(1, 'Comment nettoyer mon réfrigérateur? ', 'Nettoyage', 'prenez ces astuces pour le nettoyage', 'support1.jpg', 'Grace au support technique pratique,\r\non vous offre quelque étapes a suivre');

-- --------------------------------------------------------

--
-- Structure de la table `astuces`
--

CREATE TABLE `astuces` (
  `id` int(255) NOT NULL,
  `article_id` int(255) NOT NULL,
  `description` varchar(255) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `astuces`
--

INSERT INTO `astuces` (`id`, `article_id`, `description`) VALUES
(1, 1, 'avant de commencer a nettoyer votre réfrigérateur,débranchez le'),
(2, 1, 'sortez vos aliments du réfrigérateur et rangez-les dans un endroit frais '),
(3, 1, 'nettoyez votre réfrigérateur avec un chiffon doux '),
(4, 1, 'nettoyez le joint de porte a l\'eau claire uniquement'),
(5, 1, 'une fois que le nettoyage est terminée,\r\nrebranchez votre appareil');

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

CREATE TABLE `commandes` (
  `id` int(255) NOT NULL,
  `ref` int(255) NOT NULL,
  `user_id` int(255) NOT NULL,
  `produit_id` int(255) NOT NULL,
  `quantité` int(255) NOT NULL,
  `dataA` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `commandes`
--

INSERT INTO `commandes` (`id`, `ref`, `user_id`, `produit_id`, `quantité`, `dataA`) VALUES
(1, 5989, 1, 4, 0, '2020-09-02'),
(2, 4104, 1, 2, 2, '2020-09-02');

-- --------------------------------------------------------

--
-- Structure de la table `helps`
--

CREATE TABLE `helps` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) COLLATE utf16_bin NOT NULL,
  `description` varchar(255) COLLATE utf16_bin NOT NULL,
  `img` varchar(255) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `helps`
--

INSERT INTO `helps` (`id`, `nom`, `description`, `img`) VALUES
(1, 'Lave-Linge', 'L\'entretien de votre lave-linge,avec toute l\'expertise de nous techniciens Bosch.', 'washingmachine.png'),
(6, 'Sèche-linge', 'Une aide professionnelle et accessible pour votre sèche-linge.', 'dryer.png'),
(7, 'Lave-vaisselle', 'Nos conseils et astuces pour une vaisselle impeccable pendant toute toute la durée de vie de votre appareil.', 'dishwasher.png'),
(8, 'Réfrigérateurs et congélateurs', 'Des idées novatrices.pour une fraîcheur longue durée en profitant d\'économie d\'énergie.', 'fridge.png');

-- --------------------------------------------------------

--
-- Structure de la table `produits`
--

CREATE TABLE `produits` (
  `id` int(255) NOT NULL,
  `nom` varchar(255) COLLATE utf16_bin NOT NULL,
  `description` varchar(255) COLLATE utf16_bin NOT NULL,
  `longueur` int(255) NOT NULL,
  `largeur` int(255) NOT NULL,
  `type` varchar(255) COLLATE utf16_bin NOT NULL,
  `prix` int(255) NOT NULL,
  `stock` int(11) NOT NULL,
  `img` varchar(255) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `produits`
--

INSERT INTO `produits` (`id`, `nom`, `description`, `longueur`, `largeur`, `type`, `prix`, `stock`, `img`) VALUES
(1, 'Bac 1l Avec Egouttoir', 'Bac avec égouttoir pour réaliser des fromages blanc et des yaourts filtrés\r\nColoris :Blanc\r\nNombre de pièces : 1\r\nCapacité : 1,00 L\r\nUsage : Préparations sucrées\r\nIdéal pour un usage familial\r\nPratique et simple à utiliser', 52, 25, 'piéces', 80, 5, 'boxref.jpg'),
(2, 'Soudal Éliminateur de colle ', 'Marque Soudal   \r\n400 ml\r\nÉliminateur d\'adhésifs\r\nMélange de solvants transparent ultra fort\r\nÉlimine la colle en vaporisant le produit sur la surface à traiter. \r\nQualité professionnelle ', 32, 20, 'nettoyage', 18, 1, 'proN.jpg'),
(3, 'Metaltex Corbeille à suspendre', 'Revêtement en plastique. \r\nIdéal pour:\r\nLa cuisine et la salle de bain\r\nGarantie 3 ans', 0, 0, 'accessoires', 23, 0, 'acce.jpg'),
(4, 'Thermostat Atea A13', 'Modele\r\nTHERMOSTAT ATEA A13 - 0705\r\nMarque de l\'appareil\r\nWHIRLPOOL, BAUKNECHT, IGNIS, LADEN', 0, 0, 'piéces', 110, 5, 'ter.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `repairdemand`
--

CREATE TABLE `repairdemand` (
  `id` int(255) NOT NULL,
  `user_id` int(255) NOT NULL,
  `type` varchar(255) COLLATE utf16_bin NOT NULL,
  `phone` int(255) NOT NULL,
  `ville` varchar(255) COLLATE utf16_bin NOT NULL,
  `adresse` varchar(255) COLLATE utf16_bin NOT NULL,
  `generated_num` int(255) NOT NULL,
  `status` varchar(255) COLLATE utf16_bin NOT NULL,
  `dataA` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `repairdemand`
--

INSERT INTO `repairdemand` (`id`, `user_id`, `type`, `phone`, `ville`, `adresse`, `generated_num`, `status`, `dataA`) VALUES
(1, 1, 'Lave-Séche', 7, 'ben arous', 'sdf', 3889, 'en cours', '2020-08-31');

-- --------------------------------------------------------

--
-- Structure de la table `services`
--

CREATE TABLE `services` (
  `id` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf16_bin NOT NULL,
  `description` varchar(255) COLLATE utf16_bin NOT NULL,
  `img` varchar(255) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `services`
--

INSERT INTO `services` (`id`, `type`, `description`, `img`) VALUES
(1, 'Réparation et problémes', 'Demande de réparation si vous rencontrez un problème avec votre électroménager ', 'wrench.png'),
(2, 'aide et conseils', 'pour résoudre rapidement par vous-même les problèmes ', 'question.png'),
(3, 'Nettoyage', 'conseils de dépannage (bruits et odeurs)', 'cleaning.png');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(255) NOT NULL,
  `unique_id` varchar(255) COLLATE utf16_bin NOT NULL,
  `fullname` varchar(255) COLLATE utf16_bin NOT NULL,
  `email` varchar(255) COLLATE utf16_bin NOT NULL,
  `password` varchar(255) COLLATE utf16_bin NOT NULL,
  `salt` varchar(255) COLLATE utf16_bin NOT NULL,
  `adresse` varchar(255) COLLATE utf16_bin NOT NULL,
  `tel` int(255) NOT NULL,
  `created_at` date NOT NULL,
  `updated_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `unique_id`, `fullname`, `email`, `password`, `salt`, `adresse`, `tel`, `created_at`, `updated_at`) VALUES
(1, '58590ddc-3ff4-40b1-9a6a-5d82cd76caa3', 'kraoua houssem', 'houssem.kr', '33fcbcc9c838c52858945dcf4a3f2fd009b8d364e8d5e6c4ac390413f55f9017d42a8a3c0f60fc7255c49f0a1b0e69e3dfc0819ee04a90bc1b05166729de8873', '3b96e55d6343f954', 'zaghouan bassatine', 55437408, '2020-08-31', '2020-08-31');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `astuces`
--
ALTER TABLE `astuces`
  ADD PRIMARY KEY (`id`),
  ADD KEY `article_id` (`article_id`);

--
-- Index pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `produit_id` (`produit_id`);

--
-- Index pour la table `helps`
--
ALTER TABLE `helps`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `produits`
--
ALTER TABLE `produits`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `repairdemand`
--
ALTER TABLE `repairdemand`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `articles`
--
ALTER TABLE `articles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `astuces`
--
ALTER TABLE `astuces`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `commandes`
--
ALTER TABLE `commandes`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `helps`
--
ALTER TABLE `helps`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `produits`
--
ALTER TABLE `produits`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `repairdemand`
--
ALTER TABLE `repairdemand`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `services`
--
ALTER TABLE `services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `astuces`
--
ALTER TABLE `astuces`
  ADD CONSTRAINT `astuces_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`);

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `commandes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `commandes_ibfk_2` FOREIGN KEY (`produit_id`) REFERENCES `produits` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
