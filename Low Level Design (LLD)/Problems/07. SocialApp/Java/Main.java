import models.*;
import services.SocialMediaApp;

public class Main {
    public static void main(String[] args) {
        SocialMediaApp app = new SocialMediaApp();

        User alice = app.createUser("Alice");
        User bob = app.createUser("Bob");
        User carol = app.createUser("Carol");

        alice.sendFriendRequest(bob);
        bob.acceptFriendRequest(alice);

        Post post = app.createPost(alice, "Hello, I'm Alice!");
        post.addLike(bob);
        post.addLike(carol);

        Comment comment = app.createComment(bob, "Welcome, Alice!");
        post.addComment(comment);

        Story story = app.createStory(alice, "This is Alice's story.");

        bob.receiveNotification("Alice posted a new update!");

        System.out.println("Post by: " + post.getAuthor().getName());
        System.out.println("Content: " + post.getContent());
        System.out.println("Likes: " + post.getLikes().size());
        System.out.println("Comments: " + post.getComments().size());
        System.out.println("Is story expired? " + (story.isExpired() ? "Yes" : "No"));
        System.out.println("Bob's notifications:");
        bob.getNotifications().forEach(n -> System.out.println(" - " + n.getMessage()));

        System.out.print("Alice's friends: ");
        alice.getFriends().forEach(friend -> System.out.print(friend.getName() + " "));
    }
}
