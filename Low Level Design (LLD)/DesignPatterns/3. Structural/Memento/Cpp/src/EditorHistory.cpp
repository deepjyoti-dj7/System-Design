#include "../include/EditorHistory.h"

void EditorHistory::save(const EditorMemento& memento) {
    history.push(memento);
}

EditorMemento EditorHistory::undo() {
    if (!history.empty()) {
        EditorMemento memento = history.top();
        history.pop();
        return memento;
    }
    return EditorMemento("");
}

bool EditorHistory::hasHistory() const {
    return !history.empty();
}
