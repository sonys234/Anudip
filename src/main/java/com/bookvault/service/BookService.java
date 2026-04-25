package com.bookvault.service;

import com.bookvault.dto.BookDTO;

public interface BookService {
    boolean addBook(BookDTO book);
}