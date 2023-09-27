package org.spring.securityOauth2_ex1.service;

import lombok.RequiredArgsConstructor;
import org.spring.securityOauth2_ex1.dto.BoardDto;
import org.spring.securityOauth2_ex1.entity.BoardEntity;
import org.spring.securityOauth2_ex1.entity.MemberEntity;
import org.spring.securityOauth2_ex1.repository.BoardRepository;
import org.spring.securityOauth2_ex1.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    //게시글 작성자가 있는지 확인
    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public void insertBoard(BoardDto boardDto, String email) {

        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);

        Long boardId= boardRepository.save(
                BoardEntity.builder().title(boardDto.getTitle()).hit(0).content(boardDto.getContent())
                        .writer(boardDto.getWriter()).memberEntity(memberEntity).build()
        ).getId();

        boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);

    }

    @Transactional
    public List<BoardDto> boardListDo() {

        List<BoardDto> boardDtos=new ArrayList<>();
        List<BoardEntity> boardEntities= boardRepository.findAll();

        for(BoardEntity boardEntity:boardEntities){
            // Entity->Dto
            boardDtos.add( BoardDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .writer(boardEntity.getWriter())
                    .createTime(boardEntity.getCreateTime())
                    .memberEntity(boardEntity.getMemberEntity())
                    .build());
        }

        return boardDtos;
    }

    @Transactional
    public BoardDto boardDetail(Long id) {

        boardHit(id);

        BoardEntity boardEntity =
                boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return BoardDto.builder()
                .id(boardEntity.getId())
                .hit(boardEntity.getHit())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writer(boardEntity.getWriter())
                .memberEntity(boardEntity.getMemberEntity())
                .build();
    }

    private void boardHit(Long id) {
        boardRepository.boardHit(id);
    }

    @Transactional
    public void boardDelete(Long id) {

        Optional<BoardEntity> boardEntity = boardRepository.findById(id);

        boardRepository.delete(boardEntity.get());

//        Optional<BoardEntity> optionalBoardEntity1
//                = boardRepository.findById(id);



    }
}