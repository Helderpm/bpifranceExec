package com.bpifranceexec.exec.exposition.rest.mapper;

import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntrepriseDtoMapper {

    @Mapping(source = "entrepriseDtoId", target = "id")
    @Mapping(source = "entrepriseDtoNom", target = "nom")
    @Mapping(source = "entrepriseDtoSiret", target = "siret")
    Entreprise toDomain(EntrepriseDto dto);
    
    @Mapping(source = "id", target = "entrepriseDtoId")
    @Mapping(source = "nom", target = "entrepriseDtoNom")
    @Mapping(source = "siret", target = "entrepriseDtoSiret")
    EntrepriseDto toDto(Entreprise domain);
}
