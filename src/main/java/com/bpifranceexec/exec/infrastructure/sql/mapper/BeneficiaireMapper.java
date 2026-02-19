package com.bpifranceexec.exec.infrastructure.sql.mapper;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.infrastructure.sql.dao.BeneficiaireJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeneficiaireMapper {

    @Mapping(source = "beneficiaireId", target = "id")
    Beneficiaire toDomain(BeneficiaireJpaEntity entity);

    @Mapping(source = "id", target = "beneficiaireId")
    BeneficiaireJpaEntity toEntity(Beneficiaire domain);
}
