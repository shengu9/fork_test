package org.spring.securityOauth2_ex1.entity;

import lombok.*;
import org.spring.securityOauth2_ex1.utils.BaseEntity;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_board08")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_title", nullable = false)
    private String title;

    @Column(name = "board_content", nullable = false)
    private String content;  // board_title

    @Column(name = "board_writer", nullable = false)
    private String writer;

    private int hit;  // 조회수

    // N:1    sec_board08테이블 컬럼명 member_id FK   PK member_id sec_member08
    @ManyToOne(fetch = FetchType.LAZY)  // 지연로딩, 기본
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


}
