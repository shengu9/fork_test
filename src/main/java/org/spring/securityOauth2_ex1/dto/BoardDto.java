package org.spring.securityOauth2_ex1.dto;

import lombok.*;
import org.spring.securityOauth2_ex1.entity.MemberEntity;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardDto {

    private Long id;

    private String title;// board_title

    private String content;// board_title

    private String writer;

    private int hit;  // 조회수

    private MemberEntity memberEntity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
