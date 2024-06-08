package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "sleeps")
public class Sleep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sleep_id", nullable = false)
    private long sleepId;

    @Column(name = "is_sleep", nullable = false)
    private boolean isSleep;

    @Column(name = "amount_sleeps", nullable = false)
    private int amountSleeps = 0;

    @Column(name = "check_date", nullable = false)
    private LocalDateTime checkDate = now();

    @Column(name = "minutes_for_sleep", nullable = false)
    private int minutesForSleep = 180;

    @Column(name = "energy_after_sleep", nullable = false)
    private int energyAfterSleep = 50;

}
