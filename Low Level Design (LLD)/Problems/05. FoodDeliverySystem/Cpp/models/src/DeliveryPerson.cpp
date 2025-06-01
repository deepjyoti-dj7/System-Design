#include "../include/DeliveryPerson.h"

DeliveryPerson::DeliveryPerson(const std::string& id,
                               const std::string& name,
                               const std::string& phone,
                               DeliveryStatus status)
    : id(id), name(name), phone(phone), status(status) {}

const std::string& DeliveryPerson::getId() const {
    return id;
}

const std::string& DeliveryPerson::getName() const {
    return name;
}

const std::string& DeliveryPerson::getPhone() const {
    return phone;
}

DeliveryStatus DeliveryPerson::getStatus() const {
    return status;
}

void DeliveryPerson::setStatus(DeliveryStatus newStatus) {
    status = newStatus;
}
