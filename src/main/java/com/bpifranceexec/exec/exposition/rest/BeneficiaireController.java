package com.bpifranceexec.exec.exposition.rest;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.domaine.port.in.GestionBeneficiairePort;
import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import com.bpifranceexec.exec.exposition.rest.mapper.BeneficiaireDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.EntrepriseDtoMapper;
import com.bpifranceexec.exec.exposition.rest.mapper.PersonnePhysiqueDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag( name = "Gestion des Bénéficiaires",
        description = "Endpoints pour gérer les entreprises, personnes et bénéficiaires.")
@Slf4j
public class BeneficiaireController {

    @Autowired
    private EntrepriseDtoMapper entrepriseMapper;
    
    @Autowired
    private PersonnePhysiqueDtoMapper personneMapper;
    
    @Autowired
    private BeneficiaireDtoMapper beneficiaireMapper;

    @Autowired
    private GestionBeneficiairePort gestionBeneficiairePort;

    @PostMapping("/entreprise")
    @Operation(summary = "Ajouter une nouvelle entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entreprise créée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<EntrepriseDto> addEntreprise(@RequestBody EntrepriseDto entrepriseDto) {
        log.info("Received request to add a new entreprise: {}", entrepriseDto.entrepriseDtoNom());
        return gestionBeneficiairePort.creerEntreprise(entrepriseMapper.toDomain(entrepriseDto))
                .map(entrepriseMapper::toDto)
                .map(e -> {
                    log.info("Entreprise created successfully with ID: {}", e.entrepriseDtoId());
                    return new ResponseEntity<>(e, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    log.error("Failed to create entreprise.");
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
    
    @PostMapping("/personne")
    @Operation(summary = "Ajouter une nouvelle personne physique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Personne physique créée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<PersonnePhysiqueDto> addPersonne(@RequestBody PersonnePhysiqueDto personneDto) {
        log.info("Received request to add a new personne physique: {} {}", personneDto.personneDtoPrenom(), personneDto.personneDtoNom());
        return gestionBeneficiairePort.creerPersonnePhysique(personneMapper.toDomain(personneDto))
                .map(personneMapper::toDto)
                .map(p -> {
                    log.info("Personne physique created successfully with ID: {}", p.personneDtoId());
                    return new ResponseEntity<>(p, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    log.error("Failed to create personne physique.");
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
    
    @PostMapping("/beneficiaire")
    @Operation(summary = "Ajouter un nouveau bénéficiaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bénéficiaire créé avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<BeneficiaireDto> addBeneficiaire(@RequestBody BeneficiaireDto beneficiaireDto) {
        log.info("Received request to add a new beneficiaire with details: {}", beneficiaireDto);
        return gestionBeneficiairePort.ajouterBeneficiaire(beneficiaireMapper.toDomain(beneficiaireDto))
                .map(beneficiaireMapper::toDto)
                .map(b -> {
                    log.info("Bénéficiaire created successfully with ID: {}", b.beneficiaireDtoId());
                    return new ResponseEntity<>(b, HttpStatus.CREATED);
                })
                .orElseGet(() -> {
                    log.error("Failed to create bénéficiaire.");
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }
    
    @GetMapping("/entreprise/{entrepriseId}/beneficiaires")
    @Operation(summary = "Récupérer la liste des bénéficiaires d'une entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des bénéficiaires récupérée avec succès"),
            @ApiResponse(responseCode = "204", description = "Aucun bénéficiaire trouvé pour l'entreprise"),
            @ApiResponse(responseCode = "404", description = "L'entreprise n'existe pas")
    })
    public ResponseEntity<List<BeneficiaireDto>> getBeneficiaires(
            @PathVariable Long entrepriseId,
            @RequestParam(name = "type", required = false, defaultValue = "all") String type) {
        log.info("Received request to get beneficiaires for entrepriseId: {} with type: {}", entrepriseId, type);
        List<Beneficiaire> beneficiaires = gestionBeneficiairePort.recupererBeneficiaires(entrepriseId, type);
        
        if (beneficiaires.isEmpty()) {
            log.info("No beneficiaires found for entrepriseId: {}", entrepriseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<BeneficiaireDto> beneficiaireDtos = beneficiaires.stream()
                .map(beneficiaireMapper::toDto)
                .toList();
        
        log.info("Found {} beneficiaires for entrepriseId: {}", beneficiaireDtos.size(), entrepriseId);
        return new ResponseEntity<>(beneficiaireDtos, HttpStatus.OK);
    }
}