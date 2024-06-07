package ru.game.cat.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.game.cat.enums.StickerNames;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "stickers")
public class Sticker {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "sticker_id")
    private StickerNames stickerId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "path", nullable = false, length = 1000)
    private String path;

    @Column(name = "file_id",length = 1000)
    private String fileId;
}
