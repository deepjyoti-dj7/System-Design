# 🟩 Open/Closed Principle (OCP)

### 🔹 **Definition:**

> **Software entities (classes, modules, functions, etc.) should be open for extension but closed for modification.**

This means you should be able to **add new behavior** without changing existing code.

---

## 🔸 **Why is OCP Important?**

- Promotes **extensibility** without risking existing functionality
- Makes code **less fragile** when adding new features
- Encourages **clean, pluggable architecture**

---

## ❌ **Bad Example: Constantly Modifying Existing Code**

Suppose you're building a payment system:

```java
class PaymentProcessor {
    public void processPayment(String type) {
        if (type.equals("credit")) {
            System.out.println("Processing credit card payment...");
        } else if (type.equals("paypal")) {
            System.out.println("Processing PayPal payment...");
        } else {
            System.out.println("Unsupported payment type.");
        }
    }
}
```

### 👎 What’s Wrong?

- Every time a new payment method is added, you must **modify** this class.
- Violates OCP: It's **not closed for modification**.
- Leads to **code bloat and bugs** over time.

---

## ✅ **Good Example: Use Abstraction to Extend Without Modifying**

Use **interfaces** or **abstract classes** and **polymorphism**:

```java
interface PaymentMethod {
    void pay();
}

class CreditCardPayment implements PaymentMethod {
    public void pay() {
        System.out.println("Processing credit card payment...");
    }
}

class PayPalPayment implements PaymentMethod {
    public void pay() {
        System.out.println("Processing PayPal payment...");
    }
}

class PaymentProcessor {
    public void process(PaymentMethod payment) {
        payment.pay();
    }
}
```

### 🔧 Usage:

```java
public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        PaymentMethod credit = new CreditCardPayment();
        PaymentMethod paypal = new PayPalPayment();

        processor.process(credit);
        processor.process(paypal);
    }
}
```

---

### 👍 What’s Good?

- You can add `BitcoinPayment`, `ApplePayPayment`, etc. **without modifying** `PaymentProcessor`.
- Existing code remains **stable**.
- New behavior is added through **extension**.

---

## 🔸 **Real-World Analogy:**

Think of a **power strip**:

- You can **plug in** any device—TV, laptop, phone charger—without **modifying** the strip.
- That’s OCP: **extend** usage by plugging in new functionality.

---
