#include <iostream>
#include "../include/DraftState.h"
#include "../include/ModerationState.h"
#include "../include/Document.h"

void DraftState::edit(Document* doc) {
    std::cout << "Editing document in draft.\n";
}

void DraftState::publish(Document* doc) {
    std::cout << "Submitting document for review.\n";
    doc->setState(new ModerationState());
}
