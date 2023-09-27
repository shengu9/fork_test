package org.spring.securityOauth2_ex1.entity;

import lombok.*;
import org.spring.securityOauth2_ex1.constrant.Role;
import org.spring.securityOauth2_ex1.dto.MemberDto;
import org.spring.securityOauth2_ex1.utils.BaseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_member08")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;   // username

    @Column(nullable = false)
    private String pw;  // password

    @Enumerated(EnumType.STRING)
    private Role role;      // roles

    @Column(nullable = false)
    private String nickName;

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY,
                        cascade = CascadeType.REMOVE)   // 작성자를 삭제하면 게시글 모두 삭제
    private List<BoardEntity> memberEntityList = new ArrayList<>();

    public static MemberEntity toMemberEntity(MemberDto memberDto, PasswordEncoder passwordEncoder) {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPw(passwordEncoder.encode(memberDto.getPw()));
        memberEntity.setNickName(memberDto.getNicKName());
        memberEntity.setRole(Role.MEMBER);
        return memberEntity;

    }
}



