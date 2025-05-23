#include "NotificationService.h"

void NotificationService::sendBookingConfirmation(Passenger* passenger, Booking* booking, Flight* flight, Seat* seat) {
    std::cout << "Email: " << passenger->getEmail() << "\n"
              << "Booking id: " << booking->getBookingId() << "\n"
              << "Flight: " << flight->getFlightNumber() << "\n"
              << "Seat No: " << seat->getSeatNumber() << "\n"
              << "Route: " << flight->getRoute() << "\n"
              << "Schedule: " << flight->getSchedule() << "\n";
}
