package com.yiseull.dahaeng.domain.note.service;

import com.yiseull.dahaeng.domain.note.dto.NoteRequest;

public interface NoteService {

    void createNote(NoteRequest.CreateNote request, int userId);
}
