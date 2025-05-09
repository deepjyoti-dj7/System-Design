public class Logger {

    private static Logger loggerInstance = null;
    private static int counter = 0;

    private Logger() {
        counter++;
        System.out.println("Instance created, number of instances: " + counter);
    }

    public static ThreadSafeSingleton getInstanceUsingDoubleLocking() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }

    public void Log(String message) {
        System.out.println(message);
    }
}
