package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.callback.CallbackQueryExecutor;
import ru.game.cat.factory.ButtonsFactory;
import ru.game.cat.bot.callback.KeyboardGenerator;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Statistics;
import ru.game.cat.exceptions.HealthyException;
import ru.game.cat.exceptions.SatietyException;
import ru.game.cat.utils.Texts;

import java.util.ArrayList;
import java.util.List;

@Service("statisticService")
@RequiredArgsConstructor
public class StatisticService implements KeyboardGenerator, CallbackQueryExecutor {

    private static final String HEALTH = "здоровье";
    private static final String SATIETY = "сытость";
    private static final String HAPPY = "счастье";

    private final CatService catService;
    private final MessageSender messageSender;

    private String getInfo(Update update) {
        return catService.findActualCat(update).getStatistics().getInfo();
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(ButtonsFactory.getBackToCatInfoButton());
        rows.add(row1);
        markup.setKeyboard(rows);
        return markup;
    }

    @Override
    public void executeCallback(Update update) {
        messageSender.editMessageWithKeyboard(update, getInfo(update), getKeyboard());
    }

    public Statistics getActualStatistics(@NonNull Cat cat) {
        return cat.getStatistics();
    }

    public Statistics getActualStatistics(@NonNull Update update) {
        return catService.findActualCat(update).getStatistics();
    }

    public String plusHealth(@NonNull Cat cat, int health) throws HealthyException {
        Statistics statistics = getActualStatistics(cat);
        int currentHealth = statistics.getHealth();
        int max = statistics.getMaxHealth();
        if (currentHealth == statistics.getMaxHealth()) {
            throw new HealthyException(Emojy.HEALTH_EMOJY + " " + Texts.HEALTHY_TEXT + Emojy.SMILE);
        }
        statistics.setHealth(currentHealth + health);
        if (statistics.getHealth() > max) {
            statistics.setHealth(max);
        }
        cat.setStatistics(statistics);
        catService.save(cat);
        long result = max - currentHealth;
        if (result > health) {
            result = health;
        }
        return HEALTH + " +" + result;
    }

    public String minusHealth(@NonNull Cat cat, int health) {
        Statistics statistics = getActualStatistics(cat);
        int currentHealth = statistics.getHealth();
        int min = 0;
        statistics.setHealth(currentHealth - health);
        if (statistics.getHealth() < min) {
            statistics.setHealth(min);
        }
        cat.setStatistics(statistics);
        catService.save(cat);
        if (health < currentHealth) {
            health = currentHealth;
        }
        return HEALTH + " -" + health;
    }

    public String plusSatiety(@NonNull Cat cat, int satiety) throws SatietyException {
        Statistics statistics = getActualStatistics(cat);
        int currentSatiety = statistics.getSatiety();
        int max = statistics.getMaxSatiety();
        if (currentSatiety == statistics.getMaxSatiety()) {
            throw new SatietyException(Emojy.SATIETY_EMOJY + " " + Texts.SATIETY_TEXT + Emojy.SMILE);
        }
        statistics.setSatiety(currentSatiety + satiety);
        if (statistics.getSatiety() > max) {
            statistics.setSatiety(max);
        }
        cat.setStatistics(statistics);
        catService.save(cat);
        long result = max - currentSatiety;
        if (result > satiety) {
            result = satiety;
        }
        return SATIETY + " +" + result;
    }

    public String minusSatiety(@NonNull Cat cat, int satiety) {
        Statistics statistics = getActualStatistics(cat);
        int currentSatiety = statistics.getSatiety();
        int min = 0;
        statistics.setSatiety(currentSatiety - satiety);
        if (statistics.getSatiety() < min) {
            statistics.setSatiety(min);
        }
        cat.setStatistics(statistics);
        catService.save(cat);
        if (satiety < currentSatiety) {
            satiety = currentSatiety;
        }
        return SATIETY + " -" + satiety;
    }

    public String plusHappy(@NonNull Cat cat, int happy) {
        Statistics statistics = getActualStatistics(cat);
        int currentHappy = statistics.getHappiness();
        int max = statistics.getMaxHappiness();
        if (currentHappy < max) {
            statistics.setHappiness(currentHappy + happy);
            if (statistics.getHappiness() > max) {
                statistics.setHappiness(max);
            }
            cat.setStatistics(statistics);
            catService.save(cat);
            long result = max - currentHappy;
            if (result > happy) {
                result = happy;
            }
            return HAPPY + " +" + result;
        }
        return "";
    }

    public String minusHappy(@NonNull Cat cat, int happy) {
        Statistics statistics = getActualStatistics(cat);
        int currentHappy = statistics.getHappiness();
        int min = 0;
        statistics.setHappiness(currentHappy - happy);
        if (statistics.getHappiness() < min) {
            statistics.setHappiness(min);
        }
        cat.setStatistics(statistics);
        catService.save(cat);
        if (happy < currentHappy) {
            happy = currentHappy;
        }
        return HEALTH + " -" + happy;
    }

    public String plusEnergy(@NonNull Cat cat, int energy) {
        Statistics statistics = getActualStatistics(cat);
        int currentEnergy = statistics.getEnergy();
        int max = statistics.getMaxEnergy();
        if (currentEnergy < max) {
            statistics.setEnergy(currentEnergy + energy);
            if (statistics.getEnergy() > max) {
                statistics.setEnergy(max);
            }
            cat.setStatistics(statistics);
            catService.save(cat);
            long result = max - currentEnergy;
            if (result > energy) {
                result = energy;
            }
            return Emojy.ENERGY_EMOJY + " +" + result;
        }
        return "";
    }

    public String minusEnergy(@NonNull Cat cat, int energy) {
        Statistics statistics = getActualStatistics(cat);
        int currentEnergy = statistics.getEnergy();
        int min = 0;
        statistics.setEnergy(currentEnergy - energy);
        if (statistics.getEnergy() < min) {
            statistics.setEnergy(min);
        }
        cat.setStatistics(statistics);
        catService.save(cat);
        if (energy < currentEnergy) {
            energy = currentEnergy;
        }
        return Emojy.ENERGY_EMOJY + " -" + energy;
    }
}
