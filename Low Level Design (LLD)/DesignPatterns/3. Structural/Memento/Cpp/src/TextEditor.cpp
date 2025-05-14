#include "../include/TextEditor.h"

void TextEditor::write(const std::string& text) {
    content = text;
}

std::string TextEditor::read() const {
    return content;
}

EditorMemento TextEditor::save() const {
    return EditorMemento(content);
}

void TextEditor::restore(const EditorMemento& memento) {
    content = memento.getContent();
}
