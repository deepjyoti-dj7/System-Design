import models.*;
import payment.Payment;
import service.*;
import utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Initialize services
        UserService userService = new UserService();
        RestaurantService restaurantService = new RestaurantService();
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        DeliveryService deliveryService = new DeliveryService();
        CouponService couponService = new CouponService();
        NotificationService notificationService = new NotificationService();

        // Create User
        User customer = new User(IDGenerator.generateID(), "Alice", "alice@example.com", "9821857874", UserRole.CUSTOMER);
        userService.registerUser(customer);

        // Create Food Items
        FoodItem pizza = new FoodItem(IDGenerator.generateID(), "Chicken Pizza", 100.0);
        FoodItem burger = new FoodItem(IDGenerator.generateID(), "Veg Burger", 70.0);
        List<FoodItem> menu = Arrays.asList(pizza, burger);

        // Create a Restaurant
        Restaurant restaurant = new Restaurant(IDGenerator.generateID(), "Street-Za", "123 Main Street", menu);
        restaurantService.addRestaurant(restaurant);

        // Place an order
        List<FoodItem> itemsOrdered = Arrays.asList(pizza, burger);
        double totalAmount = itemsOrdered.stream().mapToDouble(FoodItem::getPrice).sum();
        Order order = new Order(IDGenerator.generateID(), customer, restaurant, itemsOrdered, totalAmount, OrderStatus.PLACED);
        orderService.placeOrder(order);
        notificationService.sendNotification("Restaurant is preparing your Order!", customer.getName());

        // Process Payment
        Payment payment = new Payment(IDGenerator.generateID(), order.getId(), order.getTotalAmount(), PaymentStatus.PENDING);
        paymentService.processPayment(payment);

        // Register a delivery person
        DeliveryPerson dp = new DeliveryPerson(IDGenerator.generateID(), "Bob", "9812341125", DeliveryStatus.AVAILABLE);
        deliveryService.registerDeliveryPerson(dp);

        // Assign Delivery
        DeliveryPerson assigned = deliveryService.getAvailableDeliveryPerson();
        if (assigned != null) {
            assigned.setStatus(DeliveryStatus.ON_DELIVERY);
            order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
            notificationService.sendNotification("Your order is out for delivery!", customer.getName());
        }

        // Order Delivered
        order.setStatus(OrderStatus.DELIVERED);
        assigned.setStatus(DeliveryStatus.AVAILABLE);
        notificationService.sendNotification("Order delivered Successfully!", customer.getName());

        System.out.println("Hurray!! Order completed!");
    }
}
