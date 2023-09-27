package org.spring.securityOauth2_ex1.repository;

import org.spring.securityOauth2_ex1.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {


    Optional<MemberEntity> findByEmail(String email);
}
