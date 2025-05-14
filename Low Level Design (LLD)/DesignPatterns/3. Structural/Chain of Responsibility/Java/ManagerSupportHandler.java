public class ManagerSupportHandler extends SupportHandler {

    @Override
    public void handleRequest(SupportRequest request) {
        if (request.getType().equalsIgnoreCase("advanced")) {
            System.out.println("ManagerSupportHandler: Handling advanced request.");
        } else {
            System.out.println("ManagerSupportHandler: Cannot handle request of type " + request.getType());
        }
    }
}
