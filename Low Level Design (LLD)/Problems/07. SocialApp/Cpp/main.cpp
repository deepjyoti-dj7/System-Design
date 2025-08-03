#include "./include/SocialMediaApp.h"
#include <iostream>

int main() {
    SocialMediaApp app;
    auto alice = app.createUser("Alice");
    auto bob = app.createUser("Bob");
    auto carol = app.createUser("Carol");

    // Friend requests
    alice->sendFriendRequest(bob);
    bob->acceptFriendRequest(alice);

    // Posts and interactions
    auto post = app.createPost(alice, "Hello, I'm Alice!");
    post->addLike(bob);
    post->addLike(carol);

    auto comment = app.createComment(bob, "Welcome, Alice!");
    post->addComment(comment);

    // Stories
    auto story = app.createStory(alice, "This is my new story!");

    // Notifications (simple demo)
    bob->receiveNotification("Alice added a new post");

    // Print summary
    std::cout << "=== Social Media Demo ===\n";
    std::cout << "Post by: " << post->getAuthor()->getName() << "\n";
    std::cout << "Content: " << post->getContent() << "\n";
    std::cout << "Likes: " << post->getLikes().size() << "\n";
    std::cout << "Comments: " << post->getComments().size() << "\n";
    std::cout << "Story expired? " << (story->isExpired() ? "Yes" : "No") << "\n";
    std::cout << "Bob's notifications: " << (bob->getNotifications().empty() ? 0 : 1) << "\n";

    // Friend list
    std::cout << "Alice's friends: ";
    for (const auto& f : alice->getFriends()) {
        std::cout << f->getName() << " ";
    }
    std::cout << "\n";

    return 0;
}
