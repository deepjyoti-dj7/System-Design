public class Logger {

    private static Logger loggerInstance = null;
    private static int counter = 0;

    private Logger() {
        counter++;
        System.out.println("Instance created, number of instances: " + counter);
    }

    public static synchronized Logger getLogger() {
        if (loggerInstance == null) {
            loggerInstance = new Logger();
        }
        return loggerInstance;
    }

    public void Log(String message) {
        System.out.println(message);
    }
}
