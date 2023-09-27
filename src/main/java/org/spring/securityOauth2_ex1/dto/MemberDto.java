package org.spring.securityOauth2_ex1.dto;

import lombok.*;
import org.spring.securityOauth2_ex1.constrant.Role;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;

    private String email;  // username

    private String pw;     //password

    private Role role;     // roles


    private String nicKName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
