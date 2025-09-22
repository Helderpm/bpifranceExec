package com.bpifranceexec.exec.exposition.rest.dto;


public record  BeneficiaireDto (
        Long beneficiaireDtoId,
        EntrepriseDto entrepriseMereDtoId,
        PersonnePhysiqueDto personnePhysiqueDtoId,
        EntrepriseDto entrepriseFilleDtoId,
        int pourcentageDetentionDto) {
}
