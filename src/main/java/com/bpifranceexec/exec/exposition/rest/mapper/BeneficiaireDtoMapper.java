package com.bpifranceexec.exec.exposition.rest.mapper;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.exposition.rest.dto.BeneficiaireDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeneficiaireDtoMapper {
    @Mapping(target = "entrepriseMereId", source = "entrepriseMere.id")
    @Mapping(target = "personnePhysiqueId", source = "personnePhysique.id")
    @Mapping(target = "entrepriseFilleId", source = "entrepriseFille.id")
    BeneficiaireDto toDto(Beneficiaire domain);

    @Mapping(target = "entrepriseMere.id", source = "entrepriseMereId")
    @Mapping(target = "personnePhysique.id", source = "personnePhysiqueId")
    @Mapping(target = "entrepriseFille.id", source = "entrepriseFilleId")
    Beneficiaire toDomain(BeneficiaireDto dto);
}
