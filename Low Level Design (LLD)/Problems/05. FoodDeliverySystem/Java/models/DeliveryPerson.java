package models;

import utils.DeliveryStatus;

public class DeliveryPerson {
    private String id;
    private String name;
    private String phone;
    private DeliveryStatus status;

    public DeliveryPerson(String id, String name, String phone, DeliveryStatus status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public DeliveryStatus getStatus() {
        return status;
    }
}