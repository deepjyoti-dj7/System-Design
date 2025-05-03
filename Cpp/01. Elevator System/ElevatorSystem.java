import java.util.*;

enum Direction {
    UP, DOWN, IDLE
}

class Elevator {
    int currentFloor;
    Direction direction;
    boolean isMoving;

    public Elevator() {
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.isMoving = false;
    }

    public void moveUp() {
        currentFloor++;
        direction = Direction.UP;
        isMoving = true;
        System.out.println("Elevator moving up to floor: " + currentFloor);
    }

    public void moveDown() {
        currentFloor--;
        direction = Direction.DOWN;
        isMoving = true;
        System.out.println("Elevator moving down to floor: " + currentFloor);
    }

    public void stop() {
        direction = Direction.IDLE;
        isMoving = false;
        System.out.println("Elevator stopped at floor: " + currentFloor + "\n");
    }

    public boolean shouldMoveUp(int floor) {
        return currentFloor < floor;
    }

    public boolean shouldMoveDown(int floor) {
        return currentFloor > floor;
    }
}

class ElevatorController {
    Elevator elevator;
    PriorityQueue<Integer> upRequests;
    PriorityQueue<Integer> downRequests;
    Set<Integer> processedRequests;
    Map<Integer, Integer> destinationFloors;

    public ElevatorController() {
        this.elevator = new Elevator();
        this.upRequests = new PriorityQueue<>(); // Min-heap for UP direction
        this.downRequests = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap for DOWN direction
        this.processedRequests = new HashSet<>();
        this.destinationFloors = new HashMap<>();
    }

    public void addRequest(int floor, Direction direction, int destinationFloor) {
        if (processedRequests.contains(floor)) return; // Skip if already processed
        processedRequests.add(floor);
        destinationFloors.put(floor, destinationFloor);

        if (direction == Direction.UP) {
            upRequests.offer(floor);
        } else if (direction == Direction.DOWN) {
            downRequests.offer(floor);
        }
    }

    public void processRequests() {
        while (!upRequests.isEmpty() || !downRequests.isEmpty()) {
            if (elevator.direction == Direction.IDLE) {
                determineInitialDirection();
            }

            if (elevator.direction == Direction.UP) {
                handleUpRequests();
                if (upRequests.isEmpty() && !downRequests.isEmpty()) {
                    elevator.direction = Direction.DOWN;
                }
            } else if (elevator.direction == Direction.DOWN) {
                handleDownRequests();
                if (downRequests.isEmpty() && !upRequests.isEmpty()) {
                    elevator.direction = Direction.UP;
                }
            }
        }
    }

    private void determineInitialDirection() {
        elevator.direction = Direction.UP;
    }

    private void handleUpRequests() {
        while (!upRequests.isEmpty() && elevator.direction == Direction.UP) {
            int nextFloor = upRequests.poll();
            moveToFloor(nextFloor);
        }
    }

    private void handleDownRequests() {
        while (!downRequests.isEmpty() && elevator.direction == Direction.DOWN) {
            int nextFloor = downRequests.poll();
            moveToFloor(nextFloor);
        }
    }

    private void moveToFloor(int floor) {
        while (elevator.currentFloor != floor) {
            if (elevator.shouldMoveUp(floor)) {
                elevator.moveUp();
            } else if (elevator.shouldMoveDown(floor)) {
                elevator.moveDown();
            }
        }
        elevator.stop();
        addRequest(destinationFloors.get(floor), 
                  (destinationFloors.get(floor) - floor) > 0 ? Direction.UP : Direction.DOWN, 
                  destinationFloors.get(floor));
        processedRequests.remove(floor);
    }
}

public class ElevatorSystem {
    public static void main(String[] args) {
        ElevatorController controller = new ElevatorController();

        // Simulate requests
        controller.addRequest(3, Direction.UP, 7);
        controller.addRequest(1, Direction.DOWN, 0);
        controller.addRequest(5, Direction.UP, 6);
        controller.addRequest(2, Direction.DOWN, 1);

        // Process all requests
        controller.processRequests();
    }
}
