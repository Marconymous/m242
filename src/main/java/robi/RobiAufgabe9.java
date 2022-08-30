package robi;

public class RobiAufgabe9 extends RobiController {
    public static void main(String[] args) {
        new RobiAufgabe9();
    }

    @Override
    public void run() {
        var speed = 100;
        var atSpeed20 = 17750;
        var time = (int) (atSpeed20 * (20f / speed)) * 2.5;
        while (true) {
            robi.setRightDriveSpeed(0);
            robi.setLeftDriveSpeed(speed);
            new SandUhr().warten((int) time);
            robi.setRightDriveSpeed(speed);
            robi.setLeftDriveSpeed(0);
            new SandUhr().warten((int) time);
            robi.stop();
        }
    }
}
