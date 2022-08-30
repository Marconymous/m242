package robi;

public class RobiAufgabe8 extends RobiController {
    public static void main(String[] args) {
        new RobiAufgabe8();
    }

    @Override
    public void run() {
        enum States {
            AtFirstWall,
            InBetweenWalls,
            WalkingBack,
            DriveIntoSpot
        }
        var state = States.AtFirstWall;
        robi.drive(20);

        long firstWallTime = 0;
        long secondWallTime = 0;
        while (true) {
            robi.getDistSensorValues();
            switch (state) {
                case AtFirstWall -> {
                    if (robi.readSensor(7) < 100) {
                        firstWallTime = System.currentTimeMillis();
                        state = States.InBetweenWalls;
                    }
                }

                case InBetweenWalls -> {
                    if (robi.readSensor(6) > 100) {
                        secondWallTime = System.currentTimeMillis();
                        robi.stop();
                        state = States.WalkingBack;
                    }
                }

                case WalkingBack -> {
                    var sw = new SandUhr();
                    var backTime = (secondWallTime - firstWallTime) / 2;
                    robi.drive(-20);
                    sw.warten((int) backTime);
                    robi.turn(-45);
                    sw.warten(2200);
                    robi.stop();
                    robi.drive(20);

                    state = States.DriveIntoSpot;
                }

                case DriveIntoSpot -> {
                    if (robi.readSensor(2) > 100) {
                        robi.stop();
                        return;
                    }
                }
            }
        }
    }
}
