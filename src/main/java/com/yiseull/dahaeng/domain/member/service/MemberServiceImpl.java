package com.yiseull.dahaeng.domain.member.service;

import com.yiseull.dahaeng.domain.member.Member;
import com.yiseull.dahaeng.domain.member.dto.MemberDto;
import com.yiseull.dahaeng.domain.member.repository.MemberRepository;
import com.yiseull.dahaeng.domain.note.dto.NoteRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void addMember(MemberDto.MemberInfo member) {
        memberRepository.save(Member.builder()
                .userId(member.getUserId())
                .noteId(member.getNoteId())
                .build());
    }

    @Override
    public boolean existMember(int userId, int noteId) {
        return memberRepository.existsByUserIdAndNoteId(userId, noteId);
    }

    @Override
    public List<Integer> findNoteList(int userId) {
        List<Integer> noteIdList = new ArrayList<>();
        for (Member m : memberRepository.findByUserId(userId)) {
            noteIdList.add(m.getNoteId());
        }

        return noteIdList;
    }
}
