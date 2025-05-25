package models;

import java.util.List;

public class Restaurant {
    private String id;
    private String name;
    private String address;
    private List<FoodItem> menu;

    public Restaurant(String id, String name, String address, List<FoodItem> menu) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menu = menu;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<FoodItem> getMenu() {
        return menu;
    }
}
