package robi;

import java.util.Arrays;

/** VERBALE BESCHREIBUNG:
 *  Robi fährt gerade aus, bis ein Objekt in seinem Weg erscheint, er hält an bis das Objekt entfernt wird.
 *  Sobald dies getan ist fährt er wieder weiter.
 *
 *
 *
 *  VIDEO URL: https://youtu.be/xre_yW4Frlo
 */
public class RobiWaitTillObjectRemoved {

    private RobiAPI robi;


    // Used to start the Robi
    public static void main(String[] args) {
        var r = new RobiWaitTillObjectRemoved("localhost", 51953);   // Tragen Sie hier die Portnummer auf Ihrem Simulator ein.
        // hier Robi Methoden aufrufen
        r.templateMethod();

    }

    public RobiWaitTillObjectRemoved(String hostname, int portNummer) {
        robi = new RobiAPI(hostname, portNummer);
    }

    public boolean canDrive() {
        final int[] checkSensors = {12, 11, 10, 2, 4, 5, 6};

        robi.getDistSensorValues();
        return Arrays.stream(checkSensors).map(robi::readSensor).allMatch(value -> value == 0);
    }

    /*
     *	Template-Methode für den Robi
     */
    public void templateMethod() {
        // Variabel deklarieren
        final var FORWARD = 0;
        final var STOPPED = 1;
        var currentStatus = STOPPED;
        robi.connect();

        while (true) switch (currentStatus) {
            case FORWARD -> {
                if (!canDrive()) {
                    robi.stop();
                    currentStatus = STOPPED;
                }
            }

            case STOPPED -> {
                if (canDrive()) {
                    robi.drive(15);
                    currentStatus = FORWARD;
                }
            }
        }
    }

}
