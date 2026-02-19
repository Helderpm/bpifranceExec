package com.bpifranceexec.exec.application;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.domaine.port.out.BeneficiaireRepositoryPort;
import com.bpifranceexec.exec.domaine.exception.EntrepriseInexistanteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GestionBeneficiaireServiceTest {

    @InjectMocks
    private GestionBeneficiaireService gestionBeneficiaireService;
    
    @Mock
    private BeneficiaireRepositoryPort beneficiaireRepositoryPort;
    
    private Entreprise sampleEntreprise;
    private PersonnePhysique samplePersonnePhysique;
    private Beneficiaire sampleBeneficiaire;
    
    @BeforeEach
    void setUp() {
        // Create sample immutable records for testing
        sampleEntreprise = new Entreprise(100L, "Sample Company", "12345678901234");
        samplePersonnePhysique = new PersonnePhysique(101L, "Doe", "John");
        sampleBeneficiaire = new Beneficiaire(102L, sampleEntreprise, samplePersonnePhysique, null, 50);
    }
    
    @Test
    void testCreerEntreprise_shouldReturnOptionalOfEntreprise() {
    
        when(beneficiaireRepositoryPort.saveEntreprise(any(Entreprise.class)))
                .thenReturn(sampleEntreprise);
        
    
        Optional<Entreprise> result = gestionBeneficiaireService.creerEntreprise(sampleEntreprise);
        
    
        assertTrue(result.isPresent());
        assertEquals(sampleEntreprise.id(), result.get().id());
        assertEquals(sampleEntreprise.nom(), result.get().nom());
    }
    
    @Test
    void testCreerPersonnePhysique_shouldReturnOptionalOfPersonnePhysique() {
    
        when(beneficiaireRepositoryPort.savePersonnePhysique(any(PersonnePhysique.class)))
                .thenReturn(samplePersonnePhysique);
        
        Optional<PersonnePhysique> result = gestionBeneficiaireService.creerPersonnePhysique(samplePersonnePhysique);
        
        assertTrue(result.isPresent());
        assertEquals(samplePersonnePhysique.id(), result.get().id());
        assertEquals(samplePersonnePhysique.nom(), result.get().nom());
    }
    
    @Test
    void testAjouterBeneficiaire_shouldReturnOptionalOfBeneficiaire() {
        when(beneficiaireRepositoryPort.existsPersonnePhysiqueById(101L)).thenReturn(true);
        when(beneficiaireRepositoryPort.existsEntrepriseById(100L)).thenReturn(true); // entreprise mère existe
        when(beneficiaireRepositoryPort.saveBeneficiaire(any(Beneficiaire.class)))
                .thenReturn(sampleBeneficiaire);
    
        Optional<Beneficiaire> result = gestionBeneficiaireService.ajouterBeneficiaire(sampleBeneficiaire);
    
        assertTrue(result.isPresent());
        assertEquals(sampleBeneficiaire.id(), result.get().id());
        assertEquals(sampleBeneficiaire.pourcentageDetention(), result.get().pourcentageDetention());
    }
    
    @Test
    void testRecupererBeneficiaires_all_shouldReturnAllBeneficiaries() {
    
        Beneficiaire beneficiaire1 = new Beneficiaire(103L, sampleEntreprise, samplePersonnePhysique, null, 10);
        Beneficiaire beneficiaire2 = new Beneficiaire(104L, sampleEntreprise, null, new Entreprise(111L, "Child Co", "SIRET test 33"), 20);
        List<Beneficiaire> allBeneficiaires = List.of(beneficiaire1, beneficiaire2);
    
        when(beneficiaireRepositoryPort.findByEntrepriseMereId(any(Long.class)))
                .thenReturn(allBeneficiaires);
    
        List<Beneficiaire> result = gestionBeneficiaireService.recupererBeneficiaires(123L, "all");
    
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(allBeneficiaires, result);
    }
    
    @Test
    void testRecupererBeneficiaires_personnesPhysiques_shouldReturnOnlyPersonnesPhysiques() {
    
        Beneficiaire beneficiaire1 = new Beneficiaire(105L, sampleEntreprise, samplePersonnePhysique, null, 10);
        Beneficiaire beneficiaire2 = new Beneficiaire(106L, sampleEntreprise, null, new Entreprise(123L, "Child Co", null), 20);
        List<Beneficiaire> allBeneficiaires = List.of(beneficiaire1, beneficiaire2);
        
        when(beneficiaireRepositoryPort.findByEntrepriseMereId(any(Long.class)))
                .thenReturn(allBeneficiaires);
    
        List<Beneficiaire> result = gestionBeneficiaireService.recupererBeneficiaires(123L, "personnes_physiques");
    
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(beneficiaire1.id(), result.getFirst().id());
    }
    
    @Test
    void testRecupererBeneficiaires_beneficiairesEffectifs_shouldReturnOnlyEffectiveBeneficiaries() {
        // Arrange
        Beneficiaire beneficiaire1 = new Beneficiaire(113L, sampleEntreprise, samplePersonnePhysique, null, 40);
        Beneficiaire beneficiaire2 = new Beneficiaire(113L, sampleEntreprise, samplePersonnePhysique, null, 20);
        Beneficiaire beneficiaire3 = new Beneficiaire(113L, sampleEntreprise, null, new Entreprise(213L, "Child Co", null), 50);
        List<Beneficiaire> allBeneficiaires = List.of(beneficiaire1, beneficiaire2, beneficiaire3);
    
        when(beneficiaireRepositoryPort.findByEntrepriseMereId(any(Long.class)))
                .thenReturn(allBeneficiaires);
    
        List<Beneficiaire> result = gestionBeneficiaireService.recupererBeneficiaires(123L, "beneficiaires_effectifs");
    
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(beneficiaire1.id(), result.getFirst().id());
        assertEquals(40, result.getFirst().pourcentageDetention());
    }
    
    @Test
    void testAjouterBeneficiaire_shouldThrowExceptionWhenEntrepriseMereNotExists() {
        // Create beneficiaire with non-existent entreprise mère
        Entreprise nonExistentEntrepriseMere = new Entreprise(888L, "Non Existent Mere", "88888888888888");
        Beneficiaire beneficiaireWithNonExistentMere = new Beneficiaire(104L, nonExistentEntrepriseMere, samplePersonnePhysique, null, 30);
        
        when(beneficiaireRepositoryPort.existsPersonnePhysiqueById(101L)).thenReturn(true);
        when(beneficiaireRepositoryPort.existsEntrepriseById(888L)).thenReturn(false); // entreprise mère n'existe pas
        
        // Should throw EntrepriseInexistanteException
        assertThrows(EntrepriseInexistanteException.class, () -> {
            gestionBeneficiaireService.ajouterBeneficiaire(beneficiaireWithNonExistentMere);
        });
    }
    
    @Test
    void testAjouterBeneficiaire_shouldThrowExceptionWhenEntrepriseFilleNotExists() {
        // Create beneficiaire with non-existent entreprise fille
        Entreprise nonExistentEntrepriseFille = new Entreprise(999L, "Non Existent", "99999999999999");
        Beneficiaire beneficiaireWithNonExistentEntreprise = new Beneficiaire(103L, sampleEntreprise, samplePersonnePhysique, nonExistentEntrepriseFille, 25);
        
        when(beneficiaireRepositoryPort.existsPersonnePhysiqueById(101L)).thenReturn(true);
        when(beneficiaireRepositoryPort.existsEntrepriseById(100L)).thenReturn(true); // entreprise mère existe
        when(beneficiaireRepositoryPort.existsEntrepriseById(999L)).thenReturn(false); // entreprise fille n'existe pas
        
        // Should throw EntrepriseInexistanteException
        assertThrows(EntrepriseInexistanteException.class, () -> {
            gestionBeneficiaireService.ajouterBeneficiaire(beneficiaireWithNonExistentEntreprise);
        });
    }
}
