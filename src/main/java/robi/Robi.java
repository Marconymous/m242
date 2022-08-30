package robi;

import static robi.Robi.States.*;

public class Robi {

    private RobiAPI robi;


    // Used to start the Robi
    public static void main(String[] args) {
        Robi r = new Robi("localhost",64750);   // Tragen Sie hier die Portnummer auf Ihrem Simulator ein.
//        r.planlosFahren(10);
        r.pendeln(2, new int[]{40, 2000, 1000, -20, 4000, 3000});
    }

    enum States {
        GoingFW,
        Wait1,
        GoingBW,
        Wait2
    }

    public Robi(String hostname, int portNummer) {
        robi = new RobiAPI(hostname, portNummer);
    }

    /**
     *
     * @param config 1. fwSpeed
     *               2. fwTime
     *               3. waitTime1
     *               4. bwSpeed
     *               5. bwTime
     *               6. waitTime2
     */
    public void pendeln(int times, int[] config) {
        robi.connect();

        for (int i = 0; i < times; i++) {
            var status = GoingFW;
            while (status != null) {
                switch (status) {
                    case GoingFW -> {
                        robi.drive(config[0]);
                        new SandUhr().warten(config[1]);
                        robi.stop();
                        status = Wait1;
                    }

                    case Wait1 -> {
                        new SandUhr().warten(config[2]);
                        status = GoingBW;
                    }

                    case GoingBW -> {
                        robi.drive(config[3]);
                        new SandUhr().warten(config[4]);
                        robi.stop();
                        status = Wait2;
                    }

                    case Wait2 -> {
                        new SandUhr().warten(config[5]);
                        status = null;
                    }
                }
            }
        }

        robi.setLeftDriveSpeed(-100);
        robi.setRightDriveSpeed(100);
        new SandUhr().warten(5000);

        robi.stop();


        robi.disconnect();
    }

    /*
     *	Der Roboter faehrt planlos herum. Er faehrt zuerst geradeaus vorwaerts, bis
     *	er irgendwo an eine Wand anstoesst. Danach faehrt er ein kurzes Stueck
     *	zurueck, dreht an Ort ab. Dann beginnt der naechste Zyklus. Das Verfahren
     *	ist auch unter dem Begriff "random walk" bekannt.
     */
    public void planlosFahren(int maxWandberuehrungen) {
        final int RobiFaehrtVorwaerts = 0;
        final int RobiFaehrtRueckwaerts = 1;
        final int RobiDrehtAb = 2;

        System.out.println("PLANLOS FAHREN");

        // Intitialisieren
        int anzahlWandberuehrungen = 0;
        SandUhr uhr = new SandUhr();
        int zustand = RobiFaehrtVorwaerts;


        robi.connect();
        robi.drive(5);
        System.out.println("Robi faehrt vorwaerts");

        while (anzahlWandberuehrungen < maxWandberuehrungen) {
            switch (zustand) {
                case RobiFaehrtVorwaerts -> {
                    robi.getDistSensorValues();
                    if (anWand()) {
                        sensorWerteAusgeben();
                        robi.drive(-10);
                        uhr.starten(1000);
                        zustand = RobiFaehrtRueckwaerts;
                        System.out.println("Robi faehrt rueckwaerts");
                    }

                }
                case RobiFaehrtRueckwaerts -> {
                    if (uhr.abgelaufen()) {
                        robi.turn(20);
                        uhr.starten(2000);
                        zustand = RobiDrehtAb;
                        System.out.println("Robi dreht ab");
                    }
                }
                case RobiDrehtAb -> {
                    if (uhr.abgelaufen()) {
                        anzahlWandberuehrungen++;
                        robi.drive(5);
                        zustand = RobiFaehrtVorwaerts;
                        System.out.println("Robi faehrt vorwaerts");
                    }
                }
            }
        }

        robi.setLeftDriveSpeed(-100);
        robi.setRightDriveSpeed(100);
        new SandUhr().warten(5000);

        robi.stop();
        robi.disconnect();

        System.out.println("FERTIG");
        System.out.println();
    }

    private boolean anWand() {
        for (int i = 2; i < 16; i++) {
            if (robi.readSensor(i) > 100) {
                return true;
            }
        }
        return false;
    }

    private void sensorWerteAusgeben() {
        for (int i = 2; i < 16; i++) {
            System.out.print(robi.readSensor(i) + " ");
        }
        System.out.println();
    }
}
