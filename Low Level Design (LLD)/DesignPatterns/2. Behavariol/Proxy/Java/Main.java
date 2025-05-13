public class Main {
    public static void main(String[] args) {
        Internet internet = new ProxyInternet();
        try {
            internet.connectTo("example.com");
            internet.connectTo("abc.com"); // should throw an exception
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
