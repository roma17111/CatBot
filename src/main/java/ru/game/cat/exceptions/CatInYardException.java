package ru.game.cat.exceptions;

public class CatInYardException extends Exception{

    public CatInYardException() {
    }

    public CatInYardException(String message) {
        super(message);
    }
}
