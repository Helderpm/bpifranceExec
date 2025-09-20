package com.bpifranceexec.exec.domaine.model;


public record Beneficiaire(
        Long id,
        Entreprise entrepriseMere,
        PersonnePhysique personnePhysique,
        Entreprise entrepriseFille,
        int pourcentageDetention) {
}
