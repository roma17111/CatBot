package ru.game.cat.bot.command.executors;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Yard;
import ru.game.cat.enums.StickerNames;
import ru.game.cat.service.CatService;
import ru.game.cat.service.PlayToyService;
import ru.game.cat.service.SleepService;
import ru.game.cat.service.StickersService;
import ru.game.cat.service.yard.YardService;
import ru.game.cat.utils.ClockUtil;
import ru.game.cat.utils.RandomUtils;
import ru.game.cat.utils.Texts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.game.cat.bot.emojy.Emojy.SEARCH_EMOJY;
import static ru.game.cat.factory.CallbacksFactory.*;
import static ru.game.cat.utils.Texts.FINISH_WALK_IN_YARD_TEXT;
import static ru.game.cat.utils.Texts.TAKE_LOOT_TEXT;

@Component
@RequiredArgsConstructor
public class YardExecutor {

    public static final String STICKER_YARD_PATH = "stickers/yard/5291931816465275738.tgs";

    private final CatService catService;
    private final YardService yardService;
    private final MessageSender messageSender;
    private final StickersService stickersService;
    private final PlayToyService toyService;

    @Value("${bot.yard.toy-random-range}")
    private int toyRandomRange;

    @PostConstruct
    public void init() {
        if (toyRandomRange<1||toyRandomRange>=200) {
            throw new BeanCreationException("Random range для игрушки должен находиться в диапазоне 1-200");
        }
    }

    @CheckEvents(checkSleep = true, checkEnergy = true)
    public void executeCommand(@NonNull Update update) {
        stickersService.executeSticker(update, stickersService.findById(StickerNames.YARD_STICKER));
        long chatId = update.getMessage().getChatId();
        Cat cat = catService.findActualCat(update);
        Yard yard = yardService.getActualYard(cat);
        if (!yard.isInTheWalk()) {
            initInfoText(update);
        } else if (yardService.walkIsNotFinished(cat)) {
            messageSender.sendMessage(chatId,
                    getWalkText(cat));
        } else {
            initFinishWalkMessage(update);
        }
    }

    public void startCatExecuteMessage(@NonNull Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(InlineKeyboardButton.builder()
                .callbackData(START_LOOT_EXECUTOR)
                .text(Texts.START_LOOT_BUTTON_TEXT)
                .build());
        rows.add(row1);
        markup.setKeyboard(rows);

        messageSender.sendMessageWithKeyboard(update.getMessage().getChatId(),
                Texts.START_LOOT_CAT_TEXT,
                markup);
    }

    private void initInfoText(@NonNull Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();

        row1.add(InlineKeyboardButton.builder()
                .callbackData(TEN_MINUTES_YARD_CALLBACK)
                .text(Emojy.CLOCK_EMOJY + " 10 минут")
                .build());

        row2.add(InlineKeyboardButton.builder()
                .callbackData(THIRTY_MINUTES_YARD_CALLBACK)
                .text(Emojy.CLOCK_EMOJY + " 30 минут")
                .build());

        row3.add(InlineKeyboardButton.builder()
                .callbackData(HOUR_YARD_CALLBACK)
                .text(Emojy.CLOCK_EMOJY + " 1 час")
                .build());

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        markup.setKeyboard(rows);

        messageSender.sendMessageWithKeyboard(update.getMessage().getChatId(),
                Texts.YARD_INFO_TEXT, markup);
    }

    private String getWalkText(@NonNull Cat cat) {

        Yard yard = yardService.getActualYard(cat);
        var start = LocalDateTime.now();
        var end = yard.getCheckDate();

        return String.format("""
                %s Котик шастает по двору
                Ему осталось гулять\s
                                
                %s
                """, Emojy.CAT, Emojy.CLOCK_EMOJY + " " + ClockUtil.getHoursMinutesAndSeconds(start, end));
    }

    private void initFinishWalkMessage(@NonNull Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder()
                .text(TAKE_LOOT_TEXT)
                .callbackData(TAKE_LOOT_YARD_CALLBACK)
                .build());
        rows.add(row1);
        markup.setKeyboard(rows);

        messageSender.sendMessageWithKeyboard(update.getMessage().getChatId(), FINISH_WALK_IN_YARD_TEXT, markup);
    }

    public void executeTakeLootAndFinishWalk(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        if (!cat.getYard().isInTheWalk()) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Ты уже забрал свои находки, котейка" + Emojy.SMILE);
        } else if (yardService.walkIsNotFinished(cat)) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Не так быстро, котейка. Ты ещё не вернулся с прогулки" + Emojy.SMILE);
        } else {
            String text = SEARCH_EMOJY + " Посмотри, что ты сегодня нашёл\n\n"
                    + yardService.getLoot(cat, update);

            yardService.finishWalk(cat);
            messageSender.deleteMessage(
                    update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getMessage().getMessageId()
            );
            messageSender.sendMessage(update.getCallbackQuery().getMessage().getChatId(), text);
            long random = RandomUtils.getRandomNumber(200);
            if (random > toyRandomRange) {
                toyService.initFindToyEvent(update, cat);
            }
        }
    }

    public void executeWalk(@NonNull Update update, int minutes) {
        Cat cat = catService.findActualCat(update);
        if (cat.getYard() == null) {
            Yard yard = yardService.getActualYard(update);
            cat.setYard(yard);
        }
        if (cat.getYard().isInTheWalk()) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Ты уже отправился на прогулку, котейка" + Emojy.SMILE);
        } else if (yardService.walkIsNotFinished(cat)) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Ты ещё не вернулся, котофей, не так быстро" + Emojy.SMILE);
        } else {
            yardService.walk(cat, minutes);
            messageSender.deleteMessage(update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getMessage().getMessageId());
            messageSender.sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                    getWalkText(cat));
        }

    }


}
