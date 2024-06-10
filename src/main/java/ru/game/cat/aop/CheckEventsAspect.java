package ru.game.cat.aop;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.StickerNames;
import ru.game.cat.exceptions.CatAreSleepingException;
import ru.game.cat.exceptions.CatInYardException;
import ru.game.cat.exceptions.EnergyIsEmptyException;
import ru.game.cat.service.CatService;
import ru.game.cat.service.SleepService;
import ru.game.cat.service.StickersService;
import ru.game.cat.utils.Texts;

import static ru.game.cat.enums.StickerNames.SLEEP_STICKER;
import static ru.game.cat.utils.Texts.CAT_WALK_IN_YARD_TEXT;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class CheckEventsAspect {

    private final CatService catService;
    private final SleepService sleepService;
    private final StickersService stickersService;
    private final MessageSender messageSender;

    @Around("@annotation(param)")
    public Object beforeCommandCheckEvents(ProceedingJoinPoint pjp, CheckEvents param) throws Throwable {
        for (Object arg : pjp.getArgs()) {
            if (arg instanceof Update update) {
                try {
                    check(param, update);
                } catch (CatAreSleepingException |
                         EnergyIsEmptyException |
                         CatInYardException e) {
                    return pjp.getThis();
                }
            }
        }

        return pjp.proceed();

    }

    private void check(CheckEvents param, @NonNull Update update) throws CatAreSleepingException, EnergyIsEmptyException, CatInYardException {
        Cat cat = catService.findActualCat(update);

        if (param.checkSleep() && sleepService.catIsSleep(cat)) {
            stickersService.executeSticker(update, stickersService.findById(SLEEP_STICKER));
            sleepService.initTimeSleep(update, cat);
            throw new CatAreSleepingException();
        }

        if (param.checkEnergy() && cat.getStatistics().getEnergy() == 0) {
            stickersService.executeSticker(update, stickersService.findById(StickerNames.YARD_STICKER));
            messageSender.sendMessage(update.getMessage().getChatId(),
                    Texts.CAT_TYRED_SLEEP_TEXT + "\n /sleep");
            throw new EnergyIsEmptyException();
        }

        if (param.checkYard() && cat.getYard().isInTheWalk()) {
            messageSender.sendMessage(update.getMessage().getChatId(), CAT_WALK_IN_YARD_TEXT);
            throw new CatInYardException();
        }
    }
}
