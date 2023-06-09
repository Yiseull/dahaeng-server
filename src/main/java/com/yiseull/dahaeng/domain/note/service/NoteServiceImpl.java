package com.yiseull.dahaeng.domain.note.service;

import com.yiseull.dahaeng.domain.member.dto.MemberDto;
import com.yiseull.dahaeng.domain.member.service.MemberService;
import com.yiseull.dahaeng.domain.note.Note;
import com.yiseull.dahaeng.domain.note.dto.NoteRequest;
import com.yiseull.dahaeng.domain.note.repository.NoteRepository;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import com.yiseull.dahaeng.exception.ErrorCode;
import com.yiseull.dahaeng.exception.NoteException;
import com.yiseull.dahaeng.exception.UserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final MemberService memberService;

    @Override
    public void createNote(NoteRequest.NoteInfo request, int userId) {
        if (!userRepository.existsById(userId))
            throw new UserException(ErrorCode.USER_NOT_FOUND);

        Note savedNote = noteRepository.save(Note.builder()
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .noteColor((int) (Math.random() * 5))
                .build());

        memberService.addMember(MemberDto.MemberInfo.builder()
                .userId(userId)
                .noteId(savedNote.getNoteId())
                .build());
    }

    @Override
    public void deleteNote(int noteId) {
        noteRepository.deleteById(noteId);
    }

    @Override
    public NoteRequest.NoteInfo updateNote(int noteId, NoteRequest.NoteInfo request) {
        Note findNote = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(ErrorCode.NOTE_NOT_FOUND));
        findNote.updateNote(request);

        return NoteRequest.NoteInfo.builder()
                .noteId(noteId)
                .title(findNote.getTitle())
                .startDate(findNote.getStartDate())
                .endDate(findNote.getEndDate())
                .description(findNote.getDescription())
                .build();
    }

    @Override
    public NoteRequest.NoteInfo getNote(int noteId, int userId) {
        Note findNote = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(ErrorCode.NOTE_NOT_FOUND));

        if (!memberService.existMember(userId, noteId))
            throw new NoteException(ErrorCode.NOTE_FORBIDDEN);

        return NoteRequest.NoteInfo.builder()
                .noteId(noteId)
                .title(findNote.getTitle())
                .startDate(findNote.getStartDate())
                .endDate(findNote.getEndDate())
                .description(findNote.getDescription())
                .build();
    }

    @Override
    public List<NoteRequest.NoteInfo> getNoteList(int userId) {
        List<NoteRequest.NoteInfo> noteList = new ArrayList<>();
        for (Integer noteId : memberService.findNoteList(userId)) {
            Note note = noteRepository.findById(noteId).orElseThrow(() -> new NoteException(ErrorCode.NOTE_NOT_FOUND));

            noteList.add(NoteRequest.NoteInfo.builder()
                    .noteId(noteId)
                    .title(note.getTitle())
                    .startDate(note.getStartDate())
                    .endDate(note.getEndDate())
                    .description(note.getDescription())
                    .build());
        }

        return noteList;
    }
}
