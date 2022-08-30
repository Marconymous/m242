package robi;

public abstract class RobiController {
    protected final RobiAPI robi;

    public RobiController() {
        this("localhost", 56748);
    }

    public RobiController(String hostname, int portNummer) {
        robi = new RobiAPI(hostname, portNummer);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("ShutdownHook")));

        robi.connect();
        run();
        robi.stop();
        robi.disconnect();
    }

    public abstract void run();
}
