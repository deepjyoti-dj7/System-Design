public abstract class SupportHandler {
    protected SupportHandler nextHandler;

    public void setNextHandler(SupportHandler handler) {
        this.nextHandler = handler;
    }

    public abstract void handleRequest(SupportRequest request);
}
