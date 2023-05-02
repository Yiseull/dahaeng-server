package com.yiseull.dahaeng.domain.note.controller;

import com.yiseull.dahaeng.domain.note.dto.NoteRequest;
import com.yiseull.dahaeng.domain.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity createNote(@RequestBody NoteRequest.NoteInfo request, @RequestParam int userId) {
        noteService.createNote(request, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<NoteRequest.NoteInfo> deleteNote(@PathVariable int noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{noteId}")
    public ResponseEntity updateNote(@PathVariable int noteId, @RequestBody NoteRequest.NoteInfo request) {
        return ResponseEntity.ok(noteService.updateNote(noteId, request));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteRequest.NoteInfo> getNote(@PathVariable int noteId) {
        return ResponseEntity.ok(noteService.getNote(noteId));
    }

    @GetMapping
    public ResponseEntity<List<NoteRequest.NoteInfo>> getNoteList() {
        return ResponseEntity.ok(noteService.getNoteList());
    }
}
