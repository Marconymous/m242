package robi.controller;

import robi.RobiAPI;

public class RobiRunner {
    private static final int PORT = 55950;

    public static void run(Class<? extends RobiInstructions> robiClass, String[] args) {
        var instructions = Utils.classToRobiInstructions(robiClass);

        run(instructions, args);
    }

    public static void run(RobiInstructions instructions, String[] args) {
        var robiApi = new RobiApiV2("localhost", PORT);

        robiApi.connect();

        instructions.run(robiApi, args);

        robiApi.stop();
        robiApi.disconnect();
    }

    public interface RobiInstructions {
        void run(RobiApiV2 robi, String[] args);
    }
}
