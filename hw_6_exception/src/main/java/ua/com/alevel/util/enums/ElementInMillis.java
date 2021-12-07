package ua.com.alevel.util.enums;

public enum ElementInMillis{

    MILLI(1),
    SECOND(MILLI.getValue() / 1000),
    MINUTE(SECOND.getValue() / 60),
    HOUR(MINUTE.getValue() / 60),
    DAY(HOUR.getValue() / 24),
    YEAR(31536000000L),
    LEAP(31622400000L);

    private double value;

    ElementInMillis(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

}
