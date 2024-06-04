package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;

import java.time.LocalDateTime;

@Entity
@Table(name = "cats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Cat {

    private static final int HUNDRED = 100;

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
    private long necessary_xp_for_up = 100;

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

    public String getPercent() {
        return String.valueOf(xpFromLevel / necessary_xp_for_up * HUNDRED);
    }

}
