package ru.game.cat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "toys")
public class Toy {

    @Id
    @Column(name = "toy_id",nullable = false)
    private UUID uuid;

    @Column(name = "max_games",nullable = false)
    private long maxGames = 15;

    @Column(name = "total_games", nullable = false)
    private long totalGames = 0;


}
