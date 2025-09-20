package com.bpifranceexec.exec.infrastructure.sql.mapper;

import com.bpifranceexec.exec.domaine.model.Entreprise;
import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntrepriseMapper {
    EntrepriseMapper INSTANCE = Mappers.getMapper(EntrepriseMapper.class);
    Entreprise toDomain(EntrepriseJpaEntity entity);
    EntrepriseJpaEntity toEntity(Entreprise domain);
}
