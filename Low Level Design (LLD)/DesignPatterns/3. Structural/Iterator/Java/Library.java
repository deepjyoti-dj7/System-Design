import java.util.ArrayList;
import java.util.List;

public class Library implements BookCollection {
    private List<Book> books = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public BookIterator createIterator() {
        return new ConcreteBookIterator(books);
    }
}
