package com.bpifranceexec.exec.exposition.rest.dto;

import java.util.UUID;

public record EntrepriseDto(
        Long id,
        String nom,
        String siret) {
}