#pragma once

#include <string>
#include <memory>
#include <unordered_map>
#include "../../models/include/DeliveryPerson.h"
#include "../../utils/include/DeliveryStatus.h"

class DeliveryService {
private:
    std::unordered_map<std::string, std::shared_ptr<DeliveryPerson>> deliveryPersons;

public:
    void registerDeliveryPerson(const std::shared_ptr<DeliveryPerson>& person);
    std::shared_ptr<DeliveryPerson> getAvailableDeliveryPerson() const;
};