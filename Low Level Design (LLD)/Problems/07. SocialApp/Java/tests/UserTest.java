package tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import models.*;

class UserTest {

    @Test
    void testAddRemoveFriend() {
        User u1 = new User(1, "Alice");
        User u2 = new User(2, "Bob");
        u1.addFriend(u2);
        assertTrue(u1.getFriends().contains(u2));
        u1.removeFriend(u2);
        assertFalse(u1.getFriends().contains(u2));
    }

    @Test
    void testFriendRequests() {
        User u1 = new User(1, "Tom");
        User u2 = new User(2, "Jerry");
        u1.sendFriendRequest(u2);
        u2.acceptFriendRequest(u1);
        assertTrue(u1.getFriends().contains(u2));
        assertTrue(u2.getFriends().contains(u1));
    }
}
