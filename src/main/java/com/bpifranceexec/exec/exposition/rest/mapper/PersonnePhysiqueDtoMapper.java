package com.bpifranceexec.exec.exposition.rest.mapper;

import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonnePhysiqueDtoMapper {

    @Mapping(source = "personneDtoId", target = "id")
    @Mapping(source = "personneDtoNom", target = "nom")
    @Mapping(source = "personneDtoPrenom", target = "prenom")
    PersonnePhysique toDomain(PersonnePhysiqueDto dto);
    
    @Mapping(source = "id", target = "personneDtoId")
    @Mapping(source = "nom", target = "personneDtoNom")
    @Mapping(source = "prenom", target = "personneDtoPrenom")
    PersonnePhysiqueDto toDto(PersonnePhysique domain);
}
