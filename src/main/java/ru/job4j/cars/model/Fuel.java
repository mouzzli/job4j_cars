package ru.job4j.cars.model;

public enum Fuel {
    PETROL("бензин"),
    DIESEL("дизель"),
    ELECTRIC("электро"),
    HYBRID_PETROL_ELECTRIC("бензин/электро"),
    HYBRID_DIESEL_ELECTRIC("дизель/электро");

    private final String name;

    Fuel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
