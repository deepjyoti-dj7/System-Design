#include "../include/EditorMemento.h"

EditorMemento::EditorMemento(const std::string& content) : content(content) {}

std::string EditorMemento::getContent() const {
    return content;
}
