package com.bpifranceexec.exec.exposition.rest.mapper;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {EntrepriseDtoMapper.class, PersonnePhysiqueDtoMapper.class})
public interface BeneficiaireDtoMapper {

// Corrected toDto method
@Mapping(source = "id", target = "beneficiaireDtoId")
@Mapping(source = "entrepriseMere", target = "entrepriseMereDto")
@Mapping(source = "personnePhysique", target = "personnePhysiqueDto")
@Mapping(source = "entrepriseFille", target = "entrepriseFilleDto")
@Mapping(source = "pourcentageDetention", target = "pourcentageDetentionDto")
BeneficiaireDto toDto(Beneficiaire domain);

@Mapping(source = "beneficiaireDtoId", target = "id")
@Mapping(source = "entrepriseMereDto", target = "entrepriseMere")
@Mapping(source = "personnePhysiqueDto", target = "personnePhysique")
@Mapping(source = "entrepriseFilleDto", target = "entrepriseFille")
@Mapping(source = "pourcentageDetentionDto", target = "pourcentageDetention")
Beneficiaire toDomain(BeneficiaireDto dto);
}
