package ru.game.cat.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Component
public class CatNameFactory {

    private static final Random random = new SecureRandom();
    private static final List<String> names = List.of(
            "Миу",
            "Аcя",
            "Васёк",
            "Мур",
            "Мурка",
            "Кетти",
            "Уася",
            "Жора",
            "Соня",
            "Тимоша",
            "Зося",
            "Захар",
            "Снежок",
            "Варя",
            "Муся",
            "Котя",
            "Белянка",
            "Беляш",
            "Куся",
            "Lucky",
            "Kendy",
            "Лола",
            "Маня",
            "Тося",
            "Клео",
            "Луи",
            "Лео",
            "Мэгги",
            "Лапа",
            "Чита",
            "Ласка",
            "Ариель",
            "Леопольд",
            "Барсик",
            "Мурёна",
            "Китти",
            "Иззи",
            "Фиона",
            "Рубби",
            "Мисси"
    );

    public String getName() {
        int index = random.nextInt(names.size());
        return names.get(index);
    }

}
