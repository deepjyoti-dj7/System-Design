# Aggregation in Java

## Introduction

Aggregation is a key concept in object-oriented programming (OOP) that represents a "has-a" relationship between two classes, but with a crucial distinction: the lifecycle of the contained object is independent of the container object. This means that while one class contains another, the contained object can exist independently of the container.

Aggregation allows for better modularity, code reuse, and maintainability. It is different from composition, where the contained object cannot exist without the container.

---

## What is Aggregation?

Aggregation is a form of association in OOP where an object of one class contains a reference to an object of another class. However, the contained object can exist independently of the container. This means that even if the container object is destroyed, the contained object can still be used elsewhere in the application.

### Key Characteristics of Aggregation:

- Represents a **has-a** relationship.
- The contained object **can exist independently** of the container.
- Implemented using references (pointers) to objects.
- Promotes **loose coupling** between objects.

### Example: A Library and its Books

```java
import java.util.*;

class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void displayInfo() {
        System.out.println("Book: " + title + " by " + author);
    }
}

class Library {
    private String libraryName;
    private List<Book> books;

    public Library(String libraryName) {
        this.libraryName = libraryName;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void showBooks() {
        System.out.println("Books in " + libraryName + ":");
        for (Book book : books) {
            book.displayInfo();
        }
    }
}

public class AggregationLibraryExample {
    public static void main(String[] args) {
        Book b1 = new Book("1984", "George Orwell");
        Book b2 = new Book("To Kill a Mockingbird", "Harper Lee");

        Library library = new Library("City Central Library");
        library.addBook(b1);
        library.addBook(b2);

        library.showBooks();

        // Books can still be used independently
        b1.displayInfo();
    }
}
```

### Output:

```
Books in City Central Library:
Book: 1984 by George Orwell
Book: To Kill a Mockingbird by Harper Lee
Book: 1984 by George Orwell
```

---

## Aggregation vs Composition

| Feature      | Aggregation                                  | Composition                                             |
| ------------ | -------------------------------------------- | ------------------------------------------------------- |
| Relationship | "Has-a"                                      | "Has-a"                                                 |
| Ownership    | Contained object **can exist independently** | Contained object **cannot exist without** the container |
| Lifetime     | Contained object **outlives** the container  | Contained object **is destroyed** with the container    |
| Example      | Library and Books                            | Car and Engine                                          |

---

## Why Use Aggregation?

### 1. **Promotes Code Reusability**

- Aggregated objects can be used in multiple places without being tightly coupled to a single container class.

### 2. **Encourages Loose Coupling**

- Aggregation allows objects to interact without being dependent on the lifecycle of each other.

### 3. **Better Maintainability**

- Changes in one class do not heavily impact the other, making the codebase easier to modify and extend.

### 4. **Real-World Applicability**

- Many real-world relationships, such as a school and its teachers, a company and its employees, naturally fit the aggregation model.

---

## Aggregation with Interfaces

Using interfaces, we can further enhance the flexibility of aggregation.

```java
import java.util.*;

interface Transportable {
    void transport();
}

class Package implements Transportable {
    private String id;

    public Package(String id) {
        this.id = id;
    }

    public void transport() {
        System.out.println("Transporting package ID: " + id);
    }
}

class DeliveryService {
    private String serviceName;
    private List<Transportable> packages;

    public DeliveryService(String serviceName) {
        this.serviceName = serviceName;
        this.packages = new ArrayList<>();
    }

    public void addPackage(Transportable pkg) {
        packages.add(pkg);
    }

    public void deliverAll() {
        System.out.println("Delivering packages via " + serviceName + ":");
        for (Transportable pkg : packages) {
            pkg.transport();
        }
    }
}

public class InterfaceAggregationDemo {
    public static void main(String[] args) {
        Package p1 = new Package("PKG001");
        Package p2 = new Package("PKG002");

        DeliveryService dhl = new DeliveryService("DHL");
        dhl.addPackage(p1);
        dhl.addPackage(p2);

        dhl.deliverAll();
    }
}
```

### Output:

```
Delivering packages via DHL:
Transporting package ID: PKG001
Transporting package ID: PKG002
```

---

## When to Use Aggregation?

- When an object **can exist independently** from the container.
- When designing **loosely coupled** systems.
- When different objects need to be **shared** across multiple containers.
- When following **SOLID principles**, particularly the **Dependency Inversion Principle (DIP)**.
