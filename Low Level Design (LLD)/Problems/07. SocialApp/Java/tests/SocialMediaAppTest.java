package tests;

import models.*;
import services.SocialMediaApp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocialMediaAppTest {

    @Test
    void testBasicFlow() {
        SocialMediaApp app = new SocialMediaApp();
        User u1 = app.createUser("Sam");
        User u2 = app.createUser("Max");

        Post post = app.createPost(u1, "Hello World!");
        assertEquals(u1, post.getAuthor());

        post.addLike(u2);
        assertEquals(1, post.getLikes().size());

        Comment comment = app.createComment(u2, "Nice!");
        post.addComment(comment);
        assertEquals(1, post.getComments().size());

        Story story = app.createStory(u1, "My Story");
        assertFalse(story.isExpired());
    }
}
