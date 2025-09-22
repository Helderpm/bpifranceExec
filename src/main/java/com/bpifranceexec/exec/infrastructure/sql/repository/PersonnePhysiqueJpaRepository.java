package com.bpifranceexec.exec.infrastructure.sql.repository;

import com.bpifranceexec.exec.infrastructure.sql.dao.PersonnePhysiqueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnePhysiqueJpaRepository extends JpaRepository<PersonnePhysiqueJpaEntity, Long> {

    List<PersonnePhysiqueJpaEntity> findByNom(String name);
}