package com.example.receiptprocessor.utils;

import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.model.Receipt;

public class PointsCalculator {

    public static int calculatePoints(Receipt receipt) {
        int points = 0;

        // 1. Alphanumeric characters in retailer name
        points += receipt.getRetailer().replaceAll("[^A-Za-z0-9]", "").length();

        // 2. Round dollar amount
        if (receipt.getTotal().endsWith(".00")) {
            points += 50;
        }

        // 3. Multiple of 0.25
        if (Double.parseDouble(receipt.getTotal()) % 0.25 == 0) {
            points += 25;
        }

        // 4. Every two items
        points += (receipt.getItems().size() / 2) * 5;

        // 5. Item description multiples of 3
        for (Item item : receipt.getItems()) {
            int trimmedLength = item.getShortDescription().trim().length();
            if (trimmedLength % 3 == 0) {
                points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }

        // 6. Odd day of purchase
        if (Integer.parseInt(receipt.getPurchaseDate().split("-")[2]) % 2 != 0) {
            points += 6;
        }

        // 7. Time between 2:00pm and 4:00pm
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        if (hour == 14 || (hour == 15 && minute < 60)) {
            points += 10;
        }

        return points;
    }
}