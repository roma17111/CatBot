package ru.game.cat.exceptions;

public class EnergyIsEmptyException extends Exception{

    public EnergyIsEmptyException() {
    }

    public EnergyIsEmptyException(String message) {
        super(message);
    }
}
