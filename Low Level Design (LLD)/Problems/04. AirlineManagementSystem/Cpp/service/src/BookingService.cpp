#include "../include/BookingService.h"
#include <stdexcept>
#include <random>
#include <sstream>

static std::string generateUUID() {
    static std::random_device rd;
    static std::mt19937 gen(rd());
    static std::uniform_int_distribution<> dis(0, 15);
    std::stringstream ss;
    for (int i = 0; i < 32; ++i)
        ss << std::hex << dis(gen);
    return ss.str();
}

Booking* BookingService::createBooking(Flight* flight, Passenger* passenger, const std::string& seatNumber) {
    Seat* seat = flight->getSeatByNumber(seatNumber);
    if (!seat || seat->getSeatStatus() == SeatStatus::BOOKED) {
        throw std::runtime_error("Seat not available");
    }

    Booking* booking = new Booking(generateUUID(), flight, passenger, seat);
    Payment* payment = new Payment(generateUUID(), 100.0);

    booking->confirmBooking(payment);
    passenger->addBooking(booking);

    NotificationService().sendBookingConfirmation(passenger, booking, flight, seat);

    return booking;
}
