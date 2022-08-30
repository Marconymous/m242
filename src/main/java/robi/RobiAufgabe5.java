package robi;

import java.util.Arrays;

public class RobiAufgabe5 extends RobiController {
    public static void main(String[] args) {
        new RobiAufgabe5();
    }

    public RobiAufgabe5() {
        super("localhost", 60195);
    }

    @Override
    public void run() {
        final var turningLeft = 0;
        final var turningRight = 1;
        final var forward = 2;
        var current = forward;

        robi.drive(15);
        while (true) switch (current) {
            case forward -> {
                var dir = checkDirection();
                switch (dir) {
                    case -1 -> {
                        current = turningLeft;
                        robi.setRightDriveSpeed(15);
                        robi.setLeftDriveSpeed(-5);
                    }

                    case 1 -> {
                        current = turningRight;
                        robi.setLeftDriveSpeed(15);
                        robi.setRightDriveSpeed(-5);
                    }
                }
            }

            case turningLeft -> current = turningAlgo() ? forward : turningLeft;
            case turningRight -> current = turningAlgo() ? forward : turningRight;
        }
    }

    private boolean turningAlgo() {
        robi.getDistSensorValues();

        if (robi.readSensor(2) > 0) {
            robi.drive(15);
            return true;
        }

        return false;
    }

    private int checkDirection() {
        int[] left = {4, 5, 6, 7, 8, 9, 3};
        int[] all = {4, 5, 6, 7, 8, 9, 3, 10, 11, 12, 13, 14, 15};

        robi.getDistSensorValues();

        for (int i : all) {
            if (robi.readSensor(i) > 0 && robi.readSensor(i) < 256) {
                if (Arrays.stream(left).anyMatch(l -> l == i)) {
                    return -1;
                }

                return 1;
            }
        }

        return 0;
    }
}
