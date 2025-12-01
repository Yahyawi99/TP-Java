-- ===========================
-- Création de la Base
-- ===========================
CREATE DATABASE IF NOT EXISTS bibliotheque;
USE bibliotheque;

-- ===========================
-- Table des utilisateurs
-- ===========================
CREATE TABLE utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100)  NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    dateInscription DATE NOT NULL
);

-- ===========================
-- Table des documents
-- ===========================
CREATE TABLE documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL, -- LIVRE / MAGAZINE / DVD
    disponible BOOLEAN NOT NULL DEFAULT TRUE
    anneePublication DATE NOT NULL,

);

-- ===========================
-- Table des emprunts
-- ===========================
CREATE TABLE emprunts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    utilisateur_id BIGINT NOT NULL,
    document_id BIGINT NOT NULL,

    date_emprunt DATE NOT NULL,
    date_retour_prevue DATE NOT NULL,
    date_retour_effective DATE NULL,

    retourne BOOLEAN NOT NULL DEFAULT FALSE,

    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    FOREIGN KEY (document_id) REFERENCES documents(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- LIVRES
CREATE TABLE livres (
    id BIGINT PRIMARY KEY,
    auteur VARCHAR(100) NOT NULL,
    nb_pages INT NOT NULL,
    FOREIGN KEY (id) REFERENCES documents(id)
);

-- MAGAZINES
CREATE TABLE magazines (
    id BIGINT PRIMARY KEY,
    numero INT NOT NULL,
    FOREIGN KEY (id) REFERENCES documents(id)
);

-- DVD
CREATE TABLE dvds (
    id BIGINT PRIMARY KEY,
    duree INT NOT NULL,
    FOREIGN KEY (id) REFERENCES documents(id)
);
