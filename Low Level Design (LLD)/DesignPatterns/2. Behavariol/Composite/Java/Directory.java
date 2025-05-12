import java.util.ArrayList;
import java.util.List;

public class Directory implements FileComponent {
    private String name;
    private List<FileComponent> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void add(FileComponent component) {
        children.add(component);
    }

    public void remove(FileComponent component) {
        children.remove(component);
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "Directory: " + name);
        for (FileComponent child : children) {
            child.showDetails(indent + "  ");
        }
    }
}
