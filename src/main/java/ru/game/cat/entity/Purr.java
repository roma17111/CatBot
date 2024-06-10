package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static ru.game.cat.utils.Numbers.HUNDRED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "purrs")
public class Purr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purr_id", nullable = false)
    private long purrId;

    @Column(name = "count_purrs", nullable = false)
    private long countPurrs = 0;

    @Column(name = "check_date", nullable = false)
    private LocalDateTime checkDate = LocalDateTime.now().minusYears(HUNDRED);

    @Column(name = "hours_interval", nullable = false)
    private int hoursInterval;

    @Column(name = "max_poor_loot",nullable = false)
    private long maxPurrLoot = 0;
}
