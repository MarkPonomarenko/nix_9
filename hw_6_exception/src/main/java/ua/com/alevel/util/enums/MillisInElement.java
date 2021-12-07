package ua.com.alevel.util.enums;

public enum MillisInElement {
    MILLI(1L),
    SECOND(1000L),
    MINUTE(60000L),
    HOUR(3600000L),
    DAY(86400000L),
    YEAR(31536000000L),
    LEAP(31622400000L)
    ;

    private long milli;

    MillisInElement(long milli) {
        this.milli = milli;
    }

    public long getMilli() {
        return milli;
    }
}
