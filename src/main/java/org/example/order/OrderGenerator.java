package org.example.order;

import org.example.order.Order;

import java.time.LocalDate;
import java.util.Random;

public class OrderGenerator {
    private final Random random = new Random();

    public Order generateRandomOrder() {
        String firstName = generateRandomString(5, 10);
        String lastName = generateRandomString(5, 10);
        String address = generateRandomString(10, 20);
        String metroStation = generateRandomMetroStation();
        String phone = generateRandomPhone();
        int rentTime = random.nextInt(7) + 1;
        LocalDate deliveryDate = generateRandomDeliveryDate();
        String comment = generateRandomString(10, 30);
        String[] colors = generateRandomColors();

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);
    }

    public int generateRandomTrack() {
        return random.nextInt(Integer.MAX_VALUE);
    }
    private String generateRandomString(int minLength, int maxLength) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    private String generateRandomMetroStation() {
        String[] stations = {"Бульвар Рокоссовского", "Черкизовская", "Калужская", "Сокольники", "Преображенская площадь"};
        int index = random.nextInt(stations.length);
        return stations[index];
    }

    private String generateRandomPhone() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private LocalDate generateRandomDeliveryDate() {
        LocalDate currentDate = LocalDate.now();
        int daysToAdd = random.nextInt(30) + 1;
        return currentDate.plusDays(daysToAdd);
    }

    private String[] generateRandomColors() {
        String[] colors = {"BLACK", "GREY", "WHITE", "BLUE", "GREEN", "RED", "YELLOW"};
        int numColors = random.nextInt(colors.length) + 1;
        String[] randomColors = new String[numColors];
        for (int i = 0; i < numColors; i++) {
            int index = random.nextInt(colors.length);
            randomColors[i] = colors[index];
        }
        return randomColors;
    }
}