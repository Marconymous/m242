package robi;

public class RobiAufgabe7 extends RobiController {
    public static void main(String[] args) {
        new RobiAufgabe7();
    }

    public RobiAufgabe7() {
        super("localhost", 60195);


    }

    @Override
    public void run() {
        enum States {
            DriveLeft,
            DriveRight
        }

        var status = States.DriveLeft;

        while (true) {
            robi.getDistSensorValues();
            status = robi.readSensor(1) > 100 ? States.DriveRight : States.DriveLeft;

            switch (status) {
                case DriveLeft -> {
                    robi.setLeftDriveSpeed(-50);
                    robi.setRightDriveSpeed(100);
                }

                case DriveRight -> {
                    robi.setLeftDriveSpeed(100);
                    robi.setRightDriveSpeed(-50);
                }
            }
        }
    }
}
