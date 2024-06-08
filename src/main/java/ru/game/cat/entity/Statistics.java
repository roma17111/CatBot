package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.game.cat.bot.emojy.Emojy;

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
    private int maxSatiety = MAX;

    @Column(name = "happiness", nullable = false)
    private int happiness = ZERO;

    @Column(name = "max_happiness", nullable = false)
    private int maxHappiness = MAX;

    @Column(name = "satiety_per_time", nullable = false)
    private int satietyPerTime;

    @Column(name = "energy", nullable = false)
    private int energy = 0;

    @Column(name = "max_energy", nullable = false)
    private int maxEnergy = 0;

    public String getInfo() {
        return String.format("""
                        <b>Показатели кошкатуна</b>
                                        
                        %s Здоровье %s/%s
                        %s Сытость %s/%s
                        %s Уровень счастья %s/%s
                        %s Энергия котика %s/%s
                        """,
                Emojy.HEALTH_EMOJY, this.getHealth(), this.getMaxHealth(),
                Emojy.SATIETY_EMOJY, this.getSatiety(), this.getMaxSatiety(),
                Emojy.HAPPY_EMOJY, this.getHappiness(), this.getMaxHappiness(),
                Emojy.ENERGY_EMOJY, this.getEnergy(), this.getMaxEnergy()
        );
    }
}
