-- src/test/resources/db/data.sql

-- Cleanup tables to ensure a clean state before each test
DELETE FROM beneficiaire;
DELETE FROM personne_physique;
DELETE FROM entreprise;

-- Insert a parent company
INSERT INTO entreprise (entreprise_id, nom, siret) VALUES (1, 'TestCorp', '12345678901234');

-- Insert another company that will be a child company
INSERT INTO entreprise (entreprise_id, nom, siret) VALUES (2, 'ChildCorp', '98765432109876');

-- Insert a physical person
INSERT INTO personne_physique (personne_id, nom, prenom) VALUES (3, 'Doe', 'John');

-- Insert beneficiaries
INSERT INTO beneficiaire (beneficiaire_id, entreprise_mere_entreprise_id, personne_physique_personne_id, pourcentage_detention) VALUES (101, 1, 3, 50);
INSERT INTO beneficiaire (beneficiaire_id, entreprise_mere_entreprise_id, entreprise_fille_entreprise_id, pourcentage_detention) VALUES (102, 1, 2, 20);

-- Insert a company with no beneficiaries to test the 204 status
INSERT INTO entreprise (entreprise_id, nom, siret) VALUES (99, 'EmptyCorp', '11111111111111');