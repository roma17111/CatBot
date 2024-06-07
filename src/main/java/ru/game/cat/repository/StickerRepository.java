package ru.game.cat.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.game.cat.entity.Sticker;
import ru.game.cat.enums.StickerNames;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, StickerNames> {

    Sticker findByStickerId(StickerNames stickerId);
}
