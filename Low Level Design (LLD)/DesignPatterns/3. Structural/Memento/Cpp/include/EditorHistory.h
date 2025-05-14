#pragma once
#include <stack>
#include "EditorMemento.h"

class EditorHistory {
private:
    std::stack<EditorMemento> history;

public:
    void save(const EditorMemento& memento);
    EditorMemento undo();
    bool hasHistory() const;
};
