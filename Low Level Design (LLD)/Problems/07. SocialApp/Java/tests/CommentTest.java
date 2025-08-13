package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import models.*;

class CommentTest {

    @Test
    void testCreation() {
        User user = new User(1, "Tester");
        Comment comment = new Comment(1, user, "Test comment");
        assertEquals(1, comment.getId());
        assertEquals(user, comment.getAuthor());
        assertEquals("Test comment", comment.getContent());
    }
}
