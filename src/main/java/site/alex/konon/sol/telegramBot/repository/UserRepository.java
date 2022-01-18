package site.alex.konon.sol.telegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.alex.konon.sol.telegramBot.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u where lower(u.login)=lower(:login)")
    Optional<User> findOneByLogin(@Param("login") String login);

    @Query("from User u where u.registrationToken = :code")
    Optional<User> findOneByRegistrationToken(@Param("code") String code);
}
