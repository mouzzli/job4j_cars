package ru.job4j.cars.model;

public enum WheelDrive {
    FWD("передний"),
    FULL_WD("полный"),
    RWD("задний");

    private final String name;

    WheelDrive(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
