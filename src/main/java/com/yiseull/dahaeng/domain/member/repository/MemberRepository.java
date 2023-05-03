package com.yiseull.dahaeng.domain.member.repository;

import com.yiseull.dahaeng.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByUserIdAndNoteId(int userId, int noteId);
}
