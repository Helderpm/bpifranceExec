package com.bpifranceexec.exec.infrastructure.sql.mapper;

import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonnePhysiqueMapper {
    PersonnePhysique toDomain(PersonnePhysiqueJpaEntity entity);
    PersonnePhysiqueJpaEntity toEntity(PersonnePhysique domain);
}
