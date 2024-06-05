package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Inventory;
import ru.game.cat.entity.Statistics;
import ru.game.cat.entity.Sticker;
import ru.game.cat.repository.CatRepository;
import ru.game.cat.utils.CatNameFactory;

import java.time.LocalDateTime;

import static ru.game.cat.utils.Numbers.HUNDRED;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final CatNameFactory catNameFactory;
    private final StickersService stickersService;
    private static final String USER_PREFIX = "@";

    @Transactional
    public Cat registerCat(@NonNull Update update) {
        Sticker sticker = stickersService.findById(1);
        Cat cat = Cat.builder()
                .chatId(update.getMessage().getChatId())
                .catName(catNameFactory.getName())
                .firstname(update.getMessage().getChat().getFirstName())
                .lastname(update.getMessage().getChat().getLastName())
                .username(USER_PREFIX + update.getMessage().getChat().getUserName())
                .regDate(LocalDateTime.now())
                .sticker(sticker)
                .level(1)
                .statistics(Statistics.builder()
                        .max_happiness(HUNDRED)
                        .max_satiety(HUNDRED)
                        .maxHealth(HUNDRED)
                        .build())
                .inventory(new Inventory())
                .necessary_xp_for_up(HUNDRED)
                .build();
        catRepository.save(cat);
        return cat;
    }

    public Cat findActualCat(@NonNull Update update) {
        return catRepository.findByChatId(update.getMessage().getChatId());
    }
}
