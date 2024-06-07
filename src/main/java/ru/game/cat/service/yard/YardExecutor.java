package ru.game.cat.service.yard;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Yard;
import ru.game.cat.service.CatService;
import ru.game.cat.utils.ClockUtil;

import java.time.LocalDateTime;

import static ru.game.cat.bot.emojy.Emojy.SEARCH_EMOJY;

@Component
@RequiredArgsConstructor
public class YardExecutor {

    private final CatService catService;
    private final YardService yardService;
    private final MessageSender messageSender;

    public void executeCommand(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        Yard yard = yardService.getActualYard(cat);
        if (!yard.isInTheWalk()) {
            //Приветствие кота во дворе
        } else if (!yardService.walkIsNotFinished(cat)) {
            messageSender.sendMessage(update.getMessage().getChatId(),
                    getWalkText(cat));
        } else {
            //Забрать лут
        }
    }

    private String getWalkText(@NonNull Cat cat) {

        Yard yard = yardService.getActualYard(cat);
        var start = LocalDateTime.now();
        var end = yard.getCheckDate();

        return String.format("""
                %s Котик шастает по двору
                Ему осталось гулять\s
                %s
                """, Emojy.CAT, ClockUtil.getHoursMinutesAndSeconds(start, end));
    }

    public void executeTakeLootAndFinishWalk(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        if (!cat.getYard().isInTheWalk()) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Ты уже забрал свои находки, котейка" + Emojy.SMILE);
        } else if (!yardService.walkIsNotFinished(cat)) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Ты уже забрал свои находки, котейка" + Emojy.SMILE);
        } else {
            String text = SEARCH_EMOJY+" Посмотри, что ты сегодня нашёл\n\n"
                    + yardService.getLoot(cat, update);
            yardService.finishWalk(cat);
            messageSender.deleteMessage(
                    update.getCallbackQuery().getMessage().getChatId(),
                    update.getCallbackQuery().getMessage().getMessageId()
            );
            messageSender.editMessage(update,text);
        }
    }

    public void executeWalk(@NonNull Update update, int minutes) {
        Cat cat = catService.findActualCat(update);
        if (cat.getYard().isInTheWalk()) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Ты уже отправился на прогулку, котейка" + Emojy.SMILE);
        } else if (yardService.walkIsNotFinished(cat)) {
            messageSender.sendMessageDialog(update, Emojy.CAT_ERROR_EMOJY + " Ты ещё не вернулся, котофей, не так быстро" + Emojy.SMILE);
        } else {
            yardService.walk(cat, minutes);
            messageSender.editMessage(update,
                    getWalkText(cat));
        }

    }


}
