package com.example.receiptprocessor.service;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.utils.PointsCalculator;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
public class ReceiptService {

    private final Map<String, Integer> receiptPoints = new ConcurrentHashMap<>();

    public String processReceipt(Receipt receipt) {
        int points = PointsCalculator.calculatePoints(receipt);
        String id = UUID.randomUUID().toString();
        receiptPoints.put(id, points);
        return id;
    }

    public Integer getPoints(String id) {
        return receiptPoints.get(id);
    }
}