package ru.game.cat.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.service.CatService;
import ru.game.cat.utils.StickersCatFactory;
import ru.game.cat.utils.Texts;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static ru.game.cat.bot.emjy.Emojy.SMILE;

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
        messageSender.sendMessage(update.getMessage().getChatId(),
                "Добро пожаловать " + update.getMessage().getFrom().getFirstName());
        messageSender.execute(StickersCatFactory.getMainSticker(update));
        Cat cat = catService.findActualCat(update);
        if (cat == null) {
            cat = catService.registerCat(update);
            messageSender.sendMessage(update.getMessage().getChatId(), Texts.REG_TEXT);
            messageSender.sendMessage(update.getMessage().getChatId(), "Меня зовут - " + cat.getCatName());
        } else {
            messageSender.sendMessage(update.getMessage().getChatId(), "Ты уже зарегистрирован, котофяу" + SMILE);
        }

    }

}
