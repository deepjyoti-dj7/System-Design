public class Main {
    public static void user1Logs() {
        Logger logger = Logger.getLogger();
        logger.Log("This message is from user1");
    }

    public static void user2Logs() {
        Logger logger = Logger.getLogger();
        logger.Log("This message is from user2");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(Main::user1Logs);
        Thread t2 = new Thread(Main::user2Logs);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
