class Elevator {
  constructor() {
    this.currentFloor = 0;
    this.direction = "IDLE";
    this.isMoving = false;
  }

  moveUp() {
    this.currentFloor++;
    this.direction = "UP";
    this.isMoving = true;
    console.log(`Elevator moving up to floor: ${this.currentFloor}`);
  }

  moveDown() {
    this.currentFloor--;
    this.direction = "DOWN";
    this.isMoving = true;
    console.log(`Elevator moving down to floor: ${this.currentFloor}`);
  }

  stop() {
    this.direction = "IDLE";
    this.isMoving = false;
    console.log(`Elevator stopped at floor: ${this.currentFloor}\n`);
  }

  shouldMoveUp(floor) {
    return this.currentFloor < floor;
  }

  shouldMoveDown(floor) {
    return this.currentFloor > floor;
  }
}

class ElevatorController {
  constructor() {
    this.elevator = new Elevator();
    this.upRequests = [];
    this.downRequests = [];
    this.processedRequests = new Set();
    this.destinationFloors = new Map();
  }

  addRequest(floor, direction, destinationFloor) {
    if (this.processedRequests.has(floor)) return;
    this.processedRequests.add(floor);
    this.destinationFloors.set(floor, destinationFloor);

    if (direction === "UP") {
      this.upRequests.push(floor);
      this.upRequests.sort((a, b) => a - b); // Min-heap simulation
    } else if (direction === "DOWN") {
      this.downRequests.push(floor);
      this.downRequests.sort((a, b) => b - a); // Max-heap simulation
    }
  }

  processRequests() {
    while (this.upRequests.length > 0 || this.downRequests.length > 0) {
      if (this.elevator.direction === "IDLE") {
        this.elevator.direction = "UP";
      }

      if (this.elevator.direction === "UP") {
        this.handleUpRequests();
        if (this.upRequests.length === 0 && this.downRequests.length > 0) {
          this.elevator.direction = "DOWN";
        }
      } else if (this.elevator.direction === "DOWN") {
        this.handleDownRequests();
        if (this.downRequests.length === 0 && this.upRequests.length > 0) {
          this.elevator.direction = "UP";
        }
      }
    }
  }

  handleUpRequests() {
    while (this.upRequests.length > 0 && this.elevator.direction === "UP") {
      let nextFloor = this.upRequests.shift();
      this.moveToFloor(nextFloor);
    }
  }

  handleDownRequests() {
    while (this.downRequests.length > 0 && this.elevator.direction === "DOWN") {
      let nextFloor = this.downRequests.shift();
      this.moveToFloor(nextFloor);
    }
  }

  moveToFloor(floor) {
    while (this.elevator.currentFloor !== floor) {
      if (this.elevator.shouldMoveUp(floor)) {
        this.elevator.moveUp();
      } else if (this.elevator.shouldMoveDown(floor)) {
        this.elevator.moveDown();
      }
    }
    this.elevator.stop();
    let destFloor = this.destinationFloors.get(floor);
    this.addRequest(destFloor, destFloor > floor ? "UP" : "DOWN", destFloor);
    this.processedRequests.delete(floor);
  }
}

// Main function to simulate requests
function main() {
  const controller = new ElevatorController();

  // Simulate requests
  controller.addRequest(3, "UP", 7);
  controller.addRequest(1, "DOWN", 0);
  controller.addRequest(5, "UP", 6);
  controller.addRequest(2, "DOWN", 1);

  // Process all requests
  controller.processRequests();
}

// Run the main function
main();
