package ru.game.cat.service;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.annotations.CheckEventsCallbackQuery;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Inventory;
import ru.game.cat.entity.Statistics;
import ru.game.cat.entity.Toy;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.enums.StickerNames;
import ru.game.cat.factory.CallbacksFactory;
import ru.game.cat.repository.ToyRepository;
import ru.game.cat.service.purr.CatCoinsLootExecutor;
import ru.game.cat.service.yard.loot.XPLootExecutor;
import ru.game.cat.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.game.cat.bot.emojy.Emojy.CAT_INFO_EMOJY;
import static ru.game.cat.bot.emojy.Emojy.TOTAL_EMOJY;
import static ru.game.cat.enums.StickerNames.*;
import static ru.game.cat.factory.CallbacksFactory.PLAY_TOY_CALLBACK;
import static ru.game.cat.utils.Texts.*;

@Service
@RequiredArgsConstructor
public class PlayToyService {

    private final CatService catService;
    private final ToyRepository toyRepository;
    private final StickersService stickersService;
    private final MessageSender messageSender;
    private final CatCoinsLootExecutor catCoinsLootExecutor;
    private final XPLootExecutor xpLootExecutor;

    @Value("${bot.toy.max-random-xp}")
    private int maxRandomXp;

    @Value("${bot.toy.max-random-cash}")
    private int maxRandomCash;

    @Value("${bot.toy.min-random-satiety}")
    private int minRandomSatiety;
    @Value("${bot.toy.min-random-energy}")
    private int minRandomEnergy;

    @Value("${bot.toy.max-random-satiety}")
    private int maxRandomSatiety;
    @Value("${bot.toy.max-random-energy}")
    private int maxRandomEnergy;

    private static final List<String> WAYS_TO_GET_TOY = List.of(
            String.format("/%s - %s %s", BotCommands.YARD.getCommand(), Emojy.YARD_TREE_EMOJY, BotCommands.YARD.getDescription())
    );

    @PostConstruct
    public void init() {
        if (minRandomEnergy >= maxRandomEnergy) {
            throw new BeanCreationException("Минимальная энергия не должна быть больше максимальной");
        } else if (minRandomSatiety >= maxRandomSatiety) {
            throw new BeanCreationException("Минимальная сытость не может быть больше максимальной");
        }
    }

    private void initToyNotFoundMessage(@NonNull Update update) {
        StringBuilder ways = new StringBuilder(TOY_NOT_FOUND_TEXT);
        ways.append("Способы получения:\n");
        for (int i = 0; i < WAYS_TO_GET_TOY.size(); i++) {
            ways.append(String.format("%d. %s", i + 1, WAYS_TO_GET_TOY.get(i))).append("\n");
        }
        messageSender.sendMessage(update.getMessage().getChatId(), ways.toString());
    }

    private static final List<StickerNames> stickers = List.of(
            PLAY_TOY_ONE,
            PLAY_TOY_TWO
    );

    public void executeCommand(@NonNull Update update) {
        initMainToySticker(update);
        Cat cat = catService.findActualCat(update);
        if (!cat.hasToy()) {
            initToyNotFoundMessage(update);
        } else {
            initToyInfoMessage(update, cat);
        }
    }

    public void initToyInfoMessage(@NonNull Update update, @NonNull Cat cat) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder()
                .text(Emojy.PLAY_GAME_EMOJY + "Играть")
                .callbackData(PLAY_TOY_CALLBACK)
                .build());
        rows.add(row);
        markup.setKeyboard(rows);

        String text = Emojy.GAME_CUBE_EMOJY + CAT_INFO_EMOJY + " Осталось игр - " + cat.getInventory().getToy().getTotalGames();
        messageSender.sendMessageWithKeyboard(update.getMessage().getChatId(), text, markup);
    }

    @CheckEventsCallbackQuery(checkEnergy = true, checkSleep = true, checkEat = true)
    @Transactional
    public void playGameCallback(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        if (!cat.hasToy()) {
            messageSender.sendAlert(update,Emojy.CAT_ERROR_EMOJY+" Котик, у тебя нет игрушки");
            return;
        }
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        messageSender.deleteMessage(chatId, messageId);
        initFinishSticker(update);
        initPlayingToy(update);
        messageSender.sendMessage(chatId, getXpAndCash(update, cat));
        if (!cat.hasToy()) {
            initBreakToy(update);
        }
    }

    private String getXpAndCash(Update update, Cat cat) {
        StringBuilder builder = new StringBuilder(Emojy.GAME_CUBE_EMOJY + " За игру котёнок получил\n\n");
        String xp = xpLootExecutor.getLoot(update, cat, RandomUtils.getRandomNumber(maxRandomXp));
        String cash = catCoinsLootExecutor.getLoot(cat, RandomUtils.getRandomNumber(maxRandomCash));
        builder.append(xp).append("\n");
        builder.append(cash).append("\n");
        return builder.toString();
    }

    private void initPlayingToy(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        Toy toy = catService.findActualCat(update).getInventory().getToy();

        if (toy == null) {
            return;
        }
        long games = toy.getTotalGames();
        if (games == 1) {
            deleteToy(update);
        } else {
            toy.setTotalGames(games - 1);
            toyRepository.save(toy);
        }
        long randomEnergy = RandomUtils.getRandomNumber(minRandomEnergy, maxRandomEnergy);
        long randomSatiety = RandomUtils.getRandomNumber(minRandomSatiety, maxRandomSatiety);
        Statistics statistics = cat.getStatistics();
        statistics.setEnergy((int) (statistics.getEnergy() - randomEnergy));
        statistics.setSatiety((int) (statistics.getSatiety() - randomSatiety));
        cat.setStatistics(statistics);
        catService.save(cat);
    }

    public void initFindToyEvent(@NonNull Update update, @NonNull Cat cat) {
        Toy toy = addToyToCat(update, cat);
        messageSender.sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                SUCCESSFULLY_FIND_TOY_TEXT + "\n" +
                        TOTAL_EMOJY + " <b>Количество игр </b> - " + toy.getTotalGames() + "\n"
                        + Emojy.PLAY_GAME_EMOJY + " Поиграть с игрушкой - /" + BotCommands.TOY.getCommand());
    }

    public Toy addToyToCat(@NonNull Update update, @NonNull Cat cat) {
        long random = RandomUtils.getRandomNumber(5, 15);
        Toy toy = Toy.builder()
                .uuid(UUID.randomUUID())
                .totalGames(random)
                .build();
        Inventory inventory = cat.getInventory();
        inventory.setToy(toy);
        cat.setInventory(inventory);
        catService.save(cat);
        return toy;
    }

    public Toy addToyToCat(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        return addToyToCat(update, cat);
    }

    public void deleteToy(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        Inventory inventory = cat.getInventory();
        Toy toy = inventory.getToy();
        if (toy != null) {
            inventory.setToy(null);
            toyRepository.delete(toy);
            cat.setInventory(inventory);
            catService.save(cat);
        }
    }


    private void initMainToySticker(@NonNull Update update) {
        stickersService.executeSticker(update, stickersService.findById(MAIN_TOY_STICKER));
    }

    private void initFinishSticker(@NonNull Update update) {
        long random = RandomUtils.getRandomNumber(0, stickers.size());
        StickerNames stickerNames = stickers.get((int) random);
        stickersService.executeSticker(update, stickersService.findById(stickerNames));
    }

    private void initBreakToy(@NonNull Update update) {
        StringBuilder ways = new StringBuilder(BREAK_TOY_TEXT + "\n");
        ways.append("Способы получения новой игрушки:\n");
        for (int i = 0; i < WAYS_TO_GET_TOY.size(); i++) {
            ways.append(String.format("%d. %s", i + 1, WAYS_TO_GET_TOY.get(i))).append("\n");
        }
        messageSender.sendMessage(update.getCallbackQuery().getMessage().getChatId(), ways.toString());
    }

    public void initMessageForInventory(@NonNull Update update) {
        messageSender.sendMessageDialog(update,
                Emojy.CAT + Emojy.TOY_EMOJY + "Поиграть с игрушкой можно прописав команду /" + BotCommands.TOY.getCommand());
    }
}
