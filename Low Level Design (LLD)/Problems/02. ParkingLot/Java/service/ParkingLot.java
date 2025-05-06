package service;

import model.*;
import strategy.ParkingFeeStrategy;

import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private static ParkingLot instance;
    private final List<ParkingFloor> floors;
    private ParkingFeeStrategy feeStrategy;

    private ParkingLot() {
        floors = new ArrayList<>();
    }

    public static ParkingLot getInstance() {
        if (instance == null)
            instance = new ParkingLot();
        return instance;
    }

    public void setFeeStrategy(ParkingFeeStrategy strategy) {
        this.feeStrategy = strategy;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            ParkingSlot slot = floor.getFreeSlot(vehicle);
            if (slot != null) {
                slot.assignVehicle(vehicle);
                return new Ticket(vehicle, slot);
            }
        }
        return null;
    }

    public double unparkVehicle(Ticket ticket) {
        LocalDateTime exitTime = LocalDateTime.now();
        double fee = feeStrategy.calculateFee(ticket.getVehicle(), ticket.getEntryTime(), exitTime);
        ticket.getSlot().removeVehicle(ticket.getVehicle());
        return fee;
    }
}
