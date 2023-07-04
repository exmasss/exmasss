import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.*;

class Main {
    private static final Map<Character, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put('I', 1);
        ROMAN_NUMERALS.put('V', 5);
        ROMAN_NUMERALS.put('X', 10);
        ROMAN_NUMERALS.put('L', 50);
        ROMAN_NUMERALS.put('C', 100);
        ROMAN_NUMERALS.put('D', 500);
        ROMAN_NUMERALS.put('M', 1000);
    }

    private static final String[] ROMAN_SYMBOLS = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    private static final int[] ROMAN_VALUES = {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);
        out.print("ввод значения: ");
        String input = scanner.nextLine();
        String result;
        try {
            result = calc(input);
            out.println("результат: " + result);
        } catch (Exception e) {
            out.println("ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("некорректное выражение");
        }

        String aStr = tokens[0];
        String operator = tokens[1];
        String bStr = tokens[2];

        boolean isRoman = isRomanNumeral(aStr) && isRomanNumeral(bStr);
        int a;
        int b;

        if (isRoman) {
            a = convertRomanToArabic(aStr);
            b = convertRomanToArabic(bStr);
        } else {
            try {
                a = Integer.parseInt(aStr);
                b = Integer.parseInt(bStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("некорректные числа");
            }
        }

        int result = switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };

        if (isRoman) {
            if (result < 1) {
                throw new IllegalArgumentException("некорректный результат");
            }
            return convertArabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static boolean isRomanNumeral(String input) {
        for (char c : input.toCharArray()) {
            if (!ROMAN_NUMERALS.containsKey(Character.toUpperCase(c))) {
                return false;
            }
        }
        return true;
    }

    private static int convertRomanToArabic(String input) {
        int result = 0;
        int prevValue = 0;
        for (int i = 0; i < input.length(); i++) {
            char currentChar = Character.toUpperCase(input.charAt(i));
            int currentValue = ROMAN_NUMERALS.get(currentChar);
            if (currentValue > prevValue) {
                result += currentValue - 2 * prevValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }
        return result;
    }

    private static String convertArabicToRoman(int number) {
        if (number <= 0) throw new IllegalArgumentException("некорректное число");

        StringBuilder romanNumber;
        romanNumber = new StringBuilder();
        int remaining = number;

        int i = 0;
        while (i < ROMAN_VALUES.length) {
            while (remaining >= ROMAN_VALUES[i]) {
                romanNumber.append(ROMAN_SYMBOLS[i]);
                remaining -= ROMAN_VALUES[i];
            }
            i++;
        }

        return romanNumber.toString();
    }
}