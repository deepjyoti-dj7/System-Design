public class BasicSupportHandler extends SupportHandler {

    @Override
    public void handleRequest(SupportRequest request) {
        if (request.getType().equalsIgnoreCase("basic")) {
            System.out.println("BasicSupportHandler: Handling basic request.");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("BasicSupportHandler: No handler found for " + request.getType());
        }
    }
}
