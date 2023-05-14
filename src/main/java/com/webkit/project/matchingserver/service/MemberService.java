package com.webkit.project.matchingserver.service;


import com.webkit.project.matchingserver.domain.MemberEntity;
import com.webkit.project.matchingserver.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void validate(final MemberEntity entity) {
        if (entity == null) {
            log.warn("entity가 null입니다.");
            throw new RuntimeException("entity가 null입니다.");
        }
    }

//    public List<MemberEntity> create(final MemberEntity entity) {
//        validate(entity);
//
//
//        if (entity.getEmail() == null) {
//            throw new RuntimeException("Invalid arguments");
//        }
//
//        final String email = entity.getEmail();
//        if (repository.existsByEmail(email)) {
//            log.warn("Email already exists {}", email);
//            throw new RuntimeException("Email already exists");
//        }
//
//        repository.save(entity);
//        log.info("Entity id: {} is saved.", entity.getEmail());
//
//
//        return repository.findByEmail(email);
//    }

    public MemberEntity create(final MemberEntity memberEntity) {
        if (memberEntity == null || memberEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String email = memberEntity.getEmail();
        if (memberRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return memberRepository.save(memberEntity);
    }

    public MemberEntity getByCredentials(String email, String password, PasswordEncoder passwordEncoder) {
        final List<MemberEntity> originalUser = memberRepository.findByEmail(email);

        if(originalUser != null && passwordEncoder.matches(password, originalUser.get(0).getPassword())){
            return originalUser.get(0);
        }
        return null;
    }

    public List<MemberEntity> retrieve(final String email) {
        return memberRepository.findByEmail(email == null ? "" : email);
    }

    public List<MemberEntity> deleteOne(final MemberEntity entity) {
        validate(entity);
        List<MemberEntity> del_one = memberRepository.findByEmail(entity.getEmail());
        try {
            memberRepository.deleteById(del_one.get(0).getId());
        } catch (Exception e) {
            throw new RuntimeException(entity.getEmail() + " 삭제 시 에러 발생 >>> " + e.getMessage());
        }
        return retrieve(entity.getEmail());
    }


    public List<MemberEntity> getDataByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }

    public List<MemberEntity> updateMemberInfo(MemberEntity entity) {

        final Optional<MemberEntity> original = memberRepository.findById(entity.getId());

        original.ifPresent(member -> {
            member.setTech(entity.getTech());
            member.setPosition(entity.getPosition());
            member.setName(entity.getName());

            memberRepository.save(member);
        });

        return retrieve(entity.getEmail());

//        if (memberRepository.existsByEmail(entity.getEmail())) {
//            memberRepository.save(entity);
//        } else {
//            throw new RuntimeException("Unknown USER");
//        }
//
//        return memberRepository.findByEmail(entity.getEmail());
    }

    public Optional<MemberEntity> getDataById(String id) {
        return memberRepository.findById(id);
    }
}
