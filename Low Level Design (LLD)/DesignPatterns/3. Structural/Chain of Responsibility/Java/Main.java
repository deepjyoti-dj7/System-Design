public class Main {
    public static void main(String[] args) {
        // Create handlers
        SupportHandler basic = new BasicSupportHandler();
        SupportHandler supervisor = new SupervisorSupportHandler();
        SupportHandler manager = new ManagerSupportHandler();

        // Chain them together
        basic.setNextHandler(supervisor);
        supervisor.setNextHandler(manager);

        // Send different types of requests
        SupportRequest r1 = new SupportRequest("basic");
        SupportRequest r2 = new SupportRequest("intermediate");
        SupportRequest r3 = new SupportRequest("advanced");
        SupportRequest r4 = new SupportRequest("unknown");

        basic.handleRequest(r1);  // Handled by Basic
        basic.handleRequest(r2);  // Handled by Supervisor
        basic.handleRequest(r3);  // Handled by Manager
        basic.handleRequest(r4);  // Unhandled
    }
}
