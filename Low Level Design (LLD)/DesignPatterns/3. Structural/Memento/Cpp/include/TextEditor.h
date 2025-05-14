#pragma once
#include <string>
#include "EditorMemento.h"

class TextEditor {
private:
    std::string content;

public:
    void write(const std::string& text);
    std::string read() const;
    EditorMemento save() const;
    void restore(const EditorMemento& memento);
};
