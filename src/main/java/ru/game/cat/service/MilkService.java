package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.callback.CallbackQueryExecutor;
import ru.game.cat.bot.callback.KeyboardGenerator;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.MilkBonus;
import ru.game.cat.exceptions.HealthyException;
import ru.game.cat.exceptions.SatietyException;
import ru.game.cat.factory.MilkBonusGifsFactory;
import ru.game.cat.utils.ClockUtil;
import ru.game.cat.utils.Texts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;
import static ru.game.cat.factory.CallbacksFactory.GET_MILK_BONUS_CALLBACK;
import static ru.game.cat.bot.emojy.Emojy.CENTER_MILK_EMOJY;

@Service
@RequiredArgsConstructor
public class MilkService implements KeyboardGenerator, CallbackQueryExecutor {

    private static final String MILK_STICKER_ID = "CAACAgIAAxkDAAICTmZisAZuHjgUmoVFePYmEFdYJGFfAALpSQACb6EZS-Eo1B33eoDBNQQ";

    private final StickersService stickersService;
    private final CatService catService;
    private final StatisticService statisticService;
    private final MessageSender messageSender;
    private final MilkBonusGifsFactory bonusStickerFactory;

    public MilkBonus getActualMilkBonus(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        MilkBonus bonus = cat.getMilkBonus();
        if (bonus == null) {
            bonus = MilkBonus.builder()
                    .amount(ZERO)
                    .satiety(100)
                    .health(20)
                    .periodPerHour(3)
                    .checkDate(LocalDateTime.now().minusYears(100))
                    .build();
            cat.setMilkBonus(bonus);
            catService.save(cat);
        }
        return bonus;
    }

    public MilkBonus getActualMilkBonus(@NonNull Cat cat, @NonNull Update update) {
        MilkBonus bonus = cat.getMilkBonus();
        if (bonus == null) {
            bonus = MilkBonus.builder()
                    .amount(ZERO)
                    .satiety(100)
                    .health(20)
                    .periodPerHour(3)
                    .checkDate(LocalDateTime.now().minusYears(100))
                    .build();
            cat.setMilkBonus(bonus);
            catService.save(cat);
        }
        return bonus;
    }

    public void plusMilk(@NonNull Cat cat, @NonNull Update update) {
        MilkBonus bonus = getActualMilkBonus(cat, update);
        bonus.setAmount(bonus.getAmount() + 1);
        bonus.setCheckDate(LocalDateTime.now().plusHours(bonus.getPeriodPerHour()));
        catService.save(cat);
    }


    public void initForCommand(@NonNull Update update) {
        stickersService.executeSticker(update, MILK_STICKER_ID);
        StringBuilder builder = new StringBuilder(CENTER_MILK_EMOJY + " " + Texts.MILK_INFO_TEXT);
        Cat cat = catService.findActualCat(update);
        if (milkIsGot(cat, update)) {
            builder.append("\n").append("До следующей раздачи \n")
                    .append(Emojy.CLOCK_EMOJY).append(ClockUtil.getHoursMinutesAndSeconds(LocalDateTime.now(), cat.getMilkBonus().getCheckDate()));
            messageSender.sendMessage(update.getMessage().getChatId(), builder.toString());
        } else {
            messageSender.sendMessageWithKeyboard(update.getMessage().getChatId(), builder.toString(), getKeyboard());
        }
    }

    public boolean milkIsGot(Cat cat, Update update) {
        MilkBonus bonus = getActualMilkBonus(cat, update);
        var start = LocalDateTime.now();
        var end = bonus.getCheckDate();
        long minutes = Duration.between(end, start).toMinutes();
        return minutes < (cat.getMilkBonus().getPeriodPerHour() * 60L);
    }

    public String takeMilk(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        StringBuilder builder = new StringBuilder();
        try {
            String eat = statisticService.plusSatiety(cat, cat.getMilkBonus().getSatiety());
            builder.append(eat).append(" ");
        } catch (SatietyException e) {
            builder.append(e.getMessage()).append(" ");
        }
        try {
            String health = statisticService.plusHealth(cat, cat.getMilkBonus().getHealth());
            builder.append(health);
        } catch (HealthyException e) {
            builder.append(e.getMessage());
        }
        plusMilk(cat, update);
        return builder.toString();
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder()
                .callbackData(GET_MILK_BONUS_CALLBACK)
                .text(Emojy.TONGUES_EMOJY + Emojy.MILK_EMOJY + " " + Texts.DRINK_MILK_TEXT)
                .build());
        rows.add(row1);
        markup.setKeyboard(rows);
        return markup;
    }

    @Override
    public void executeCallback(Update update) {
        String text = takeMilk(update);
        messageSender.sendAlert(update, text);
        StringBuilder builder = new StringBuilder(CENTER_MILK_EMOJY + " " + Texts.MILK_INFO_TEXT);
        Cat cat = catService.findActualCat(update);
        builder.append("\n").append("До следующей раздачи \n")
                .append(ClockUtil.getHoursMinutesAndSeconds(LocalDateTime.now(), cat.getMilkBonus().getCheckDate()));
        messageSender.deleteMessage(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getMessage().getMessageId());
        bonusStickerFactory.sendCatGif(update.getCallbackQuery().getMessage().getChatId());
        messageSender.sendMessage(update.getCallbackQuery().getMessage().getChatId(), builder.toString());
    }
}
