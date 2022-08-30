package robi;

public class RobiAufgabe10 extends RobiController{
    public static void main(String[] args) {
        new RobiAufgabe10();
    }

    @Override
    public void run() {
        enum States {
            FindForwEnd,
            FindBackEnd,
            Done
        }

        var state = States.FindForwEnd;
        var forwardStartTime = System.currentTimeMillis();
        var forwardEndTime = 0L;
        var endEndTime = 0L;

        robi.drive(10);
        while (state != States.Done) switch (state) {
            case FindForwEnd -> {
                robi.getDistSensorValues();
                if (robi.readSensor(6) < 100) {
                    forwardEndTime = System.currentTimeMillis();
                    state = States.FindBackEnd;
                    robi.drive(-10);
                }
            }

            case FindBackEnd -> {
                robi.getDistSensorValues();
                if (robi.readSensor(7) < 100) {
                    endEndTime = System.currentTimeMillis();
                    state = States.Done;
                    robi.stop();
                }
            }
        }
        var speed = (forwardEndTime - forwardStartTime > endEndTime - forwardEndTime) ? -20 : 20;
        robi.turn(speed);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
