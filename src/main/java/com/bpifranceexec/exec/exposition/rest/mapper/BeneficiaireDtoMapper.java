package com.bpifranceexec.exec.exposition.rest.mapper;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EntrepriseDtoMapper.class, PersonnePhysiqueDtoMapper.class})
public interface BeneficiaireDtoMapper {

@Mapping(source = "id", target = "beneficiaireDtoId")
@Mapping(source = "entrepriseMere", target = "entrepriseMereDtoId")
@Mapping(source = "personnePhysique", target = "personnePhysiqueDtoId")
@Mapping(source = "entrepriseFille", target = "entrepriseFilleDtoId")
@Mapping(source = "pourcentageDetention", target = "pourcentageDetentionDto")
BeneficiaireDto toDto(Beneficiaire domain);

@Mapping(source = "beneficiaireDtoId", target = "id")
@Mapping(source = "entrepriseMereDtoId", target = "entrepriseMere")
@Mapping(source = "personnePhysiqueDtoId", target = "personnePhysique")
@Mapping(source = "entrepriseFilleDtoId", target = "entrepriseFille")
@Mapping(source = "pourcentageDetentionDto", target = "pourcentageDetention")
Beneficiaire toDomain(BeneficiaireDto dto);
}
