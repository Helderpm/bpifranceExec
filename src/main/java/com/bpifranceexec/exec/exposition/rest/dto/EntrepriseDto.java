package com.bpifranceexec.exec.exposition.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EntrepriseDto(
        @JsonProperty("entrepriseId")
        Long entrepriseDtoId,
        @JsonProperty("entrepriseNom")
        String entrepriseDtoNom,
        @JsonProperty("entrepriseSiret")
        String entrepriseDtoSiret) {
}