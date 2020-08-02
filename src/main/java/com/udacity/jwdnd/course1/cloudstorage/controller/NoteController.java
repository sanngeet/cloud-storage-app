package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/note")
@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String addAndUpdateNote(Authentication authentication, Note note, Model model) {
        String errorMessage = null;

        if (note.getNoteId() == null) {
            note.setUserId(this.userService.getUserId(authentication.getName()));
            if (this.noteService.insert(note) < 1) {
                errorMessage = "There was an error saving the note. Please try again.";
            }
        } else {
            if (this.noteService.updateNote(note) < 1) {
                errorMessage = "There was an error updating the note. Please try again.";
            }
        }

        model.addAttribute("error", errorMessage);

        return "result";
    }

    @PostMapping("/delete")
    public String deleteNote(@RequestParam(value = "noteId") Integer noteId, Model model) {
        String errorMessage = null;

        if (this.noteService.delete(noteId) < 1) {
            errorMessage = "There was an error deleting the note. Please try again.";
        }

        model.addAttribute("error", errorMessage);
        return "result";
    }
}
