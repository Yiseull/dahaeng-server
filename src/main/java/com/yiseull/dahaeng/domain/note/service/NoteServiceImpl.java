package com.yiseull.dahaeng.domain.note.service;

import com.yiseull.dahaeng.domain.note.Note;
import com.yiseull.dahaeng.domain.note.dto.NoteRequest;
import com.yiseull.dahaeng.domain.note.repository.NoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public void createNote(NoteRequest.CreateNote request, int userId) {
        noteRepository.save(Note.builder()
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .noteColor((int)(Math.random() * 5))
                .build());
    }
}
