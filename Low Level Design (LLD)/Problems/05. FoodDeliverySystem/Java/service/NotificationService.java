package service;

public class NotificationService {
    public void sendNotification(String message, String recipient) {
        System.out.println("Notification sent to " + recipient + ": " + message);
    }
}
