package com.example.receiptprocessor.ServiceTests;

import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptServiceTest {

    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptService();
    }

    private Item createItem(String description, String price) {
        Item item = new Item();
        item.setShortDescription(description);
        item.setPrice(price);
        return item;
    }

    private Receipt createReceipt(String retailer, String date, String time, String total, List<Item> items) {
        Receipt receipt = new Receipt();
        receipt.setRetailer(retailer);
        receipt.setPurchaseDate(date);
        receipt.setPurchaseTime(time);
        receipt.setTotal(total);
        receipt.setItems(items);
        return receipt;
    }

    @Test
    void testProcessReceipt_ValidReceipt() {
        // Arrange: Create a valid receipt
        List<Item> items = new ArrayList<>();
        items.add(createItem("Mountain Dew 12PK", "6.49"));
        items.add(createItem("Emils Cheese Pizza", "12.25"));

        Receipt receipt = createReceipt("Target", "2022-01-01", "13:01", "18.74", items);

        // Act: Process receipt
        String receiptId = receiptService.processReceipt(receipt);

        // Assert: Validate that a receipt ID is generated
        assertNotNull(receiptId, "Receipt ID should not be null");
    }

    @Test
    void testGetPoints_ValidReceiptId() {
        // Arrange: Process a receipt to generate an ID
        List<Item> items = new ArrayList<>();
        items.add(createItem("Mountain Dew 12PK", "6.49"));
        items.add(createItem("Knorr Creamy Chicken", "1.26"));

        Receipt receipt = createReceipt("Target", "2022-01-01", "13:01", "7.75", items);

        String receiptId = receiptService.processReceipt(receipt);

        // Act: Retrieve points using the receipt ID
        Integer points = receiptService.getPoints(receiptId);

        // Assert: Validate points
        assertNotNull(points, "Points should not be null");
        assertTrue(points > 0, "Points should be greater than zero");
    }

    @Test
    void testGetPoints_InvalidReceiptId() {
        // Act: Try to fetch points for a non-existent receipt ID
        Integer points = receiptService.getPoints("invalid-id");

        // Assert: Validate that no points are returned
        assertNull(points, "Points should be null for an invalid receipt ID");
    }

    @Test
    void testProcessReceipt_EmptyItems() {
        // Arrange: Create a receipt with no items
        Receipt receipt = createReceipt("Target", "2022-01-01", "13:01", "0.00", new ArrayList<>());

        // Act: Process receipt
        String receiptId = receiptService.processReceipt(receipt);

        // Assert: Validate that a receipt ID is generated
        assertNotNull(receiptId, "Receipt ID should not be null for receipt with no items");
    }

    @Test
    void testProcessReceipt_NullFields() {
        // Arrange: Create a receipt with null fields
        Receipt receipt = new Receipt();
        receipt.setRetailer(null);
        receipt.setPurchaseDate(null);
        receipt.setPurchaseTime(null);
        receipt.setTotal(null);
        receipt.setItems(null);

        // Act & Assert: Ensure processing throws a NullPointerException or handles null gracefully
        assertThrows(NullPointerException.class, () -> receiptService.processReceipt(receipt),
                "Processing a receipt with null fields should throw NullPointerException");
    }
}
