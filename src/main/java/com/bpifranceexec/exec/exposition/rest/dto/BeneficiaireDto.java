package com.bpifranceexec.exec.exposition.rest.dto;


public record  BeneficiaireDto (
        Long id,
        Long entrepriseMereId,
        Long personnePhysiqueId,
        Long entrepriseFilleId,
        int pourcentageDetention) {
}
