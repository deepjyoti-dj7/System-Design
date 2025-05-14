public interface BookCollection {
    void addBook(Book book);
    BookIterator createIterator();
}
