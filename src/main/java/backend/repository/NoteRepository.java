package backend.repository;

import backend.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note,String> {

    @Query("{noteId:?0 }")
    List<Note> getNoteByNoteIdUsingRepository(String noteId);

    @Query("{userId:?0 }")
    List<Note> getNotesByUserIdUsingRepository(String userId);

}
