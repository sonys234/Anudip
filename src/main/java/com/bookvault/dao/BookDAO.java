package com.bookvault.dao;

import java.util.List;

import com.bookvault.dto.BookDTO;

public interface BookDAO {
    boolean insertBookToDB(BookDTO book);
    List<BookDTO> getAllBooks();
    boolean updateBookPrice(int bookId, double newPrice);
    boolean deleteBook(int bookId);

    BookDTO getBookById(int bookId);
    

    boolean reduceStock(int bookId, int qtyPurchased);
    
 //  Restock books when an order is cancelled
    boolean addStock(int bookId, int qtyReturned);
    
} 