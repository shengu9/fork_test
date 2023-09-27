package org.spring.securityOauth2_ex1.repository;

import org.spring.securityOauth2_ex1.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query(value = "update BoardEntity b set b.hit = b.hit+1 " +
            " where board_id =:id ")
    void boardHit(@Param("id") Long id);


}
