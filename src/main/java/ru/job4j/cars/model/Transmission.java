package ru.job4j.cars.model;

public enum Transmission {
    MT("механическая"),
    AT("автоматическая"),
    AMT("робот"),
    CVT("вариатор");

    private final String name;

    Transmission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
