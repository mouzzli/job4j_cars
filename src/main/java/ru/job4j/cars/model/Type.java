package ru.job4j.cars.model;

public enum Type {
    HATCHBACK("хетчбэк"),
    SEDAN("седан"),
    LIFTBACK("лифтбэк"),
    STATION_WAGON("универсал"),
    COUPE("купе"),
    CROSSOVER("кроссовер"),
    SPORT_UTILITY("внедорожник"),
    MINIVAN("минивэн");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
