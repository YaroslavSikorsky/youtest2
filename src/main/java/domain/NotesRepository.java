package domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotesRepository extends CrudRepository<Note, UUID> {

    @Modifying
    @Query("INSERT INTO public.notes (data, type, user_id) VALUES (:data, :type, :userId)")
    Integer saveNotes(@Param("data") String data,
                      @Param("type") String type,
                      @Param("userId") UUID userId);

    @Query("SELECT * FROM public.notes WHERE user_id = :userId")
    List<Note> show(@Param("userId") UUID userId);

    @Query("SELECT * FROM public.notes WHERE type = :type")
    List<Note> showByType(@Param("type") NoteType type);

}
