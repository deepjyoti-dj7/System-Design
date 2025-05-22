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
        cout << "Elevator stopped at floor: " << currentFloor << endl << endl;
    }

    bool shouldMoveUp(int floor) {
        return currentFloor < floor;
    }

    bool shouldMoveDown(int floor) {
        return currentFloor > floor;
    }
};

class ElevatorController {
public:
    Elevator elevator;
    
    priority_queue<int, vector<int>, greater<int>> upRequests; // Min-heap for UP direction
    priority_queue<int> downRequests; // Max-heap for DOWN direction
    unordered_set<int> processedRequests; // Track processed requests
    unordered_map<int, int> destinationFloors;

    void addRequest(int floor, Direction direction, int destinationFloor) {
        if (processedRequests.find(floor) != processedRequests.end()) return; // Skip if already processed
        processedRequests.insert(floor);
        destinationFloors[floor] = destinationFloor;

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
        elevator.direction = UP;
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
        this->addRequest(destinationFloors[floor], (destinationFloors[floor] - floor) > 0 ? UP : DOWN, destinationFloors[floor]);
        processedRequests.erase(floor);
    }
};

int main() {
    ElevatorController controller;

    // Simulate requests
    controller.addRequest(3, UP, 7);
    controller.addRequest(1, DOWN, 0);
    controller.addRequest(5, UP, 6);
    controller.addRequest(2, DOWN, 1);

    // Process all requests
    controller.processRequests();

    return 0;
}