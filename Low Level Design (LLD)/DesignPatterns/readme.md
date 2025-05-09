# 🎯 Design Patterns

## 📘 What Are Design Patterns?

**Design Patterns** are standard, reusable solutions to common problems in software design. They are not one-size-fits-all code but conceptual templates that guide how to structure and solve programming challenges in an elegant and efficient way.

These patterns help you:

- Avoid reinventing the wheel.
- Improve communication in development teams.
- Create software that is easier to understand, maintain, and extend.

---

## 🧠 Why Should You Use Design Patterns?

Design patterns offer many benefits in modern software development:

- ✅ **Reusability** – Solve recurring problems using proven solutions.
- ✅ **Maintainability** – Encourage cleaner, modular, and decoupled code.
- ✅ **Scalability** – Promote design structures that handle growth.
- ✅ **Testability** – Make code easier to isolate and test.
- ✅ **Shared Vocabulary** – Improve team communication with common terminology.

---

## 👑 The Gang of Four (GoF) Patterns

The **Gang of Four** (GoF) introduced 23 foundational design patterns in their book _Design Patterns: Elements of Reusable Object-Oriented Software_. These patterns are grouped into:

- 🏗️ **Creational** – How objects are created.
- 🧱 **Structural** – How objects and classes are composed.
- 🔄 **Behavioral** – How objects interact and assign responsibilities.

---

## 🏗️ Creational Patterns

### 1. Singleton

Ensures only one instance of a class exists and provides a global point of access to it.

### 2. Factory Method

Provides an interface for creating an object, but lets subclasses alter the type of objects that will be created.

### 3. Abstract Factory

Provides an interface to create families of related objects without specifying their concrete classes.

### 4. Builder

Separates the construction of a complex object from its representation so the same construction can create different representations.

### 5. Prototype

Creates new objects by copying an existing object (a prototype), instead of creating from scratch.

---

## 🧱 Structural Patterns

### 6. Adapter

Converts the interface of a class into another interface that a client expects.

### 7. Bridge

Decouples an abstraction from its implementation so both can vary independently.

### 8. Composite

Allows you to compose objects into tree structures to represent part-whole hierarchies.

### 9. Decorator

Adds responsibilities to objects dynamically, without modifying their structure.

### 10. Facade

Provides a simplified, high-level interface to a complex subsystem.

### 11. Flyweight

Minimizes memory usage by sharing as much data as possible with similar objects.

### 12. Proxy

Acts as a placeholder or intermediary for another object to control access to it.

---

## 🔄 Behavioral Patterns

### 13. Chain of Responsibility

Passes a request along a chain of potential handlers until one handles it.

### 14. Command

Encapsulates a request as an object, allowing it to be parameterized, stored, or undone.

### 15. Interpreter

Defines a grammatical representation for a language and provides an interpreter to interpret sentences in the language.

### 16. Iterator

Provides a way to access the elements of a collection sequentially without exposing the underlying structure.

### 17. Mediator

Defines an object that coordinates communication between other objects to reduce coupling.

### 18. Memento

Captures and stores an object’s internal state without violating encapsulation so it can be restored later.

### 19. Observer

Defines a one-to-many dependency so that when one object changes state, all its dependents are notified and updated automatically.

### 20. State

Allows an object to alter its behavior when its internal state changes, appearing as if the object changed its class.

### 21. Strategy

Defines a family of interchangeable algorithms and allows the client to choose one at runtime.

### 22. Template Method

Defines the skeleton of an algorithm and lets subclasses redefine certain steps without changing its structure.

### 23. Visitor

Allows adding new operations to existing object structures without modifying the structures.

---

## 💼 Real-World Analogies

| Pattern                     | Real-World Analogy                                                                               |
| --------------------------- | ------------------------------------------------------------------------------------------------ |
| **Singleton**               | A country’s president – there’s only one, globally accessible.                                   |
| **Factory Method**          | A pizza store – same process, but creates different types of pizza.                              |
| **Abstract Factory**        | A furniture showroom – choose a theme (e.g., modern, Victorian) and get matching furniture sets. |
| **Builder**                 | Assembling a computer – build it part by part based on needs.                                    |
| **Prototype**               | Photocopy machine – creates a copy of a document instead of writing it from scratch.             |
| **Adapter**                 | Power plug adapter – makes one interface compatible with another.                                |
| **Bridge**                  | Remote control – same abstraction (remote), works with different devices.                        |
| **Composite**               | File system – folders contain files or subfolders, and you treat both the same.                  |
| **Decorator**               | Coffee shop – start with plain coffee, add sugar, milk, etc. dynamically.                        |
| **Facade**                  | Hotel concierge – provides a simplified interface to many complex services.                      |
| **Flyweight**               | Chess pieces – reuse similar objects with shared state (e.g., all white pawns).                  |
| **Proxy**                   | Credit card – acts as a proxy for your bank account.                                             |
| **Chain of Responsibility** | Customer service – level 1 support escalates to level 2 if needed.                               |
| **Command**                 | Restaurant waiter – takes orders and passes them to the kitchen.                                 |
| **Interpreter**             | Calculator – interprets and evaluates math expressions.                                          |
| **Iterator**                | TV remote – lets you iterate through channels without knowing the internals.                     |
| **Mediator**                | Air traffic controller – central point for pilot communication.                                  |
| **Memento**                 | Video game save – save game state and load it later.                                             |
| **Observer**                | YouTube subscribers – get notified when a new video is uploaded.                                 |
| **State**                   | Traffic light – behavior changes based on its current color (state).                             |
| **Strategy**                | Route planning – pick between fastest, shortest, or scenic route.                                |
| **Template Method**         | Cooking recipe – fixed steps, but ingredients can vary.                                          |
| **Visitor**                 | Tax auditor – visits departments and performs audits without changing them.                      |

---

## 🧰 When to Use Design Patterns?

Use design patterns when:

- You identify recurring problems that can benefit from reusable architecture.
- You want to ensure your code is scalable and maintainable.
- You are working in teams and need a shared design vocabulary.
- You want to follow **SOLID principles** and best practices.

## 🔗 Further Reading

- _Design Patterns: Elements of Reusable Object-Oriented Software_ – GoF
- _Head First Design Patterns_ – O’Reilly Media
- _Refactoring to Patterns_ – Joshua Kerievsky

---

## 🏁 Conclusion

Design patterns are not magic bullets, but they **empower developers** with knowledge and vocabulary to create **robust, flexible, and scalable** systems. By mastering them, you're not just writing code — you're crafting software architecture.

Happy Coding! 🎉

---
