package robi;

public class RobiAufgabe6 extends RobiController{
    public RobiAufgabe6() {
        super("localhost", 60195);
    }

    public static void main(String[] args) {
        new RobiAufgabe6();
    }

    @Override
    public void run() {
        robi.drive(15);

        var counter = 0;
        var lastColor = 0;
        while (true) {
            robi.getDistSensorValues();
            var current = robi.readSensor(0);
            if (current == 0 && lastColor != 0) {
                counter++;
            }
            lastColor = current;
            if (current == 255 && counter == 3) {
                break;
            }
        }

        robi.stop();
    }
}
