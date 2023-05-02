package com.yiseull.dahaeng.domain.note.service;

import com.yiseull.dahaeng.domain.note.Note;
import com.yiseull.dahaeng.domain.note.dto.NoteRequest;
import com.yiseull.dahaeng.domain.note.repository.NoteRepository;
import com.yiseull.dahaeng.domain.user.User;
import com.yiseull.dahaeng.domain.user.repository.UserRepository;
import com.yiseull.dahaeng.exception.ErrorCode;
import com.yiseull.dahaeng.exception.UserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public void createNote(NoteRequest.NoteInfo request, int userId) {
        if (!userRepository.existsById(userId))
            throw new UserException(ErrorCode.USER_NOT_FOUND);

        noteRepository.save(Note.builder()
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .noteColor((int)(Math.random() * 5))
                .build());
    }

    @Override
    public void deleteNote(int noteId) {
        noteRepository.deleteById(noteId);
    }


}
