package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Inventory;
import ru.game.cat.entity.Statistics;
import ru.game.cat.repository.CatRepository;
import ru.game.cat.utils.CatNameFactory;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final CatNameFactory catNameFactory;
    private static final String USER_PREFIX = "@";

    public void registerCat(@NonNull Update update) {
        Cat cat = Cat.builder()
                .chatId(update.getMessage().getChatId())
                .catName(catNameFactory.getName())
                .firstname(update.getMessage().getChat().getFirstName())
                .lastname(update.getMessage().getChat().getLastName())
                .username(USER_PREFIX + update.getMessage().getChat().getUserName())
                .regDate(LocalDateTime.now())
                .statistics(Statistics.builder()
                        .max_happiness(100)
                        .max_satiety(100)
                        .maxHealth(100)
                        .build())
                .inventory(new Inventory())
                .necessary_xp_for_up(100)
                .build();
        catRepository.save(cat);
    }
}
