package com.yiseull.dahaeng.domain.note.controller;

import com.yiseull.dahaeng.domain.note.dto.NoteRequest;
import com.yiseull.dahaeng.domain.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/note")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity createNote(@RequestBody NoteRequest.CreateNote request, @RequestParam int userId) {
        noteService.createNote(request, userId);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/{noteId}")
//    public ResponseEntity updateNote(@PathVariable int noteId, @RequestParam int userId) {
//
//    }
//
//    @DeleteMapping("/{noteId}")
//    public ResponseEntity deleteNote(@PathVariable int noteId, @RequestParam int userId) {
//
//    }
//
//    @GetMapping("/{noteId}")
//    public ResponseEntity getNote(@PathVariable int noteId, @RequestParam int userId) {
//
//    }
//
//    @GetMapping
//    public ResponseEntity getNote(@RequestParam int userId) {
//
//    }
}
