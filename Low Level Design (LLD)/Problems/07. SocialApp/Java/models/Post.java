package models;

import java.util.*;

public class Post {
    private final int id;
    private final User author;
    private final String content;

    private final Set<User> likes = new HashSet<>();
    private final List<Comment> comments = new ArrayList<>();

    public Post(int id, User author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public void addLike(User user) {
        likes.add(user);
    }

    public void removeLike(User user) {
        likes.remove(user);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    // Getters
    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Set<User> getLikes() {
        return Collections.unmodifiableSet(likes);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
