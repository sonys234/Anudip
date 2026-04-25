package com.bookvault.service.impl;
import java.util.List;
import com.bookvault.dao.BookDAO;
import com.bookvault.dao.CartDAO;
import com.bookvault.dao.impl.BookDAOImpl;
import com.bookvault.dao.impl.CartDAOImpl;
import com.bookvault.dto.BookDTO;
import com.bookvault.dto.CartDTO;
import com.bookvault.service.CartService;

public class CartServiceImpl implements CartService {
    private CartDAO cartDAO = new CartDAOImpl();
    private BookDAO bookDAO = new BookDAOImpl();

    @Override
    public boolean addItem(CartDTO cartItem) {
        // BUSINESS LOGIC: Stock Check
        BookDTO book = bookDAO.getBookById(cartItem.getBookId());
        
        if (book == null) {
            System.out.println("❌ Error: Book ID does not exist.");
            return false;
        }
        if (cartItem.getQuantity() > book.getQuantity()) {
            System.out.println("❌ Error: Only " + book.getQuantity() + " copies of '" + book.getTitle() + "' are available.");
            return false;
        }
        return cartDAO.addToCart(cartItem);
    }

    @Override
    public List<CartDTO> viewCart(int userId) { return cartDAO.getCartByUserId(userId); }

    @Override
    public boolean removeItem(int cartId) { return cartDAO.removeFromCart(cartId); }

    @Override
    public boolean clearCart(int userId) { return cartDAO.clearCart(userId); }
}