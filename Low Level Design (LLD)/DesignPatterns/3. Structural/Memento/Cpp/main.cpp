#include <iostream>
#include "./include/TextEditor.h"
#include "./include/EditorHistory.h"

int main() {
    TextEditor editor;
    EditorHistory history;

    editor.write("Version 1");
    history.save(editor.save());

    editor.write("Version 2");
    history.save(editor.save());

    editor.write("Version 3");

    std::cout << "Current content: " << editor.read() << std::endl;

    if (history.hasHistory()) {
        editor.restore(history.undo());
        std::cout << "After undo 1: " << editor.read() << std::endl;
    }

    if (history.hasHistory()) {
        editor.restore(history.undo());
        std::cout << "After undo 2: " << editor.read() << std::endl;
    }

    return 0;
}
