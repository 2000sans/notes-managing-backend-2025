package backend.service;

import backend.customfeatures.RandomIdGenerator;
import backend.model.Note;
import backend.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteService {
    @Autowired
    private NoteRepository note_repository;

    public Map<String, String> addNoteService(Note note) {

        Map<String, String> response = new HashMap<>();
        response.put("message", "");

        try {

            // Generate a unique note ID
            RandomIdGenerator obj = new RandomIdGenerator();
            String generatedNoteId = obj.getGeneratedId("note").toString();
            note.setNoteId(generatedNoteId);

            // Validate note fields
            if (note.getNoteTitle() == null || note.getNoteTitle().trim().isEmpty() ||
                    note.getNoteDescription() == null || note.getNoteDescription().trim().isEmpty()) {
                response.replace("message", "Title or Description must be valid, and cannot be empty");
                return response;
            }

            // Save the note to the repository
            note_repository.save(note);

            // Prepare the response with note details
            response.clear();
            response.put("noteId", note.getNoteId());
            response.put("noteTitle", note.getNoteTitle());
            response.put("noteDescription", note.getNoteDescription());
            response.put("userId", note.getUserId());

            return response;

        } catch (Exception e) {
            response.clear();
            response.put("message", "Error saving note: " + e.getMessage());
            return response;
        }
    }

    public List<Note> getNoteByNoteIdUtility(String noteId){
        return note_repository.getNoteByNoteIdUsingRepository(noteId);
    }

    public Note updateSingleNoteService(Note noteRequest){

        List<Note> existingNote = getNoteByNoteIdUtility(noteRequest.getNoteId());
        existingNote.get(0).setNoteTitle(noteRequest.getNoteTitle());
        existingNote.get(0).setNoteDescription(noteRequest.getNoteDescription());

        note_repository.save(existingNote.get(0));

        return existingNote.get(0);

    }

    public List<Note> getNotesByUserIdUtility(String userId){
        return note_repository.getNotesByUserIdUsingRepository(userId);
    }

    public List<Note> getNotesByUserIdService(Note noteRequest){
        return getNotesByUserIdUtility(noteRequest.getUserId());
    }

    public Map<String, String> deleteAllNotesByUserIdService(Note noteRequest){

        Map<String, String> response = new HashMap<>();
        response.put("message", "");

        List<Note> existingNotes = getNotesByUserIdUtility(noteRequest.getUserId());

        note_repository.deleteAll(existingNotes);

        response.replace("message", "Deleted All The Notes of User Id : " + noteRequest.getUserId());

        return response;

    }

    public Map<String, String> deleteAllNotesService(){

        Map<String, String> response = new HashMap<>();
        response.put("message", "");

        note_repository.deleteAll();

        response.replace("message", "All notes are deleted.");

        return response;

    }

}