package tests;

import models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testNotificationReceived() {
        User user = new User(1, "Amy");
        user.receiveNotification("You've got mail!");
        assertFalse(user.getNotifications().isEmpty());
        assertEquals("You've got mail!", user.getNotifications().get(0).getMessage());
    }
}
