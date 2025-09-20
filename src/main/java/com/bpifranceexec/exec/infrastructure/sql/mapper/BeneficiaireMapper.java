package com.bpifranceexec.exec.infrastructure.sql.mapper;

import com.bpifranceexec.exec.domaine.model.Beneficiaire;
import com.bpifranceexec.exec.infrastructure.sql.dao.BeneficiaireJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeneficiaireMapper {

    Beneficiaire toDomain(BeneficiaireJpaEntity entity);
    
    BeneficiaireJpaEntity toEntity(Beneficiaire domain);
}
