import heapq

class Direction:
    UP = "UP"
    DOWN = "DOWN"
    IDLE = "IDLE"

class Elevator:
    def __init__(self):
        self.current_floor = 0
        self.direction = Direction.IDLE
        self.is_moving = False

    def move_up(self):
        self.current_floor += 1
        self.direction = Direction.UP
        self.is_moving = True
        print(f"Elevator moving up to floor: {self.current_floor}")

    def move_down(self):
        self.current_floor -= 1
        self.direction = Direction.DOWN
        self.is_moving = True
        print(f"Elevator moving down to floor: {self.current_floor}")

    def stop(self):
        self.direction = Direction.IDLE
        self.is_moving = False
        print(f"Elevator stopped at floor: {self.current_floor}\n")

    def should_move_up(self, floor):
        return self.current_floor < floor

    def should_move_down(self, floor):
        return self.current_floor > floor

class ElevatorController:
    def __init__(self):
        self.elevator = Elevator()
        self.up_requests = []  # Min-heap for UP requests
        self.down_requests = []  # Max-heap for DOWN requests (inverted values for max behavior)
        self.processed_requests = set()
        self.destination_floors = {}

    def add_request(self, floor, direction, destination_floor):
        if floor in self.processed_requests:
            return
        self.processed_requests.add(floor)
        self.destination_floors[floor] = destination_floor

        if direction == Direction.UP:
            heapq.heappush(self.up_requests, floor)  # Min-heap
        elif direction == Direction.DOWN:
            heapq.heappush(self.down_requests, -floor)  # Max-heap (negative values)

    def process_requests(self):
        while self.up_requests or self.down_requests:
            if self.elevator.direction == Direction.IDLE:
                self.elevator.direction = Direction.UP

            if self.elevator.direction == Direction.UP:
                self.handle_up_requests()
                if not self.up_requests and self.down_requests:
                    self.elevator.direction = Direction.DOWN

            elif self.elevator.direction == Direction.DOWN:
                self.handle_down_requests()
                if not self.down_requests and self.up_requests:
                    self.elevator.direction = Direction.UP

    def handle_up_requests(self):
        while self.up_requests and self.elevator.direction == Direction.UP:
            next_floor = heapq.heappop(self.up_requests)
            self.move_to_floor(next_floor)

    def handle_down_requests(self):
        while self.down_requests and self.elevator.direction == Direction.DOWN:
            next_floor = -heapq.heappop(self.down_requests)  # Convert back to positive
            self.move_to_floor(next_floor)

    def move_to_floor(self, floor):
        while self.elevator.current_floor != floor:
            if self.elevator.should_move_up(floor):
                self.elevator.move_up()
            elif self.elevator.should_move_down(floor):
                self.elevator.move_down()

        self.elevator.stop()
        dest_floor = self.destination_floors.get(floor)
        if dest_floor is not None:
            self.add_request(dest_floor, Direction.UP if dest_floor > floor else Direction.DOWN, dest_floor)
        self.processed_requests.discard(floor)

# Main function to simulate elevator requests
def main():
    controller = ElevatorController()

    # Simulate requests
    controller.add_request(3, Direction.UP, 7)
    controller.add_request(1, Direction.DOWN, 0)
    controller.add_request(5, Direction.UP, 6)
    controller.add_request(2, Direction.DOWN, 1)

    # Process all requests
    controller.process_requests()

# Run the main function
if __name__ == "__main__":
    main()
