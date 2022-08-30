package robi.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Utils {
    public static RobiRunner.RobiInstructions classToRobiInstructions(Class<? extends RobiRunner.RobiInstructions> robiClass) {
        try {
            Constructor<? extends RobiRunner.RobiInstructions> constructor = robiClass.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
