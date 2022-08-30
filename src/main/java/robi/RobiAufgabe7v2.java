package robi;

public class RobiAufgabe7v2 extends RobiController {
    private static final int maxSpeed = 5;
    public static void main(String[] args) {
        new RobiAufgabe7();
    }

    public RobiAufgabe7v2() {
        super("localhost", 60195);
    }

    @Override
    public void run() {
        enum States {
            DriveLeft,
            DriveRight,
            DriveStraight
        }

        var status = States.DriveStraight;

        while (true) {
            robi.getDistSensorValues();
            var left = robi.readSensor(0);
            var right = robi.readSensor(1);
            if (left < 100 && right > 100) {
                status = States.DriveStraight;
            } else if (left < 100 && right < 100) {
                status = States.DriveLeft;
            } else if (left > 100 && right > 100) {
                status = States.DriveRight;
            }

            switch (status) {
                case DriveLeft -> {
                    robi.setLeftDriveSpeed(-(maxSpeed / 2));
                    robi.setRightDriveSpeed(maxSpeed);
                }

                case DriveRight -> {
                    robi.setLeftDriveSpeed(maxSpeed);
                    robi.setRightDriveSpeed(-(maxSpeed / 2));
                }

                case DriveStraight -> robi.drive(maxSpeed);
            }
        }
    }
}
