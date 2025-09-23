package com.bpifranceexec.exec.exposition.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonnePhysiqueDto(
        @JsonProperty("personneId")
        Long personneDtoId,
        @JsonProperty("personneNom")
        String personneDtoNom,
        @JsonProperty("personnePrenom")
        String personneDtoPrenom) {
}
