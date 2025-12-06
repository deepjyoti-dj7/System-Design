# API Documentation - Urban Company Clone

## Base URL
```
Production: https://api.urbanclone.com
Development: http://localhost:8080
```

## Authentication
All requests (except registration and login) require JWT token in header:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## User Service APIs

### 1. Register User
**POST** `/api/v1/users/register`

**Request Body:**
```json
{
  "email": "user@example.com",
  "phoneNumber": "+919876543210",
  "password": "SecurePass123",
  "firstName": "John",
  "lastName": "Doe",
  "role": "CUSTOMER",
  "address": {
    "street": "123 Main St",
    "city": "Mumbai",
    "state": "Maharashtra",
    "country": "India",
    "zipCode": "400001",
    "latitude": 19.0760,
    "longitude": 72.8777
  }
}
```

**Response:** `201 Created`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400000,
  "user": {
    "id": 1,
    "email": "user@example.com",
    "phoneNumber": "+919876543210",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER",
    "status": "ACTIVE",
    "emailVerified": false,
    "phoneVerified": false,
    "createdAt": "2024-12-01T10:30:00"
  }
}
```

### 2. User Login
**POST** `/api/v1/users/login`

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "SecurePass123"
}
```

**Response:** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400000,
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER"
  }
}
```

### 3. Get User Profile
**GET** `/api/v1/users/{userId}`

**Response:** `200 OK`
```json
{
  "id": 1,
  "email": "user@example.com",
  "phoneNumber": "+919876543210",
  "firstName": "John",
  "lastName": "Doe",
  "role": "CUSTOMER",
  "status": "ACTIVE",
  "address": {
    "street": "123 Main St",
    "city": "Mumbai",
    "state": "Maharashtra",
    "zipCode": "400001"
  },
  "createdAt": "2024-12-01T10:30:00"
}
```

---

## Booking Service APIs

### 1. Create Booking
**POST** `/api/v1/bookings`

**Headers:**
```
X-User-Id: 1
Authorization: Bearer <token>
```

**Request Body:**
```json
{
  "serviceId": 5,
  "scheduledAt": "2024-12-05T14:00:00",
  "address": {
    "street": "456 Park Ave",
    "city": "Mumbai",
    "state": "Maharashtra",
    "zipCode": "400002",
    "latitude": 19.0760,
    "longitude": 72.8777,
    "apartmentNumber": "A-101",
    "landmark": "Near Central Mall"
  },
  "customerNotes": "Please bring all necessary tools",
  "paymentMethod": "UPI"
}
```

**Response:** `201 Created`
```json
{
  "id": 101,
  "bookingNumber": "BK1701433256ABC12",
  "customerId": 1,
  "serviceId": 5,
  "serviceName": "AC Repair",
  "scheduledAt": "2024-12-05T14:00:00",
  "status": "PENDING",
  "address": {
    "street": "456 Park Ave",
    "city": "Mumbai",
    "apartmentNumber": "A-101"
  },
  "basePrice": 500.00,
  "discount": 0.00,
  "tax": 90.00,
  "totalPrice": 590.00,
  "paymentStatus": "PENDING",
  "paymentMethod": "UPI",
  "createdAt": "2024-12-01T10:45:00"
}
```

### 2. Get Booking Details
**GET** `/api/v1/bookings/{bookingId}`

**Response:** `200 OK`
```json
{
  "id": 101,
  "bookingNumber": "BK1701433256ABC12",
  "customerId": 1,
  "serviceId": 5,
  "partnerId": 25,
  "partnerName": "Rajesh Kumar",
  "scheduledAt": "2024-12-05T14:00:00",
  "status": "PARTNER_ASSIGNED",
  "totalPrice": 590.00,
  "paymentStatus": "PENDING"
}
```

### 3. Get Customer Bookings
**GET** `/api/v1/bookings/customer/{customerId}?page=0&size=10`

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 101,
      "bookingNumber": "BK1701433256ABC12",
      "serviceId": 5,
      "serviceName": "AC Repair",
      "status": "PARTNER_ASSIGNED",
      "scheduledAt": "2024-12-05T14:00:00",
      "totalPrice": 590.00
    }
  ],
  "totalElements": 15,
  "totalPages": 2,
  "size": 10,
  "number": 0
}
```

### 4. Assign Partner to Booking
**PUT** `/api/v1/bookings/{bookingId}/assign-partner/{partnerId}`

**Response:** `200 OK`
```json
{
  "id": 101,
  "bookingNumber": "BK1701433256ABC12",
  "partnerId": 25,
  "status": "PARTNER_ASSIGNED",
  "message": "Partner assigned successfully"
}
```

### 5. Update Booking Status
**PUT** `/api/v1/bookings/{bookingId}/status?status=IN_PROGRESS`

**Response:** `200 OK`
```json
{
  "id": 101,
  "status": "IN_PROGRESS",
  "message": "Status updated successfully"
}
```

### 6. Cancel Booking
**PUT** `/api/v1/bookings/{bookingId}/cancel?reason=Customer%20unavailable`

**Response:** `200 OK`
```json
{
  "id": 101,
  "status": "CANCELLED",
  "cancellationReason": "Customer unavailable",
  "cancelledAt": "2024-12-01T11:00:00"
}
```

---

## Payment Service APIs

### 1. Initiate Payment
**POST** `/api/v1/payments`

**Request Body:**
```json
{
  "bookingId": 101,
  "customerId": 1,
  "amount": 590.00,
  "paymentMethod": "UPI"
}
```

**Response:** `201 Created`
```json
{
  "id": 501,
  "transactionId": "TXN1701433400XYZ89",
  "bookingId": 101,
  "amount": 590.00,
  "status": "SUCCESS",
  "paymentMethod": "UPI",
  "gateway": "RAZORPAY",
  "processedAt": "2024-12-01T11:10:00",
  "createdAt": "2024-12-01T11:09:55"
}
```

### 2. Get Payment Details
**GET** `/api/v1/payments/{paymentId}`

**Response:** `200 OK`
```json
{
  "id": 501,
  "transactionId": "TXN1701433400XYZ89",
  "bookingId": 101,
  "amount": 590.00,
  "status": "SUCCESS",
  "gateway": "RAZORPAY",
  "processedAt": "2024-12-01T11:10:00"
}
```

### 3. Process Refund
**POST** `/api/v1/payments/{paymentId}/refund`

**Request Body:**
```json
{
  "amount": 590.00,
  "reason": "Service cancelled by customer"
}
```

**Response:** `200 OK`
```json
{
  "id": 501,
  "transactionId": "TXN1701433400XYZ89",
  "status": "REFUNDED",
  "refundedAmount": 590.00,
  "refundedAt": "2024-12-01T11:30:00"
}
```

---

## Partner Service APIs

### 1. Register Partner
**POST** `/api/v1/partners`

**Request Body:**
```json
{
  "userId": 10,
  "businessName": "Kumar Home Services",
  "serviceIds": [1, 5, 8],
  "yearsOfExperience": 5,
  "bio": "Professional AC repair and maintenance",
  "workingArea": {
    "city": "Mumbai",
    "state": "Maharashtra",
    "latitude": 19.0760,
    "longitude": 72.8777,
    "radiusKm": 15.0
  },
  "certifications": ["HVAC Certified", "Electrician License"]
}
```

**Response:** `201 Created`
```json
{
  "id": 25,
  "userId": 10,
  "businessName": "Kumar Home Services",
  "status": "PENDING_VERIFICATION",
  "averageRating": null,
  "totalCompletedBookings": 0,
  "createdAt": "2024-12-01T12:00:00"
}
```

### 2. Get Available Partners
**GET** `/api/v1/partners/available?serviceId=5&latitude=19.0760&longitude=72.8777&radius=10`

**Response:** `200 OK`
```json
{
  "partners": [
    {
      "id": 25,
      "businessName": "Kumar Home Services",
      "averageRating": 4.8,
      "totalCompletedBookings": 156,
      "distance": 3.5,
      "isAvailable": true
    },
    {
      "id": 32,
      "businessName": "Quick Fix Services",
      "averageRating": 4.6,
      "totalCompletedBookings": 98,
      "distance": 5.2,
      "isAvailable": true
    }
  ]
}
```

### 3. Set Partner Availability
**POST** `/api/v1/partners/{partnerId}/availability`

**Request Body:**
```json
{
  "slots": [
    {
      "dayOfWeek": "MONDAY",
      "startTime": "09:00:00",
      "endTime": "18:00:00",
      "isAvailable": true
    },
    {
      "dayOfWeek": "TUESDAY",
      "startTime": "09:00:00",
      "endTime": "18:00:00",
      "isAvailable": true
    }
  ]
}
```

**Response:** `201 Created`
```json
{
  "message": "Availability updated successfully",
  "slotsCreated": 2
}
```

---

## Catalog Service APIs

### 1. List All Services
**GET** `/api/v1/catalog/services?page=0&size=20`

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 5,
      "name": "AC Repair & Service",
      "description": "Professional AC repair and maintenance",
      "categoryId": 2,
      "categoryName": "Home Appliances",
      "basePrice": 500.00,
      "durationMinutes": 60,
      "imageUrl": "https://cdn.example.com/ac-repair.jpg",
      "averageRating": 4.5,
      "isPopular": true
    }
  ],
  "totalElements": 45,
  "totalPages": 3
}
```

### 2. Get Service Details
**GET** `/api/v1/catalog/services/{serviceId}`

**Response:** `200 OK`
```json
{
  "id": 5,
  "name": "AC Repair & Service",
  "description": "Complete AC repair and maintenance service",
  "category": {
    "id": 2,
    "name": "Home Appliances"
  },
  "basePrice": 500.00,
  "durationMinutes": 60,
  "features": [
    "Gas refilling",
    "Filter cleaning",
    "General checkup",
    "90 days warranty"
  ],
  "averageRating": 4.5,
  "totalBookings": 1250
}
```

### 3. List Categories
**GET** `/api/v1/catalog/categories`

**Response:** `200 OK`
```json
{
  "categories": [
    {
      "id": 1,
      "name": "Cleaning",
      "description": "Home cleaning services",
      "iconUrl": "https://cdn.example.com/cleaning-icon.png",
      "serviceCount": 12
    },
    {
      "id": 2,
      "name": "Home Appliances",
      "description": "Appliance repair and maintenance",
      "iconUrl": "https://cdn.example.com/appliances-icon.png",
      "serviceCount": 15
    }
  ]
}
```

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-12-01T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": {
    "email": "Invalid email format",
    "phoneNumber": "Phone number is required"
  }
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-12-01T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-12-01T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 999"
}
```

### 409 Conflict
```json
{
  "timestamp": "2024-12-01T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Email already registered"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-12-01T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

## Rate Limiting
- 100 requests per minute per IP
- 1000 requests per hour per user

## Pagination
All list endpoints support pagination:
- `page`: Page number (0-indexed)
- `size`: Items per page (default: 20, max: 100)
- `sort`: Sort field and direction (e.g., `createdAt,desc`)
