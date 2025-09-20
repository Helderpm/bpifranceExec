package com.bpifranceexec.exec.infrastructure.sql.mapper;

import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntrepriseMapper {
    
    @Mapping(source = "entrepriseId", target = "id")
    Entreprise toDomain(EntrepriseJpaEntity entity);
    
    @Mapping(source = "id", target = "entrepriseId")
    EntrepriseJpaEntity toEntity(Entreprise domain);
}
