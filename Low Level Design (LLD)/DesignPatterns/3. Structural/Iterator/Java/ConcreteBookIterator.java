import java.util.List;

public class ConcreteBookIterator implements BookIterator {
    private List<Book> books;
    private int position = 0;

    public ConcreteBookIterator(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean hasNext() {
        return position < books.size();
    }

    @Override
    public Book next() {
        return books.get(position++);
    }
}
