package ua.com.alevel.check;

import ua.com.alevel.date.DateClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckFormat {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private static final String[] MONTHS = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};

    public static boolean check(String date, String choice) {
        boolean isValid = false;
        switch (choice) {
            case "1":
                isValid = firstFormat(date);
                break;
            case "2":
                isValid = secondFormat(date);
                break;
            case "3":
                isValid = thirdFormat(date);
                break;
            case "4":
                isValid = fourthFormat(date);
                break;
            default:
                System.out.println("Incorrect input");
        }
        return !isValid;
    }

    public static boolean firstFormat(String dateStr) {
        LOGGER_INFO.info("[Checker] Check first format");
        try {
            if (dateStr.contains("/") || dateStr.contains("-")) {
                String punct;
                if (dateStr.contains("/")) {
                    punct = "/";
                } else {
                    punct = "-";
                }

                String[] split = new String[4];

                switch (punct) {
                    case "/":
                        split = dateStr.split("[/ ]", 4);
                        break;
                    case "-":
                        split = dateStr.split("[- ]", 4);
                        break;
                }

                if (split.length >= 3) {
                    int day;
                    if (split[0].equals("")) {
                        day = 1;
                    } else {
                        day = Integer.parseInt(split[0]);
                    }

                    int month;
                    if (split[1].equals("")) {
                        month = 1;
                    } else {
                        month = Integer.parseInt(split[1]);
                    }

                    int year;
                    if (split[2].equals("")) {
                        year = 0;
                    } else {
                        year = Integer.parseInt(split[2]);
                    }

                    if ((split[0].length() == 2 || split[0].matches(""))
                            && (split[1].length() == 2 || split[1].matches(""))) {
                        if (day > 0 && month <= 12 && month >= 1 && year >= 0) {
                            return DateClass.dateValidation(year, month, day);
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER_ERROR.error("Incorrect index");
            return false;
        }
    }

    public static boolean secondFormat(String stringDate) {
        LOGGER_INFO.info("Check second format");
        try {
            if (stringDate.contains("/") || stringDate.contains("-")) {
                String punct;
                if (stringDate.contains("/")) {
                    punct = "/";
                } else {
                    punct = "-";
                }

                String[] split = new String[4];

                switch (punct) {
                    case "/":
                        split = stringDate.split("[/ ]", 4);
                        break;
                    case "-":
                        split = stringDate.split("[- ]", 4);
                        break;
                }

                if (split.length >= 3) {
                    int day;
                    if (split[1].equals("")) {
                        day = 1;
                    } else {
                        day = Integer.parseInt(split[1]);
                    }
                    int month;
                    if (split[0].equals("")) {
                        month = 1;
                    } else {
                        month = Integer.parseInt(split[0]);
                    }

                    int year;
                    if (split[2].equals("")) {
                        year = 0;
                    } else {
                        year = Integer.parseInt(split[2]);
                    }

                    if (day > 0 && month <= 12 && month >= 1 && year >= 0) {
                        return DateClass.dateValidation(year, month, day);
                    }
                    return false;
                }
                return false;
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER_ERROR.error("Incorrect index");
            return false;
        }
    }

    public static boolean thirdFormat(String stringDate) {
        LOGGER_INFO.info("Check third format");
        try {
            int month;
            String[] split;
            if (stringDate.contains("-")) {
                split = stringDate.split("[- ]", 4);
            } else {
                split = stringDate.split("[ ]", 4);
            }
            if (split.length >= 3) {
                int day;
                if (split[1].equals("")) {
                    day = 1;
                } else {
                    day = Integer.parseInt(split[1]);
                }
                int year;
                if (split[2].equals("")) {
                    year = 0;
                } else {
                    year = Integer.parseInt(split[2]);
                }
                if (split[0].equals("")) {
                    month = 1;
                    return DateClass.dateValidation(year, month, day);
                } else {
                    for (int i = 0; i < MONTHS.length; i++) {
                        if (split[0].equalsIgnoreCase(MONTHS[i])) {
                            month = i + 1;
                            return DateClass.dateValidation(year, month, day);
                        }
                    }
                }
            }
            return false;
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            return false;
        }
    }

    public static boolean fourthFormat(String stringDate) {
        LOGGER_INFO.info("Check fourth format");
        try {
            String[] split;
            if (stringDate.contains("-")) {
                split = stringDate.split("[- ]", 4);
            } else {
                split = stringDate.split("[ ]", 4);
            }
            if (split.length >= 3) {
                int day;
                if (split[0].equals("")) {
                    day = 1;
                } else {
                    day = Integer.parseInt(split[0]);
                }
                int year;
                if (split[2].equals("")) {
                    year = 0;
                } else {
                    year = Integer.parseInt(split[2]);
                }
                int month;
                if (split[1].equals("")) {
                    month = 1;
                    return DateClass.dateValidation(year, month, day);
                } else {
                    if (split[0].length() == 2 || split[0].matches("")) {
                        for (int i = 0; i < MONTHS.length; i++) {
                            if (split[1].equalsIgnoreCase(MONTHS[i])) {
                                month = i + 1;
                                return DateClass.dateValidation(year, month, day);
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
            return false;
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            return false;
        }
    }

    public static boolean checkFormatOfTime(String input) {
        boolean flag = true;
        try {
            if (input.contains("/") || input.contains("-")) {
                String punct;
                if (input.contains("/")) punct = "/";
                else punct = "-";
                String[] split = new String[4];

                switch (punct) {
                    case "/":
                        split = input.split("[/ ]");
                        break;
                    case "-":
                        split = input.split("[- ]");
                        break;
                }
                if (split.length > 3) {
                    String[] timeSplit = split[3].split(":");
                    if (!timeSplit[0].equals("")) {
                        if (Integer.parseInt(timeSplit[0]) > 23 || Integer.parseInt(timeSplit[0]) < 0) {
                            flag = false;
                        }
                    }
                    if (timeSplit.length > 1) {
                        if (Integer.parseInt(timeSplit[1]) > 59 || Integer.parseInt(timeSplit[1]) < 0) {
                            flag = false;
                        }
                    }
                    if (timeSplit.length > 2) {
                        if (Integer.parseInt(timeSplit[2]) > 59 || Integer.parseInt(timeSplit[2]) < 0) {
                            flag = false;
                        }
                    }
                    if (timeSplit.length > 3) {
                        if (Integer.parseInt(timeSplit[3]) > 999 || Integer.parseInt(timeSplit[3]) < 0) {
                            flag = false;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            LOGGER_ERROR.error("Incorrect number format");
            return true;
        }
        return !flag;
    }
}
