#include <bits/stdc++.h>
using namespace std;

enum Direction { UP, DOWN, IDLE };

class Elevator {
public:
    int currentFloor;
    Direction direction;
    bool isMoving;

    Elevator() {
        currentFloor = 0;
        direction = IDLE;
        isMoving = false;
    }

    void moveUp() {
        currentFloor++;
        direction = UP;
        isMoving = true;
        cout << "Elevator moving up to floor: " << currentFloor << endl;
    }

    void moveDown() {
        currentFloor--;
        direction = DOWN;
        isMoving = true;
        cout << "Elevator moving down to floor: " << currentFloor << endl;
    }

    void stop() { 
        direction = IDLE;
        isMoving = false;
        cout << "Elevator stopped at floor: " << currentFloor << endl;
    }

    bool shouldMoveUp(int floor) {
        return currentFloor < floor;
    }

    bool shouldMoveDown(int floor) {
        return currentFloor > floor;
    }
};

class Request {
public:
    int floor;
    Direction direction;
    int requestId;

    Request(int aFloor, Direction aDirection, int aRequestId) {
        floor = aFloor;
        direction = aDirection;
        requestId = aRequestId;
    }
};

class ElevatorController {
public:
    Elevator elevator;
    
    priority_queue<int, vector<int>, greater<int>> upRequests; // Min-heap for UP direction
    priority_queue<int> downRequests; // Max-heap for DOWN direction
    unordered_set<int> processedRequests; // Track processed requests
    int requestCounter; // To generate unique request IDs

    ElevatorController() {
        requestCounter = 0;
    }

    void addRequest(int floor, Direction direction) {
        if (processedRequests.find(floor) != processedRequests.end()) return; // Skip if already processed
        processedRequests.insert(floor);

        if (direction == UP) {
            upRequests.push(floor);
        }
        else if (direction == DOWN) {
            downRequests.push(floor);
        }
    }

    void processRequests() {
        while (!upRequests.empty() || !downRequests.empty()) {
            if (elevator.direction == IDLE) {
                determineInitialDirection();
            }

            if (elevator.direction == UP) {
                handleUpRequests();
                if (upRequests.empty() && !downRequests.empty()) {
                    elevator.direction = DOWN;
                }
            }
            else if (elevator.direction == DOWN) {
                handleDownRequests();
                if (downRequests.empty() && !upRequests.empty()) {
                    elevator.direction = UP;
                }
            }
        }
    }

private:
    void determineInitialDirection() {
        if (upRequests.empty() && !downRequests.empty()) {
            elevator.direction = DOWN;
        }
        else if (!upRequests.empty() && downRequests.empty()) {
            elevator.direction = UP;
        }
        else if (!upRequests.empty() && !downRequests.empty()) {
            int upRequestFloor = upRequests.top();
            int downRequestFloor = downRequests.top();
            if (abs(elevator.currentFloor - upRequestFloor) <= abs(elevator.currentFloor - downRequestFloor)) {
                elevator.direction = UP;
            }
            else {
                elevator.direction = DOWN;
            }
        }
    };

    void handleUpRequests() {
        while (!upRequests.empty() && elevator.direction == UP) {
            int nextFloor = upRequests.top();
            upRequests.pop();
            moveToFloor(nextFloor);
        }
    };
    
    void handleDownRequests() {
        while (!downRequests.empty() && elevator.direction == DOWN) {
            int nextFloor = downRequests.top();
            downRequests.pop();
            moveToFloor(nextFloor);
        }
    }

    void moveToFloor(int floor) {
        while (elevator.currentFloor != floor) {
            if (elevator.shouldMoveUp(floor)) {
                elevator.moveUp();
            }
            else if (elevator.shouldMoveDown(floor)) {
                elevator.moveDown();
            }
        }
        elevator.stop();
        processedRequests.erase(floor);
    }
};

int main() {
    ElevatorController controller;

    // Simulate requests
    controller.addRequest(3, UP);
    controller.addRequest(1, DOWN);
    controller.addRequest(5, UP);
    controller.addRequest(2, DOWN);
    controller.addRequest(4, UP);

    // Process all requests
    controller.processRequests();

    return 0;
}