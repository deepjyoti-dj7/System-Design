#include "../include/DeliveryService.h"

void DeliveryService::registerDeliveryPerson(const std::shared_ptr<DeliveryPerson>& person) {
    deliveryPersons[person->getId()] = person;
}

std::shared_ptr<DeliveryPerson> DeliveryService::getAvailableDeliveryPerson() const {
    for (const auto& pair : deliveryPersons) {
        const auto& person = pair.second;
        if (person->getStatus() == DeliveryStatus::AVAILABLE) {
            return person;
        }
    }
    return nullptr;
}