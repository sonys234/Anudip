package com.bookvault.service.impl;

import com.bookvault.dao.BookDAO;
import com.bookvault.dao.impl.BookDAOImpl;
import com.bookvault.dto.BookDTO;
import com.bookvault.service.BookService;

public class BookServiceImpl implements BookService {
    
    // The Service layer talks to the DAO layer
    private BookDAO bookDAO = new BookDAOImpl();

    @Override
    public boolean addBook(BookDTO book) {
        
        if (book.getRating() < 0.0 || book.getRating() > 5.0) {
            book.setRating(0.0); 
        }
        
        // Once business rules are applied, hand it off to the Database!
        return bookDAO.insertBookToDB(book);
    }
}