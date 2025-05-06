package model;

public class ParkingSlot {
    private final int slotNumber;
    private final int floorNumber;
    private final VehicleType type;
    private boolean isOccupied;
    private Vehicle currentVehicle;

    public ParkingSlot(int slotNumber, int floorNumber, VehicleType type) {
        this.slotNumber = slotNumber;
        this.floorNumber = floorNumber;
        this.type = type;
        this.isOccupied = false;
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        return !isOccupied && vehicle.getType() == type;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
        this.isOccupied = true;
    }

    public void removeVehicle(Vehicle vehicle) {
        this.currentVehicle = null;
        this.isOccupied = false;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}
