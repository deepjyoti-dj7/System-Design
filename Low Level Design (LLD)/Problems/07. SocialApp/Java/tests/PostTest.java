package tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import models.*;

class PostTest {

    @Test
    void testCreatePostAndLikes() {
        User user = new User(1, "DJ");
        Post post = new Post(1, user, "First Post!");
        assertEquals(post.getAuthor(), user);
        post.addLike(user);
        assertEquals(1, post.getLikes().size());
        post.removeLike(user);
        assertTrue(post.getLikes().isEmpty());
    }

    @Test
    void testAddComment() {
        User user = new User(2, "A");
        Post post = new Post(2, user, "Another Post!");
        Comment comment = new Comment(1, user, "Nice post!");
        post.addComment(comment);
        assertEquals(1, post.getComments().size());
        assertEquals(comment, post.getComments().get(0));
    }
}
