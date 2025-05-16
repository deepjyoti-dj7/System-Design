import java.util.ArrayList;
import java.util.List;

public class Directory implements Element {
    private String name;
    private List<Element> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void add(Element element) {
        children.add(element);
    }

    public String getName() {
        return name;
    }

    public List<Element> getChildren() {
        return children;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Element e : children) {
            e.accept(visitor);
        }
    }
}
