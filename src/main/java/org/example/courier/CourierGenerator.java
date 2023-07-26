package org.example.courier;

import java.util.Random;

public class CourierGenerator {

    private static final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final String[] NUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public String generateRandomString(int size, String[] source) {
        StringBuilder generatedString = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            generatedString.append(source[rand.nextInt(source.length)]);
        }
        return generatedString.toString();
    }

    public String generateRandomLogin(int size) {
        return generateRandomString(size, ALPHABET);
    }

    public String generateRandomPassword(int size) {
        return generateRandomString(size, NUMBERS);
    }

    public String generateRandomName(int size) {
        return generateRandomLogin(size);
    }

    public Courier generateRandomCourier() {
        String login = generateRandomLogin(6);
        String password = generateRandomPassword(6);
        String name = generateRandomName(6);
        return new Courier(login, password, name);
    }

}