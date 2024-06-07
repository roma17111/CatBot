package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "yards")
public class Yard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yard_id", nullable = false)
    private long yardId;

    @Column(name = "check_date")
    private LocalDateTime checkDate;

    @Column(name = "max_loot", nullable = false)
    private int maxLoot = 2;

    @Column(name = "current_walk_minutes", nullable = false)
    private int currentWalkMinutes;

    @Column(name = "total_walk_minutes")
    private int totalWalkMinutes;

    @Column(name = "is_in_the_walk", nullable = false)
    private boolean isInTheWalk = false;

    @Column(name = "is_meet_adventure", nullable = false)
    private boolean isMeetAdventure = false;

    @Column(name = "max_xp",nullable = false)
    private int maxXp = 5;
}
