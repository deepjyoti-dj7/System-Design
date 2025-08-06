package models;

public class Comment {
    private final int id;
    private final User author;
    private final String content;

    public Comment(int id, User author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
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
}
