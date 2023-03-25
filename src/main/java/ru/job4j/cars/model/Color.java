package ru.job4j.cars.model;

public enum Color {
    BLACK("чёрный"),
    SILVER("серебристый"),
    WHITE("белый"),
    GRAY("серый"),
    BLUE("синий"),
    RED("красный"),
    GREEN("зелёный"),
    BROWN("коричневый"),
    BEIGE("бежевый"),
    GOLDEN("золотистый"),
    PURPLE("пурпунрый"),
    VIOLET("фиолетовый"),
    YELLOW("желтый"),
    PINK("розовый");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
