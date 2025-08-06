package services;

import models.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SocialMediaApp {
    private final AtomicInteger userId = new AtomicInteger(0);
    private final AtomicInteger postId = new AtomicInteger(0);
    private final AtomicInteger commentId = new AtomicInteger(0);
    private final AtomicInteger storyId = new AtomicInteger(0);

    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Post> posts = new HashMap<>();
    private final Map<Integer, Comment> comments = new HashMap<>();
    private final Map<Integer, Story> stories = new HashMap<>();

    public User createUser(String name) {
        int id = userId.getAndIncrement();
        User user = new User(id, name);
        users.put(id, user);
        return user;
    }

    public Post createPost(User author, String content) {
        int id = postId.getAndIncrement();
        Post post = new Post(id, author, content);
        posts.put(id, post);
        author.addPost(post);
        return post;
    }

    public Comment createComment(User author, String content) {
        int id = commentId.getAndIncrement();
        Comment comment = new Comment(id, author, content);
        comments.put(id, comment);
        return comment;
    }

    public Story createStory(User author, String content) {
        int id = storyId.getAndIncrement();
        Story story = new Story(id, author, content);
        stories.put(id, story);
        return story;
    }

    // Getters by id
    public Optional<User> getUser(int id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<Post> getPost(int id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Optional<Comment> getComment(int id) {
        return Optional.ofNullable(comments.get(id));
    }

    public Optional<Story> getStory(int id) {
        return Optional.ofNullable(stories.get(id));
    }
}
