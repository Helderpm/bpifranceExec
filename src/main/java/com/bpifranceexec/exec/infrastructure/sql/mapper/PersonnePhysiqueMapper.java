package com.bpifranceexec.exec.infrastructure.sql.mapper;

import com.bpifranceexec.exec.domaine.model.PersonnePhysique;
import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonnePhysiqueMapper {
    
    @Mapping(source = "personneId", target = "id")
    PersonnePhysique toDomain(PersonnePhysiqueJpaEntity entity);
    
    @Mapping(source = "id", target = "personneId")
    PersonnePhysiqueJpaEntity toEntity(PersonnePhysique domain);
}
