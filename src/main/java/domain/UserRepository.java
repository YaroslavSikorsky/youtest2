package domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

//
//    void saveUser(User user);

//    User findByEmail(String email);


    @Modifying
    @Query("INSERT INTO public.users (email, password) VALUES (:email, :password)")
    void saveUser(String email, String password);

    @Query("SELECT * FROM public.users WHERE email = :email")
    User getUser(@Param("email") String email);

}
