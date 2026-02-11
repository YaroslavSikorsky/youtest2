package domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotesRepository extends CrudRepository<Note, UUID> {

    //    @Modifying
//        @Query("INSERT INTO public.notes (title, data, type, user_id) VALUES (:title, :data, :type, :userId)")
//        Integer saveNotes(@Param("title") String title,
//                          @Param("data") String data,
//                      @Param("type") String type,
//                      @Param("userId") UUID userId);
// Создание новой заметки
    @Modifying
    @Query("INSERT INTO public.notes (id, title, data, type, user_id, status, done, calendar, calendar_date, created_at) " +
            "VALUES (:id, :title, :data, :type, :userId, :status, :done, :calendar, :calendarDate, :createdAt)")
    Integer saveNotes(@Param("id") UUID id,
                      @Param("title") String title,
                      @Param("data") String data,
                      @Param("type") String type,
                      @Param("userId") UUID userId,
                      @Param("status") CanbanType status,
                      @Param("done") Boolean done,
                      @Param("calendar") Boolean calendar,
                      @Param("calendarDate") LocalDate calendarDate,
                      @Param("createdAt") LocalDate createdAt);

    @Query("SELECT * FROM public.notes WHERE user_id = :userId")
    List<Note> show(@Param("userId") UUID userId);

    @Query("SELECT * FROM public.notes WHERE type = :type")
    List<Note> showByType(@Param("type") NoteType type);

    // Получить заметку по ID
    @Query("SELECT * FROM public.notes WHERE id = :id")
    Optional<Note> findById(@Param("id") UUID id);

    // Обновить заметку
    @Modifying
    @Query("UPDATE public.notes SET " +
            "title = :title, " +
            "data = :data, " +
            "type = :type, " +
            "status = :status, " +
            "done = :done, " +
            "calendar = :calendar, " +
            "calendar_date = :calendarDate " +
            "WHERE id = :id")
    Integer updateNote(@Param("id") UUID id,
                       @Param("title") String title,
                       @Param("data") String data,
                       @Param("type") String type,
                       @Param("status") CanbanType status,
                       @Param("done") Boolean done,
                       @Param("calendar") Boolean calendar,
                       @Param("calendarDate") LocalDate calendarDate);

    // Обновить только статус (для drag & drop в канбане)
    @Modifying
    @Query("UPDATE public.notes SET status = :status WHERE id = :id")
    Integer updateStatus(@Param("id") UUID id, @Param("status") String status);

    // Удалить заметку
    @Modifying
    @Query("DELETE FROM public.notes WHERE id = :id")
    Integer deleteNote(@Param("id") UUID id);

    // Получить заметки для календаря (где calendar = true)
    @Query("SELECT * FROM public.notes WHERE user_id = :userId AND calendar = true AND calendar_date IS NOT NULL ORDER BY calendar_date ASC")
    List<Note> getCalendarNotes(@Param("userId") UUID userId);

    // Получить заметки на конкретную дату
    @Query("SELECT * FROM public.notes WHERE user_id = :userId AND calendar_date = :date")
    List<Note> getNotesByDate(@Param("userId") UUID userId, @Param("date") LocalDate date);

}
