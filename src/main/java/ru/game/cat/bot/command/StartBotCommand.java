package ru.game.cat.bot.command;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;
import ru.game.cat.service.CatService;
import ru.game.cat.service.StickersService;
import ru.game.cat.service.yard.YardExecutor;
import ru.game.cat.utils.Texts;

import static ru.game.cat.enums.StickerNames.FIRST_CAT_STICKER;
import static ru.game.cat.utils.Texts.ALREADY_EXISTS_CAT;

@Component
public class StartBotCommand extends AbstractBotCommand {

    private final MessageSender messageSender;
    private final CatService catService;
    private final YardExecutor yardExecutor;

    private final StickersService stickersService;
    public StartBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, MessageSender messageSender1, CatService catService, YardExecutor yardExecutor, StickersService stickersService) {
        super(botCommandFactory, messageSender);
        this.messageSender = messageSender1;
        this.catService = catService;
        this.yardExecutor = yardExecutor;
        this.stickersService = stickersService;
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
            stickersService.executeSticker(update,stickersService.findById(FIRST_CAT_STICKER));
            cat = catService.registerCat(update);
            messageSender.sendMessage(update.getMessage().getChatId(), Texts.REG_TEXT);
            messageSender.sendMessage(update.getMessage().getChatId(), "Меня зовут - " + cat.getCatName());
            yardExecutor.startCatExecuteMessage(update);
        } else {
            messageSender.sendMessage(update.getMessage().getChatId(), ALREADY_EXISTS_CAT);
        }

    }

}
