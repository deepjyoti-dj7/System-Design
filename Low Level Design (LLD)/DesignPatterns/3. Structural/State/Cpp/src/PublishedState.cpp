#include <iostream>
#include "../include/PublishedState.h"
#include "../include/Document.h"

void PublishedState::edit(Document* doc) {
    std::cout << "Cannot edit a published document.\n";
}

void PublishedState::publish(Document* doc) {
    std::cout << "Document is already published.\n";
}
