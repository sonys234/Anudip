package com.bookvault.service.impl;

import java.util.List;

import com.bookvault.dao.BookDAO;
import com.bookvault.dao.OrderDAO;
import com.bookvault.dao.OrderItemDAO;
import com.bookvault.dao.impl.BookDAOImpl;
import com.bookvault.dao.impl.OrderDAOImpl;
import com.bookvault.dao.impl.OrderItemDAOImpl;
import com.bookvault.dto.OrderDTO;
import com.bookvault.dto.OrderItemDTO;
import com.bookvault.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    private BookDAO bookDAO = new BookDAOImpl(); // Needed for restocking

    @Override
    public int createOrder(OrderDTO order) { 
        return orderDAO.placeOrder(order); 
    }

    @Override
    public List<OrderDTO> getHistory(int userId) { 
        return orderDAO.getOrderHistory(userId); 
    }

    @Override
    public boolean cancelOrder(int orderId, int userId) { 
        // 1. Fetch the exact items from the receipt
        List<OrderItemDTO> itemsToRefund = orderItemDAO.getItemsByOrderId(orderId);
        
        // 2. Loop through and RESTOCK the inventory!
        for (OrderItemDTO item : itemsToRefund) {
            bookDAO.addStock(item.getBookId(), item.getQuantity());
        }
        
        // 3. Delete the order items to prevent Foreign Key constraint errors
        orderItemDAO.deleteItemsByOrderId(orderId);
        
        // 4. Finally, cancel the main order (this deletes payments and the order row)
        return orderDAO.cancelOrder(orderId, userId); 
    }
}