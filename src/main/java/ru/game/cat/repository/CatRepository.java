package ru.game.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.game.cat.entity.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {

    Cat findByChatId(long chatId);
}
