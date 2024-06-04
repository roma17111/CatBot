package ru.game.cat.entity;

import io.micrometer.common.lang.NonNullApi;
import jakarta.persistence.*;
import lombok.*;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    private long inventoryId;

    @Column(name = "rat_tail", nullable = false)
    private long ratTail = ZERO;

    @Column(name = "mouse_paws", nullable = false)
    private long mousePaws = ZERO;

    @Column(name = "tin_can", nullable = false)
    private long tinCan = ZERO;
}
