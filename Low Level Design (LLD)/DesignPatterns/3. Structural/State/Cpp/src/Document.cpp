#include "../include/Document.h"
#include "../include/DraftState.h"

Document::Document() {
    state = new DraftState();
}

Document::~Document() {
    delete state;
}

void Document::setState(State* newState) {
    delete state;
    state = newState;
}

void Document::edit() {
    state->edit(this);
}

void Document::publish() {
    state->publish(this);
}
