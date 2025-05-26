package service;

import models.Restaurant;

import java.util.HashMap;
import java.util.Map;

public class RestaurantService {
    private Map<String, Restaurant> restaurants = new HashMap<>();

    public void addRestaurant(Restaurant restaurant) {
        restaurants.put(restaurant.getId(), restaurant);
    }

    public Restaurant getRestaurantById(String id) {
        return restaurants.get(id);
    }
}
