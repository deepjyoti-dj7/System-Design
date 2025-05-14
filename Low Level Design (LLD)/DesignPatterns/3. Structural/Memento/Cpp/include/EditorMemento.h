#pragma once
#include <string>

class EditorMemento {
private:
    std::string content;

public:
    EditorMemento(const std::string& content);
    std::string getContent() const;
};
