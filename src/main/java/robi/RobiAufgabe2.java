package robi;

public class RobiAufgabe2 extends RobiController{
    public RobiAufgabe2() {
        super("localhost", 58664);
    }

    public static void main(String[] args) {
        new RobiAufgabe2();
    }

    @Override
    public void run() {
        final var A = 0;
        final var B = 1;
        final var C = 2;
        var status = A;

        var sw = new SandUhr();
        sw.starten(5000);
        while (true) switch (status) {
            case A -> {
                // check for C
                robi.getDistSensorValues();
                if (robi.readSensor(3) > 100) {
                    robi.stop();
                    status = C;
                    break;
                }

                // check for B
                if (sw.abgelaufen()) {
                    robi.drive(15);
                    status = B;
                }
            }

            default -> status = A;
        }
    }
}
