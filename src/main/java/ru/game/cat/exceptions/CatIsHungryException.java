package ru.game.cat.exceptions;

public class CatIsHungryException extends Exception{

    public CatIsHungryException() {
    }

    public CatIsHungryException(String message) {
        super(message);
    }
}
