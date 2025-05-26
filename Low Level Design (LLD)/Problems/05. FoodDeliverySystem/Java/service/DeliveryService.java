package service;

import models.DeliveryPerson;
import utils.DeliveryStatus;

import java.util.HashMap;
import java.util.Map;

public class DeliveryService {
    private Map<String, DeliveryPerson> deliveryPersons = new HashMap<>();

    public void registerDeliveryPerson(DeliveryPerson person) {
        deliveryPersons.put(person.getId(), person);
    }

    public DeliveryPerson getAvailableDeliveryPerson() {
        for (DeliveryPerson person : deliveryPersons.values()) {
            if (person.getStatus() == DeliveryStatus.AVAILABLE) {
                return person;
            }
        }
        return null;
    }
}
