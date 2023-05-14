package com.webkit.project.matchingserver.controller;

import com.webkit.project.matchingserver.domain.MemberEntity;
import com.webkit.project.matchingserver.dto.MemberDTO;
import com.webkit.project.matchingserver.dto.ResponseDTO;
import com.webkit.project.matchingserver.security.TokenProvider;
import com.webkit.project.matchingserver.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping("/auth")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO) {
        try {
            MemberEntity user = MemberEntity.builder()
                    .email(memberDTO.getEmail())
                    .password(passwordEncoder.encode(memberDTO.getPassword()))
                    .name(memberDTO.getName())
                    .tech(memberDTO.getTech())
                    .position(memberDTO.getPosition())
                    .score(0)
                    .build();

            MemberEntity registeredUser = memberService.create(user);
            MemberDTO responseUserDTO = MemberDTO.builder()
                    .email(registeredUser.getEmail())
                    .name(registeredUser.getName())
                    .tech(registeredUser.getTech())
                    .position(registeredUser.getPosition())
                    .id(registeredUser.getId())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);

        } catch (Exception e) {
            ResponseDTO<MemberDTO> response = ResponseDTO.<MemberDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody MemberDTO memberDTO) {
        MemberEntity member = memberService.getByCredentials(memberDTO.getEmail(), memberDTO.getPassword(), passwordEncoder);

        if (member != null) {
            final String token = tokenProvider.create(member);
            final MemberDTO responseUserDTO = MemberDTO.builder()
                    .email(member.getEmail())
                    .id(member.getId())
                    .name(member.getName())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO<MemberDTO> responseDTO = ResponseDTO.<MemberDTO>builder()
                    .error("Login Failed...").build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @GetMapping("/origin")
    public ResponseEntity<?> getMemberData(@RequestParam(value="email") String email) {
        try {
            List<MemberEntity> entities = memberService.getDataByEmail(email);

            List<MemberDTO> todos = entities.stream()
                    .map(MemberDTO::new).collect(Collectors.toList());
            ResponseDTO<MemberDTO> response = ResponseDTO
                    .<MemberDTO>builder().data(todos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<MemberDTO> res = ResponseDTO.<MemberDTO>builder().error("search list 에러: "+ e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }


    @GetMapping("/origintoast")
    public ResponseEntity<?> getMemberDataToast(@RequestParam(value="id") String id) {
        try {
            Optional<MemberEntity> entities = memberService.getDataById(id);

            List<MemberDTO> todos = entities.stream()
                    .map(MemberDTO::new).collect(Collectors.toList());
            ResponseDTO<MemberDTO> response = ResponseDTO
                    .<MemberDTO>builder().data(todos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<MemberDTO> res = ResponseDTO.<MemberDTO>builder().error("search list 에러: "+ e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }



    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateMemberInfo(@RequestBody MemberDTO dto) {
        try {
            MemberEntity entity = dto.dtoToEntity();

            List<MemberEntity> entities = memberService.updateMemberInfo(entity);

            List<MemberDTO> dtos = entities.stream().map(MemberDTO::new).collect(Collectors.toList());

            ResponseDTO<MemberDTO> response = ResponseDTO
                    .<MemberDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<MemberDTO> response = ResponseDTO
                    .<MemberDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @RequestMapping(value="/one",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOne(@RequestBody MemberDTO dto){
        try {

            MemberEntity entity = dto.dtoToEntity();
            List<MemberEntity> entities = memberService.deleteOne(entity);

            List<MemberDTO> members = entities.stream()
                    .map(MemberDTO::new).collect(Collectors.toList());
            ResponseDTO<MemberDTO> response = ResponseDTO
                    .<MemberDTO>builder().data(members).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println(dto);
            ResponseDTO<MemberDTO> res = ResponseDTO.<MemberDTO>builder().error("search list 에러: "+ e.getMessage()).build();
            return ResponseEntity.badRequest().body(res);
        }
    }
}



