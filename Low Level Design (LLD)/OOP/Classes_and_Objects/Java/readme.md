# Classes and Objects in Java

Classes and objects are core concepts in Object-Oriented Programming (OOP), defining the structure and behavior of data.

## What is a Class?

A class is a blueprint or template for creating objects. It specifies the attributes (fields) and methods (behaviors) that objects created from the class will possess.

### Defining a Class in Java

In Java, a class is defined using the `class` keyword followed by the class name. Below is an example of a `Book` class:

```java
public class Book {
    // Attributes
    private String title;
    private String author;
    private int yearPublished;
    private double price;

    // Constructor to initialize the object state
    public Book(String title, String author, int yearPublished, double price) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.price = price;
    }

    // Method to display book details
    public void displayDetails() {
        System.out.println("Book Title: " + title);
        System.out.println("Book Author: " + author);
        System.out.println("Year Published: " + yearPublished);
        System.out.println("Price: $" + price);
    }
}
```

- **Attributes**: The `Book` class has four attributes: `title`, `author`, `yearPublished`, and `price`, which describe the characteristics of a book.
- **Constructor**: The `Book(String title, String author, int yearPublished, double price)` constructor initializes a new `Book` object with specified values.
- **Methods**: The `displayDetails` method prints the book's details.

## What is an Object?

An object is an instance of a class, representing a specific entity created from the class blueprint. Each object holds its own data (state) and can perform the behaviors (methods) defined by the class.

### Creating Objects in Java

Objects are created using the `new` keyword followed by the class constructor. Below is an example of creating and using objects of the `Book` class:

```java
public class Main {
    public static void main(String[] args) {
        // Creating objects of the Book class
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, 14.99);
        Book book2 = new Book("1984", "George Orwell", 1949, 12.99);

        // Displaying details of each book
        book1.displayDetails();
        System.out.println("----------------------");
        book2.displayDetails();
    }
}
```

1. **Instantiation**: The `new` keyword allocates memory for the object and invokes the constructor.
2. **Initialization**: The `Book` constructor sets the object's attributes with provided values.
3. **Reference**: Variables (`book1`, `book2`) store references to the objects, enabling interaction with their attributes and methods.

## Key Points

- **Attributes**: These define the state of an object. For the `Book` class, they include `title`, `author`, `yearPublished`, and `price`.
- **Constructor**: A special method that initializes a new object with values for its attributes.
- **Methods**: These define the actions an object can perform. The `displayDetails` method displays a book's information.
- **Object Creation**: Objects are instances of classes, created using the `new` keyword and a constructor.

Classes provide structure, while objects represent specific instances with real data, enabling effective modeling of real-world entities in Java.
