package ru.game.cat.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.game.cat.service.yard.LootExecutor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class YardLootFactory {

    private final ApplicationContext applicationContext;

    public List<LootExecutor> getAllObjects() {
        var objects = applicationContext.getBeansOfType(LootExecutor.class);
        return objects.values().stream().toList();
    }
}
