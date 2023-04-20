package com.yiseull.dahaeng.domain.note.repository;

import com.yiseull.dahaeng.domain.note.Note;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void insertNote(Note vo) {
        entityManager.persist(vo);
    }

    public void updateNote(Note vo) {
        entityManager.merge(vo);
    }

    public void deleteNoteCompletely(int noteId) {
        entityManager.remove(entityManager.find(Note.class, noteId));
    }

    public Note getNote(int noteId) {
        return (Note) entityManager.find((Note.class), noteId);
    }

    public List<Note> getNoteList(String email) {

        String jpql = "select n from Note n where n.noteId In (select m.noteId from Member m where m.email = '"+email+"')";
        return entityManager.createQuery(jpql).getResultList();
    }

    public int getNoteId() {
        String jpql = "SELECT max(n.noteId) FROM Note n";
        return (int) entityManager.createQuery(jpql).getSingleResult();
    }
}
