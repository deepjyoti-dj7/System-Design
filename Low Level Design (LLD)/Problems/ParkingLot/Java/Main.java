package main;

import java.util.*;
import model.*;
import factory.VehicleFactory;
import service.ParkingLot;
import strategy.VehicleBasedHourlyFeeStrategy;

public class Main {
    private static final Map<String, Ticket> activeTickets = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParkingLot lot = ParkingLot.getInstance();
        lot.setFeeStrategy(new VehicleBasedHourlyFeeStrategy());

        // Setup floors and slots
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSlot(new ParkingSlot(1, 1, VehicleType.CAR));
        floor1.addSlot(new ParkingSlot(2, 1, VehicleType.BIKE));
        floor1.addSlot(new ParkingSlot(3, 1, VehicleType.TRUCK));
        lot.addFloor(floor1);

        while (true) {
            System.out.println("\n--- Parking Lot Menu ---");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Unpark Vehicle");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter vehicle type (CAR/BIKE/TRUCK): ");
                    String typeInput = scanner.nextLine().trim().toUpperCase();
                    VehicleType type;
                    try {
                        type = VehicleType.valueOf(typeInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid vehicle type!");
                        break;
                    }

                    System.out.print("Enter license number: ");
                    String license = scanner.nextLine().trim();

                    Vehicle vehicle = VehicleFactory.createVehicle(type, license);
                    Ticket ticket = lot.parkVehicle(vehicle);
                    if (ticket != null) {
                        activeTickets.put(license, ticket);
                        System.out.println("Your " + ticket.getVehicle().getType()
                                + " " + ticket.getVehicle().getLicenseNumber()
                                + " is parked at floor "
                                + ticket.getSlot().getFloorNumber()
                                + ", slot " + ticket.getSlot().getSlotNumber());
                    } else {
                        System.out.println("Parking full for " + type);
                    }
                    break;

                case 2:
                    System.out.print("Enter license number to unpark: ");
                    String lic = scanner.nextLine().trim();
                    Ticket t = activeTickets.get(lic);
                    if (t != null) {
                        double fee = lot.unparkVehicle(t);
                        activeTickets.remove(lic);
                        System.out.println("Your " + t.getVehicle().getType()
                                + " is unparked. Fee: ₹" + fee);
                    } else {
                        System.out.println("No vehicle found with license: " + lic);
                    }
                    break;

                case 3:
                    System.out.println("Exiting... Thank you!");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}

//import factory.VehicleFactory;
//import model.*;
//        import service.ParkingLot;
//import strategy.HourlyFeeStrategy;
//
//public class Main {
//    public static void main(String[] args) throws InterruptedException {
//        ParkingLot lot = ParkingLot.getInstance();
//        lot.setFeeStrategy(new HourlyFeeStrategy(30.0)); // ₹30/hour
//
//        ParkingFloor floor1 = new ParkingFloor(1);
//        floor1.addSlot(new ParkingSlot(1, 1, VehicleType.CAR));
//        floor1.addSlot(new ParkingSlot(2, 1, VehicleType.BIKE));
//        floor1.addSlot(new ParkingSlot(3, 1, VehicleType.TRUCK));
//        lot.addFloor(floor1);
//
//        Vehicle vehicle = VehicleFactory.createVehicle(VehicleType.CAR, "KA-01-1234");
//        Ticket ticket = lot.parkVehicle(vehicle);
//
//        if (ticket != null) {
//            System.out.println("Your " + vehicle.getType() + " "
//                    + vehicle.getLicenseNumber() + " is parked at slot "
//                    + ticket.getSlot().getSlotNumber() + " and at floor "
//                    + ticket.getSlot().getFloorNumber());
//            Thread.sleep(1000); // Simulate wait
//            double fee = lot.unparkVehicle(ticket);
//            System.out.println("Unparked. Fee: ₹" + fee);
//        } else {
//            System.out.println("Parking full!");
//        }
//    }
//}

