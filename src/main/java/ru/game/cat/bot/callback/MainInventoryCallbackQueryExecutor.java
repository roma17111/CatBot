package ru.game.cat.bot.callback;


import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MainInventoryCallbackQueryExecutor  {


    void executeMainInventory(@NonNull Update update);
}
