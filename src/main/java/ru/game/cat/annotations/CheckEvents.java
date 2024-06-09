package ru.game.cat.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface CheckEvents {

    boolean checkSleep() default false;
    boolean checkEnergy() default false;
    boolean checkYard() default false;
}
