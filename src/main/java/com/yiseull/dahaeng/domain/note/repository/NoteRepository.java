package com.yiseull.dahaeng.domain.note.repository;

import com.yiseull.dahaeng.domain.note.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

}
