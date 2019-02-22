package am.dreamteam.bookservice.repositories;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersBooksRepository extends JpaRepository<UserBooks, Integer> {
    Page<UserBooks> findAll(Pageable pageable);
    Page<UserBooks> findAllByUser(User user, Pageable pageable);
    List<UserBooks> findAllByUser(User user);
    @Query("select ub from UserBooks ub inner join Book b on (LOWER(b.title) like %:term%) and b.id = ub.book")
    List<UserBooks> searchByTitle(@Param("term") String term);

    @Query("select ba from UserBooks ba inner join ba.book ba_b inner join ba_b.authors a on (LOWER(a.fullName) like %:term%)")
    List<UserBooks> searchByAuthor(@Param("term") String term);

    @Query(value = "select * from users_books u order by RANDOM() limit 10", nativeQuery = true)
    List<UserBooks> getRandom();

}
