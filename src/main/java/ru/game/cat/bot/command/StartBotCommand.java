package ru.game.cat.bot.command;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;
import ru.game.cat.service.CatService;
import ru.game.cat.utils.Texts;

import static ru.game.cat.utils.Texts.ALREADY_EXISTS_CAT;

@Component
public class StartBotCommand extends AbstractBotCommand {

    private final MessageSender messageSender;
    private final CatService catService;

    public StartBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, MessageSender messageSender1, CatService catService) {
        super(botCommandFactory, messageSender);
        this.messageSender = messageSender1;
        this.catService = catService;
    }

    @Override
    public String getName() {
        return BotCommands.START.getCommand();
    }

    @Override
    public String getDescription() {
        return BotCommands.START.getDescription();
    }

    @SneakyThrows
    @Override
    public void execute(Update update) {
        Cat cat = catService.findActualCat(update);
        if (cat == null) {
            cat = catService.registerCat(update);
            messageSender.sendMessage(update.getMessage().getChatId(), Texts.REG_TEXT);
            messageSender.sendMessage(update.getMessage().getChatId(), "Меня зовут - " + cat.getCatName());
        } else {
            messageSender.sendMessage(update.getMessage().getChatId(), ALREADY_EXISTS_CAT);
        }

    }

}
