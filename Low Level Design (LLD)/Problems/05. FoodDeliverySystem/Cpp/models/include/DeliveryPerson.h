#pragma once

#include <string>
#include "../../utils/include/DeliveryStatus.h"

class DeliveryPerson {
private:
    std::string id;
    std::string name;
    std::string phone;
    DeliveryStatus status;

public:
    DeliveryPerson(const std::string& id, const std::string& name, const std::string& phone, DeliveryStatus status);

    const std::string& getId() const;
    const std::string& getName() const;
    const std::string& getPhone() const;
    DeliveryStatus getStatus() const;
    
    void setStatus(DeliveryStatus status);
};