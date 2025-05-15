#include <iostream>
#include "../include/ModerationState.h"
#include "../include/PublishedState.h"
#include "../include/Document.h"

void ModerationState::edit(Document* doc) {
    std::cout << "Cannot edit while in moderation.\n";
}

void ModerationState::publish(Document* doc) {
    std::cout << "Publishing document.\n";
    doc->setState(new PublishedState());
}
