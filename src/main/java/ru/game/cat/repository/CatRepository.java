package ru.game.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.game.cat.entity.Cat;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {

    @Query("""
            SELECT c from Cat c
            left join fetch c.inventory
            left join fetch c.statistics
            left join fetch c.sticker
            left join fetch c.milkBonus
            where c.chatId = :chat_id
            """)
    Cat findByChatId(@Param("chat_id") long chatId);

    @Query("""
            SELECT c from Cat c
            left join fetch c.inventory
            left join fetch c.statistics
            left join fetch c.sticker
            left join fetch c.milkBonus
            """)
    List<Cat> getAllCats();
}
