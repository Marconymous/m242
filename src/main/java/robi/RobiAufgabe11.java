package robi;

import robi.controller.RobiApiV2;
import robi.controller.RobiRunner;

public class RobiAufgabe11 implements RobiRunner.RobiInstructions {
    public static void main(String[] args) {
        RobiRunner.run(RobiAufgabe11.class, args);
    }

    enum States {
        Forward,
        CorrectLeft,
        CorrectRight,
        Done
    }

    @Override
    public void run(RobiApiV2 robi, String[] args) {
        final var SIDE_SENSOR_NUMBER = 6;
        final var SIDE_FRONT_SENSOR_NUMBER = 5;
        var state = States.Forward;

        whileLoop:
        while (true) {
            robi.getDistSensorValues();
            switch (state) {
                case Forward -> {
                    robi.drive(15);
                    var frontDist = robi.readSensor(2);
                    if (frontDist > 100) {
                        state = States.Done;
                        break;
                    }

                    // check if too left
                    var leftFrontDist = robi.readSensor(SIDE_FRONT_SENSOR_NUMBER);
                    if (leftFrontDist > 100) {
                        state = States.CorrectLeft;
                        break;
                    }

                    // check if to right
                    var leftDist = robi.readSensor(SIDE_SENSOR_NUMBER);
                    if (leftDist < 100) {
                        state = States.CorrectRight;
                    }

                }

                case CorrectLeft -> {
                    robi.drive(20, 10);

                    var distance = robi.readSensor(SIDE_FRONT_SENSOR_NUMBER);
                    if (distance < 100) {
                        state = States.Forward;
                    }
                }

                case CorrectRight -> {
                    robi.drive(10,15);

                    var distance = robi.readSensor(SIDE_FRONT_SENSOR_NUMBER);
                    if (distance > 100) {
                        state = States.Forward;
                    }
                }

                case Done -> {
                    break whileLoop;
                }
            }
        }
    }
}
