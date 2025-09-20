package com.bpifranceexec.exec.exposition.rest.mapper;

import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.exposition.rest.dto.PersonnePhysiqueDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonnePhysiqueDtoMapper {
    PersonnePhysique toDomain(PersonnePhysiqueDto dto);
    PersonnePhysiqueDto toDto(PersonnePhysique domain);
}
