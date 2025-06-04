#include "./models/include/DeliveryPerson.h"
#include "./models/include/FoodItem.h"
#include "./models/include/Location.h"
#include "./models/include/Order.h"
#include "./models/include/Restaurant.h"
#include "./models/include/User.h"
#include "./payment/include/Coupon.h"
#include "./payment/include/Payment.h"
#include "./service/include/CouponService.h"
#include "./service/include/DeliveryService.h"
#include "./service/include/NotificationService.h"
#include "./service/include/OrderService.h"
#include "./service/include/PaymentService.h"
#include "./service/include/RestaurantService.h"
#include "./service/include/UserService.h"
#include "./utils/include/IDGenerator.h"

int main() {
    UserService userService;
    RestaurantService restaurantService;
    OrderService orderService;
    PaymentService paymentService;
    DeliveryService deliveryService;
    NotificationService notificationService;

    // Create User
    std::shared_ptr<User> customer = std::make_shared<User>(IDGenerator::generateID(), "Alice", "alice@example.com", "9821857874", UserRole::CUSTOMER);
    userService.registerUser(customer);

    // Create Food Items
    FoodItem pizza(IDGenerator::generateID(), "Chicken Pizza", 100.0);
    FoodItem burger(IDGenerator::generateID(), "Veg Burger", 70.0);
    std::vector<FoodItem> menu = {pizza, burger};

    // Create a Restaurant
    std::shared_ptr<Restaurant> restaurant = std::make_shared<Restaurant>(IDGenerator::generateID(), "Street-Za", "123 Main Street", menu);
    restaurantService.addRestaurant(restaurant);

    // Place an Order
    std::vector<FoodItem> itemsOrdered = {pizza, burger};
    double totalAmount = pizza.getPrice() + burger.getPrice();
    std::shared_ptr<Order> order = std::make_shared<Order>(IDGenerator::generateID(), *customer, *restaurant, itemsOrdered, totalAmount, OrderStatus::PLACED);
    orderService.placeOrder(order);
    notificationService.sendNotification("Restaurant is preparing your Order!", customer->getName());

    // Process Payment
    Payment payment(IDGenerator::generateID(), order->getId(), order->getTotalAmount(), PaymentStatus::PENDING);
    paymentService.processPayment(payment);

    // Register and Assign Delivery
    std::shared_ptr<DeliveryPerson> deliveryPerson = std::make_shared<DeliveryPerson>(IDGenerator::generateID(), "Bob", "9812341125", DeliveryStatus::AVAILABLE);
    deliveryService.registerDeliveryPerson(deliveryPerson);

    std::shared_ptr<DeliveryPerson> assigned = deliveryService.getAvailableDeliveryPerson();
    if (assigned != nullptr) {
        assigned->setStatus(DeliveryStatus::ON_DELIVERY);
        order->setStatus(OrderStatus::OUT_FOR_DELIVERY);
        notificationService.sendNotification("Your order is out for delivery!", customer->getName());
    }

    // Order Delivered
    order->setStatus(OrderStatus::DELIVERED);
    assigned->setStatus(DeliveryStatus::AVAILABLE);
    notificationService.sendNotification("Order delivered Successfully!", customer->getName());

    std::cout << "Hurray!! Order completed!" << std::endl;

    return 0;
}
