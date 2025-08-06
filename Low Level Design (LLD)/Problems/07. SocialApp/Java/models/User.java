package models;

import java.util.*;

public class User {
    private final int id;
    private final String name;

    private final Set<User> friends = new HashSet<>();
    private final Set<User> incomingRequests = new HashSet<>();
    private final Set<User> outgoingRequests = new HashSet<>();
    private final List<Post> posts = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addFriend(User user) {
        friends.add(user);
    }

    public void removeFriend(User user) {
        friends.remove(user);
    }

    public void sendFriendRequest(User to) {
        if (to == this || friends.contains(to) || outgoingRequests.contains(to)) return;
        outgoingRequests.add(to);
        to.receiveFriendRequest(this);
    }

    public void receiveFriendRequest(User from) {
        incomingRequests.add(from);
    }

    public void acceptFriendRequest(User from) {
        if (incomingRequests.remove(from)) {
            addFriend(from);
            from.addFriend(this);
            from.outgoingRequests.remove(this);
        }
    }

    public void rejectFriendRequest(User from) {
        incomingRequests.remove(from);
        from.outgoingRequests.remove(this);
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void receiveNotification(String message) {
        notifications.add(new Notification(message));
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    public List<Post> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    public List<Notification> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    public Set<User> getIncomingRequests() {
        return Collections.unmodifiableSet(incomingRequests);
    }

    public Set<User> getOutgoingRequests() {
        return Collections.unmodifiableSet(outgoingRequests);
    }
}
