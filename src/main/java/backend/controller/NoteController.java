package backend.controller;

import backend.model.Note;
import backend.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/notes")
public class NoteController {
    @Autowired
    private NoteService note_service;

    @PostMapping("/add_new_note")
    public Map<String, String> addNewNote(@RequestBody Note note) {
        return note_service.addNoteService(note);
    }

    @PutMapping("/update_single_note")
    public Note updateSingleNote(@RequestBody Note note){
        return note_service.updateSingleNoteService(note);
    }

    @GetMapping("/get_notes_by_user_id")
    public List<Note> getNotesByUserId(@RequestBody Note note){
        return note_service.getNotesByUserIdService(note);
    }

    @DeleteMapping("/delete_notes_by_user_id")
    public Map<String, String> deleteNotesByUserId(@RequestBody Note note){
        return note_service.deleteAllNotesByUserIdService(note);
    }

    @DeleteMapping("/delete_all_notes")
    public Map<String, String> deleteAllNotes(){
        return note_service.deleteAllNotesService();
    }

}
