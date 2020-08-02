package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    public Integer updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public Integer insert(Note note) {
        return noteMapper.insert(note);
    }

    public Integer delete(Integer noteId) {
        return noteMapper.delete(noteId);
    }
}
