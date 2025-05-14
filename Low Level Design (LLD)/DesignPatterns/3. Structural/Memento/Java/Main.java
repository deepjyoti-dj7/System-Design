public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        EditorHistory history = new EditorHistory();

        editor.write("Version 1");
        history.save(editor.save());

        editor.write("Version 2");
        history.save(editor.save());

        editor.write("Version 3");

        System.out.println("Current content: " + editor.read());

        editor.restore(history.undo());
        System.out.println("After undo 1: " + editor.read());

        editor.restore(history.undo());
        System.out.println("After undo 2: " + editor.read());
    }
}
