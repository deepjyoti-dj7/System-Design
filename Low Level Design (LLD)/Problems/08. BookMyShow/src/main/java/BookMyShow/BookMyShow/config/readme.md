## DB schema & relationships (ER summary)

- User (1) --- (N) Booking
- Movie (1) --- (N) Show
- Theatre (1) --- (N) Screen
- Screen (1) --- (N) Seat
- Show (1) --- (N) Ticket (via Booking) & references Screen
- Booking (1) --- (N) Ticket