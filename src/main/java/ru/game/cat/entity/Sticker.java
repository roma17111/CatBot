package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "stickers")
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private long stickerId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "path", nullable = false, length = 1000)
    private String path;
}
