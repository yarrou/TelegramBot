package site.alex.konon.sol.telegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.alex.konon.sol.telegramBot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
