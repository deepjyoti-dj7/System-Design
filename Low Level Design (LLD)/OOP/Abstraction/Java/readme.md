# Abstraction in Java

## Introduction

**Abstraction** is one of the four fundamental principles of Object-Oriented Programming (OOP). It allows you to hide **implementation details** while exposing only the necessary parts of an object. This helps in reducing complexity and increasing maintainability.

Abstraction in Java is mainly achieved using:

1. **Abstract Classes**
2. **Interfaces**

---

## What is Abstraction?

**Abstraction** means showing only the **essential details** and hiding the **implementation**. It allows programmers to focus on **what an object does** rather than **how it does it**.

### Key Benefits of Abstraction

- **Reduces complexity**: Hides unnecessary implementation details.
- **Increases code reusability**: Encourages the reuse of abstracted logic.
- **Enhances security**: Protects internal object details from unintended modifications.
- **Improves maintainability**: Makes code easier to manage and update.

---

## 1. Abstraction Using Abstract Classes

An **abstract class** in Java is a class that **cannot be instantiated**. It is used to define common behavior that multiple subclasses should implement.

### Example: Abstract Class in Java

```java
abstract class Appliance {
    protected String name;

    public Appliance(String name) {
        this.name = name;
    }

    public void displayName() {
        System.out.println("Appliance: " + name);
    }

    public abstract void turnOn(); // Abstract method
}

class Refrigerator extends Appliance {
    public Refrigerator(String name) {
        super(name);
    }

    @Override
    public void turnOn() {
        System.out.println("Refrigerator is now cooling...");
    }
}

public class Main {
    public static void main(String[] args) {
        Appliance fridge = new Refrigerator("LG Double Door");
        fridge.displayName();
        fridge.turnOn();
    }
}
```

### Output

```
Appliance: LG Double Door
Refrigerator is now cooling...
```

---

## 2. Abstraction Using Interfaces

An **interface** in Java is a reference type that can contain only abstract methods (until Java 8+ which allows default and static methods).

### Example: Interface in Java

```java
interface Drawable {
    void draw(); // Abstract method
}

class Circle implements Drawable {
    public void draw() {
        System.out.println("Drawing a Circle.");
    }
}

class Rectangle implements Drawable {
    public void draw() {
        System.out.println("Drawing a Rectangle.");
    }
}

public class Main {
    public static void main(String[] args) {
        Drawable shape1 = new Circle();
        shape1.draw();

        Drawable shape2 = new Rectangle();
        shape2.draw();
    }
}
```

### Output

```
Drawing a Circle.
Drawing a Rectangle.
```

---

## Abstract Class vs Interface: Key Differences

| Feature              | Abstract Class                         | Interface                                  |
| -------------------- | -------------------------------------- | ------------------------------------------ |
| Methods              | Can have abstract and concrete methods | Only abstract methods (Java 7 and below)   |
| Fields               | Can have instance variables            | Only static final constants                |
| Constructors         | Can have constructors                  | Cannot have constructors                   |
| Multiple Inheritance | Not supported for classes              | Supported for interfaces                   |
| Access Modifiers     | Can have any access modifier           | Methods are public and abstract by default |

---

## Real-World Example: Notification System

Abstraction is widely used in real-world applications, such as notification handling.

### Example: Notification System with Abstraction

```java
abstract class Notification {
    protected String recipient;

    public Notification(String recipient) {
        this.recipient = recipient;
    }

    public abstract void send(); // Abstract method
}

class EmailNotification extends Notification {
    public EmailNotification(String recipient) {
        super(recipient);
    }

    public void send() {
        System.out.println("Email sent to " + recipient);
    }
}

class PushNotification extends Notification {
    public PushNotification(String recipient) {
        super(recipient);
    }

    public void send() {
        System.out.println("Push notification sent to " + recipient);
    }
}

public class Main {
    public static void main(String[] args) {
        Notification n1 = new EmailNotification("john@example.com");
        n1.send();

        Notification n2 = new PushNotification("User123");
        n2.send();
    }
}
```

### Output

```
Email sent to john@example.com
Push notification sent to User123
```

---

## Why Use Abstraction in Notification Systems?

- Multiple notification types can be introduced without modifying the system.
- Encourages **open/closed principle**: classes are open for extension but closed for modification.
- Simplifies usage: client code only interacts with the abstraction, not the concrete implementations.

---

## Summary Table

| Concept        | Description                                                              |
| -------------- | ------------------------------------------------------------------------ |
| Abstraction    | Hides implementation, shows only necessary features                      |
| Abstract Class | Partially abstract, can include implemented methods                      |
| Interface      | Fully abstract (Java 7 and earlier), can have default methods in Java 8+ |
| Use Case       | When you want different implementations of a common concept              |

---

## Final Thoughts

Abstraction is a critical part of designing scalable and maintainable Java systems. It helps you decouple your components and work at a higher level of thinking, allowing for better design decisions and less tangled code.

---
