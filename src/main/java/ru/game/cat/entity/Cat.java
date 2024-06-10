package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.game.cat.bot.emojy.Emojy;

import java.time.LocalDateTime;

import static ru.game.cat.utils.Numbers.HUNDRED;

@Entity
@Table(name = "cats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Cat {

    private static final String CAT_INFO = """
            Мой питомец:
                                    
            %s Котейка %s
            %s Уровень %s
            <b>XP</b> опыт %d%%
            %s <b>CatCoins</b> %s
            """;

    @Id
    @Column(name = "chat_id", nullable = false, unique = true)
    private long chatId;

    @Column(name = "cat_name", nullable = false, length = 30)
    private String catName;

    @Column(name = "firstname", length = 100)
    private String firstname;

    @Column(name = "lastname", length = 100)
    private String lastname;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "regDate", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "cat_coins", nullable = false)
    private long catCoins = 0;

    @Column(name = "level", nullable = false)
    private long level = 1;

    @Column(name = "xp", nullable = false)
    private long xp = 0;

    @Column(name = "xp_from_level", nullable = false)
    private long xpFromLevel = 0;

    @Column(name = "necessary_xp_for_up", nullable = false)
    private long necessaryXpForUp = 100;

    @Column(name = "is_banned", nullable = false)
    private boolean isBanned = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stats_id")
    private Statistics statistics;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @OneToOne
    @JoinColumn(name = "sticker_id")
    private Sticker sticker;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "milk_id")
    private MilkBonus milkBonus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "yard_id")
    private Yard yard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sleep_id")
    private Sleep sleep;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purr_id")
    private Purr purr;

    public long getPercent() {
        double divided = (double) this.xpFromLevel / this.necessaryXpForUp;
        return (long) (divided * HUNDRED);
    }

    public String getInfo() {
        return String.format(CAT_INFO,
                Emojy.CAT,
                this.catName,
                Emojy.LEVEL,
                getLevel(),
                getPercent(),
                Emojy.CAT_COINS, getCatCoins());
    }

}
