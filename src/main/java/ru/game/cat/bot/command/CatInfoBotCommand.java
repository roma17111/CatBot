package ru.game.cat.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.service.CatInfoService;

@Component
public class CatInfoBotCommand extends AbstractBotCommand {

    private final CatInfoService catInfoService;

    public CatInfoBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, CatInfoService catInfoService) {
        super(botCommandFactory, messageSender);
        this.catInfoService = catInfoService;
    }

    @Override
    public String getName() {
        return BotCommands.CAT.getCommand();
    }

    @Override
    public String getDescription() {
        return BotCommands.CAT.getDescription();
    }

    @Override
    public void execute(Update update) {
        catInfoService.initInfo(update);
    }
}
