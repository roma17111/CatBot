package ru.game.cat.bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class InventoryConfig {

    public static final int MAX_ROTATION_INVENTORY = 200;

    @Value("${bot.inventory.max-bad-food-satiety}")
    private int eat;

    @Value("${bot.inventory.max-minus-xp-bad-food}")
    private int maxMinusXpBadFood;
}
