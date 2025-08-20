package tests;

import models.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StoryTest {

    @Test
    void testIsExpiredRightAfterCreation() {
        User user = new User(1, "Eve");
        Story story = new Story(1, user, "My story");
        assertFalse(story.isExpired());
    }
}
