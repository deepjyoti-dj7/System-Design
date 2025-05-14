#include "include/ChatRoom.h"
#include "include/ConcreteUser.h"

int main() {
    ChatMediator* chatRoom = new ChatRoom();

    User* user1 = new ConcreteUser(chatRoom, "Alice");
    User* user2 = new ConcreteUser(chatRoom, "Bob");
    User* user3 = new ConcreteUser(chatRoom, "Charlie");

    chatRoom->addUser(user1);
    chatRoom->addUser(user2);
    chatRoom->addUser(user3);

    user1->send("Hi everyone!");
    user2->send("Hello Alice!");

    delete user1;
    delete user2;
    delete user3;
    delete chatRoom;

    return 0;
}
