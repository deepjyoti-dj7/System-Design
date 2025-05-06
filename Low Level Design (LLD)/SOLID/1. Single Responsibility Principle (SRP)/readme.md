## ✅ **Single Responsibility Principle (SRP)**

### 🔹 **Definition:**

A **class should have only one reason to change**, meaning it should be **responsible for only one part of the functionality** of a program.

---

### 🔸 **Why is SRP Important?**

- Easier to **understand** and **modify** classes
- Encourages **clean separation of concerns**
- Helps **avoid bugs** due to unrelated changes
- Improves **testability** and **scalability**

---

## ❌ **Bad Example: One Class Doing Too Much**

```java
class Invoice {
    private String customerName;
    private double amount;

    public Invoice(String customerName, double amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    public void printInvoice() {
        System.out.println("Customer: " + customerName);
        System.out.println("Amount: $" + amount);
    }

    public void saveToDatabase() {
        System.out.println("Saving invoice to database...");
    }

    public void sendEmail() {
        System.out.println("Sending invoice email to customer...");
    }
}
```

### 👎 What's Wrong Here?

- This class **creates**, **prints**, **saves**, and **emails** an invoice.
- If any one of those needs to change, we touch the same class—**tight coupling** and **poor maintainability**.

---

## ✅ **Good Example: Break Responsibilities Into Focused Classes**

```java
class Invoice {
    private String customerName;
    private double amount;

    public Invoice(String customerName, double amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }
}

class InvoicePrinter {
    public void print(Invoice invoice) {
        System.out.println("Customer: " + invoice.getCustomerName());
        System.out.println("Amount: $" + invoice.getAmount());
    }
}

class InvoiceRepository {
    public void save(Invoice invoice) {
        System.out.println("Saving invoice to database...");
    }
}

class InvoiceMailer {
    public void sendEmail(Invoice invoice) {
        System.out.println("Email sent to: " + invoice.getCustomerName());
    }
}
```

### 👍 What's Good Here?

- `Invoice`: Only holds invoice data.
- `InvoicePrinter`: Only handles printing.
- `InvoiceRepository`: Handles saving.
- `InvoiceMailer`: Handles email sending.

Now each class has **one reason to change**, making your code **modular**, **maintainable**, and **reusable**.

---

### 🔸 **Real-World Analogy:**

Imagine a **restaurant**:

- The **chef** cooks.
- The **waiter** serves food.
- The **cashier** handles billing.

You wouldn't want the chef doing everything. That’s what SRP avoids in software.
