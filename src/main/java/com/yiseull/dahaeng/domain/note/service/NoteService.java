package com.yiseull.dahaeng.domain.note.service;

import com.yiseull.dahaeng.domain.note.dto.NoteRequest;

import java.util.List;

public interface NoteService {

    void createNote(NoteRequest.NoteInfo request, int userId);

    void deleteNote(int noteId);

    NoteRequest.NoteInfo updateNote(int noteId, NoteRequest.NoteInfo request);

    NoteRequest.NoteInfo getNote(int noteId, int userId);

    List<NoteRequest.NoteInfo> getNoteList();
}
