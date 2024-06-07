package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "milk_bonuses")
public class MilkBonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milk_id", nullable = false)
    private long milkId;

    @Column(name = "check_date", nullable = false)
    private LocalDateTime checkDate = LocalDateTime.now().minusYears(100);

    @Column(name = "amount", nullable = false)
    private int amount = 0;

    @Column(name = "period_per_hour", nullable = false)
    private int periodPerHour = 3;

    @Column(name = "satiety", nullable = false)
    private int satiety = 100;

    @Column(name = "health", nullable = false)
    private int health = 20;
}
