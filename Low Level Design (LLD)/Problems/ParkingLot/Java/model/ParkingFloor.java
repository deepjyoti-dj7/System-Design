package model;

import java.util.*;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSlot> slots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
    }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public ParkingSlot getFreeSlot(Vehicle vehicle) {
        for (ParkingSlot slot : slots) {
            if (slot.canFitVehicle(vehicle)) {
                return slot;
            }
        }
        return null;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
