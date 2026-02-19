package com.bpifranceexec.exec.exposition.rest.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record  BeneficiaireDto (
        @JsonProperty("beneficiaireId")
        Long beneficiaireDtoId,
        @JsonProperty("entrepriseMere")
        EntrepriseDto entrepriseMereDto,
        @JsonProperty("personnePhysique")
        PersonnePhysiqueDto personnePhysiqueDto,
        @JsonProperty("entrepriseFille")
        EntrepriseDto entrepriseFilleDto,
        @JsonProperty("pourcentage")
        int pourcentageDetentionDto) {
}
