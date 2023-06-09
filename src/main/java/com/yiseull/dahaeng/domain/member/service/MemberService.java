package com.yiseull.dahaeng.domain.member.service;

import com.yiseull.dahaeng.domain.member.dto.MemberDto;

import java.util.List;

public interface MemberService {

    void addMember(MemberDto.MemberInfo member);

    boolean existMember(int userId, int noteId);

    List<Integer> findNoteList(int userId);
}
