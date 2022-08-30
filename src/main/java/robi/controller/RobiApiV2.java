package robi.controller;

import robi.RobiAPI;
import robi.SandUhr;

public class RobiApiV2 extends RobiAPI {
    public RobiApiV2(String hostname) {
        super(hostname);
    }

    public RobiApiV2(String hostname, int port) {
        super(hostname, port);
    }

    public void drive(int left, int right) {
        setLeftDriveSpeed(left);
        setRightDriveSpeed(right);
    }

    public void driveLeftDifference(int speed, int difference) {
        setLeftDriveSpeed(speed + difference);
        setRightDriveSpeed(speed);
    }

    public void driveRightDifference(int speed, int difference) {
        setLeftDriveSpeed(speed);
        setRightDriveSpeed(speed + difference);
    }
}
