# 🟨 Liskov Substitution Principle (LSP)

### 🔹 **Definition:**

> **Subtypes must be substitutable for their base types** without altering the correctness of the program.

In simpler terms:
If class `B` is a subclass of class `A`, then `A`'s objects should be replaceable with `B`'s objects **without breaking the program**.

---

## 🔸 **Why is LSP Important?**

- Ensures **reliable polymorphism**
- Encourages **correct inheritance**
- Prevents **unexpected behavior** when using subclasses

---

## ❌ **Bad Example: Subclass Violating Behavior**

```java
class Bird {
    public void fly() {
        System.out.println("Bird is flying");
    }
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches can't fly!");
    }
}
```

### 👎 What’s Wrong?

- Ostrich **is-a** Bird but violates the **expected behavior**: all Birds can fly.
- If some code relies on `fly()` working for all Birds, substituting an `Ostrich` **will break it**.

---

## ✅ **Good Example: Fix the Inheritance Hierarchy**

Separate flying birds from non-flying birds:

```java
interface Bird {
    void makeSound();
}

interface FlyingBird extends Bird {
    void fly();
}

class Sparrow implements FlyingBird {
    public void makeSound() {
        System.out.println("Chirp chirp");
    }

    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

class Ostrich implements Bird {
    public void makeSound() {
        System.out.println("Boom boom");
    }
}
```

### 🔧 Usage:

```java
public class Zoo {
    public static void main(String[] args) {
        FlyingBird bird = new Sparrow();
        bird.fly();

        Bird ostrich = new Ostrich();
        ostrich.makeSound();
    }
}
```

---

### 👍 What’s Good?

- `Sparrow` and `Ostrich` are treated according to their **capabilities**.
- `Ostrich` no longer pretends it can fly, so **no broken promises**.
- **Behavior is preserved**, which fulfills the LSP.

---

## 🔸 **Real-World Analogy:**

Imagine a **plug adapter**:
If a new adapter “fits” but fries your phone, it's not a valid substitute.

LSP ensures every subclass **“fits” and “works” safely** in place of its parent class.

---
