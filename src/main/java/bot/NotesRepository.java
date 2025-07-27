package bot;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends CrudRepository<Note, String> {

    @Modifying
    @Query("INSERT INTO project.notes (data, type) VALUES (:data, :type)")
    Integer saveNotes(String data, String type);

    @Query("SELECT * FROM project.notes")
    List<Note> show();
}
