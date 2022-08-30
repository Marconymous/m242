package robi;

import java.util.Arrays;

public class RobiAufgabe12 extends RobiController {
    public static void main(String[] args) {
        new RobiAufgabe12();
    }

    @Override
    public void run() {
        robi.drive(30);
        while (true) {
            if (checkIfShouldTurn()) {
                randomTurn();
            }
        }
    }

    private void randomTurn() {
        robi.stop();

        var turnSpeed = Math.random() * 200 - 100;
        robi.turn((int) turnSpeed);
        new SandUhr().warten((int) (Math.random() * 2000));
        robi.drive(30);
    }

    private boolean checkIfShouldTurn() {
        robi.getDistSensorValues();

        final int[] sensors = {2, 4, 5, 6, 10, 11, 12};
        return Arrays.stream(sensors).map(robi::readSensor).anyMatch(value -> value > 100);
    }
}
