public class Logger {

    private static int counter = 0;

    private Logger() {
        counter++;
        System.out.println("Instance created, number of instances: " + counter);
    }

    private static class LoggerHelper {
        private static final Logger INSTANCE = new Logger();
    }

    public static Logger getLogger() {
        return LoggerHelper.INSTANCE;
    }

    public void log(String message) {
        System.out.println(message);
    }
}
