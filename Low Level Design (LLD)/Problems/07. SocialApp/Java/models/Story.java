package models;

import java.time.Duration;
import java.time.Instant;

public class Story {
    private final int id;
    private final User author;
    private final String content;
    private final Instant createdAt;

    public Story(int id, User author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = Instant.now();
    }

    public boolean isExpired() {
        return Duration.between(createdAt, Instant.now()).toHours() > 24;
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

    public Instant getCreatedAt() {
        return createdAt;
    }
}
