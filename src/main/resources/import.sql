INSERT INTO role (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');
INSERT INTO `user` (`dtype`, `id`, `password`, `username`, `date_naissance`, `date_retrait`, `nom`, `prenom`, `adresse_id`, `lieu_naissance_id`) VALUES ('Admin', 1, '$2a$12$0b3Jfor2/iEdB88pkiIk3e/DndfVAZlTFuYbp.4ct16lhSLxA1Pbq', 'baedaar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` (`dtype`, `id`, `password`, `username`, `date_naissance`, `date_retrait`, `nom`, `prenom`, `adresse_id`, `lieu_naissance_id`) VALUES ('Employe', 2, '$2a$12$0b3Jfor2/iEdB88pkiIk3e/DndfVAZlTFuYbp.4ct16lhSLxA1Pbq', 'toto', NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `lieu_naissance` (`id`, `pays_naissance`, `ville_naissance`) VALUES (1, 'France', 'Paris'),(2, 'États-Unis', 'New York');

INSERT INTO `adresse` (`id`, `numero_rue`, `rue`, `ville`, `employe_id`) VALUES (1, 12, 'Rue de la Paix', 'Paris', 1),(2, 34, 'Broadway', 'New York', 2);

INSERT INTO `user` (`dtype`, `id`, `password`, `username`, `date_naissance`, `date_retrait`, `nom`, `prenom`, `adresse_id`, `lieu_naissance_id`) VALUES ('Admin', 3, '$2a$12$0b3Jfor2/iEdB88pkiIk3e/DndfVAZlTFuYbp.4ct16lhSLxA1Pbq', 'admin', '1980-01-01', NULL, 'Doe', 'John', 1, 1), ('Employe', 4, '$2a$12$0b3Jfor2/iEdB88pkiIk3e/DndfVAZlTFuYbp.4ct16lhSLxA1Pbq', 'user', '1990-01-01', NULL, 'Smith', 'Jane', 2, 2);

INSERT INTO `user_roles` (`user_id`, `roles_id`) VALUES (1, 1), (2, 2);

INSERT INTO `user_roles` (`user_id`, `roles_id`) VALUES (3, 1), (4, 2);

INSERT INTO `categorie_document` (`id`, `nom_categorie`) VALUES (1, 'Financier'), (2, 'Technique');

INSERT INTO `demandes` (`id`, `description`, `objet_demande`) VALUES (1, 'Facture', 'Congé estival'), (2, 'Fiche de Paie', 'Formation SQL');
