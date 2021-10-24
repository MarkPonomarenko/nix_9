package string.methods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringReverse {

    public static String reverse(String text, Boolean choice) {
        if (!choice) {
            StringBuilder result = new StringBuilder();
            String[] words = text.split(" |\\!|\\.|\\,|\\?");

            for (String word : words) {
                StringBuilder reverseWord = new StringBuilder();
                for (int i = word.length() - 1; i >= 0; i--) {
                    reverseWord.append(word.charAt(i));
                }
                result.append(reverseWord).append(' ');
            }
            result.setLength(result.length() - 1);
            return result.toString();
        }
        else {
            StringBuilder result = new StringBuilder();
            char[] textChar = text.toCharArray();

            for (int i = textChar.length - 1; i >= 0; i--) {
                result.append(textChar[i]);
            }
            result.setLength(result.length());
            return result.toString();
        }
    }

    public static String reverse(String text, String subText) {
        Pattern pattern = Pattern.compile(subText);
        Matcher match = pattern.matcher(text);
        String word = reverse(subText, false);
        return match.replaceAll(word);
    }

    public static String reverse(String text, int firstIndex, int lastIndex) {
        String targetString = text.substring(firstIndex, lastIndex + 1);
        targetString = reverse(targetString, false);
        StringBuilder reversed = new StringBuilder(text);
        reversed.replace(firstIndex, lastIndex + 1, targetString);
        return reversed.toString();
    }
    //рекурсивное дополнение к реверсу меж чарами для реверса всех подстрок
    public static String reverse(String text, char firstChar, char lastChar, int index) {
            int firstIndex = text.indexOf(firstChar, index);
            int lastIndex = text.indexOf(lastChar, firstIndex);
            String result = text;
            if (lastIndex != -1 && firstIndex != -1) {
                result = reverse(text, firstIndex, lastIndex);
                if ((lastIndex - firstIndex) <= (text.length() - lastIndex - 1)) {
                    result = reverse(result, firstChar, lastChar, lastIndex + 1);
                }
            }
            return result;
        }

    public static String reverse(String text, char firstChar, char lastChar) {
        int firstIndex = text.indexOf(firstChar);
        int lastIndex = text.indexOf(lastChar, firstIndex);
        String result = text;
        if (lastIndex != -1 && firstIndex != -1) {
            result = reverse(text, firstIndex, lastIndex);
            if ((lastIndex - firstIndex) <= (text.length() - lastIndex - 1)) {
                result = reverse(result, firstChar, lastChar, lastIndex + 1);
            }
        }
        return result;
    }
    //рекурсивное дополнение
    public static String reverse(String text, String first, String last, int index) {
        int firstIndex = text.indexOf(first, index);
        int lastIndex = text.indexOf(last, firstIndex) + last.length() - 1;
        if (firstIndex == -1 || lastIndex == -1) return text;
        String result = reverse(text, firstIndex, lastIndex);
        if ((lastIndex - firstIndex) <= (text.length() - lastIndex - 1)) {
            result = reverse(result, first, last, lastIndex + 1);
        }
        return result;
    }
    public static String reverse(String text, String first, String last) {
        int firstIndex = text.indexOf(first);
        int lastIndex = text.indexOf(last, firstIndex) + last.length() - 1;
        if (firstIndex == -1 || lastIndex == -1) return text;
        String result = reverse(text, firstIndex, lastIndex);
        if ((lastIndex - firstIndex) <= (text.length() - lastIndex - 1)) {
            result = reverse(result, first, last, lastIndex + 1);
        }
        return result;
    }
}