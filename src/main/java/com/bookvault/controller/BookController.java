package com.bookvault.controller;

import com.bookvault.dto.BookDTO;
import com.bookvault.service.BookService;
import com.bookvault.service.impl.BookServiceImpl;

public class BookController {
    
    // The Controller layer talks to the Service layer
    private BookService bookService = new BookServiceImpl();

    public boolean insertBook(BookDTO book) {
        // --- BASIC VALIDATION GOES HERE ---
        // We reject bad data before it ever reaches the business logic
        
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            System.out.println("⚠️ Validation Error: Book title cannot be empty.");
            return false;
        }
        
        if (book.getPrice() <= 0) {
            System.out.println("⚠️ Validation Error: Book price must be greater than zero.");
            return false;
        }
        
        if (book.getQuantity() < 0) {
            System.out.println("⚠️ Validation Error: Initial stock quantity cannot be negative.");
            return false;
        }

        // If validation passes, forward to the Service Layer
        System.out.println("✅ Validation passed. Forwarding to Service layer...");
        return bookService.addBook(book);
    }
}