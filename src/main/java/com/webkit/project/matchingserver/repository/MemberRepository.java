package com.webkit.project.matchingserver.repository;

import com.webkit.project.matchingserver.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    Boolean existsByEmail(String email);

    List<MemberEntity> findByNameContaining(String name);

    List<MemberEntity> findByEmail(String email);


    MemberEntity findByEmailAndPassword(String email, String password);
}
