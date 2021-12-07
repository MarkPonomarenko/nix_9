package ua.com.alevel.date;


public class DateClass {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    public DateClass() {

    }

    private static final int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static int[] getDays() {
        return days;
    }

    public static boolean leapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else return year % 100 != 0;
    }

    public void setValues(int y, int m, int d, int h, int min, int sec, int mils) {
            if (dateValidation(y, m, d) && timeValidation(h, min, sec, mils)) {
                this.year = y;
                this.month = m;
                this.day = d;
                this.hour = h;
                this.minute = min;
                this.second = sec;
                this.millisecond = mils;
            } else {
                System.out.println("Ошибка ввода! Проверьте ещё раз");
            }
    }

    public static boolean timeValidation(int h, int m, int s, int ms) {
        boolean valid = true;
        if (h < 0 || h > 23) {
            System.out.println("Неверное значение - час");
            valid = false;
        }
        if (m < 0 || m > 59) {
            System.out.println("Неверное значение - минута");
            valid = false;
        }
        if (s < 0 || s > 59) {
            System.out.println("Неверное значение - секунда");
            valid = false;
        }
        if (ms < 0 || ms > 999) {
            System.out.println("Неверное значение - милисекунда");
            valid = false;
        }
        return valid;
    }

    public static boolean dateValidation(int y, int m, int d) {
        boolean valid = true;
        int month = m - 1;
        if (m < 0 || m > 11) {
            System.out.println("Неверное значение - месяц");
            valid =  false;
        }
        int maxD = days[month];
        if (month == 1 && leapYear(y)) {
            maxD = 29;
        }

        if (d < 1 || d > maxD) {
            System.out.println("Неверное значение - день");
            valid = false;
        }
        return valid;
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year + " "
                + hour + ":" + minute + ":" + second + ":" + millisecond;
    }

    @Override
    public boolean equals(Object date) {
        if (this == date)
            return true;
        if (!(date instanceof DateClass))
            return false;
        DateClass tmp = (DateClass) date;
        return ((this.year == tmp.year) && (this.month == tmp.month) && (this.day == tmp.day)
                && (this.hour == tmp.day) && (this.minute == tmp.day) && (this.second == tmp.second)
                && (this.millisecond == tmp.millisecond));
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(int millisecond) {
        this.millisecond = millisecond;
    }

}
