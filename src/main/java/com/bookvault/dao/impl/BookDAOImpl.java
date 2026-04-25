package com.bookvault.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.bookvault.dao.BookDAO;
import com.bookvault.dto.BookDTO;
import com.bookvault.utility.DbConnection;

public class BookDAOImpl implements BookDAO {

    @Override
    public boolean insertBookToDB(BookDTO book) {
        boolean isInserted = false;
        
        // Match the columns from your master SQL blueprint
        String sql = "INSERT INTO Books (Title, Author, Description, Genre, Price, Quantity, Rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDescription());
            ps.setString(4, book.getGenre());
            ps.setDouble(5, book.getPrice());
            ps.setInt(6, book.getQuantity());
            ps.setDouble(7, book.getRating());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                isInserted = true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return isInserted;
    }

    @Override
    
    public List<BookDTO> getAllBooks() {
        List<BookDTO> bookList = new java.util.ArrayList<>();
        String sql = "SELECT * FROM Books";
        
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                BookDTO book = new BookDTO();
                
                // CRITICAL: Fetch the ID so the Admin can use it for Updates/Deletes
                book.setBookId(rs.getInt("Book_id")); 
                
                book.setTitle(rs.getString("Title"));
                book.setAuthor(rs.getString("Author"));
                book.setPrice(rs.getDouble("Price"));
                book.setQuantity(rs.getInt("Quantity"));
                book.setGenre(rs.getString("Genre"));
                book.setRating(rs.getDouble("Rating"));
                
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }
    
    @Override
    public boolean updateBookPrice(int bookId, double newPrice) {
        String sql = "UPDATE Books SET Price = ? WHERE Book_id = ?";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newPrice);
            ps.setInt(2, bookId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM Books WHERE Book_id = ?";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    
    @Override
    public BookDTO getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE Book_id = ?";
        try (java.sql.Connection con = com.bookvault.utility.DbConnection.establishConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, bookId);
            java.sql.ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                BookDTO book = new BookDTO();
                book.setBookId(rs.getInt("Book_id"));
                book.setTitle(rs.getString("Title"));
                book.setAuthor(rs.getString("Author"));
                book.setGenre(rs.getString("Genre"));
                book.setDescription(rs.getString("Description"));
                book.setPrice(rs.getDouble("Price"));
                book.setQuantity(rs.getInt("Quantity"));
                book.setRating(rs.getDouble("Rating"));
                return book;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean reduceStock(int bookId, int qtyPurchased) {
        // This SQL directly subtracts the purchased amount from the current stock
        String sql = "UPDATE books SET Quantity = Quantity - ? WHERE Book_id = ?";
        try (Connection con = DbConnection.establishConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, qtyPurchased);
            ps.setInt(2, bookId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
    
    @Override
    public boolean addStock(int bookId, int qtyReturned) {
        String sql = "UPDATE books SET Quantity = Quantity + ? WHERE Book_id = ?";
        try (java.sql.Connection con = com.bookvault.utility.DbConnection.establishConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, qtyReturned);
            ps.setInt(2, bookId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}