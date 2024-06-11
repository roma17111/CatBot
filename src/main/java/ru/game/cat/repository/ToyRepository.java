package ru.game.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.game.cat.entity.Toy;

import java.util.UUID;

@Repository
public interface ToyRepository extends JpaRepository<Toy, UUID> {
}
