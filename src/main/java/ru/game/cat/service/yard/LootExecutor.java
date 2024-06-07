package ru.game.cat.service.yard;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface LootExecutor {

    String getLoot(@NonNull Update update);
}
