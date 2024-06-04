package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "statistics")
public class Statistics {

    private static final int MAX = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stats_id", nullable = false)
    private long statsId;

    @Column(name = "health", nullable = false)
    private int health = ZERO;

    @Column(name = "max_health", nullable = false)
    private int maxHealth = MAX;

    @Column(name = "satiety", nullable = false)
    private int satiety = 50;

    @Column(name = "max_satiety", nullable = false)
    private int max_satiety = MAX;

    @Column(name = "happiness", nullable = false)
    private int happiness = ZERO;

    @Column(name = "max_happiness", nullable = false)
    private int max_happiness = MAX;


}
