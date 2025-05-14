public class SupervisorSupportHandler extends SupportHandler {

    @Override
    public void handleRequest(SupportRequest request) {
        if (request.getType().equalsIgnoreCase("intermediate")) {
            System.out.println("SupervisorSupportHandler: Handling intermediate request.");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("SupervisorSupportHandler: No handler found for " + request.getType());
        }
    }
}
