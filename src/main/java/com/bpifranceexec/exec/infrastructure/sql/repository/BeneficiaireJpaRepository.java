package com.bpifranceexec.exec.infrastructure.sql.repository;

import com.bpifranceexec.exec.infrastructure.sql.dao.BeneficiaireJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaireJpaRepository extends JpaRepository<BeneficiaireJpaEntity, Long> {
    
    List<BeneficiaireJpaEntity> findByEntrepriseMereId(Long entrepriseMereId);
}
