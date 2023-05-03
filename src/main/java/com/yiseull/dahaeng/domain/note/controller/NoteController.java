package com.yiseull.dahaeng.domain.note.controller;

import com.yiseull.dahaeng.domain.note.dto.NoteRequest;
import com.yiseull.dahaeng.domain.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Note 📕", description = "노트 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "노트 생성")
    @PostMapping
    public ResponseEntity createNote(@RequestBody NoteRequest.NoteInfo request, @RequestParam int userId) {
        noteService.createNote(request, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "노트 삭제")
    @DeleteMapping("/{noteId}")
    public ResponseEntity<NoteRequest.NoteInfo> deleteNote(@PathVariable int noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "노트 업데이트")
    @PostMapping("/{noteId}")
    public ResponseEntity updateNote(@PathVariable int noteId, @RequestBody NoteRequest.NoteInfo request) {
        return ResponseEntity.ok(noteService.updateNote(noteId, request));
    }

    @Operation(summary = "노트 조회")
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteRequest.NoteInfo> getNote(@PathVariable int noteId, @RequestParam int userId) {
        return ResponseEntity.ok(noteService.getNote(noteId, userId));
    }

    @Operation(summary = "노트 목록 조회")
    @GetMapping
    public ResponseEntity<List<NoteRequest.NoteInfo>> getNoteList() {
        return ResponseEntity.ok(noteService.getNoteList());
    }
}
