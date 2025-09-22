package com.bpifranceexec.exec.infrastructure.sql.repository;

import com.bpifranceexec.exec.infrastructure.sql.dao.EntrepriseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrepriseJpaRepository extends JpaRepository<EntrepriseJpaEntity, Long> {

    List< EntrepriseJpaEntity> findByNom(String name);
}
