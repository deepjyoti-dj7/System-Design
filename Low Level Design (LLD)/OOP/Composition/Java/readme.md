# Composition in Java

## Introduction

Composition is one of the key principles of object-oriented programming (OOP). It allows classes to be composed of other objects, promoting code reuse, flexibility, and better maintainability. Composition represents a "has-a" relationship, as opposed to inheritance, which models an "is-a" relationship.

## What is Composition?

Composition is a design principle where one class contains instances of other classes as fields. These contained classes are often referred to as **components**, and the class that contains them is the **composite class**. This allows for the construction of complex systems by combining simpler objects.

### Example: A Computer and its Components

Consider a `Computer` class that has several components, such as `Processor`, `RAM`, and `Storage`. Instead of inheriting from these components, a `Computer` object **contains** instances of `Processor`, `RAM`, and `Storage` as fields.

```java
class Processor {
    private String model;

    public Processor(String model) {
        this.model = model;
    }

    public void performTask() {
        System.out.println(model + " processor is performing a task.");
    }
}

class RAM {
    private int size;

    public RAM(int size) {
        this.size = size;
    }

    public void loadData() {
        System.out.println("Loading data into " + size + "GB RAM.");
    }
}

class Storage {
    private String type;

    public Storage(String type) {
        this.type = type;
    }

    public void storeData() {
        System.out.println("Storing data on " + type + " storage.");
    }
}

class Computer {
    private Processor processor;
    private RAM ram;
    private Storage storage;

    public Computer(Processor processor, RAM ram, Storage storage) {
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
    }

    public void run() {
        processor.performTask();
        ram.loadData();
        storage.storeData();
        System.out.println("Computer is running!");
    }
}

public class CompositionExample {
    public static void main(String[] args) {
        Processor processor = new Processor("Intel i7");
        RAM ram = new RAM(16);
        Storage storage = new Storage("SSD");

        Computer computer = new Computer(processor, ram, storage);
        computer.run();
    }
}
```

### Output:

```
Intel i7 processor is performing a task.
Loading data into 16GB RAM.
Storing data on SSD storage.
Computer is running!
```

---

## Why Prefer Composition Over Inheritance?

### 1. **Encapsulation and Flexibility**

- Composition allows for **dynamic behavior changes**. For instance, components like `Processor`, `RAM`, and `Storage` can be swapped out at runtime without modifying the core `Computer` class.
- Inheritance, on the other hand, creates a rigid class hierarchy that can be harder to maintain and modify without breaking existing code.

### 2. **Better Code Reusability**

- Composition encourages **code reuse** because individual components (like `Processor`, `RAM`, `Storage`) can be reused across different classes (e.g., `Laptop`, `Desktop`, `Server`) without the need for duplication.

### 3. **Avoids Inheritance Pitfalls**

- Inheritance can result in deep and complex class hierarchies, making the system harder to maintain.
- Composition avoids this by **delegating** responsibilities to objects, rather than forcing an "is-a" relationship between classes.

### 4. **Supports Interface-Based Design**

- Composition works well with interfaces, allowing you to design loosely coupled systems. This promotes flexibility and decoupling between components.

---

## Composition with Interfaces

Using interfaces with composition enables more flexibility and decoupling between the components and the composite class.

```java
interface Engine {
    void start();
}

class ElectricEngine implements Engine {
    public void start() {
        System.out.println("Electric Engine started silently.");
    }
}

class HybridEngine implements Engine {
    public void start() {
        System.out.println("Hybrid Engine started with both electric and gasoline power.");
    }
}

class Vehicle {
    private Engine engine;

    public Vehicle(Engine engine) {
        this.engine = engine;
    }

    public void startVehicle() {
        engine.start();
        System.out.println("Vehicle is ready to go!");
    }
}

public class InterfaceCompositionExample {
    public static void main(String[] args) {
        Vehicle electricCar = new Vehicle(new ElectricEngine());
        electricCar.startVehicle();

        Vehicle hybridCar = new Vehicle(new HybridEngine());
        hybridCar.startVehicle();
    }
}
```

### Output:

```
Electric Engine started silently.
Vehicle is ready to go!
Hybrid Engine started with both electric and gasoline power.
Vehicle is ready to go!
```

In this example, the `Vehicle` class uses composition to include an `Engine` (interface) as a field. By using different types of `Engine` implementations (e.g., `ElectricEngine`, `HybridEngine`), we can change the vehicle's behavior dynamically.

---

## When to Use Composition?

- **Complex Objects**: When building complex objects that consist of multiple independent components (e.g., `Computer`, `Car`, `House`).
- **Code Reusability**: When you want to reuse components across different classes without forcing them into a rigid inheritance hierarchy.
- **Dynamic Behavior**: When you need to change behaviors dynamically by swapping components at runtime (e.g., using different engines in a vehicle).
- **Loose Coupling**: When you prefer to decouple your classes and avoid creating deep inheritance chains. Composition supports the **"favor composition over inheritance"** principle.

This approach offers a more flexible, reusable, and maintainable design compared to inheritance, especially in scenarios where objects are made up of multiple distinct components.
