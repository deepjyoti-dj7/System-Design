#include "../include/Booking.h"

Booking::Booking(const std::string& bookingId, Flight* flight, Passenger* passenger, Seat* seat)
    : bookingId(bookingId),
      flight(flight),
      passenger(passenger),
      seat(seat),
      bookingStatus(BookingStatus::PENDING),
      payment(nullptr) {}

void Booking::confirmBooking(Payment* payment) {
    this->bookingStatus = BookingStatus::CONFIRMED;
    this->payment = payment;
    seat->book();
}

std::string Booking::getBookingId() const {
    return bookingId;
}
