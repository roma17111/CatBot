package ru.game.cat.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.game.cat.entity.Sticker;

@Repository
public interface StickerRepository extends JpaRepository<Sticker,Long> {

    Sticker findByStickerId(long stickerId);
}
