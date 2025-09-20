package com.bpifranceexec.exec.exposition.rest.mapper;

import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.exposition.rest.dto.EntrepriseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntrepriseDtoMapper {
    Entreprise toDomain(EntrepriseDto dto);
    EntrepriseDto toDto(Entreprise domain);
}
